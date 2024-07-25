package com.spring.aop;


public class ProxyFactory {
    private Object target;
    private MethodInterceptor interceptor;

    public ProxyFactory(Object target, MethodInterceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public Object getProxy() {
        if (target.getClass().getInterfaces().length > 0) {
            System.out.println("jdk");
            // 使用JDK动态代理
            return new JdkDynamicAopProxy(this).getProxy();
        } else {
            System.out.println("cglib");
            // 使用CGLIB动态代理
            return new CglibDynamicAopProxy(this).getProxy();
        }
    }

    public Object getTarget() {
        return target;
    }

    public MethodInterceptor getInterceptor() {
        return interceptor;
    }
}

