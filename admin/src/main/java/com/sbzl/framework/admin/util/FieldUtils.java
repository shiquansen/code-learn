package com.sbzl.framework.admin.util;

import com.sbzl.framework.admin.web.path.AdminPath;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main(String[] args) {
        Arrays.stream(test(AdminPath.class)).forEach(System.out::println);
    }

}
