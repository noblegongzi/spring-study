package com.zsy.cglib;

import lombok.Setter;

@Setter
public class CglibUse {
    private String name;
    private Integer age;

    public void echo() {
        System.out.println("name:" + name + ",age:" + age);
    }

}
