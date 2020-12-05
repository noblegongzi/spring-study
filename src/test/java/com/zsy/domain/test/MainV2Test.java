package com.zsy.domain.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zsy.domain.ARequest;
import com.zsy.domain.MyRequest;
import com.zsy.domain.Rec;
import com.zsy.domain.RequestDTO;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainV2Test extends BaseSpringTest {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private int threadNum = 4;
    private int streamNum = 100;
    private int captity = 1000_0000;

    @Test
    public void streamTest() {
        IntStream.range(0, streamNum).forEach(i -> {
            System.out.println(i + "******************************");
            List<String> list = Lists.newArrayListWithCapacity(captity);
            IntStream.range(0, captity).parallel().forEach(j -> {
                        list.add(j + "Hello");
                    }
            );
        });
    }

    @Test
    public void parallerStreamTest() {
        IntStream.range(0, streamNum).parallel().forEach(i -> {
            System.out.println(i + "******************************");
            List<String> list = Lists.newArrayListWithCapacity(captity);
            IntStream.range(0, captity).parallel().forEach(j -> {
                        list.add(j + "Hello");
                    }
            );
        });
    }

    @Test
    public void taskExecutorParallerStreamTest() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        IntStream.range(0, threadNum).forEach(i -> {
            try {
                taskExecutor.submit(() -> {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    IntStream.range(0, streamNum).parallel().forEach(j -> {
                        System.out.println(String.format("第%d个线程，第%d个stream", i, j));
                        List<String> list = Lists.newArrayListWithCapacity(captity);
                        IntStream.range(0, captity).forEach(k -> {
                                    list.add(k + "Hello");
                                }
                        );
                    });
                    countDownLatch.countDown();
                });
            } catch (OutOfMemoryError e) {
                System.out.println(i + e.getMessage());
            }
        });
        countDownLatch.await();
    }

    @Test
    public void JSONTest() {
        String s = "{'length':3,'width':5,'status':6}";
        System.out.println(JSON.parseObject(s, Rec.class));
    }

    @Test
    public void resourceTest() {
        Rec rec = ApplicationContextHandle.getBean(Rec.class);
        Rec rec1 = ApplicationContextHandle.getBean(Rec.class);
        rec1.setArea(200.9);
        System.out.println(rec);
        System.out.println(rec1);
    }

    @Test
    public void classTest() {
        String s = null;
        System.out.println(s.getClass().getName());
    }

    @Test
    public void nullBooleanTest() {
        Boolean b = null;
        System.out.println(b ? true : false);
    }

    @Test
    public void parseNullTest() {
        Assert.assertEquals(Long.parseLong(null), null);
    }

    @Test
    public void bigDemicalTest() {
        long value = -78921349827981L;
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.round(new MathContext((Long.toString(Math.abs(value))).length() - 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(bigDecimal.longValue(), -78921349828000L);
    }

    @Test
    public void longTest() {
        long value = 78921349827981L;
    }

    @Test
    public void finalLambdaTest() {
        int[] sum = new int[1];
        List<Integer> classroomStudentNum = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        classroomStudentNum.parallelStream().sequential().forEach(i -> sum[0] += i);
        System.out.println(sum[0]);
    }

    @Test
    public void hashcodeTest() {
        System.out.println(new Integer(1203021930).hashCode());
    }

    @Test
    public void bigdemicakTest2() {
        BigDecimal bigDecimal = new BigDecimal(897650);
        bigDecimal = bigDecimal.round(new MathContext(bigDecimal.abs().toPlainString().length() - 2, RoundingMode.HALF_UP));
        System.out.println(bigDecimal.toPlainString());
    }

    @Test
    public void jsonTest() {
        List<Integer> list = new ArrayList<>();
        System.out.println(list.stream().skip(10).limit(10).collect(Collectors.toList()));
    }

    @Test
    public void streamTest2() {
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        list.stream().peek(i -> i++).collect(Collectors.toList());
        System.out.println(list);
    }

//    private boolean exist(Place place1, Place place2) {
//        return exist(place1.getStartCode(), place2.getStartCode()) && exist(place1.getEndCode(), place2.getEndCode());
//    }

    @Test
    public void testExist() {
        Assert.assertFalse(exist("330212", "330200"));
        Assert.assertFalse(exist2("330212", "330200"));
    }

    private boolean exist(String existPlace, String curPlace) {
        String provinceStart = curPlace.substring(0, 3);
        String province = provinceStart + "000";
        String cityStart = curPlace.substring(0, 4);
        String city = cityStart + "00";
        return StringUtils.equalsIgnoreCase(existPlace, province) || StringUtils.equalsIgnoreCase(existPlace, city)
                || StringUtils.equalsIgnoreCase(existPlace, curPlace);
    }

    private boolean exist2(String existPlace, String curPlace) {
        String provinceStart = existPlace.substring(0, 3);
        String province = provinceStart + "000";
        String cityStart = existPlace.substring(0, 4);
        String city = cityStart + "00";
        return StringUtils.equalsIgnoreCase(curPlace, province) || StringUtils.equalsIgnoreCase(curPlace, city)
                || StringUtils.equalsIgnoreCase(curPlace, existPlace);
    }

    @Test
    public void testListRemove() {
        List<Integer> list = Lists.newArrayList(4, 3, 2, 1, 0);
        list.remove(new Integer(0));
        System.out.println(list);
    }

    @Test
    public void testTableSizeFor() {
        int cap = 17;
        int expect = 32;
        Assert.assertEquals(expect, tableSizeFor(cap));
    }

    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 1000) ? 1000 : n + 1;
    }

    @Test
    public void testJSON() {
        System.out.println(1);
    }

    @Test
    public void testScriptEngine() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        engine.put("name", "abcdefg");
        engine.eval("var output ='' ;" + "for (i = 0; i <= name.length; i++) { " + " output = name.charAt(i) + output"
                + "}");
        String name = (String) engine.get("output");
        System.out.printf("被翻转后的字符串：%s", name);
    }

    @Test
    public void testOptionalNull() {
        ARequest aRequest = new ARequest();
        System.out.println(Optional.ofNullable(aRequest).map(ARequest::getName).map(String::toUpperCase).orElseThrow(() -> new RuntimeException("测试")));
    }

    @Test
    public void testString2Integer() {
        String s = "{'age':'string'}";
        Assert.assertEquals(JSON.parseObject(s).getInteger("age"), new Integer(0));
    }

    @Test
    public void testGenerateStream() {
        Stream.generate(() -> "hello world!").forEach(System.out::println);
    }

    @Test
    public void testIterateStream() {
        Stream.iterate(1, t -> ++t).forEach(System.out::println);
    }

    @Test
    public void testSpringBeanUtil() {
        MyRequest myRequest = new MyRequest();
        myRequest.setAge(10);
        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(myRequest,requestDTO);
        System.out.println(requestDTO.getAge());
    }


}
