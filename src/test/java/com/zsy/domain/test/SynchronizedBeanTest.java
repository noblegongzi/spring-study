package com.zsy.domain.test;

import com.zsy.domain.SynchronizedBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.weakref.jmx.internal.guava.collect.Lists;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class SynchronizedBeanTest extends BaseSpringTest {


    private SynchronizedBean synchronizedBean = new SynchronizedBean();
    private SynchronizedBean synchronizedBean1 = new SynchronizedBean();
    private List<SynchronizedBean> synchronizedBeans = Lists.newArrayListWithCapacity(100);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    int addTime = 10000;
    int beanNums = 100;

    private CountDownLatch countDownLatch0 = new CountDownLatch(addTime);

    private CountDownLatch countDownLatch1 = new CountDownLatch(addTime * 2);

    private CountDownLatch countDownLatch2 = new CountDownLatch(addTime * beanNums);

    @Test
    public void test0() throws InterruptedException {
        IntStream.range(0, addTime * beanNums).forEach(i -> taskExecutor.execute(() -> {
            SynchronizedBean.add();
            countDownLatch0.countDown();
        }));
        countDownLatch0.await();
        Assert.assertEquals("！=", SynchronizedBean.getNum(), addTime * beanNums);
    }

    @Test
    public void test1() throws InterruptedException {
        IntStream.range(0, addTime).forEach(i -> taskExecutor.execute(() -> {
            synchronizedBean.add();
            countDownLatch1.countDown();
        }));
        IntStream.range(0, addTime).forEach(i -> taskExecutor.execute(() -> {
            synchronizedBean1.add();
            countDownLatch1.countDown();
        }));
        countDownLatch1.await();
        assert (SynchronizedBean.getNum() == addTime * 2);
    }

    @Test
    public void test2() {
        IntStream.range(0, beanNums).forEach(i -> synchronizedBeans.add(new SynchronizedBean()));
        IntStream.range(0, addTime).forEach(i -> {
            IntStream.range(0, beanNums).forEach(j -> {
                taskExecutor.execute(() -> {
                    synchronizedBeans.get(j).add();
                    countDownLatch2.countDown();
                });
            });
        });
        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(String.format("%d！=%d", SynchronizedBean.getNum(), beanNums * addTime), SynchronizedBean.getNum(), beanNums * addTime);

    }
}
