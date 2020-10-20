package com.zsy.service;

public class InnerClassStudy {
    public void innerClassStudy() {
        int num = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(num);
            }
        };
    }
}
