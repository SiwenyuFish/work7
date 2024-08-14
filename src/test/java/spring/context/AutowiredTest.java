package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.autowired.AutowiredConfig;
import spring.bean.autowired.King;
import spring.bean.value.Dragon;

public class AutowiredTest {

    @Test
    public void testAutowired(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        King bean = context.getBean(King.class);
        System.out.println(bean);
    }

    @Test
    public void testAutowired2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(King.class, Dragon.class);
        King bean = context.getBean(King.class);
        System.out.println(bean);
    }

}
