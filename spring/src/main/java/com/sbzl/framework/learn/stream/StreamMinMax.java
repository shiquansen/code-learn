package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamMinMax {


    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(99, 89,23,78,66, 100, 32);

        // 最大值
//        Optional<Integer> max = scores.stream().max((x, y) -> x - y);
//        System.out.println(max.get());

        // 最小值获取
        Optional<Integer> min = scores.stream().min((x, y) -> x - y);
        System.out.println(min.get());

    }
}
