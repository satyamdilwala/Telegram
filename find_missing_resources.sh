#!/bin/bash

echo "=== Finding Missing Raw Resources ==="
echo

# Extract the R class to see what raw resources are actually included
echo "1. Extracting R class to see included raw resources..."
cd /tmp
javap -cp . tglive.fqrs.app.R\$raw > /tmp/included_raw_resources.txt

echo "2. Finding all R.raw references in Java files..."
cd /Users/satyamdilwala/Documents/Projects/Telegram
grep -r "R\.raw\." TMessagesProj/src/main/java/ | grep -v "//" | sed 's/.*R\.raw\.\([a-zA-Z_][a-zA-Z0-9_]*\).*/\1/' | sort | uniq > /tmp/referenced_raw_resources.txt

echo "3. Finding all raw resource files..."
find TMessagesProj/src/main/res/raw/ -name "*.json" -o -name "*.glsl" | sed 's/.*\///' | sed 's/\.[^.]*$//' | sort | uniq > /tmp/available_raw_resources.txt

echo "4. Comparing to find missing resources..."
echo "Referenced but not included in R class:"
comm -23 /tmp/referenced_raw_resources.txt <(grep "public static final int" /tmp/included_raw_resources.txt | sed 's/.*int \([a-zA-Z_][a-zA-Z0-9_]*\);.*/\1/' | sort)

echo
echo "Available files but not included in R class:"
comm -23 /tmp/available_raw_resources.txt <(grep "public static final int" /tmp/included_raw_resources.txt | sed 's/.*int \([a-zA-Z_][a-zA-Z0-9_]*\);.*/\1/' | sort)

echo
echo "=== Summary ==="
echo "Total referenced resources: $(wc -l < /tmp/referenced_raw_resources.txt)"
echo "Total available files: $(wc -l < /tmp/available_raw_resources.txt)"
echo "Total included in R class: $(grep "public static final int" /tmp/included_raw_resources.txt | wc -l)"
