package com.sbzl.framework.juc;

import java.util.ArrayList;
import java.util.List;

public class ThreadState {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        while(true) {
            Object object = new Object();
            list.add(object);
        }

    }


}
