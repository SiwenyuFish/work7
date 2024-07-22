package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.Config3;

public class AwareTest {

    @Test
    public void testAware(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
    }

}
