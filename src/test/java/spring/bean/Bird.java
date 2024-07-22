package spring.bean;

import com.spring.context.config.ApplicationContext;
import com.spring.context.config.ApplicationContextAware;
import com.spring.core.beans.BeansException;
import com.spring.core.beans.factory.BeanFactory;
import com.spring.core.beans.factory.BeanFactoryAware;
import com.spring.core.beans.factory.support.BeanNameAware;

public class Bird implements BeanNameAware, BeanFactoryAware , ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("context容器是:"+applicationContext);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("beanFactory是:"+beanFactory);
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("beanName是:"+beanName);
    }
}
