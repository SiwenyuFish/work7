package spring.bean.scan;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;
import spring.bean.Fox;

@Configuration
public class ScanConfig3 {

    @Bean
    public Fox fox(){
        Fox fox = new Fox();
        fox.setColor("red");
        fox.setName("hu");
        return fox;
    }

}
