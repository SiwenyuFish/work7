package com.spring.core.factory.config;

import com.spring.core.BeansException;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
