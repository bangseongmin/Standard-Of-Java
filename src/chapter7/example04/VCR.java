package chapter7.example04;

public class VCR {

    boolean power;
    int counter = 0;

    void power() {
        this.power = !this.power;
    }

    void play() {
        System.out.println("play");
    }
    void stop() {
        System.out.println("stop");
    }

    void rew() {
        System.out.println("rew");
    }

    void ff() {
        System.out.println("ff");
    }
}
