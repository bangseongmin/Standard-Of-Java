package chapter06;

public class Car2 {

    String color;
    String gearType;
    int door;

    Car2() {
        this("whilte", "auto", 4);
    }

    Car2(String color) {
        this(color, "auto", 4);
    }

    Car2(String c, String g, int d) {
        this.color = c;
        this.gearType = g;
        this.door = d;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", gearType='" + gearType + '\'' +
                ", door=" + door +
                '}';
    }
}
