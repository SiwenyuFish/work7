package com.spring.web.servlet.method;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Method method;
    private Parameter parameter;
    private int parameterIndex;

    public MethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameter = method.getParameters()[parameterIndex];
        this.parameterIndex = parameterIndex;
    }

    public MethodParameter(Method method) {
        this.method = method;
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

    public Class<?> getReturnType() {
        return method.getReturnType();
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
