package com.spring.web.tomcat;

import com.spring.core.factory.BeanFactory;
import com.spring.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;


import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

public class TomcatServer {
    private Tomcat tomcat;


    public void start(BeanFactory beanFactory) throws LifecycleException, ServletException, IOException {

        tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");

        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();

        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        context.addServletContainerInitializer((set, servletContext) -> {
            DispatcherServlet servlet = beanFactory.getBean(DispatcherServlet.class);
            // 设置访问 Servlet 的路径
            servletContext.addServlet("DispatcherServlet", servlet).addMapping("/");
        }, Collections.emptySet());

        tomcat.start();

        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        //设置常驻线程防止tomcat中途退出
        Thread awaitThread = new Thread("tomcat-await-thread"){
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设置为非守护线程
        awaitThread.setDaemon(false);
        awaitThread.start();

    }

}
