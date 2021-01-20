package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamCount {


    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(13, 25, 66, 99, 32);
        long count = scores.stream().filter(it -> it >= 60)
                .count();

        System.out.println(count);

    }
}
