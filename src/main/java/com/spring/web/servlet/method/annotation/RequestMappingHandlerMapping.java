package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.Controller;
import com.spring.web.annotation.RequestMapping;
import com.spring.web.servlet.HandlerMapping;
import com.spring.web.servlet.method.HandlerMethod;
import com.spring.web.servlet.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping {
    private final Map<String, HandlerMethod> handlerMethods = new HashMap<>();

    public RequestMappingHandlerMapping() {
        // 扫描所有控制器，找到@RequestMapping注解的方法并注册
        detectHandlers("com.spring.mvc.controller");
    }

    private void detectHandlers(String basePackage) {
        String packagePath = basePackage.replace('.', '/');
        URL resource = getClass().getClassLoader().getResource(packagePath);
        if (resource == null) {
            return;
        }

        File directory = new File(resource.getFile());
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        for (String fileName : directory.list()) {
            if (fileName.endsWith(".class")) {
                String className = basePackage + '.' + fileName.substring(0, fileName.length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        // 找到控制器类，遍历其方法
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                // 找到@RequestMapping注解的方法
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                String path = "/spring_war"+requestMapping.value();
                                handlerMethods.put(path, new HandlerMethod(clazz.newInstance(), method));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        String path = request.getRequestURI();
        HandlerMethod handlerMethod = handlerMethods.get(path);
        if (handlerMethod != null) {
            return new HandlerExecutionChain(handlerMethod);
        }
        return null;
    }
}
