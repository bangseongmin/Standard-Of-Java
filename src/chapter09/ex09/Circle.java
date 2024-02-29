package chapter09.ex09;

public class Circle implements Cloneable {

    Point p;
    double r;

    public Circle(Point p, double r) {
        this.p = p;
        this.r = r;
    }

    public Circle shallowCopy() {
        Object obj = null;

        try {
            obj = super.clone();
        }catch (CloneNotSupportedException e) {}

        return (Circle) obj;
    }

    public Circle deepCopy() {
        Object obj = null;

        try {
            obj = super.clone();
        }catch (CloneNotSupportedException e) {}

        Circle circle = (Circle) obj;
        circle.p = new Point(this.p.x, this.p.y);

        return circle;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "p=" + p +
                ", r=" + r +
                '}';
    }
}
