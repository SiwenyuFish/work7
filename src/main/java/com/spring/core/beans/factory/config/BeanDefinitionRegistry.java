package com.spring.core.beans.factory.config;

import com.spring.core.beans.BeansException;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
