package spring.aop;

import com.spring.aop.MethodInterceptor;
import com.spring.aop.MethodInvocation;
import com.spring.aop.ProxyFactory;
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
        ProxyFactory proxyFactory = new ProxyFactory(target, new LoggingInterceptor());
        JdkService proxy = (JdkService) proxyFactory.getProxy();
        proxy.jdkMethod();

   }

    @Test
    public void testProxyFactory2() {
       MyService target = new MyService();
       ProxyFactory proxyFactory = new ProxyFactory(target, new LoggingInterceptor());
       MyService proxy = (MyService) proxyFactory.getProxy();
       proxy.myMethod();
    }



}
