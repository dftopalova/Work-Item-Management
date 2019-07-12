package com.KSDT.models.common;

public class HistoryHelper {

    public static <E> String collectChange(E objBefore, E objAfter) {
        StringBuilder strBuilder = new StringBuilder();
        String objClassName = objBefore.getClass().getSimpleName().replace("Type", "");

        strBuilder.append(objClassName);
        strBuilder.append(" from " + objBefore.toString());
        strBuilder.append(" to " + objAfter.toString());
        return strBuilder.toString();
    }

    public static <Number> String collectChange(Number objBefore, Number objAfter, String objectToChangeName) {
        StringBuilder strBuilder = new StringBuilder();
        Character.toUpperCase(objectToChangeName.charAt(0));

        strBuilder.append(objectToChangeName);
        strBuilder.append(" from " + objBefore.toString());
        strBuilder.append(" to " + objAfter.toString());
        return strBuilder.toString();
    }


}
