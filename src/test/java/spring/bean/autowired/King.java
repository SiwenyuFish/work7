package spring.bean.autowired;

import com.spring.core.beans.factory.annotation.Autowired;
import com.spring.core.beans.factory.annotation.Component;
import com.spring.core.beans.factory.annotation.Value;
import spring.bean.value.Dragon;

@Component
public class King {

    @Value("${king.name}")
    private String name;
    @Autowired
    private Dragon dragon;

    public void setName(String name) {
        this.name = name;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    public String getName() {
        return name;
    }

    public Dragon getDragon() {
        return dragon;
    }

    @Override
    public String toString() {
        return "King{" +
                "name='" + name + '\'' +
                ", dragon=" + dragon +
                '}';
    }
}
