package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamSkip {


    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(99, 89,23,56,66, 99, 32);
        long count = scores.stream()
                .skip(3)
                .filter(it -> it >= 60)
                .count();

        System.out.println(count);

    }
}
