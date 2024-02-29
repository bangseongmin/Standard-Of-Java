package chapter07.example02;

public class Circle extends Shape {

    Point center;       // 원의 원점좌표
    int r;              // 반지름

    Circle() {
        this(new Point(0, 0), 100);     // Circle(Point center, int r)를 호출
    }

    Circle(Point center, int r) {
        this.center = center;
        this.r = r;
    }

    void draw() {
        System.out.printf("[center=(%d, %d), r=%d, color=%s]\n", center.x, center.y, r, color);
    }

}
