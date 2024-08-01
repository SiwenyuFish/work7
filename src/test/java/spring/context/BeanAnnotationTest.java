package spring.context;

import com.spring.context.support.BeanAnnotationBeanPostProcessor;
import com.spring.core.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;
import spring.bean.Config;

public class BeanAnnotationTest {

    @Test
    public void testBeanAnnotation() {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanAnnotationBeanPostProcessor beanPostProcessor = new BeanAnnotationBeanPostProcessor();
        beanPostProcessor.processBeanDefinitions(beanFactory, Config.class);
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        beanFactory.getBean("bean1");
        beanFactory.getBean("bean2");
        beanFactory.getBean("Config");
        beanFactory.getBean("bean3");
        beanFactory.getBean("bean4");
    }

}
