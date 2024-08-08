package com.spring.web.tomcat;

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

    public void start() throws LifecycleException, ServletException, IOException {

        // 1. 创建 Tomcat
        tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");
        // 2. 创建项目文件夹，即 docBase 文件夹
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();
        // 3. 创建 tomcat 项目，在 tomcat 中称为 Context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());
        // 4. 编程添加 Servlet
        context.addServletContainerInitializer((set, servletContext) -> {
            DispatcherServlet servlet = new DispatcherServlet();
            // 设置访问 Servlet 的路径
            servletContext.addServlet("DispatcherServlet", servlet).addMapping("/");
        }, Collections.emptySet());
        // 5. 启动 tomcat
        tomcat.start();
        // 6. 创建连接器，设置监听端口
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
