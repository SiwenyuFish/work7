package com.spring.core.factory;

import com.spring.core.BeansException;
import com.spring.core.factory.config.BeanPostProcessor;


public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

    boolean containsBean(String name);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
