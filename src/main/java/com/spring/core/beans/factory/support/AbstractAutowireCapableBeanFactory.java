package com.spring.core.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.PropertyValue;
import com.spring.core.beans.factory.AutowiredCapableBeanFactory;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.BeanFactoryAware;
import com.spring.core.beans.factory.config.*;

import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowiredCapableBeanFactory {

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
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);

            //为解决循环依赖问题，将实例化后的bean放进缓存中提前暴露
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }

            applyPropertyValues(beanName,bean,beanDefinition);
            bean = initializeBean(beanName,bean,beanDefinition);

        } catch (Exception e) {
            throw new BeansException("实例化失败", e);
        }

        if(beanDefinition.isSingleton()) {
            //只有单例才注册销毁方法
            registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
            //registerSingleton(beanName, bean);
        }

        Object exposedObject = bean;

        if (beanDefinition.isSingleton()) {
            //如果有代理对象，此处获取代理对象
            exposedObject = getSingleton(beanName);
            registerSingleton(beanName, exposedObject);
        }
        return exposedObject;
    }

    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) bp).getEarlyBeanReference(exposedObject, beanName);
                if (exposedObject == null) {
                    return exposedObject;
                }
            }
        }

        return exposedObject;
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

        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);


        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("初始化"+beanName+"失败");
        }

        //执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;

    }

    protected void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();

        //自定义的初始化方法在实现了InitializingBean接口的情况下不能与afterPropertiesSet重名
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

                //通过反射设置属性
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
