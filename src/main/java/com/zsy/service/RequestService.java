package com.zsy.service;

import com.zsy.domain.ARequest;
import com.zsy.domain.BRequest;

public class RequestService {

    public void resolveReq(ARequest request){
        System.out.println("A");
    }

    public void resolveReq(BRequest request){
        System.out.println("B");
    }

}
