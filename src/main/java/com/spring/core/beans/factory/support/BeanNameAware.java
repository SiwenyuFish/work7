package com.spring.core.beans.factory.support;

import com.spring.core.beans.factory.Aware;

public interface BeanNameAware extends Aware {
    public void setBeanName(String beanName);
}
