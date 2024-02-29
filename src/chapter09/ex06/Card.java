package chapter09.ex06;

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

    @Override
    public String toString() {
        return "Card{" +
                "kind='" + kind + '\'' +
                ", number=" + number +
                '}';
    }
}
