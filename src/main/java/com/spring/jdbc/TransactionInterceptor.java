package com.spring.jdbc;

import com.spring.aop.MethodInterceptor;
import com.spring.aop.MethodInvocation;

import java.lang.reflect.Method;


public class TransactionInterceptor implements MethodInterceptor {

    private Object target;
    private TransactionManager transactionManager;

    public TransactionInterceptor(Object target, TransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional != null) {

            try {
                transactionManager.beginTransaction();
                Object result = method.invoke(target, args);
                transactionManager.commit();
                System.out.println("Transaction committed");
                return result;
            } catch (Exception e) {
                transactionManager.rollback();
                System.out.println("Transaction rolled back");
                throw e;
            }
        } else {
            return method.invoke(target, args);
        }
    }
}
