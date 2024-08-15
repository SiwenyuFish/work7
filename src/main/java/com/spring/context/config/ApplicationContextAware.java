package com.spring.context.config;

import com.spring.core.BeansException;
import com.spring.core.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
