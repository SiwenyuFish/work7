package spring.bean;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;

@Configuration
public class Config3 {

    @Bean
    public Bird bird(){
        return new Bird();
    }

}
