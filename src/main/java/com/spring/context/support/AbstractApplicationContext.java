package com.spring.context.support;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ConfigurableApplicationContext;
import com.spring.core.BeansException;
import com.spring.core.factory.BeanFactory;
import com.spring.core.factory.ConfigurableListableBeanFactory;
import com.spring.core.factory.config.BeanFactoryPostProcessor;
import com.spring.core.factory.config.BeanPostProcessor;
import com.spring.core.factory.support.DefaultListableBeanFactory;


import java.util.Map;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private ApplicationContext parent;

    public AbstractApplicationContext(ApplicationContext parent) {
        this.parent = parent;
    }

    public AbstractApplicationContext() {
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.getBeanFactory().getParentBeanFactory();
    }

    @Override
    public ApplicationContext getParent() {
        return this.parent;
    }

    public void setParent(ApplicationContext parent) {
        this.parent = parent;
        if(parent instanceof GenericApplicationContext){
            ((DefaultListableBeanFactory) this.getBeanFactory()).setParentBeanFactory( ((GenericApplicationContext) parent).getBeanFactory());
        }
    }

    public boolean containsLocalBean(String name) {
        return this.getBeanFactory().containsLocalBean(name);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException{

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();


    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beans = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : beans.values()) {
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beans = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor postProcessor : beans.values()) {
            postProcessor.postProcessBeanFactory(beanFactory);
        }
    }



    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return this.getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return this.getBeanFactory().containsBean(name);
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    public void close() {
        Thread shutdownHook = new Thread() {
            public void run() {
                getBeanFactory().destroySingletons();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }



}
