package chapter09.ex09;

public class ShallowDeepCopy {

    public static void main(String[] args) {
        Circle c1 = new Circle(new Point(1, 1), 2.0);
        Circle c2 = c1.shallowCopy();
        Circle c3 = c1.deepCopy();

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());

        c1.p.x = 9;
        c1.p.y = 8;

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());

    }
}
