package com.zsy.domain.test;

import com.zsy.domain.Son;
import org.junit.Test;

import javax.annotation.Resource;

public class ExtendPostConstructTest extends BaseSpringTest {

    @Resource
    private Son son;

    @Test
    public void test(){
        System.out.println("test son");
    }

}
