#!/bin/bash

# Simple script to find all remaining old package name references
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

# Function to search and display results
search_pattern() {
    local pattern="$1"
    local description="$2"
    
    echo -e "${BLUE}Searching for: ${description}${NC}"
    echo "Pattern: $pattern"
    echo "----------------------------------------"
    
    # Find files containing the pattern
    local files_found=$(find . -type f \( -name "*.java" -o -name "*.cpp" -o -name "*.h" -o -name "*.xml" -o -name "*.gradle" -o -name "*.pro" -o -name "*.properties" -o -name "*.json" -o -name "*.kt" \) -exec grep -l "$pattern" {} \; 2>/dev/null)
    
    if [ -z "$files_found" ]; then
        echo -e "${GREEN}✓ No issues found${NC}"
    else
        echo -e "${RED}Found issues in the following files:${NC}"
        echo "$files_found" | while read -r file; do
            if [ -n "$file" ]; then
                echo -e "${RED}  $file${NC}"
                # Show first few occurrences with line numbers
                grep -n "$pattern" "$file" 2>/dev/null | head -3 | while read -r line; do
                    echo "    $line"
                done
                local count=$(grep -c "$pattern" "$file" 2>/dev/null || echo "0")
                if [ "$count" -gt 3 ]; then
                    echo "    ... and $((count - 3)) more occurrence(s)"
                fi
                echo ""
            fi
        done
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

echo -e "${YELLOW}10. Searching for any remaining 'org.telegram' references...${NC}"
search_pattern "org\.telegram" "Any remaining org.telegram references"

echo "=========================================="
echo -e "${BLUE}SUMMARY${NC}"
echo "=========================================="
echo -e "${GREEN}Search completed. Check the results above for any remaining issues.${NC}"
echo ""
echo -e "${BLUE}Quick fix suggestions:${NC}"
echo "1. For Java files: Update package declarations and imports"
echo "2. For C++ files: Update JNI class paths"
echo "3. For XML files: Update android:name attributes"
echo "4. For Gradle files: Update namespace and applicationId"
echo "5. For ProGuard files: Update -keep class rules"
echo ""
echo -e "${BLUE}Script completed.${NC}"
