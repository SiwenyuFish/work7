package com.spring.aop;

import java.lang.reflect.Method;

public class BeforeAdvice implements Advice {
    private Object aspect;
    private Method aspectMethod;

    public BeforeAdvice(Object aspect, Method aspectMethod) {
        this.aspect = aspect;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        aspectMethod.invoke(aspect);
        return invocation.proceed();
    }
}
