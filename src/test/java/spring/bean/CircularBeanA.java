package spring.bean;

public class CircularBeanA {


    private CircularBeanB circularBeanB;

    public void aaa(){
        System.out.println("CircularBeanA");
    }

    public void setCircularBeanB(CircularBeanB circularBeanB) {
        this.circularBeanB = circularBeanB;
    }
}
