package com.spring.context.support;

import com.spring.context.config.ConfigurableApplicationContext;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;
import com.spring.core.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.core.beans.factory.config.BeanPostProcessor;
import com.spring.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {


    @Override
    public void refresh() throws BeansException, IllegalStateException{

        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

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

    protected abstract void refreshBeanFactory();


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

    public void registerShutdownHook() {
        Thread shutdownHook = new Thread() {
            public void run() {
                getBeanFactory().destroySingletons();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);

    }
}
