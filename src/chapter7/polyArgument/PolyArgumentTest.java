package chapter7.polyArgument;

public class PolyArgumentTest {

    public static void main(String[] args) {
        Buyer b = new Buyer();

        b.buy(new TV());
        b.buy(new Computer());

        System.out.println("현재 남은 돈은 " + b.money + "만원");
        System.out.println("현재 보너스 점수는 " + b.bonusPoint + "점");
    }

}
