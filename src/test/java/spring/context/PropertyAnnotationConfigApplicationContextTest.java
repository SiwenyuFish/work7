package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.Cat;
import spring.bean.Config2;
import spring.bean.Fish;

public class PropertyAnnotationConfigApplicationContextTest {

    @Test
    public void testPropertyAnnotationConfigApplicationContext(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        Cat cat = (Cat) context.getBean("cat");
        System.out.println(cat);
        Fish fish = (Fish) context.getBean("fish");
        System.out.println(fish);
        context.registerShutdownHook();
    }

}
