package com.spring.context.support;

import com.spring.context.annotation.AnnotationConfigRegistry;
;


public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        this.register(componentClasses);
        this.refresh();
    }

    public AnnotationConfigApplicationContext() {
        this.refresh();
    }

    @Override
    public void register(Class<?>... componentClasses) {
        this.registerBean(BeanAnnotationBeanPostProcessor.class);
        for (Class<?> componentClass : componentClasses) {
            this.registerBean(componentClass);
        }
    }

}
