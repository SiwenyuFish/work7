package com.spring.core.beans.factory;

public interface HierarchicalBeanFactory extends BeanFactory {
    BeanFactory getParentBeanFactory();
}
