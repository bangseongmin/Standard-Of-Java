package chapter07.example01;

public class TV {

    boolean power;      // 전원상태(on/off)
    int channel;        // 채널

    void power() { power = !power; }
    void channelUp() { this.channel++; }
    void channelDown() { this.channel--; }
}
