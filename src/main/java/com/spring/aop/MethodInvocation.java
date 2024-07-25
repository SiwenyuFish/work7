package com.spring.aop;

import java.lang.reflect.Method;

public interface MethodInvocation {
    Method getMethod();
    Object[] getArguments();
    Object proceed() throws Throwable;
}