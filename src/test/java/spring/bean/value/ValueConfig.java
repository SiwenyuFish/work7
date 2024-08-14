package spring.bean.value;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Component;

@Component
public class ValueConfig {

    @Bean
    public Dragon dragon(){
        Dragon dragon = new Dragon();
        return dragon;
    }


}
