package com.common.utils.otp;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class GenerateOTPCode {
    private static final String NUMBERS = "0123456789";

    public static String getOTPCode() {
        return randomString();
    }

    private static String randomString() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(NUMBERS.length());
            stringBuilder.append(NUMBERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}
