package com.spring.web.servlet.method;

import java.lang.reflect.Method;

public class HandlerMethod {
    private final Object controller;
    private final Method method;

    public HandlerMethod(Object controller, String methodName, Class<?>... parameterTypes) {
        this.controller = controller;
        try {
            this.method = controller.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("无效的方法名或参数", e);
        }
    }

    public HandlerMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }


    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke(Object... args) throws Exception {
        return method.invoke(controller, args);
    }
}
