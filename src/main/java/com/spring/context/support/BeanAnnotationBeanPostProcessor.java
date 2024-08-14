package com.spring.context.support;

import cn.hutool.core.util.StrUtil;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.PropertyValue;
import com.spring.core.beans.PropertyValues;
import com.spring.core.beans.factory.ConfigurableListableBeanFactory;
import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Component;
import com.spring.core.beans.factory.annotation.Configuration;
import com.spring.core.beans.factory.config.BeanDefinition;
import com.spring.core.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.core.beans.factory.config.BeanReference;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanAnnotationBeanPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            try {
                Class<?> beanClass = beanDefinition.getBeanClass();

                processBeanDefinitions(beanFactory,beanClass);

            } catch (Exception e) {
                System.out.println("处理Configuration异常");
            }
        }
    }

    public void processBeanDefinitions(ConfigurableListableBeanFactory beanFactory,Class<?>... configClasses) {
        try {
            for (Class<?> configClass : configClasses) {

                if (!configClass.isAnnotationPresent(Configuration.class)&&!configClass.isAnnotationPresent(Component.class)) {
                    continue;
                }

                if (beanFactory instanceof DefaultListableBeanFactory) {
                    BeanDefinition bd = new BeanDefinition(configClass);
                    ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(configClass.getSimpleName(), bd);
                }

                for (Method method : configClass.getDeclaredMethods()) {

                    if (!method.isAnnotationPresent(Bean.class)) {
                        continue;
                    }

                    BeanDefinition beanDefinition;
                    PropertyValues propertyValues = new PropertyValues();
                    Object bean = null;

                    String init = method.getAnnotation(Bean.class).initMethod();
                    String dest = method.getAnnotation(Bean.class).destroyMethod();

                    if (method.getReturnType().getDeclaredFields().length==0){
                        //无属性创建BeanDefinition
                        beanDefinition = new BeanDefinition(method.getReturnType());
                        if(StrUtil.isNotEmpty(init)) {
                            beanDefinition.setInitMethodName(init);
                        }
                        if(StrUtil.isNotEmpty(dest)) {
                            beanDefinition.setDestroyMethodName(dest);
                        }

                    }else {

                        if (method.getParameterTypes().length == 0) {
                            //无依赖bean方法
                            bean = method.invoke(configClass.getConstructor().newInstance());
                            Field[] fields = method.getReturnType().getDeclaredFields();
                            for (Field field : fields) {
                                field.setAccessible(true);
                                propertyValues.addPropertyValue(new PropertyValue(field.getName(), field.get(bean)));
                            }
                        } else {
                            //有依赖bean方法
                            bean = method.invoke(configClass.getConstructor().newInstance(), (Object) null);
                            Field[] fields = method.getReturnType().getDeclaredFields();
                            for (Field field : fields) {
                                field.setAccessible(true);
                                if (field.get(bean) != null) {
                                    propertyValues.addPropertyValue(new PropertyValue(field.getName(), field.get(bean)));
                                } else {
                                    //属性值为空说明该成员为依赖项
                                    propertyValues.addPropertyValue(new PropertyValue(field.getName(), new BeanReference(field.getName())));
                                }
                            }
                        }

                        beanDefinition = new BeanDefinition(method.getReturnType(), propertyValues);
                        if(StrUtil.isNotEmpty(init)) {
                            beanDefinition.setInitMethodName(init);
                        }
                        if(StrUtil.isNotEmpty(dest)) {
                            beanDefinition.setDestroyMethodName(dest);
                        }
                    }

                    if (beanFactory instanceof DefaultListableBeanFactory) {
                        ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(method.getName(), beanDefinition);
                    }

                }

            }
        } catch (Exception e) {
            System.out.println("BeanAnnotationBeanPostProcessor Exception");
        }
    }

}
