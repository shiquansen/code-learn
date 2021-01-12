package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamAvg {


    public static void main(String[] args) {
//        List<Integer> scores = Arrays.asList(99, 89,23,78,66, 100, 32);
        List<Integer> scores = Arrays.asList(100,50);

        OptionalDouble average = scores.stream().mapToDouble(it -> Double.parseDouble(it.toString()))
                .average();
        System.out.println(average.getAsDouble());

    }
}
