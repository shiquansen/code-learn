package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamSort {


    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(99, 89,23,78,66, 99, 32);
        // 升序
//        List<Integer> collect = scores.stream().sorted().collect(Collectors.toList());
//
//        System.out.println(collect);


        // 降序
        List<Integer> collect = scores.stream().sorted((x, y) -> y - x).collect(Collectors.toList());

        System.out.println(collect);


    }
}
