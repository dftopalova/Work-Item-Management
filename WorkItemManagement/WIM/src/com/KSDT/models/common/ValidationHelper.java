package com.KSDT.models.common;

import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ValidationHelper {

    public static void statusTypeCheck(StatusType statusType, String workItemType) {
        String workItemStatusType = statusType.name().replaceAll("_.*","");
        if (!workItemStatusType.equalsIgnoreCase(workItemType)) {
            throw new IllegalArgumentException(INCOMPATIBLE_STATUSTYPE_AND_WORKITEMTYPE);
        }
    }

    public static <E> void equalityCheck(E obj1, E obj2, String exception) {
        if (obj1.equals(obj2))
            throw new IllegalArgumentException(exception);
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

    public static void positiveCheck(Number number) {
        if (number.doubleValue() < 0)
            throw new IllegalArgumentException(NEGATIVE_NUMBER_MESSAGE);
    }
}