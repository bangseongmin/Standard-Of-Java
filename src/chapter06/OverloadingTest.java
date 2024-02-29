package chapter06;

public class OverloadingTest {

    public static void main(String[] args) {
        MyMath3 mm = new MyMath3();
        System.out.println("mm.add(3, 3) 결과: " + mm.add(3, 3));
        System.out.println("mm.add(3L, 3) 결과: " + mm.add(3L, 3));
        System.out.println("mm.add(3, 3L) 결과: " + mm.add(3, 3L));
        System.out.println("mm.add(3L, 3L) 결과: " + mm.add(3L, 3L));

        int[] a = { 100, 200, 300 };
        System.out.println("mm.add(a) 결과: " + mm.add(a));
    }
}
