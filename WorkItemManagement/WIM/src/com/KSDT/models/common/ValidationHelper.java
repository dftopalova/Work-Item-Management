package com.KSDT.models.common;

import java.util.List;

public class ValidationHelper {
    private static final String INCORRECT_LENGTH_MESSAGE = "Text length is incorrect.";
    private static final String EXACT_SYMBOLS_LENGTH_MESSAGE = "Text length cannot be less than %d symbols.";
    private static final String NULL_OBJECT_MESSAGE = "Object cannot be null.";
    private static final String NEGATIVE_NUMBER_MESSAGE = "Number cannot be negative";
    private static final String EMPTY_STRING_MESSAGE = "String cannot be empty.";


    public static <E> void equalityCheck(E obj1, E obj2, String exception) {
        if (obj1.equals(obj2))
            throw new IllegalArgumentException(exception);
    }


    public static <E> void nullCheck(E obj) {
        if (obj == null) {
            throw new IllegalArgumentException(NULL_OBJECT_MESSAGE);
        }
    }

    public static void digitCheck(String string, String exception) {
        for (Character chars : string.toCharArray()) {
            if (!Character.isDigit(chars))
                throw new IllegalArgumentException(exception);
        }
    }

    public static void lengthCheck(String str, int min) {
        if (str.length() < min) {
            throw new IllegalArgumentException(String.format(EXACT_SYMBOLS_LENGTH_MESSAGE, min));
        }
    }

    public static void lengthExactCheck(String str, int exactSymbols, String exception) {
        if (str.length() != exactSymbols) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static void lengthCheck(String str, int min, int max) {
        if (str.length() < min || str.length() > max) {
            throw new IllegalArgumentException(INCORRECT_LENGTH_MESSAGE);
        }
    }

    public static void lengthCheck(String str, int min, int max, String exception) {
        if (str.length() < min || str.length() > max) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static void emptyStringCheck(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_STRING_MESSAGE);
        }
    }

    public static void nullCheck(List<String> strings) {
        if (strings == null)
            throw new IllegalArgumentException(NULL_OBJECT_MESSAGE);
    }

    public static void validNumberCheck(Number number, Number min, Number max, String exception) {
        if (number.doubleValue() < min.doubleValue() || number.doubleValue() > max.doubleValue())
            throw new IllegalArgumentException(exception);
    }

    public static void positiveCheck(Number number) {
        if (number.doubleValue() < 0)
            throw new IllegalArgumentException(NEGATIVE_NUMBER_MESSAGE);

    }
}