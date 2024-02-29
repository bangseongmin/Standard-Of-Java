package chapter07.example30;

public class Child extends Parent implements MyInterface, MyInterface2 {

    @Override
    public void method1() {
        System.out.println("method1() in Child");       // 오버라이딩
    }

}
