package com.spring.context.support;

import com.spring.context.config.ApplicationContext;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.BeanDefinitionRegistry;
import com.spring.core.beans.factory.config.BeanPostProcessor;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;

import java.util.function.Supplier;

public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    public GenericApplicationContext(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public GenericApplicationContext(ApplicationContext parent) {
        this();
        this.setParent(parent);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeansException {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public final <T> void registerBean(Class<T> beanClass) {
        beanFactory.registerBeanDefinition(beanClass.getSimpleName(), new BeanDefinition(beanClass));
    }

    public final ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanFactory.addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> var1) {
        return this.beanFactory.getBeanNamesForType(var1);
    }
}
