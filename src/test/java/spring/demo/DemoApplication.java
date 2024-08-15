package spring.demo;

import com.spring.boot.SpringApplication;
import com.spring.core.factory.annotation.ComponentScan;
import com.spring.core.factory.annotation.Configuration;

@Configuration
@ComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }
}
