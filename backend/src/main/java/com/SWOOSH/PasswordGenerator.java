package com.SWOOSH;

import java.util.Random;

public class PasswordGenerator {
    private static final int lengthPassword = 10;
    private static final String[] charCategories = new String[] {
            "abcdefghijklmnopqrstuvwxyz",
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "0123456789"
    };

    public static String generate() {
        StringBuilder password = new StringBuilder(lengthPassword);
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < lengthPassword; i++) {
            String charCategory = charCategories[random.nextInt(charCategories.length)];
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }

        return new String(password);
    }
}
