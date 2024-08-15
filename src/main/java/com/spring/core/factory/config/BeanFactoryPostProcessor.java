package com.spring.core.factory.config;


import com.spring.core.BeansException;
import com.spring.core.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
