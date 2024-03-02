package chapter12.ex4;

public class Grape extends Fruit {

    public Grape(String name, int weight) {
        super(name, weight);
    }

    @Override
    public String toString() {
        return "Grape";
    }
}
