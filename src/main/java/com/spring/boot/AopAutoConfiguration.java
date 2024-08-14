package com.spring.boot;

import com.spring.aop.DefaultAdvisorAutoProxyCreator;
import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;

@Configuration
public class AopAutoConfiguration {

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

}
