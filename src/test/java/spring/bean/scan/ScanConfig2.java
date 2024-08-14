package spring.bean.scan;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Component;
import spring.bean.Fish;

@Component
public class ScanConfig2 {

    @Bean
    public Fish fish(){
        Fish fish = new Fish();
        fish.setName("scanFish");
        fish.setId(258);
        return fish;
    }


}
