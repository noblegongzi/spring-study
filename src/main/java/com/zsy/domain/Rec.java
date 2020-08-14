package com.zsy.domain;

import lombok.Getter;
import lombok.Setter;


public class Rec {

    @Setter
    @Getter
    private Double length;
    @Setter
    @Getter
    private Double width;
    private Double area;

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
