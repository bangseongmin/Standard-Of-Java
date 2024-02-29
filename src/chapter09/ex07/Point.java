package chapter09.ex07;

/**
 * Cloneable 인터페이스를 구현한 클래스에서만 clone()을 호출할 수 있다.
 * 이 인터페이스를 구현하지 않고 clone()을 호출하면 예외가 발생한다.
 */
public class Point implements Cloneable {

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public Point clone() {
        Object obj = null;
        try {
            obj = super.clone();
        }catch (CloneNotSupportedException e) {

        }

        return (Point) obj;
    }
}
