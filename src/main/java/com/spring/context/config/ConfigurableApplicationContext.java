package com.spring.context.config;

import com.spring.core.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    void refresh() throws BeansException, IllegalStateException;
}
