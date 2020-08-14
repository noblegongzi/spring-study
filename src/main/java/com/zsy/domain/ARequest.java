package com.zsy.domain;

import lombok.Getter;

@Getter
public class ARequest implements Request {
    private final String name="111";
    @Override
    public void print() {
        System.out.println("A");
    }
}
