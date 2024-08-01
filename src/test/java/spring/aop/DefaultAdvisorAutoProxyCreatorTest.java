package spring.aop;

import com.spring.aop.DefaultAdvisorAutoProxyCreator;;
import com.spring.context.support.BeanAnnotationBeanPostProcessor;
import com.spring.context.support.GenericApplicationContext;
import org.junit.Test;

public class DefaultAdvisorAutoProxyCreatorTest {

    @Test
    public void testDefaultAdvisorAutoProxyCreator(){


        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(LoggingAspect.class);
        context.registerBean(BeanAnnotationBeanPostProcessor.class);
        context.registerBean(MyService.class);
        context.registerBean(DefaultAdvisorAutoProxyCreator.class);
        context.refresh();



        MyService proxy = (MyService) context.getBean("MyService");


        // 调用代理方法
        proxy.myMethod();
    }

}
