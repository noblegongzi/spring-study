package com.zsy.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Setter
@Getter
@ToString
@Component
@Scope(scopeName = "prototype")
public class Rec {

    @Resource
    private HelloWorld helloWorld;

    private Double length;
    private Double width;
    private Double area = 100.0;
    private boolean test;

    public Rec() {
    }

    public Rec(Double length, Double width) {
        this.length = length;
        this.width = width;
        this.area = length * width;
    }

    public Double getArea() {
        return area;
    }
}
