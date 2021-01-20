package com.sbzl.framework.jdkoperation.stream;

import java.util.stream.Stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class CreationStreamBuilder {

    public static void main(String[] args) {
        Stream.Builder<String> builder = Stream.builder();
        builder.accept("james");
        builder.accept("wade");
        builder.accept("bosh");
        builder.build();
        
    }
}
