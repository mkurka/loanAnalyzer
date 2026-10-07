package mk.LoanAnalyzer.util;


import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 *
 * @author fja
 */
public class StringUtil {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String EMPTY_STRING = "";


    private StringUtil() {
        /* No instances */
    }


    public static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        return (EMPTY_STRING.equals(s)) ? null : s;
    }
    
    public static boolean isEmptyOrNull(String str) {
        return (str == null || EMPTY_STRING.equals(str));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmptyOrNull(str);
    }

    public static String nullSafeString(String str) {
        return str == null ? EMPTY_STRING : str;
    }

    public static String nullSafeToString(Object o) {
        if (o == null) return null;
        return o.toString();
    }

    public static String nullSafeToString(Integer i) {
        if (i == null) return null;
        return Integer.toString(i);
    }

    public static Integer nullSafeToInteger(String s) {
        if (s == null) return null;
        return Integer.valueOf(s);
    }

    public static Integer safeInteger(String s) {
        try {
            return nullSafeToInteger(StringUtil.trimToNull(s));
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static String nullSafeToString(Long value) {
        return value == null ? null : Long.toString(value);
    }

    public static Long nullSafeToLong(String s) {
        if (s == null) return null;
        return Long.valueOf(s);
    }

    public static Long safeToLong(String s) {
        try {
            return nullSafeToLong(StringUtil.trimToNull(s));
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * @param str The string to analyze.
     * @return True if string contains just digit characters, eg. for 011223. False eg. for 1a215.
     */
    public static boolean isJustDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String generateRandomString(int length) {
        int n = ALPHABET.length();
        StringBuilder sb = new StringBuilder(length);
        SecureRandom r = new SecureRandom();

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(r.nextInt(n)));
        }
        return sb.toString();
    }

    public static String[] getPersonNames(String nameSurname) {
        if (nameSurname == null) {
            return new String[]{null, null};
        }
        nameSurname = nameSurname.trim();
        int index = nameSurname.lastIndexOf(' ');
        if (index < 0) {
            return new String[]{null, nameSurname};
        }
        return new String[]{nameSurname.substring(0, index), nameSurname.substring(index+1)};
    }

    public static Integer safeGetPostalCode(String zip) {
        if (zip == null) {
            return null;
        }
        Integer postalCode = null;
        try {
            postalCode = Integer.parseInt(zip.replace(" ", "")); // Kill spaces
        } catch (NumberFormatException nfe) { /* NOP */ }
        return postalCode;
    }

    public static String cropToLength(String s, int length) {
        if (isEmptyOrNull(s) || s.length() <= length) {
            return s;
        }
        return s.substring(0, length);
    }

    public static String cropToLengthWithDots(String s, int length) {
        if (isEmptyOrNull(s) || s.length() <= length) {
            return s;
        }
        return s.substring(0, length-2).concat("..");
    }

    public static String cropLeft(String s, char ch) {
        if (isEmptyOrNull(s)) {
            return s;
        }
        int index = 0;
        while (s.charAt(index) == ch) {
            index++;
            if (index >= s.length() ) {
                break;
            }
        }
        return s.substring(index);
    }

    public static String[] splitBySlashes(String s) {
        if (s == null){
            return null;
        }
        return s.split("/");
    }

    public static boolean safeContains(String message, String contains) {
        if (isEmptyOrNull(message)) {
            return false;
        }
        return message.contains(contains);
    }

    public static String fillString(char ch, int length) {
        if (length <= 0) {
            return EMPTY_STRING;
        }
        char[] array = new char[length];
        Arrays.fill(array, ch);
        return new String(array);
    }

    public static String toUpperCaseFirst(String s) {
        return StringUtils.capitalize(s.toLowerCase());
    }
}
