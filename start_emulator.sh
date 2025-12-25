#!/bin/bash

# Script khởi động Android Emulator từ Cursor terminal

SDK_PATH="/Users/quangthai/Library/Android/sdk"
EMULATOR="$SDK_PATH/emulator/emulator"

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}=== Khởi động Android Emulator ===${NC}\n"

# Kiểm tra emulator
if [ ! -f "$EMULATOR" ]; then
    echo -e "${RED}❌ Không tìm thấy emulator tại: $EMULATOR${NC}"
    exit 1
fi

# Liệt kê các AVD có sẵn
echo -e "${YELLOW}📱 Các AVD có sẵn:${NC}"
AVD_LIST=$($EMULATOR -list-avds)

if [ -z "$AVD_LIST" ]; then
    echo -e "${RED}❌ Không tìm thấy AVD nào. Vui lòng tạo AVD trước bằng cách chạy: ./create_avd.sh${NC}"
    exit 1
fi

echo "$AVD_LIST"
echo ""

# Nếu có tham số, sử dụng AVD đó
if [ -n "$1" ]; then
    AVD_NAME="$1"
else
    # Đếm số AVD
    AVD_COUNT=$(echo "$AVD_LIST" | wc -l | tr -d ' ')
    
    if [ "$AVD_COUNT" -eq 1 ]; then
        AVD_NAME=$(echo "$AVD_LIST" | head -1)
        echo -e "${YELLOW}Sử dụng AVD duy nhất: $AVD_NAME${NC}"
    else
        echo -e "${YELLOW}Nhập tên AVD bạn muốn khởi động:${NC}"
        read -p "Tên AVD: " AVD_NAME
    fi
fi

# Kiểm tra AVD có tồn tại không
if ! echo "$AVD_LIST" | grep -q "^$AVD_NAME$"; then
    echo -e "${RED}❌ AVD '$AVD_NAME' không tồn tại${NC}"
    exit 1
fi

# Khởi động emulator
echo -e "\n${GREEN}🚀 Đang khởi động emulator: $AVD_NAME${NC}"
echo -e "${YELLOW}Lưu ý: Emulator sẽ mất vài phút để khởi động lần đầu${NC}\n"

$EMULATOR -avd "$AVD_NAME" &

echo -e "${GREEN}✅ Emulator đang khởi động...${NC}"
echo -e "${YELLOW}Đợi emulator khởi động hoàn toàn trước khi chạy ứng dụng${NC}"

