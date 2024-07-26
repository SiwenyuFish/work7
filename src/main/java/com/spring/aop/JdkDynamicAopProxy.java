package com.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements InvocationHandler,AopProxy {


    private ProxyFactory proxyFactory;
    private Object target;
    private MethodInterceptor interceptor;

    public JdkDynamicAopProxy(ProxyFactory proxyFactory) {
       this.proxyFactory = proxyFactory;
       this.target = proxyFactory.getTarget();
       this.interceptor = proxyFactory.getInterceptor();
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return interceptor.invoke(new ReflectiveMethodInvocation(target, method, args));
    }

    private static class ReflectiveMethodInvocation implements MethodInvocation {
        private Object target;
        private Method method;
        private Object[] arguments;

        public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
            this.target = target;
            this.method = method;
            this.arguments = arguments;
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object[] getArguments() {
            return arguments;
        }

        @Override
        public Object proceed() throws Throwable {
            return method.invoke(target, arguments);
        }
    }
}



