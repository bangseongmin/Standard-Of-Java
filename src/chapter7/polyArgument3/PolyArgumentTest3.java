package chapter7.polyArgument3;

public class PolyArgumentTest3 {

    public static void main(String[] args) {
        Buyer b = new Buyer();
        TV tv = new TV();
        Computer computer = new Computer();
        Audio audio = new Audio();

        b.buy(tv);
        b.buy(computer);
        b.buy(audio);
        b.summary();
        System.out.println();
        b.refund(computer);
        b.summary();
    }
}
