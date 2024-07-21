package com.spring.core.beans.factory;

import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.config.BeanPostProcessor;


public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

    boolean containsBean(String name);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
