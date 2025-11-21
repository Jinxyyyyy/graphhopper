#!/bin/bash
set -e

EXCLUDED_MODULES="${EXCLUDED_MODULES:-core|reader-gtfs|web-bundle}"

echo "No specific modules provided. Running PIT on all modules..."

# Récupère la liste des modules Maven à la racine
MODULES=$(ls -d */pom.xml 2>/dev/null \
  | cut -d'/' -f1 \
  | grep -Ev "^($EXCLUDED_MODULES)$" \
  | tr '\n' ',' \
  | sed 's/,$//')

if [ -z "$MODULES" ]; then
  echo "No modules to test. Exiting."
  exit 0
fi

echo "Running PIT on modules: $MODULES"
echo "Starting PIT mutation testing..."

mvn -B org.pitest:pitest-maven:mutationCoverage \
  -pl "$MODULES" \
  -DtargetClasses='com.graphhopper.*' \
  -DtargetTests='com.graphhopper.*Test' \
  -DreportsDirectory=target/pit-reports \
  -DoutputFormats=XML,HTML \
  -DtimestampedReports=false \
  -Dthreads=2 \
  -DjvmArgs='-Xmx6G' \
  -DtimeoutConstant=60000 \
  -DtimeoutFactor=2 \
  -DdependencyDistance=0 \
  -DfailWhenNoMutations=false

echo "PIT mutation testing completed"
