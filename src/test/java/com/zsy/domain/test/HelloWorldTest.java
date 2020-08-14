package com.zsy.domain.test;

import com.zsy.config.HelloWorldConfig;
import com.zsy.domain.HelloWorld;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class HelloWorldTest {
    @Test
    public void test(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        HelloWorld helloWorld=ac.getBean("helloWorld",HelloWorld.class);
        helloWorld.setMes("hello world");
        System.out.println(helloWorld.getMes());
    }

    @Test
    public void test1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ac.getBean("helloWorld", HelloWorld.class).getMes();
    }
}
