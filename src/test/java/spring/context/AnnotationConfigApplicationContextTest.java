package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;

import spring.bean.Config;

public class AnnotationConfigApplicationContextTest {

    @Test
    public void testAnnotationConfigApplicationContext(){

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }


    }


}
