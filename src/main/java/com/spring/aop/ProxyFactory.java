package com.spring.aop;


import com.spring.aop.annotation.Advisor;
import com.spring.aop.annotation.After;
import com.spring.aop.annotation.Aspect;
import com.spring.aop.annotation.Before;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {

    private Object target;
    private MethodInterceptor interceptor;
    private List<Advisor> advisors = new ArrayList<>();

    public ProxyFactory(Object target, MethodInterceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public ProxyFactory(Object target) {
        this.target = target;
        this.interceptor = createInterceptorChain();
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

    //高级切面转低级切面
    public void addAspect(Object aspect) {
        if (aspect.getClass().isAnnotationPresent(Aspect.class)) {
            for (Method method : aspect.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    Before before = method.getAnnotation(Before.class);
                    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(before.value());
                    advisors.add(new Advisor(new BeforeAdvice(aspect, method), pointcut));
                }
                if (method.isAnnotationPresent(After.class)) {
                    After after = method.getAnnotation(After.class);
                    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(after.value());
                    advisors.add(new Advisor(new AfterAdvice(aspect, method), pointcut));
                }
            }
        }
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    private MethodInterceptor createInterceptorChain() {
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                return proceedWithAdvice(invocation, 0);
            }

            private Object proceedWithAdvice(MethodInvocation invocation, int index) throws Throwable {
                if (index == advisors.size()) {
                    return invocation.proceed();
                }
                Advisor advisor = advisors.get(index);

                advisor.getAdvice().invoke(new MethodInvocation() {
                    @Override
                    public Method getMethod() {
                        return invocation.getMethod();
                    }

                    @Override
                    public Object[] getArguments() {
                        return invocation.getArguments();
                    }

                    @Override
                    public Object proceed() throws Throwable {
                        return proceedWithAdvice(invocation, index + 1);
                    }

                });

                return null;
            }
        };
    }


    public Object getTarget() {
        return target;
    }

    public MethodInterceptor getInterceptor() {
        return interceptor;
    }
}

