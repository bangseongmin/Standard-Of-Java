package chapter13;

public class ThreadEx05 {

    static long startTime = 0;

    public static void main(String[] args) {
        ThreadEx5_1 th1 = new ThreadEx5_1();
        th1.start();
        startTime = System.currentTimeMillis();

        for(int i = 0; i< 1_000_000; i++) {
            System.out.printf("%s", new String("-"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간1: " + (endTime - startTime));
    }
}

class ThreadEx5_1 extends Thread {
    @Override
    public void run() {
        for(int i = 0; i< 1_000_000; i++) {
            System.out.printf("%s", new String("|"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간2: " + (endTime - ThreadEx05.startTime));
    }
}
