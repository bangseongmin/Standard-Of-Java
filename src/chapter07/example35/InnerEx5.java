package chapter07.example35;

public class InnerEx5 {

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.method1();
    }
}
