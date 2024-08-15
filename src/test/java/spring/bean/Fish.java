package spring.bean;

import com.spring.core.factory.config.DisposableBean;
import com.spring.core.factory.config.InitializingBean;

public class Fish implements InitializingBean, DisposableBean {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void customInit() {
        System.out.println("自定义Fish is initialized");
    }

    public void customDestroy() {
        System.out.println("自定义Fish is destroyed");
    }

    public void destroy() {
        System.out.println("Fish is destroyed");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Fish is initialized");

    }
}
