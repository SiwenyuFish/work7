package com.spring.core.factory.config;

import com.spring.core.BeansException;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
