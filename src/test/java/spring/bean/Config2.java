package spring.bean;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;

@Configuration
public class Config2 {

    @Bean
    public Cat cat(Fish fish){
        Cat cat = new Cat();
        cat.setName("cat");
        cat.setFish(fish);
        return cat;
    }

    @Bean(initMethod = "customInit",destroyMethod = "customDestroy")
    public Fish fish(){
        Fish fish = new Fish();
        fish.setId(233);
        fish.setName("fish");
        return fish;
    }

}
