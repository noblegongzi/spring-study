package com.zsy.domain.test;

import com.zsy.domain.Rec;
import org.junit.Test;

public class RecTest {
    @Test
    public void getAreaTest(){
        Rec rec=new Rec(3.0,2.0);
        System.out.println(rec.getArea());
    }
}
