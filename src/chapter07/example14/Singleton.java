package chapter07.example14;

public class Singleton {

    private static Singleton s = new Singleton();

    private Singleton() {
        // ...
    }

    public static Singleton getInstance() {
        return s == null ? new Singleton() : s;
    }
}
