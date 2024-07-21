package com.spring.core.beans.factory;

import com.spring.core.beans.BeansException;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
