package com.spring.core.factory;

import com.spring.core.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanNamesForType(Class<?> var1);

    String[] getBeanDefinitionNames();
}
