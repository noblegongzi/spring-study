package com.zsy.service;

import java.util.ArrayList;
import java.util.Arrays;

public class LambdaStudy {

    public static void main(String[] args) {
        String hello = "hello";
        new ArrayList<String>(Arrays.asList("a", "b")).forEach(s -> {
            System.out.println(hello + s);
        });
    }
}

