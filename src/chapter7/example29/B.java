package chapter7.example29;

public class B implements I {
    @Override
    public void methodB() {
        System.out.println("method B in class");
    }

    @Override
    public String toString() {
        return "B";
    }
}
