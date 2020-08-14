package com.zsy.converter;

import com.zsy.domain.MyRequest;
import com.zsy.domain.RequestDTO;
import org.springframework.stereotype.Service;

@Service("myConverter")
public class RequestConverter {
    public void requestDTO2MyRequest(RequestDTO source, MyRequest target) {
        target.setName(source.getName());
        target.setName1(source.getName1());
        target.setName2(source.getName2());
        target.setName3(source.getName3());
        target.setName4(source.getName4());
        target.setName5(source.getName5());
        target.setName6(source.getName6());
        target.setName7(source.getName7());
        target.setName8(source.getName8());
        target.setName9(source.getName9());
        target.setName10(source.getName10());

    }
}
