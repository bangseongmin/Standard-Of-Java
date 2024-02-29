package chapter09.ex07;

public class CloneEx1 {

    public static void main(String[] args) {
        Point original = new Point(3, 5);
        Point clone = (Point) original.clone();
        System.out.println(original);
        System.out.println(clone);
    }
}
