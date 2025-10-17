#!/bin/bash

# Script to fix all beta-related references in the codebase
# This script will replace DEBUG_PRIVATE_VERSION with DEBUG_VERSION or remove conditional logic

echo "Starting beta reference cleanup..."

# Find all Java files that contain DEBUG_PRIVATE_VERSION
find TMessagesProj/src/main/java -name "*.java" -exec grep -l "DEBUG_PRIVATE_VERSION" {} \; | while read file; do
    echo "Processing: $file"
    
    # Replace DEBUG_PRIVATE_VERSION with DEBUG_VERSION
    sed -i '' 's/BuildVars\.DEBUG_PRIVATE_VERSION/BuildVars.DEBUG_VERSION/g' "$file"
    
    # Remove standalone build checks
    sed -i '' 's/ApplicationLoader\.isStandaloneBuild()/false/g' "$file"
    sed -i '' 's/ApplicationLoader\.isHuaweiStoreBuild()/false/g' "$file"
    
    # Simplify conditional logic where DEBUG_PRIVATE_VERSION was used
    # This is a basic replacement - more complex cases may need manual review
    sed -i '' 's/|| BuildVars\.DEBUG_PRIVATE_VERSION//g' "$file"
    sed -i '' 's/&& BuildVars\.DEBUG_PRIVATE_VERSION//g' "$file"
    sed -i '' 's/BuildVars\.DEBUG_PRIVATE_VERSION &&//g' "$file"
    sed -i '' 's/BuildVars\.DEBUG_PRIVATE_VERSION ||//g' "$file"
    
    # Clean up double spaces and operators
    sed -i '' 's/  / /g' "$file"
    sed -i '' 's/|| ||/||/g' "$file"
    sed -i '' 's/&& &&/&&/g' "$file"
    sed -i '' 's/|| &&/||/g' "$file"
    sed -i '' 's/&& ||/&&/g' "$file"
done

echo "Beta reference cleanup completed!"
echo "Please review the changes and test the build."
