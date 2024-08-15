package spring.context;

import com.spring.core.PropertyValue;
import com.spring.core.PropertyValues;
import com.spring.core.factory.config.BeanDefinition;
import com.spring.core.factory.config.BeanReference;
import com.spring.core.factory.support.DefaultListableBeanFactory;
import org.junit.Test;
import spring.bean.Cat;
import spring.bean.Fish;

public class BeanReferenceTest {

    @Test
    public void testBeanReference() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues pv1 = new PropertyValues();

        pv1.addPropertyValue(new PropertyValue("id", "666"));
        pv1.addPropertyValue(new PropertyValue("name", "yu"));

        BeanDefinition beanDefinition = new BeanDefinition(Fish.class, pv1);
        beanFactory.registerBeanDefinition("fish", beanDefinition);

        Fish fish = (Fish) beanFactory.getBean("fish");
        System.out.println("=============分割线=============");
        System.out.println(fish);

        PropertyValues pv2 = new PropertyValues();

        pv2.addPropertyValue(new PropertyValue("name", "hajimi"));
        pv2.addPropertyValue(new PropertyValue("fish", new BeanReference("fish")));

        BeanDefinition beanDefinition2 = new BeanDefinition(Cat.class, pv2);
        beanFactory.registerBeanDefinition("cat", beanDefinition2);

        Cat cat = (Cat) beanFactory.getBean("cat");
        System.out.println(cat);
    }
}
