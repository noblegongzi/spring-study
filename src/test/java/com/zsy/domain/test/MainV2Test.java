package com.zsy.domain.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zsy.domain.Rec;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

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
        Assert.assertEquals(bigDecimal.longValue(),-78921349828000L);
    }

    @Test
    public void longTest(){
        long value = 78921349827981L;
    }

}
