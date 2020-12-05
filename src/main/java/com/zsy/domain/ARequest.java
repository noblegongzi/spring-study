package com.zsy.domain;

import lombok.Getter;

@Getter
public class ARequest implements Request {
    private String name;

    @Override
    public void print() {
        System.out.println("A");
    }
}
