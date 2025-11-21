#!/bin/bash
set -e

echo "=== Attempting to download previous PIT scores ==="

WORKFLOW="${1:-}"
BRANCH="${2:-}"
LIMIT="${3:-5}"

if [ -z "$WORKFLOW" ] || [ -z "$BRANCH" ]; then
  echo "Usage: $0 <workflow-name> <branch-name> [limit]"
  exit 1
fi

DOWNLOADED=false

gh run list \
  --workflow="$WORKFLOW" \
  --branch="$BRANCH" \
  --status=success \
  --limit="$LIMIT" \
  --json databaseId \
  --jq '.[].databaseId' | while read -r run_id; do


  echo "Checking run #$run_id for artifacts..."

  if gh run download "$run_id" \
    --name pit-scores-baseline \
    --dir . 2>/dev/null; then
    echo "Successfully downloaded scores from run #$run_id"
    DOWNLOADED=true
    break
  fi
done

if [ "$DOWNLOADED" = "false" ]; then
  echo "No previous scores found"
fi

echo ""
echo "=== Previous scores status ==="
if ls pit-score-*.txt 1> /dev/null 2>&1; then
  echo "Found previous scores:"
  ls -la pit-score-*.txt
else
  echo "No previous scores"
fi
