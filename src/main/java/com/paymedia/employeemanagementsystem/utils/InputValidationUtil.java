package com.paymedia.employeemanagementsystem.utils;

import java.util.regex.Pattern;

public class InputValidationUtil {

    public static boolean isEmailValid(String email) throws IllegalArgumentException{
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(pattern.matcher(email).matches()){
            return true;
        }
        throw new IllegalArgumentException("Invalid email format");
    }

    public static boolean isNotBlank(String field, String fieldName) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return true;
    }
}
