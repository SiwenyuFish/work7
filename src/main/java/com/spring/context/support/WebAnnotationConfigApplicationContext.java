package com.spring.context.support;

import com.spring.web.tomcat.TomcatServer;
import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;
import java.io.IOException;

public class WebAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {

    private final TomcatServer tomcatServer = new TomcatServer();

    public WebAnnotationConfigApplicationContext(Class<?>... componentClasses) throws ServletException, LifecycleException, IOException {
        this.register(componentClasses);
        this.refresh();
        tomcatServer.start();
    }

    public WebAnnotationConfigApplicationContext() throws ServletException, LifecycleException, IOException {
        this.refresh();
        tomcatServer.start();
    }

}
