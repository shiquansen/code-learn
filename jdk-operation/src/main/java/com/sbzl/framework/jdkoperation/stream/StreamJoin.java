package com.sbzl.framework.jdkoperation.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamJoin {

    public static void main(String[] args) {

        List<String> l = Arrays.asList("james","wade","bosh");
        String collect = l.stream().collect(Collectors.joining("~my dog~"));
        System.out.println(collect);

    }
}
