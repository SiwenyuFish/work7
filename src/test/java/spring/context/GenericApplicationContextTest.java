package spring.context;

import com.spring.context.support.BeanAnnotationBeanPostProcessor;
import com.spring.context.support.GenericApplicationContext;
import org.junit.Test;
import spring.bean.Cat;
import spring.bean.Config2;
import spring.bean.Fish;

public class GenericApplicationContextTest {

    @Test
    public void testGenericApplicationContext(){
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(BeanAnnotationBeanPostProcessor.class);
        context.registerBean(Config2.class);
        context.refresh();

        Cat cat = (Cat) context.getBean("cat");
        System.out.println(cat);
        Fish fish = (Fish) context.getBean("fish");
        System.out.println(fish);
        context.close();
    }



}
