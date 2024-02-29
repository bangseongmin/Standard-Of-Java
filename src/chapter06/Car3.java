package chapter06;

public class Car3 {

    String color;
    String gearType;
    int door;

    Car3() {
        this("whilte", "auto", 4);
    }

    Car3(Car3 car) {
        this.color = car.color;
        this.gearType = car.gearType;
        this.door = car.door;
    }

    Car3(String c, String g, int d) {
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
