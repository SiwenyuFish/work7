package com.spring.context.support;

import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.BeanDefinitionRegistry;
import com.spring.core.beans.factory.config.BeanPostProcessor;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;

public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
       this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeansException {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public final ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanFactory.addBeanPostProcessor(beanPostProcessor);
    }
}
