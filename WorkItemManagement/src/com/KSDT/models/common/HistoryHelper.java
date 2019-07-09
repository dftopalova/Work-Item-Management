package com.KSDT.models.common;

public class HistoryHelper {

    public static  <E> String collectChange (E objBefore, E objAfter ) {
        StringBuilder strBuilder = new StringBuilder();
        String objClassName = objBefore.getClass().getSimpleName(); // TODO fix the logic 
        strBuilder.append(objClassName, 0, objClassName.length()-4);

        strBuilder.append(" from " + objBefore.toString());
        strBuilder.append(" to " + objAfter.toString());
        return strBuilder.toString();
    }

}
