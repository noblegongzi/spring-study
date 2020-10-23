package com.zsy.domain;

import org.springframework.stereotype.Component;

@Component
public abstract class Base {

    public void init() {
        System.out.println("Base");
    }

}
