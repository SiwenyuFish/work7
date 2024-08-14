package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.value.Dragon;
import spring.bean.value.ValueConfig;
import spring.bean.value.ValueConfig2;

public class ValueTest {

    @Test
    public void testValue(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueConfig.class);
        Dragon dragon = context.getBean(Dragon.class);
        System.out.println(dragon);
    }

    @Test
    public void testValue2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueConfig2.class);
        Dragon dragon = context.getBean(Dragon.class);
        System.out.println(dragon);
    }

    @Test
    public void testValue3(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Dragon.class);
        Dragon dragon = context.getBean(Dragon.class);
        System.out.println(dragon);
    }

}
