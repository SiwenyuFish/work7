package com.spring.core.beans.factory.config;


import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
