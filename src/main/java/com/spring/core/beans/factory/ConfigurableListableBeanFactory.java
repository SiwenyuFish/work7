package com.spring.core.beans.factory;

import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.SingletonBeanRegistry;

public interface ConfigurableListableBeanFactory extends  AutowiredCapableBeanFactory, SingletonBeanRegistry,ListableBeanFactory,HierarchicalBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void destroySingletons();

}
