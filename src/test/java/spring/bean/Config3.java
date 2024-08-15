package spring.bean;

import com.spring.core.factory.annotation.Bean;
import com.spring.core.factory.annotation.Configuration;

@Configuration
public class Config3 {

    @Bean
    public Bird bird(){
        return new Bird();
    }

}
