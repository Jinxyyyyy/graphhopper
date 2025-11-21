#!/bin/bash
set -e

CHANGED_FILES="${1:-}"

if [ -z "$CHANGED_FILES" ]; then
  echo "Usage: $0 <changed-files>"
  exit 1
fi

# Liste des modules testables
TESTABLE_MODULES="web-api|map-matching|tools|client-hc|navigation|example"

# Extraction des modules modifiés
MODULES=$(echo "$CHANGED_FILES" \
  | grep -E "^($TESTABLE_MODULES)" \
  | cut -d'/' -f1 \
  | sort -u \
  | tr '\n' ',' \
  | sed 's/,$//')

if [ -z "$MODULES" ]; then
  echo "No testable modules changed. Running full build."
  echo ""
else
  echo "Changed modules detected: $MODULES"
  echo "$MODULES"
fi
