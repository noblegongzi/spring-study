package com.zsy.domain.test;

import com.zsy.domain.ARequest;
import com.zsy.domain.BRequest;
import com.zsy.domain.Request;
import com.zsy.service.RequestService;
import org.junit.Test;

public class RequestServiceTest {
    @Test
    public void test(){
        Request a=new ARequest();
        Request b=new BRequest();
        a.print();
        b.print();
   }
}
