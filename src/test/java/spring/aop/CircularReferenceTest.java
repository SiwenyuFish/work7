package spring.aop;

import com.spring.aop.DefaultAdvisorAutoProxyCreator;
import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.CircularBeanA;
import spring.bean.CircularBeanB;
import spring.bean.CircularConfig;

public class CircularReferenceTest {

    @Test
    public void testCircularReference(){

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CircularConfig.class, AspectForCircular.class, DefaultAdvisorAutoProxyCreator.class);

        CircularBeanA circularBeanA = (CircularBeanA) ctx.getBean("circularBeanA");
        CircularBeanB circularBeanB = (CircularBeanB) ctx.getBean("circularBeanB");
        circularBeanA.aaa();
        circularBeanB.bbb();
    }

    @Test
    public void testCircularReference2(){

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CircularConfig.class);

        CircularBeanA circularBeanA = (CircularBeanA) ctx.getBean("circularBeanA");
        CircularBeanB circularBeanB = (CircularBeanB) ctx.getBean("circularBeanB");

        System.out.println(circularBeanA);
        System.out.println(circularBeanB);

    }

}
