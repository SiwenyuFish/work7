package com.spring.context.config;

import com.spring.core.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    void refresh() throws BeansException, IllegalStateException;
}
