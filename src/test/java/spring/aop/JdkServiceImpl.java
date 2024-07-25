package spring.aop;

public class JdkServiceImpl implements JdkService {
    @Override
    public void jdkMethod() {
        System.out.println("JDK Method");
    }
}
