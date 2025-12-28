#!/bin/bash

# Script cài đặt Android iOS Emulator extension phiên bản cụ thể từ file .vsix

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}=== Cài đặt Android iOS Emulator Extension ===${NC}\n"

# Kiểm tra xem có file .vsix không
if [ -n "$1" ]; then
    VSIX_FILE="$1"
else
    echo -e "${YELLOW}Nhập đường dẫn đến file .vsix (hoặc kéo thả file vào đây):${NC}"
    read -p "File .vsix: " VSIX_FILE
fi

# Kiểm tra file có tồn tại không
if [ ! -f "$VSIX_FILE" ]; then
    echo -e "${RED}❌ Không tìm thấy file: $VSIX_FILE${NC}"
    echo -e "${YELLOW}Vui lòng tải file .vsix của extension từ:${NC}"
    echo "  - GitHub releases của extension"
    echo "  - VSIXHub: https://www.vsixhub.com/"
    echo "  - Hoặc từ nhà phát triển"
    exit 1
fi

# Kiểm tra xem có phải file .vsix không
if [[ ! "$VSIX_FILE" =~ \.vsix$ ]]; then
    echo -e "${RED}❌ File không phải là file .vsix${NC}"
    exit 1
fi

echo -e "${YELLOW}📦 File: $VSIX_FILE${NC}\n"

# Tìm đường dẫn đến Cursor
CURSOR_CMD=""

# Thử các đường dẫn phổ biến của Cursor trên macOS
if [ -f "/Applications/Cursor.app/Contents/Resources/app/bin/cursor" ]; then
    CURSOR_CMD="/Applications/Cursor.app/Contents/Resources/app/bin/cursor"
elif [ -f "/usr/local/bin/cursor" ]; then
    CURSOR_CMD="/usr/local/bin/cursor"
elif command -v cursor &> /dev/null; then
    CURSOR_CMD="cursor"
else
    echo -e "${YELLOW}⚠️  Không tìm thấy lệnh 'cursor' trong PATH${NC}"
    echo -e "${YELLOW}Vui lòng cài đặt extension thủ công:${NC}"
    echo ""
    echo "1. Mở Cursor"
    echo "2. Nhấn Cmd + Shift + P"
    echo "3. Gõ: 'Extensions: Install from VSIX...'"
    echo "4. Chọn file: $VSIX_FILE"
    exit 0
fi

echo -e "${GREEN}🔧 Đang cài đặt extension...${NC}"

# Cài đặt extension
$CURSOR_CMD --install-extension "$VSIX_FILE"

if [ $? -eq 0 ]; then
    echo -e "\n${GREEN}✅ Extension đã được cài đặt thành công!${NC}"
    echo -e "\n${YELLOW}Để tắt tự động cập nhật:${NC}"
    echo "1. Mở Extensions (Cmd + Shift + X)"
    echo "2. Tìm 'Android iOS Emulator'"
    echo "3. Click biểu tượng bánh răng → 'Disable Auto Updating'"
else
    echo -e "\n${RED}❌ Lỗi khi cài đặt extension${NC}"
    echo -e "${YELLOW}Thử cài đặt thủ công qua Command Palette${NC}"
    exit 1
fi



