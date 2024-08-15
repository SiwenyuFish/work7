package com.spring.core.factory;

import com.spring.core.BeansException;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
