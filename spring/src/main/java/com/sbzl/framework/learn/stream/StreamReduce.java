package com.sbzl.framework.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/27
 */
public class StreamReduce {

    public static void main(String[] args) {
        Integer[] ids = {1,3,5};
        Optional<Integer> reduce = Stream.of(ids).reduce(Integer::sum);
        System.out.println(reduce.get());

        List<String> l = Arrays.asList("james","wade","bosh");

        Optional<String> reduce1 = l.stream().reduce((x, y) -> x + "~~" + y);
        System.out.println(reduce1.get());

    }
}
