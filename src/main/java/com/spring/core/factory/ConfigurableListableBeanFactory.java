package com.spring.core.factory;

import com.spring.core.BeansException;
import com.spring.core.factory.config.BeanDefinition;
import com.spring.core.factory.config.SingletonBeanRegistry;

public interface ConfigurableListableBeanFactory extends  AutowiredCapableBeanFactory, SingletonBeanRegistry,ListableBeanFactory,HierarchicalBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void destroySingletons();

}
