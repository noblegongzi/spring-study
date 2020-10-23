package com.zsy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Setter
@Getter
public class Son extends Base {
    @JsonFormat(locale = "zh_CN")
    private Date date = new Date();
}
