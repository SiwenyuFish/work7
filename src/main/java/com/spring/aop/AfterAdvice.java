package com.spring.aop;

import java.lang.reflect.Method;

public class AfterAdvice implements Advice {
    private Object aspect;
    private Method aspectMethod;

    public AfterAdvice(Object aspect, Method aspectMethod) {
        this.aspect = aspect;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        invocation.proceed();
        return aspectMethod.invoke(aspect);
    }
}