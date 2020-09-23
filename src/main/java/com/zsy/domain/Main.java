package com.zsy.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    public void init() {
        HelloWorld helloWorld = applicationContext.getBean("helloWorld", HelloWorld.class);
        System.out.println(helloWorld.getMes());
    }

    public static void main(String[] args) {
        String s="hello";
        Object o=s;
        System.out.println(o.getClass());
        new Main().init();
        try {
            testNullpointer();
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        }
    }

    private static void testNullpointer() {
        String s = null;
        s.length();
    }
}
