package spring.bean;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;

@Configuration
public class CircularConfig {

    @Bean
    public CircularBeanA circularBeanA(CircularBeanB b){
        CircularBeanA circularBeanA = new CircularBeanA();
        circularBeanA.setCircularBeanB(b);

        return circularBeanA;
    }

    @Bean
    public CircularBeanB circularBeanB(CircularBeanA a){
        CircularBeanB circularBeanB = new CircularBeanB();
        circularBeanB.setCircularBeanA(a);

        return circularBeanB;
    }




}
