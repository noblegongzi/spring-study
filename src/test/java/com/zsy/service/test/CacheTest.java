package com.zsy.service.test;

import com.zsy.domain.ARequest;
import com.zsy.domain.BRequest;
import com.zsy.domain.test.BaseSpringTest;
import com.zsy.service.CacheService;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class CacheTest extends BaseSpringTest {

    @Test
    public void getBeanCopierTest() throws ExecutionException {
        Assert.assertNotNull(CacheService.getBeanCopier(String.class, String.class));
        Assert.assertEquals(CacheService.getBeanCopier(String.class, String.class), CacheService.getBeanCopier(String.class, String.class));
//        Thread.sleep(1000);
        Assert.assertNotNull(CacheService.getBeanCopier(String.class, String.class));
//        Thread.sleep(2000);
        Assert.assertNotNull(CacheService.getBeanCopier(ARequest.class, ARequest.class));
        Assert.assertNotNull(CacheService.getBeanCopier(String.class, String.class));
        Assert.assertNotNull(CacheService.getBeanCopier(BRequest.class, BRequest.class));

    }

}
