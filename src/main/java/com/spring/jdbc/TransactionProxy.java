package com.spring.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class TransactionProxy implements InvocationHandler {

    private Object target;
    private TransactionManager transactionManager;

    public TransactionProxy(Object target, TransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional != null) {
            TransactionDefinition definition = new TransactionDefinition(transactional.propagation());
            try {
                transactionManager.beginTransaction(definition);
                Object result = method.invoke(target, args);
                if (!definition.isNested()) {
                    transactionManager.commit();
                }
                return result;
            } catch (Exception e) {
                transactionManager.rollback();
                throw e;
            }
        } else {
            return method.invoke(target, args);
        }
    }

    public static Object createProxy(Object target, TransactionManager transactionManager) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TransactionProxy(target, transactionManager));
    }
}