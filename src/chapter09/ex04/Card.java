package chapter09.ex04;

public class Card {
    String kind;
    int number;

    Card() {
        this("SPADE", 1);
    }

    public Card(String kind, int number) {
        this.kind = kind;
        this.number = number;
    }
}
