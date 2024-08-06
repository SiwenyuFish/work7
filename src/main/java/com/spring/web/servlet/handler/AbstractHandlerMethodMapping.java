package com.spring.web.servlet.handler;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.config.InitializingBean;
import com.spring.web.servlet.HandlerMapping;

public abstract class AbstractHandlerMethodMapping implements InitializingBean , ApplicationContextAware, HandlerMapping {


    private ApplicationContext applicationContext;

    public void afterPropertiesSet() {
        this.initHandlerMethods();
    }

    protected void initHandlerMethods() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
