package com.spring.context.config;

import com.spring.core.beans.factory.HierarchicalBeanFactory;
import com.spring.core.beans.factory.ListableBeanFactory;
import com.spring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
    ApplicationContext getParent();
}
