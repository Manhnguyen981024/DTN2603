package com.dtn2603.utils;

import java.util.Objects;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public static boolean isValidLength(String text, int minLength, int maxLength) {
        if (Objects.isNull(text)) {
            return false;
        }
        return text.trim().length() >= minLength && text.length() <= maxLength;
    }
}
