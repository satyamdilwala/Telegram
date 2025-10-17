#!/bin/bash

# Script to find all remaining old package name references in the Telegram project
# This helps identify all issues at once instead of finding them during builds

echo "=========================================="
echo "Searching for old package name references"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Counter for total issues found
total_issues=0

# Function to search and display results
search_pattern() {
    local pattern="$1"
    local description="$2"
    local files_found=0
    
    echo -e "${BLUE}Searching for: ${description}${NC}"
    echo "Pattern: $pattern"
    echo "----------------------------------------"
    
    # Search for the pattern and count files
    while IFS= read -r -d '' file; do
        if [ -f "$file" ]; then
            # Count occurrences in this file
            count=$(grep -c "$pattern" "$file" 2>/dev/null || echo "0")
            if [ "$count" -gt 0 ]; then
                echo -e "${RED}Found $count occurrence(s) in: $file${NC}"
                # Show the actual lines with context
                grep -n "$pattern" "$file" 2>/dev/null | head -5 | while read -r line; do
                    echo "  $line"
                done
                if [ "$count" -gt 5 ]; then
                    echo "  ... and $((count - 5)) more occurrence(s)"
                fi
                echo ""
                files_found=$((files_found + 1))
                total_issues=$((total_issues + count))
            fi
        fi
    done < <(find . -type f \( -name "*.java" -o -name "*.cpp" -o -name "*.h" -o -name "*.xml" -o -name "*.gradle" -o -name "*.pro" -o -name "*.properties" -o -name "*.json" -o -name "*.kt" \) -print0)
    
    if [ "$files_found" -eq 0 ]; then
        echo -e "${GREEN}✓ No issues found${NC}"
    else
        echo -e "${YELLOW}Total files with issues: $files_found${NC}"
    fi
    echo ""
}

# Search patterns for old package names
echo -e "${YELLOW}1. Searching for 'org.telegram.messenger' references...${NC}"
search_pattern "org\.telegram\.messenger" "Old main package name"

echo -e "${YELLOW}2. Searching for 'org.telegram.tgnet' references...${NC}"
search_pattern "org\.telegram\.tgnet" "Old tgnet package name"

echo -e "${YELLOW}3. Searching for 'org.telegram.SQLite' references...${NC}"
search_pattern "org\.telegram\.SQLite" "Old SQLite package name"

echo -e "${YELLOW}4. Searching for 'org.telegram.ui' references...${NC}"
search_pattern "org\.telegram\.ui" "Old UI package name"

echo -e "${YELLOW}5. Searching for 'org.telegram.voip' references...${NC}"
search_pattern "org\.telegram\.voip" "Old VoIP package name"

echo -e "${YELLOW}6. Searching for 'org/telegram/messenger' (JNI format)...${NC}"
search_pattern "org/telegram/messenger" "Old main package in JNI format"

echo -e "${YELLOW}7. Searching for 'org/telegram/tgnet' (JNI format)...${NC}"
search_pattern "org/telegram/tgnet" "Old tgnet package in JNI format"

echo -e "${YELLOW}8. Searching for 'org/telegram/SQLite' (JNI format)...${NC}"
search_pattern "org/telegram/SQLite" "Old SQLite package in JNI format"

echo -e "${YELLOW}9. Searching for 'org/telegram/voip' (JNI format)...${NC}"
search_pattern "org/telegram/voip" "Old VoIP package in JNI format"

echo -e "${YELLOW}10. Searching for hardcoded 'org.telegram' in strings...${NC}"
search_pattern "org\.telegram" "Any remaining org.telegram references"

echo -e "${YELLOW}11. Searching for old application ID references...${NC}"
search_pattern "org\.telegram\.messenger" "Old application ID"

echo -e "${YELLOW}12. Searching for old package references in comments...${NC}"
search_pattern "//.*org\.telegram" "Old package in comments"

echo -e "${YELLOW}13. Searching for old package references in XML attributes...${NC}"
search_pattern "android:.*org\.telegram" "Old package in XML attributes"

echo -e "${YELLOW}14. Searching for old package references in import statements...${NC}"
search_pattern "import org\.telegram" "Old package in imports"

echo -e "${YELLOW}15. Searching for old package references in class names...${NC}"
search_pattern "class.*org\.telegram" "Old package in class declarations"

echo "=========================================="
echo -e "${BLUE}SUMMARY${NC}"
echo "=========================================="

if [ "$total_issues" -eq 0 ]; then
    echo -e "${GREEN}🎉 SUCCESS: No old package name references found!${NC}"
    echo -e "${GREEN}Your package renaming is complete.${NC}"
else
    echo -e "${RED}⚠️  WARNING: Found $total_issues total issues${NC}"
    echo -e "${YELLOW}Please fix these references before building.${NC}"
    echo ""
    echo -e "${BLUE}Quick fix suggestions:${NC}"
    echo "1. For Java files: Update package declarations and imports"
    echo "2. For C++ files: Update JNI class paths"
    echo "3. For XML files: Update android:name attributes"
    echo "4. For Gradle files: Update namespace and applicationId"
    echo "5. For ProGuard files: Update -keep class rules"
fi

echo ""
echo -e "${BLUE}Script completed.${NC}"
