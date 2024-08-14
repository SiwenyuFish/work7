package com.spring.aop;

import com.spring.aop.annotation.Advisor;
import com.spring.aop.annotation.Aspect;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.BeanFactoryAware;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.BeanPostProcessor;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;
import com.spring.jdbc.TransactionInterceptor;
import com.spring.jdbc.TransactionManager;
import com.spring.jdbc.Transactional;
import org.apache.commons.dbcp2.BasicDataSource;


import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.util.Collection;



public class DefaultAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return wrapIfNecessary(bean, beanName);
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {

        //避免死循环
        if (isInfrastructureClass(bean.getClass())||bean.getClass().isAnnotationPresent(Aspect.class)) {
            return bean;
        }

        for (Method declaredMethod : bean.getClass().getDeclaredMethods()) {
            if(declaredMethod.isAnnotationPresent(Transactional.class)){
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/spring");
                dataSource.setUsername("root");
                dataSource.setPassword("297710");
                ProxyFactory proxyFactory = new ProxyFactory(bean,new TransactionInterceptor(bean,new TransactionManager(dataSource)));
                return proxyFactory.getProxy();
            }
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
