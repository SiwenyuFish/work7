package spring.bean.value;

import com.spring.core.factory.annotation.Component;
import com.spring.core.factory.annotation.Value;

@Component
public class Dragon {

    @Value("${dragon.name}")
    private String name;
    @Value("${dragon.color}")
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
