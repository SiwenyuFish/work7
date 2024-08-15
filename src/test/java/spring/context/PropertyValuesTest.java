package spring.context;

import com.spring.core.PropertyValue;
import com.spring.core.PropertyValues;
import com.spring.core.factory.config.BeanDefinition;
import com.spring.core.factory.support.DefaultListableBeanFactory;
import org.junit.Test;
import spring.bean.Fish;


public class PropertyValuesTest {

    @Test
    public void testPropertyValues() {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("id", "666"));
        propertyValues.addPropertyValue(new PropertyValue("name", "yu"));

        BeanDefinition beanDefinition = new BeanDefinition(Fish.class, propertyValues);
        beanFactory.registerBeanDefinition("fish", beanDefinition);

        Fish fish = (Fish) beanFactory.getBean("fish");
        System.out.println("=============分割线=============");
        System.out.println(fish);

    }

}
