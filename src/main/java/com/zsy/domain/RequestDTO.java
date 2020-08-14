package com.zsy.domain;

import lombok.Data;

@Data
public class RequestDTO {
    private String name;
    private String name1;
    private String name2;
    private String name3;
    private String name4;
    private String name5;
    private String name6;
    private String name7;
    private String name8;
    private String name9;
    private String name10;

    public RequestDTO(Integer nameInteger) {
        String name=nameInteger.toString();
        this.name = name;
        this.name1 = name;
        this.name2 = name;
        this.name3 = name;
        this.name4 = name;
        this.name5 = name;
        this.name6 = name;
        this.name7 = name;
        this.name8 = name;
        this.name9 = name;
        this.name10 = name;
    }
}
