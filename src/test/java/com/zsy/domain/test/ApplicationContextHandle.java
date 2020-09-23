package com.zsy.domain.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class ApplicationContextHandle implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextHandle.applicationContext == null) {
            ApplicationContextHandle.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        Object o = null;
        try{
            o = getApplicationContext().getBean(name);
        }catch (Exception e){

        }
        return o;
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> var1){
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(var1);
        return beansWithAnnotation;
    }

}