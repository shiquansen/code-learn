package com.github.qiudaozhang;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamFindFirst {

    public static void main(String[] args) {
        Integer[] ids = {1,3,5,6,7,8};
        Optional<Integer> first = Stream.of(ids).filter(it -> it % 2 == 1)
                .filter(it -> it > 3)
                .findFirst();

        System.out.println(first.get());

    }
}
