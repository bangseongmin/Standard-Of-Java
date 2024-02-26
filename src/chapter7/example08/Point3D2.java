package chapter7.example08;

public class Point3D2 extends Point {

    int z = 30;

    Point3D2(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    Point3D2() {
        this(100, 200, 300);
    }

    String getLocation() {
        return "x : " + x + ", y : " + y + ", z : " + z;
    }
}
