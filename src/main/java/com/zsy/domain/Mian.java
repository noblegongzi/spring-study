package com.zsy.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mian {
    @Autowired
    private ApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
    public void init(){
        HelloWorld helloWorld=applicationContext.getBean("helloWorld",HelloWorld.class);
        System.out.println(helloWorld.getMes());
    }

    public static void main(String[] args) {
        new Mian().init();
    }
}
