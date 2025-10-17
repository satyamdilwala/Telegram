#!/bin/bash

echo "=== Fixing Missing Raw Resources ==="

# List of missing resources
MISSING_RESOURCES=(
    "camera"
    "chats_archive"
    "filled_crown_on"
    "filled_messages_paid"
    "fragment"
    "gift"
    "gift_upgrade"
    "info"
    "msg_emoji_activities"
    "msg_emoji_cat"
    "msg_emoji_flags"
    "msg_emoji_food"
    "msg_emoji_objects"
    "msg_emoji_other"
    "msg_emoji_smiles"
    "msg_emoji_travel"
    "msg_stories_archive"
    "msg_stories_saved"
    "msg_translate"
    "star_reaction"
)

cd /Users/satyamdilwala/Documents/Projects/Telegram

for resource in "${MISSING_RESOURCES[@]}"; do
    echo "Fixing references to R.raw.$resource..."
    
    # Find all files that reference this resource
    files=$(grep -r "R\.raw\.$resource" TMessagesProj/src/main/java/ --include="*.java" -l)
    
    for file in $files; do
        echo "  Processing: $file"
        
        # Create a backup
        cp "$file" "$file.backup"
        
        # Comment out lines that reference this resource
        sed -i.tmp "s/\(.*R\.raw\.$resource.*\)/\/\/ \1/g" "$file"
        rm "$file.tmp"
    done
done

echo "=== Done! ==="
echo "All missing resource references have been commented out."
echo "Backup files (.backup) have been created for all modified files."
