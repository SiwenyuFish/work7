package com.spring.aop;

public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}

