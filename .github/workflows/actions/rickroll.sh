#!/usr/bin/env bash
set -e

MODE="$1"

echo ""
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║                                                           ║"
echo "║              YOU'VE BEEN RICKROLLED!                     ║"
echo "║                                                           ║"

if [ "$MODE" = "mutation" ]; then
  echo "║           MUTATION COVERAGE REGRESSION DETECTED          ║"
else
  echo "║                       TEST FAILURE                       ║"
fi

echo "║                                                           ║"
echo "║        Never gonna give you up on fixing this!           ║"
echo "║                                                           ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

echo "Never gonna give you up"
echo "Never gonna let you down"
echo "Never gonna run around and desert you"
echo "Never gonna make you cry"
echo "Never gonna say goodbye"
echo "Never gonna tell a lie and hurt you"
echo ""


