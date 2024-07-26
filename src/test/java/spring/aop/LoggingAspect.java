package spring.aop;

import com.spring.aop.annotation.After;
import com.spring.aop.annotation.Aspect;
import com.spring.aop.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* spring.aop.MyService.*(..))")
    public void beforeAdvice() {
        System.out.println("Before annotation");
    }

    @Before("execution(* spring.aop.MyService.*(..))")
    public void beforeAdvice1() {
        System.out.println("Before method 1");
    }

    @Before("execution(* myMethod())")
    public void beforeAdvice2() {
        System.out.println("Before method 2");
    }

    @After("execution(* spring.aop.MyService.*(..))")
    public void afterAdvice() {
        System.out.println("After annotation");
    }

    @After("execution(* spring.aop.MyService.*(..))")
    public void afterAdvice1() {
        System.out.println("After method 1");
    }

    @After("execution(* spring.aop.MyService.*(..))")
    public void afterAdvice2() {
        System.out.println("After method 2");
    }

    @After("execution(* spring.aop.MyService.*(..))")
    public void afterAdvice3() {
        System.out.println("After method 3");
    }
}