package chapter7.example26;

public class DropShip extends AirUnit implements Repairable {

    public DropShip() {
        super(125);
        hitPoint = MAX_HP;
    }

    @Override
    public String toString() {
        return "Dropship";
    }
}
