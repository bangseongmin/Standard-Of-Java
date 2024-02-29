package chapter06;

public class Car {

    String color;
    String gearType;
    int door;

    Car() {}
    Car(String c, String g, int d) {
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
