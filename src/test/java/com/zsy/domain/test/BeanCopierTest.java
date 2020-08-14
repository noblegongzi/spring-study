package com.zsy.domain.test;

import com.zsy.converter.RequestConverter;
import com.zsy.domain.MyRequest;
import com.zsy.domain.RequestDTO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BeanCopierTest extends BaseSpringTest {
    @Resource(name = "myConverter")
    private RequestConverter requestConverter;

    private Integer num = 10000;

    @Test
    public void test() {
        List<RequestDTO> requests = IntStream.range(0, num).boxed().parallel().map(RequestDTO::new).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        BeanCopier beanCopier = BeanCopier.create(RequestDTO.class, MyRequest.class, false);
        long sum = requests.parallelStream().map(requestDTO -> {
            MyRequest myRequest = new MyRequest();
            beanCopier.copy(requestDTO, myRequest, null);
            return myRequest;
        }).count();
        long time = System.currentTimeMillis() - start;
        System.out.println(String.format("通过BeanCopier转换%d个dto，包含create的时间，总时间：", num));
        System.out.println(time / 1000 + "s" + time % 1000 + "ms");
    }

    @Test
    public void test0() {
        List<RequestDTO> requests = IntStream.range(0, num).boxed().parallel().map(RequestDTO::new).collect(Collectors.toList());
        BeanCopier beanCopier1 = BeanCopier.create(RequestDTO.class, MyRequest.class, false);
        long start = System.currentTimeMillis();
        long sum = requests.parallelStream().map(requestDTO -> {
            MyRequest myRequest = new MyRequest();
            beanCopier1.copy(requestDTO, myRequest, null);

            return myRequest;
        }).count();
        System.out.println(String.format("通过BeanCopier转换%d个dto，不包含create的时间，总时间：", num));
        long time = System.currentTimeMillis() - start;
        System.out.println(time / 1000 + "s" + time % 1000 + "ms");
    }

    @Test
    public void test1() {
        List<RequestDTO> requests = IntStream.range(0, num).boxed().parallel().map(RequestDTO::new).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        long sum = requests.parallelStream().map(requestDTO -> {
            MyRequest myRequest = new MyRequest();
            BeanUtils.copyProperties(requestDTO, myRequest);
            return myRequest;
        }).count();
        long time = System.currentTimeMillis() - start;
        System.out.println(String.format("通过BeanUtils转换%d个dto，总时间：", num));
        System.out.println(time / 1000 + "s" + time % 1000 + "ms");
    }

    @Test
    public void test2() {
        List<RequestDTO> requests = IntStream.range(0, num).boxed().parallel().map(RequestDTO::new).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        long sum = requests.parallelStream().map(requestDTO -> {
            MyRequest myRequest = new MyRequest();
            requestConverter.requestDTO2MyRequest(requestDTO, myRequest);
            return myRequest;
        }).count();
        long time = System.currentTimeMillis() - start;
        System.out.println(String.format("通过手动Set，Get转换%d个dto，总时间：", num));
        System.out.println(time / 1000 + "s" + time % 1000 + "ms");
    }
}
