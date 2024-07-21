package com.spring.context.support;

import com.spring.context.annotation.AnnotationConfigRegistry;
import com.spring.core.beans.factory.annotation.BeanAnnotationBeanPostProcessor;


public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        this.register(componentClasses);
        this.refresh();
    }

    @Override
    public void register(Class<?>... componentClasses) {
        new BeanAnnotationBeanPostProcessor().processBeanDefinitions(this.getBeanFactory(), componentClasses);
    }

}
