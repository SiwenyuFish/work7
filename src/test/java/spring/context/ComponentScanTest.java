package spring.context;

import com.spring.context.support.AnnotationConfigApplicationContext;
import org.junit.Test;
import spring.bean.Fish;
import spring.bean.Fox;
import spring.bean.scan.ScanConfig;
import spring.bean.scan.ScanConfig1;

public class ComponentScanTest {

    @Test
    public void testComponentScan() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
        Fish fish = context.getBean(Fish.class);
        Fox fox = context.getBean(Fox.class);
        System.out.println(fish);
        System.out.println(fox);
    }

    @Test
    public void testComponentScan2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig1.class);
        Fox fox = context.getBean(Fox.class);
        System.out.println(fox);
    }

}
