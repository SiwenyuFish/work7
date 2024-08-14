package com.spring.core.beans.factory.support;

import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.HierarchicalBeanFactory;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.BeanPostProcessor;
import com.spring.core.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements SingletonBeanRegistry, HierarchicalBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors ;

    private BeanFactory parentBeanFactory;

    public AbstractBeanFactory() {
        beanPostProcessors  = new ArrayList<>();
    }

    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this();
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);

        if (bean != null) {
            return bean;
        }

        BeanFactory parentBeanFactory = this.getParentBeanFactory();

        if(parentBeanFactory != null && !this.containsBeanDefinition(name)) {
            if(parentBeanFactory instanceof AbstractBeanFactory) {
               return parentBeanFactory.getBean(name);
            }
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);


        return createBean(name, beanDefinition);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;


    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
    }


    @Override
    public boolean containsBean(String name) {
        return false;
    }



    protected abstract boolean containsBeanDefinition(String beanName);


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.parentBeanFactory;
    }

    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public boolean containsLocalBean(String beanName) {
        return (this.containsSingleton(beanName) || this.containsBeanDefinition(beanName));
    }

}
