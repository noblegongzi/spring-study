package com.zsy.domain;

import lombok.Getter;

public class SynchronizedBean {

    @Getter
    private static int num;

    public static void add() {
        synchronized (SynchronizedBean.class){
            num++;
        }
    }

}
