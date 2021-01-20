package com.sbzl.framework.jdkoperation.stream;

/**
 * 邱道长
 * 2020/2/26
 */
public class StreamPeek {

    public static void main(String[] args) {
        UserRepository ur = new UserRepository();
//        List<User> l = ur.findAll().stream().
//                peek(it -> it.setName(it.getName().toUpperCase()))
//                .collect(Collectors.toList());
//        System.out.println(l);
//
//        System.out.println(ur.findAll());

        ur.findAll().stream()
                .peek(it -> it = null);
        System.out.println(ur.findAll());

    }
}
