package chapter7.polyArgument3;

public class Product {

    int price;
    int bonusPoint;

    public Product() {
    }

    public Product(int price) {
        this.price = price;
        this.bonusPoint = (int)(price / 10.0);
    }
}
