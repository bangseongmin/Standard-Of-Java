package chapter07.example26;

public class RepairableTest {

    public static void main(String[] args) {
        Tank tank = new Tank();
        DropShip dropShip = new DropShip();

        Marine marine = new Marine();
        SCV scv = new SCV();

        scv.repair(tank);
        scv.repair(dropShip);
//        scv.repair(marine);
    }
}
