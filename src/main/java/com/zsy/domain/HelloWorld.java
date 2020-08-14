package com.zsy.domain;

import lombok.Data;

@Data
public class HelloWorld {
    private  String mes;

    public void outMes(){
        System.out.println(mes);
    }
}
