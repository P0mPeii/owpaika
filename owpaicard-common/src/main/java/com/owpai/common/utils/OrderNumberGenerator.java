package com.owpai.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderNumberGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_LENGTH = 10;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
    private static final Random random = new Random();

    public static String generateOrderNumber() {
        // 使用时间戳作为前缀
        String timestamp = LocalDateTime.now().format(formatter);

        // 生成随机字符串
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomStr.append(CHARACTERS.charAt(index));
        }

        // 组合订单号
        return randomStr.toString();
    }
}