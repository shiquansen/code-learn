package com.sbzl.framework.jdkoperation.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamDistinct {


    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(99, 99,23,23,66, 66, 32);

        List<Integer> collect = scores.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);


    }
}
