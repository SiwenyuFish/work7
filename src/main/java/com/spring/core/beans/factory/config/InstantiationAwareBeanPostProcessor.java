package com.spring.core.beans.factory.config;

import com.spring.core.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
    return bean;
}

}
