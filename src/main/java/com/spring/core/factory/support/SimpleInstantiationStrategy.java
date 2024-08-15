package com.spring.core.factory.support;

import com.spring.core.BeansException;
import com.spring.core.factory.config.BeanDefinition;
import com.spring.core.factory.config.InstantiationStrategy;

import java.lang.reflect.Constructor;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            Constructor constructor = clazz.getConstructor();
            //打开访问受限，能使用内部类的构造器
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("不能实例化 " + clazz, e);
        }
    }
}
