package chapter07.example30;

public interface MyInterface2 {

    default void method1() {
        System.out.println("method1() in MyInterface2");
    }

    static void staticMethod() {
        System.out.println("method2() in MyInterface2");
    }
}
