package com.spring.web.servlet.method;

import com.spring.web.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Method method;
    private final Parameter parameter;
    private final int parameterIndex;

    public MethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameter = method.getParameters()[parameterIndex];
        this.parameterIndex = parameterIndex;
    }

    public Method getMethod() {
        return method;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

    public boolean hasParameterAnnotation(Class<? extends java.lang.annotation.Annotation> annotationType) {
        return parameter.isAnnotationPresent(annotationType);
    }

    public <T extends java.lang.annotation.Annotation> T getParameterAnnotation(Class<T> annotationType) {
        return parameter.getAnnotation(annotationType);
    }

    public boolean hasMethodAnnotation(Class<? extends java.lang.annotation.Annotation> responseBodyClass) {
        return method.isAnnotationPresent(responseBodyClass);
    }
}
