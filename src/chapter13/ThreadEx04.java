package chapter13;

public class ThreadEx04 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000_000; i++) {
            System.out.printf("%s", new String("-"));
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("소요시간1: " + (endTime1 - startTime));

        for(int i = 0; i < 1000_000; i++) {
            System.out.printf("%s", new String("|"));
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("소요시간2: " + (endTime2 - endTime1));
    }
}