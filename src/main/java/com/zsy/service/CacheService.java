package com.zsy.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CacheService {

    //private static long baseTime= Calendar.getInstance().setTime(new Date());

    private static Cache<String, BeanCopier> cache = CacheBuilder.newBuilder()
            .maximumSize(2)
            .removalListener(
                    (RemovalListener<String, BeanCopier>) removalNotification
                            -> System.out.println(removalNotification.getKey() + "被移除"))
            .build();

    public static <T, K> BeanCopier getBeanCopier(Class<T> tClass, Class<K> kClass) throws ExecutionException {
        String key = tClass.getName() + kClass.getName();
        return cache.get(key, () -> {
            System.out.println("新建");
            return BeanCopier.create(tClass, kClass, false);
        });
    }

    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(2000,Calendar.JANUARY,1,0,0,0);
        System.out.println(calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
        System.out.println(calendar.getTimeInMillis());
    }

}
