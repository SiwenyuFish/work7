package com.spring.web.tomcat;

import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws ServletException, LifecycleException, IOException {
        TomcatServer server = new TomcatServer();
        server.start();
    }
}
