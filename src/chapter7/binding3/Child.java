package chapter7.binding3;

public class Child extends Parent {
    int x = 200;

    void method() {
        System.out.println("x = " + x);;
        System.out.println("super.x = " + super.x);
        System.out.println("this.x = " + this.x);
    }
}
