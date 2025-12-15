package com.data.db_rikkeijobs.utils;

public class StringUtils {
    
    /**
     * Check if string is null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Return empty string if null, otherwise return the string itself
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }
    
    /**
     * Return null if string is empty, otherwise return the string itself
     */
    public static String emptyToNull(String str) {
        return isEmpty(str) ? null : str;
    }
    
    /**
     * Trim string, return null if result is empty
     */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }
        String trimmed = str.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

