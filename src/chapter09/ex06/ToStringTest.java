package chapter09.ex06;

public class ToStringTest {

    public static void main(String[] args) {
        Card c1 = new Card();
        Card c2 = new Card("HEART", 10);

        System.out.println(c1.toString());
        System.out.println(c2.toString());
    }
}
