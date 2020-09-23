package com.zsy.domain.test;

import org.junit.Test;

public class Main {
    @Test
    public  void main() {
        int num = 0;
        for (int i = 2020; i <= 1_0000_0000; i++) {
            if ((i % 4 == 0 && i % 400 != 0) || i % 100 == 0) {
                System.out.println(i);
                num++;
            }
        }
        System.out.println(num);
    }
}
