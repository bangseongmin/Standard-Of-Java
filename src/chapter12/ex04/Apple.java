package chapter12.ex04;

public class Apple extends Fruit {

    public Apple(String name, int weight) {
        super(name, weight);
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
