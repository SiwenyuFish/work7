package com.spring.web.servlet.method.annotation;

import com.spring.web.annotation.*;
import com.spring.web.servlet.HandlerInterceptor;
import com.spring.web.servlet.handler.AbstractHandlerMethodMapping;
import com.spring.web.servlet.method.HandlerMethod;
import com.spring.web.servlet.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerMapping extends AbstractHandlerMethodMapping {

    private Map<String, HandlerMethod> handlerMethods = new HashMap<>();
    private List<HandlerInterceptor> handlerInterceptors = new ArrayList<>();

    public RequestMappingHandlerMapping() {
        // 扫描所有控制器，找到@RequestMapping注解的方法并注册
        //detectHandlers("com.spring.web.controller");
        //detectHandlers("spring.web.controller");
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
                        for (Method method : clazz.getDeclaredMethods()) {
                            // 检查是否有 @RequestMapping 注解
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            if (requestMapping != null) {
                                registerHandlerMethod(method, requestMapping);
                            } else {
                                // 检查派生注解
                                checkAndRegisterDerivedAnnotations(method, clazz);
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
    protected void registerInterceptor(HandlerInterceptor interceptor) {
        handlerInterceptors.add(interceptor);
    }

    protected void checkAndRegisterDerivedAnnotations(Method method, Class<?> clazz) throws Exception {
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            registerHandlerMethod(method, new String[]{annotation.value()}, "GET", clazz);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping annotation = method.getAnnotation(PostMapping.class);
            registerHandlerMethod(method, new String[]{annotation.value()}, "POST", clazz);
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping annotation = method.getAnnotation(PutMapping.class);
            registerHandlerMethod(method, new String[]{annotation.value()}, "PUT", clazz);
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
            registerHandlerMethod(method, new String[]{annotation.value()}, "DELETE", clazz);
        }
    }

    private void registerHandlerMethod(Method method, String[] paths, String methodType, Class<?> clazz) throws Exception {
        String path = paths.length > 0 ? paths[0] : ""; // 使用第一个路径
        handlerMethods.put(path, new HandlerMethod(clazz.newInstance(), method));
        //System.out.println(handlerMethods);
    }

    protected void registerHandlerMethod(Method method, RequestMapping requestMapping) throws InstantiationException, IllegalAccessException {
        String path = requestMapping.value().length > 0 ? requestMapping.value()[0] : "";
        handlerMethods.put(path, new HandlerMethod(method.getDeclaringClass().newInstance(), method));
        //System.out.println(handlerMethods);
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        String path = request.getRequestURI();
        HandlerMethod handlerMethod = handlerMethods.get(path);
        //System.out.println(handlerMethods);
        if (handlerMethod != null) {
            return new HandlerExecutionChain(handlerMethod, handlerInterceptors);
        }
        return null;
    }
}
