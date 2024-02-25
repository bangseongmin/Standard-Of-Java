package chapter7.example26;

public class Tank extends GroundUnit implements Repairable {

    public Tank() {
        super(150);
        hitPoint = MAX_HP;
    }

    @Override
    public String toString() {
        return "Tank";
    }
}
