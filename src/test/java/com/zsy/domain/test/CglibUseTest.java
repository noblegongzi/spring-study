package com.zsy.domain.test;

import com.zsy.cglib.CglibUse;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

public class CglibUseTest extends BaseSpringTest {
    @Test
    public void test() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibUse.class);
        enhancer.setCallback(NoOp.INSTANCE);
        enhancer.setUseCache(Boolean.TRUE);
        CglibUse cglibUse = (CglibUse) enhancer.create();
        cglibUse.setName("zsy");
        cglibUse.setAge(24);
        cglibUse.echo();
        CglibUse cglibUseCopy = (CglibUse) enhancer.create();
        cglibUseCopy.echo();
    }
}
