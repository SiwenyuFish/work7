package com.spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class CglibDynamicAopProxy implements MethodInterceptor ,AopProxy{

    private ProxyFactory proxyFactory;
    private Object target;

    public CglibDynamicAopProxy(ProxyFactory proxyFactory) {
       this.proxyFactory = proxyFactory;
        this.target = proxyFactory.getTarget();
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibDynamicAopProxy(proxyFactory));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return proxyFactory.getInterceptor().invoke(new CglibMethodInvocation(target, method, args, proxy));
    }

    private static class CglibMethodInvocation implements MethodInvocation {
        private Object target;
        private Method method;
        private Object[] arguments;
        private MethodProxy proxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy proxy) {
            this.target = target;
            this.method = method;
            this.arguments = arguments;
            this.proxy = proxy;
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
            return proxy.invoke(target, arguments);
        }
    }
}






