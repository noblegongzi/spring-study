package com.zsy.enums;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

@Getter
public enum SomeEnum {
    TYPE1("type1"){
        class Inner{
            String name="zsy";
        }
    },
    TYPE2("type2"){
        class Inner{
            int age=10;
        }
    };

    private String name;

    SomeEnum(String name) {
        this.name = name;
    }

    public static SomeEnum getSomeEnum(String name) {
        return Arrays.stream(SomeEnum.values()).parallel().filter(someEnum -> StringUtils.equalsIgnoreCase(someEnum.getName(), name)).findFirst().orElse(null);
    }
}
