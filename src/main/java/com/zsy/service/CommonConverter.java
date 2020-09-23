package com.zsy.service;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CommonConverter {

    private final Map<String, BeanCopier> beanCopierMap = Maps.newConcurrentMap();
    private final Map<String, ConstructorAccess> constructorAccessMap = Maps.newConcurrentMap();

    public <T, K> void copyListProperties(List<T> sourceList, List<K> targetList) {
        int length = sourceList.size();
        IntStream.range(0, length).boxed().parallel().forEach(i -> {
            copyProperties(sourceList.get(i), targetList.get(i));
        });
    }

    public <T, K> List<K> copyListProperties(List<T> sourceList, Class<K> targetClass) {
        ConstructorAccess<K> constructorAccess = obtainConstructorAccess(targetClass);
        List<K> result = sourceList.parallelStream()
                .map(source -> copyPropertiesByConstructorAccess(source, constructorAccess))
                .collect(Collectors.toList());
        return result;
    }

    public <T, K> K copyPropertiesByClass(T source, Class<K> targetClass) {
        return copyPropertiesByConstructorAccess(source, obtainConstructorAccess(targetClass));
    }

    private <T, K> K copyPropertiesByConstructorAccess(T source, ConstructorAccess<K> kConstructorAccess) {
        K k = kConstructorAccess.newInstance();
        copyProperties(source, k);
        return k;
    }

    private <K> ConstructorAccess<K> obtainConstructorAccess(Class<K> targetClass) {
        String constructorAccessKey = targetClass.getName();
        if (!this.constructorAccessMap.containsKey(constructorAccessKey)) {
            synchronized (this) {
                if (!this.constructorAccessMap.containsKey(constructorAccessKey)) {
                    this.constructorAccessMap.put(constructorAccessKey, ConstructorAccess.get(targetClass));
                }
            }
        }
        return this.constructorAccessMap.get(constructorAccessKey);
    }

    private <K> K obtainK(Class<K> targetClass) {
        ConstructorAccess<K> constructorAccess = obtainConstructorAccess(targetClass);
        return constructorAccess.newInstance();
    }

    public <T, K> void copyProperties(T source, K target) {
        BeanCopier beanCopier = obtainBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    private <T, K> BeanCopier obtainBeanCopier(Class<T> sourceClass, Class<K> targetClass) {
        String beanCopierKey = genKey(sourceClass, targetClass);
        if (!this.beanCopierMap.containsKey(beanCopierKey)) {
            synchronized (this) {
                if (!this.beanCopierMap.containsKey(beanCopierKey)) {
                    this.beanCopierMap.put(beanCopierKey, BeanCopier.create(sourceClass, targetClass, false));
                }
            }
        }
        return this.beanCopierMap.get(beanCopierKey);
    }

    private <T, K> String genKey(Class<T> sourceClass, Class<K> targetClass) {
        return sourceClass.getName() + targetClass.getName();
    }

}
