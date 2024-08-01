package com.spring.aop;

import com.spring.aop.annotation.Advisor;
import com.spring.aop.annotation.Aspect;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.BeanFactoryAware;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        //避免死循环
        if (isInfrastructureClass(bean.getClass())||bean.getClass().isAnnotationPresent(Aspect.class)) {
            return bean;
        }
        ProxyFactory proxyFactory = new ProxyFactory(bean);

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            try {
                Class<?> beanClass = beanDefinition.getBeanClass();
                if (beanClass.isAnnotationPresent(Aspect.class)) {
                    Object bean1 = beanFactory.getBean(beanClass.getSimpleName());
                    proxyFactory.addAspect(bean1);
                }
            } catch (Exception e) {
                System.out.println("添加切面失败");
            }
        }

        Collection<Advisor> advisors = beanFactory.getBeansOfType(Advisor.class).values();

        try {
            for (Advisor advisor : advisors) {
                if (advisor.getPointcut().matches(bean.getClass())) {
                    proxyFactory.addAdvisor(advisor);
                }
            }
            if(!proxyFactory.getAdvisors().isEmpty()){
                //若Advisors不为空则创建代理
                //System.out.println(proxyFactory.getAdvisors().size());
                return proxyFactory.getProxy();
            }
            return bean;
        } catch (Exception ex) {
            throw new BeansException("创建代理失败 " + beanName, ex);
        }
    }



    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || AspectJExpressionPointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
