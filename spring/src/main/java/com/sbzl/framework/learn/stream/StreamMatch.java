package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamMatch {

    public static void main(String[] args) {
        List<String> l = Arrays.asList("james","wade","bosh");

//        boolean b = l.stream().allMatch(it -> it.length() == 4);
//        System.out.println(b);

//        boolean b = l.stream().anyMatch(it -> it.length() == 4);
//        System.out.println(b);


        List<String> l2 = Arrays.asList("james","wades","boshs");
        boolean b = l2.stream().noneMatch(it -> it.length() == 4);
        System.out.println(b);

    }
}
