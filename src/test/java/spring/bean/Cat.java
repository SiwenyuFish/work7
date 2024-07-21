package spring.bean;

public class Cat {
    private String name;
    private Fish fish;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", fish=" + fish +
                '}';
    }

    public void init() {
        System.out.println("Cat is initialized");
    }

    public void destroy() {
        System.out.println("Cat is destroyed");
    }

}
