package com.spring.boot;


import cn.hutool.core.lang.Assert;
import com.spring.context.config.ApplicationContext;
import com.spring.context.support.AnnotationConfigApplicationContext;
import com.spring.jdbc.JdbcTemplate;
import com.spring.web.tomcat.TomcatServer;
import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;
import java.io.IOException;


public class SpringApplication {

    public static void run(Class<?> componentClass,String[] args)  {

        TomcatServer tomcatServer = new TomcatServer();

        ApplicationContext context = new AnnotationConfigApplicationContext(componentClass,
                AopAutoConfiguration.class,
                WebAutoConfiguration.class,
                TransactionAutoConfiguration.class);

        try {
            tomcatServer.start(context);
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }


    }

}
