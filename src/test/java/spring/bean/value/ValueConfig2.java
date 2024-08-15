package spring.bean.value;

import com.spring.core.factory.annotation.Bean;
import com.spring.core.factory.annotation.Component;

@Component
public class ValueConfig2 {
    @Bean
    public Dragon dragon(){
        Dragon dragon = new Dragon();
        dragon.setColor("gold");
        dragon.setName("longming");
        return dragon;
    }
}
