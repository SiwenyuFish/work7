package spring.bean;

public class CircularBeanB {

    private CircularBeanA circularBeanA;


    public void bbb(){
        System.out.println("CircularBeanB");
    }

    public void setCircularBeanA(CircularBeanA circularBeanA) {
        this.circularBeanA = circularBeanA;
    }

    @Override
    public String toString() {
        return "CircularBeanB{" +
                "circularBeanA=" + circularBeanA +
                '}';
    }
}
