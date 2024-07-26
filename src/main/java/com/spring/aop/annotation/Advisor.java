package com.spring.aop.annotation;

import com.spring.aop.Advice;
import com.spring.aop.AspectJExpressionPointcut;

public class Advisor {
    private Advice advice;
    private AspectJExpressionPointcut pointcut;

    public Advisor(Advice advice, AspectJExpressionPointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public AspectJExpressionPointcut getPointcut() {
        return pointcut;
    }
}
