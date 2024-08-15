package com.spring.context.config;

import com.spring.core.factory.HierarchicalBeanFactory;
import com.spring.core.factory.ListableBeanFactory;
;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory {
    ApplicationContext getParent();
}
