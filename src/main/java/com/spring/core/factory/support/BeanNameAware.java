package com.spring.core.factory.support;

import com.spring.core.factory.Aware;

public interface BeanNameAware extends Aware {
    public void setBeanName(String beanName);
}
