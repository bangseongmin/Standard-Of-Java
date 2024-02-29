package chapter09.ex10;

public final class Card {

    String kind;
    int num;

    public Card() {
        this("SPADE", 1);
    }

    public Card(String kind, int num) {
        this.kind = kind;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Card{" +
                "kind='" + kind + '\'' +
                ", num=" + num +
                '}';
    }

}
