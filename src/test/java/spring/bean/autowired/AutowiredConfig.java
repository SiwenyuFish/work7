package spring.bean.autowired;

import com.spring.core.factory.annotation.Bean;
import com.spring.core.factory.annotation.Component;
import spring.bean.value.Dragon;

@Component
public class AutowiredConfig {

    @Bean
    public King king(){
        return new King();
    }

    @Bean
    public Dragon dragon(){
        Dragon dragon = new Dragon();
        dragon.setColor("gold");
        return dragon;
    }

}
