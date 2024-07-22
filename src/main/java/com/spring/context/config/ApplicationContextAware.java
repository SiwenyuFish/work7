package com.spring.context.config;

import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
