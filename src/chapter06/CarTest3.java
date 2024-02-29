package chapter06;

public class CarTest3 {

    public static void main(String[] args) {
        Car3 c1 = new Car3();
        Car3 c2 = new Car3(c1);

        System.out.println(c1.toString());
        System.out.println(c2.toString());

        c1.door = 200;
        System.out.println(c1.toString());
        System.out.println(c2.toString());
    }
}
