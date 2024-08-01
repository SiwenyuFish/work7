package spring.aop;

import com.spring.aop.annotation.After;
import com.spring.aop.annotation.Aspect;
import com.spring.aop.annotation.Before;


@Aspect
public class AspectForCircular {

    @Before("execution(* spring.bean.CircularBeanA.*(..))")
    public void beforeAdvice() {
        System.out.println("Before annotation");
    }
    @After("execution(* spring.bean.CircularBeanA.*(..))")
    public void afterAdvice() {
        System.out.println("After annotation");
    }
}
