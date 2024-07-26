package spring.aop;

import com.spring.aop.*;
import org.junit.Test;

public class ProxyFactoryTest {

    public class LoggingInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Before method: " + invocation.getMethod().getName());
            Object result = invocation.proceed();
            System.out.println("After method: " + invocation.getMethod().getName());
            return result;
        }
    }

    @Test
   public void testProxyFactory() {
        JdkService target = new JdkServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target,new LoggingInterceptor());
        JdkService proxy = (JdkService) proxyFactory.getProxy();
        proxy.jdkMethod();

   }

    @Test
    public void testProxyFactory2() {
       MyService target = new MyService();
       ProxyFactory proxyFactory = new ProxyFactory(target,new LoggingInterceptor());
       MyService proxy = (MyService) proxyFactory.getProxy();
       proxy.myMethod();
    }

    @Test
    public void testProxyFactory3() {
        MyService target = new MyService();
        LoggingAspect aspect = new LoggingAspect();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAspect(aspect);

        MyService proxy = (MyService) proxyFactory.getProxy();

        // 调用代理方法
        proxy.myMethod();
    }



}
