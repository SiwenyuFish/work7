package spring.bean;

import com.spring.core.beans.factory.annotation.Bean;
import com.spring.core.beans.factory.annotation.Configuration;

@Configuration
public class Config {

    static {
        System.out.println("hi,Config");
    }


    @Bean
    public Bean1 bean1(){
        return new Bean1();
    }

    @Bean
    public Bean2 bean2(){
        return new Bean2();
    }


    @Bean
    Bean3 bean3(){
        return new Bean3();
    }

    @Bean
    Bean4 bean4(){
        return new Bean4();
    }



    static class Bean3{
        public Bean3(){
            System.out.println("hi,bean3");
        }
    }

    static class Bean4{
        public Bean4(){
            System.out.println("hi,bean4");
        }

    }


}
