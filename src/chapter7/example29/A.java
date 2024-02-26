package chapter7.example29;

public class A {

    public void methodA() {
        I i = InstanceManager.getInstance();
        i.methodB();
        System.out.println(i.toString());
    }
}
