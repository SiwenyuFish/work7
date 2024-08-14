package spring.bean.scan2;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Component;
import spring.bean.Fox;

@Component
public class ScanConfig4 {

    @Bean
    public Fox fox(){
        Fox fox = new Fox();
        fox.setColor("white");
        fox.setName("hu");
        return fox;
    }

}
