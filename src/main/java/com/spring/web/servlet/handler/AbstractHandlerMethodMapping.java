package com.spring.web.servlet.handler;

import com.spring.aop.annotation.Aspect;
import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.context.support.GenericApplicationContext;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.InitializingBean;
import com.spring.core.beans.factory.util.BeanFactoryUtil;
import com.spring.web.annotation.Controller;
import com.spring.web.annotation.RequestMapping;
import com.spring.web.servlet.HandlerMapping;

import java.lang.reflect.Method;

public abstract class AbstractHandlerMethodMapping implements InitializingBean , ApplicationContextAware, HandlerMapping {


    private GenericApplicationContext applicationContext;

    public void afterPropertiesSet() {
        this.initHandlerMethods();
    }

    protected void initHandlerMethods() {

        if (this.applicationContext == null){
            return;
        }

        for (String beanDefinitionName : BeanFactoryUtil.beanNamesForTypeIncludingAncestors(applicationContext.getBeanFactory(), Object.class)) {

            BeanDefinition beanDefinition = applicationContext.getBeanFactory().getBeanDefinition(beanDefinitionName);
            try {
                Class<?> clazz = beanDefinition.getBeanClass();
                if (clazz.isAnnotationPresent(Controller.class)) {
                    for (Method method : clazz.getDeclaredMethods()) {
                        // 检查是否有 @RequestMapping 注解
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        if (requestMapping != null) {
                            registerHandlerMethod(method, requestMapping);
                        } else {
                            // 检查派生注解
                            checkAndRegisterDerivedAnnotations(method, clazz);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("扫描容器中控制器失败");
            }
        }

    }

    protected abstract void checkAndRegisterDerivedAnnotations(Method method, Class<?> clazz) throws Exception;

    protected abstract void registerHandlerMethod(Method method, RequestMapping requestMapping) throws InstantiationException, IllegalAccessException;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            this.applicationContext = (GenericApplicationContext) applicationContext;
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
