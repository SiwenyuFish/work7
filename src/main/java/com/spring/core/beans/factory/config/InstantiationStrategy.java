package com.spring.core.beans.factory.config;

import com.spring.core.beans.BeansException;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
