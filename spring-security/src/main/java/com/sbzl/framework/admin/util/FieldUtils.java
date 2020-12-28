package com.sbzl.framework.admin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FieldUtils {

    public static String[] test(Class className) {
        Field[] fields = className.getDeclaredFields();
        List<String> stringList = new ArrayList<String>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getType().toString().endsWith("java.lang.String") && Modifier.isStatic(field.getModifiers())) {
                    String s = (String)field.get(className);
                    stringList.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList.toArray(new String[stringList.size()]);
    }

}
