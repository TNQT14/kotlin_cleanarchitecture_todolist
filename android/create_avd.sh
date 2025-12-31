#!/bin/bash

# Script tạo Android Virtual Device (AVD) từ Cursor terminal
# SDK path được lấy từ local.properties

SDK_PATH="/Users/quangthai/Library/Android/sdk"
AVD_MANAGER="$SDK_PATH/cmdline-tools/latest/bin/avdmanager"
SDK_MANAGER="$SDK_PATH/cmdline-tools/latest/bin/sdkmanager"

# Màu sắc cho output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Tạo Android Virtual Device (AVD) ===${NC}\n"

# Kiểm tra SDK path
if [ ! -d "$SDK_PATH" ]; then
    echo -e "${RED}❌ Không tìm thấy Android SDK tại: $SDK_PATH${NC}"
    echo "Vui lòng kiểm tra lại đường dẫn SDK trong local.properties"
    exit 1
fi

# Kiểm tra avdmanager
if [ ! -f "$AVD_MANAGER" ]; then
    echo -e "${RED}❌ Không tìm thấy avdmanager tại: $AVD_MANAGER${NC}"
    exit 1
fi

# Liệt kê các system images có sẵn
echo -e "${YELLOW}📱 Đang kiểm tra các system images có sẵn...${NC}\n"
$SDK_MANAGER --list | grep "system-images" | grep "android-3[3-6]" | head -10

echo -e "\n${YELLOW}Chọn API level (33, 34, 35, hoặc 36 - khuyến nghị: 33):${NC}"
read -p "API Level: " API_LEVEL

# Validate API level
if [[ ! "$API_LEVEL" =~ ^(33|34|35|36)$ ]]; then
    echo -e "${RED}❌ API level không hợp lệ. Phải là 33, 34, 35, hoặc 36${NC}"
    exit 1
fi

# Chọn architecture
echo -e "\n${YELLOW}Chọn architecture:${NC}"
echo "1. x86_64 (Intel/AMD - nhanh hơn)"
echo "2. arm64-v8a (Apple Silicon - khuyến nghị cho Mac M1/M2/M3)"
read -p "Chọn (1 hoặc 2): " ARCH_CHOICE

if [ "$ARCH_CHOICE" = "2" ]; then
    ARCH="arm64-v8a"
    SYSTEM_IMAGE="system-images;android-$API_LEVEL;google_apis;$ARCH"
else
    ARCH="x86_64"
    SYSTEM_IMAGE="system-images;android-$API_LEVEL;google_apis;$ARCH"
fi

# Kiểm tra system image đã được cài đặt chưa
echo -e "\n${YELLOW}📥 Đang kiểm tra system image: $SYSTEM_IMAGE${NC}"
if ! $SDK_MANAGER --list_installed | grep -q "$SYSTEM_IMAGE"; then
    echo -e "${YELLOW}System image chưa được cài đặt. Đang tải...${NC}"
    $SDK_MANAGER "$SYSTEM_IMAGE"
    if [ $? -ne 0 ]; then
        echo -e "${RED}❌ Lỗi khi tải system image${NC}"
        exit 1
    fi
else
    echo -e "${GREEN}✓ System image đã được cài đặt${NC}"
fi

# Nhập tên AVD
echo -e "\n${YELLOW}Nhập tên cho AVD (ví dụ: Pixel_5_API_33):${NC}"
read -p "Tên AVD: " AVD_NAME

if [ -z "$AVD_NAME" ]; then
    AVD_NAME="Pixel_5_API_$API_LEVEL"
    echo -e "${YELLOW}Sử dụng tên mặc định: $AVD_NAME${NC}"
fi

# Liệt kê các device definitions có sẵn
echo -e "\n${YELLOW}📱 Các device definitions có sẵn:${NC}"
$AVD_MANAGER list device | grep "id:" | head -10

echo -e "\n${YELLOW}Nhập device ID (ví dụ: pixel_5, pixel_6, hoặc để trống cho pixel_5):${NC}"
read -p "Device ID: " DEVICE_ID

if [ -z "$DEVICE_ID" ]; then
    DEVICE_ID="pixel_5"
fi

# Tạo AVD
echo -e "\n${YELLOW}🔨 Đang tạo AVD: $AVD_NAME${NC}"
$AVD_MANAGER create avd \
    -n "$AVD_NAME" \
    -k "$SYSTEM_IMAGE" \
    -d "$DEVICE_ID"

if [ $? -eq 0 ]; then
    echo -e "\n${GREEN}✅ AVD đã được tạo thành công!${NC}"
    echo -e "\n${YELLOW}Để khởi động emulator, chạy lệnh:${NC}"
    echo "$SDK_PATH/emulator/emulator -avd $AVD_NAME"
    echo -e "\n${YELLOW}Hoặc sử dụng Android Studio: Tools → Device Manager${NC}"
else
    echo -e "\n${RED}❌ Lỗi khi tạo AVD${NC}"
    exit 1
fi

