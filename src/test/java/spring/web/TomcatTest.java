package spring.web;

import com.spring.context.support.WebAnnotationConfigApplicationContext;

import org.apache.catalina.LifecycleException;
import org.junit.Test;
import spring.web.bean.WebConfig;
import spring.web.controller.HomeController;


import javax.servlet.ServletException;
import java.io.IOException;

public class TomcatTest {

    @Test
    public void testTomcat() throws ServletException, LifecycleException, IOException {
        WebAnnotationConfigApplicationContext context = new WebAnnotationConfigApplicationContext(WebConfig.class);

    }

    public static void main(String[] args) throws ServletException, LifecycleException, IOException {
        WebAnnotationConfigApplicationContext context = new WebAnnotationConfigApplicationContext(WebConfig.class, HomeController.class );
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }

}
