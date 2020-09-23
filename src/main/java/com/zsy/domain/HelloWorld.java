package com.zsy.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class HelloWorld {
    private  String mes;

    public void outMes(){
        System.out.println(mes);
    }
}
