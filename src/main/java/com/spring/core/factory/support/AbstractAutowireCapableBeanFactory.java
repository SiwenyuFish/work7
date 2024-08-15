package com.spring.core.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.core.BeansException;
import com.spring.core.PropertyValue;
import com.spring.core.factory.AutowiredCapableBeanFactory;
import com.spring.core.factory.BeanFactory;
import com.spring.core.factory.BeanFactoryAware;
import com.spring.core.factory.annotation.Autowired;
import com.spring.core.factory.annotation.Value;
import com.spring.core.factory.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowiredCapableBeanFactory {

    private static final Logger log = LoggerFactory.getLogger(AbstractAutowireCapableBeanFactory.class);
    private InstantiationStrategy instantiationStrategy;

    public AbstractAutowireCapableBeanFactory() {
        instantiationStrategy = new SimpleInstantiationStrategy();
    }

    public AbstractAutowireCapableBeanFactory(BeanFactory parentBeanFactory) {
        this();
        this.setParentBeanFactory(parentBeanFactory);
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);

            if (beanDefinition.isSingleton()) {
                earlySingletonObjects.put(beanName, bean);
            }

            //Autowired处理
            populate(bean);

            applyPropertyValues(beanName,bean,beanDefinition);

            bean = initializeBean(beanName,bean,beanDefinition);

        } catch (Exception e) {
            throw new BeansException("实例化失败", e);
        }

        if(beanDefinition.isSingleton()) {
            registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
            //放入一级缓存
            registerSingleton(beanName, bean);
        }

        return bean;
    }

    private void populate(Object bean) throws IllegalAccessException {

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if(field.isAnnotationPresent(Value.class)) {

                Value valueAnnotation = field.getAnnotation(Value.class);
                String propertyKey = valueAnnotation.value().replace("${", "").replace("}", "");

                Properties properties = new Properties();

                try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                    if (input != null) {
                        properties.load(input);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                String propertyValue = properties.getProperty(propertyKey);

                if (propertyValue != null) {
                    field.setAccessible(true);
                    field.set(bean, propertyValue);
                }
            }
        }

        for (Field field : fields) {
            if(field.isAnnotationPresent(Autowired.class)) {
                Class<?> fieldType = field.getType();
                Object bean1 = getBean(fieldType);
                if(bean1!=null) {
                    field.setAccessible(true);
                    field.set(bean, bean1);
                }
            }
        }

    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setBeanName(beanName);
        }

        //BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);


        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("初始化"+beanName+"失败");
        }

        //BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;

    }

    protected void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();

        //自定义的初始化方法不能与afterPropertiesSet重名
        if (StrUtil.isNotEmpty(initMethodName) && !(wrappedBean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("在"+beanName+"这个bean中找不到名字为" + initMethodName + "的初始化方法");
            }
            initMethod.invoke(wrappedBean);
        }
    }


    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                //a依赖b，则先实例化b
                if(value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                if(value!=null)
                    BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception ex) {
            throw new BeansException("错误设置bean" + beanName + "的属性", ex);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object bean = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object currentBean = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            if (currentBean == null) {
                return bean;
            }
            bean = currentBean;
        }
        return bean;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object bean = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object currentBean = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
            if (currentBean == null) {
                return bean;
            }
            bean = currentBean;
        }
        return bean;
    }
}
