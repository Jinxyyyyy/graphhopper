#!/bin/bash
set -e

echo "=== Checking mutation score regression  ==="
echo ""

failed=false
report_file="mutation-report.md"

# Génération du rapport
cat > "$report_file" << EOF
## Mutation Coverage Report

| Module | Current | Previous | Change | Status |
|--------|---------|----------|--------|--------|
EOF

for file in $(find . -name "mutations.xml" | sort); do
  module=$(echo "$file" | sed 's|/target.*||' | sed 's|^\./||')

  # Vérification de la validité du fichier XML
  if ! xmllint --noout "$file" 2>/dev/null; then
    echo "WARNING: Invalid XML file for $module, skipping"
    continue
  fi

  # Compte les mutations
  total=$(xmllint --xpath "count(//mutation)" "$file" 2>/dev/null || echo "0")
  killed=$(xmllint --xpath "count(//mutation[@detected='true'])" "$file" 2>/dev/null || echo "0")

  if [ "$total" -eq 0 ]; then
    echo "$module: No mutations found (skipped)"
    continue
  fi

  # Calcul du score actuel
  coverage=$(echo "scale=2; 100 * $killed / $total" | bc -l)
  printf "%-20s %6.2f%% (%d/%d mutations killed)\n" "$module:" "$coverage" "$killed" "$total"

  # Vérification du score précédent
  old_file="pit-score-${module}.txt"
  if [ -f "$old_file" ]; then
    old=$(cat "$old_file")
    diff=$(echo "$coverage - $old" | bc -l)
    abs_diff=$(echo "$diff" | sed 's/-//')

    printf "   Previous: %.2f%% " "$old"

    # Détermination du statut
    if (( $(echo "$coverage > $old" | bc -l) )); then
      printf "(Improved: +%.2f%%)\n" "$diff"
      status="Improved"
    elif (( $(echo "$coverage < $old" | bc -l) )); then
      printf "(Regression: -%.2f%%)\n" "$abs_diff"
      status="Regression"
      failed=true
    else
      printf "(Unchanged)\n"
      status="Unchanged"
    fi


    # Ajout au rapport
    echo "| $module | ${coverage}% | ${old}% | ${diff}% | $status |" >> "$report_file"
  else
    echo "   (No previous score - baseline set)"
    echo "| $module | ${coverage}% | - | - | New baseline |" >> "$report_file"
  fi

  # Sauvegarde du nouveau score
  echo "$coverage" > "$old_file"
  echo ""
done

echo ""
if [ "$failed" = true ]; then
  echo "Build failed: one or more modules lost mutation coverage"
  echo "See mutation-report.md for details"
  cat "$report_file"
  exit 1
else
  echo "All modules maintained or improved mutation coverage."
  cat "$report_file"
fi
