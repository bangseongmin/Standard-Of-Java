package chapter13;

public class ThreadEx10 implements Runnable {

    static boolean autoSave = false;

    public static void main(String[] args) {
        Thread t = new Thread(new ThreadEx10());
        t.setDaemon(true);      // 이 부분이 없으면 종료되지 않는다.
        t.start();

        for(int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }

            System.out.println(i);
            if(i == 5) {
                autoSave = true;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3 * 1000);
            }catch (InterruptedException e) {}

            if(autoSave) {
                autoSave();
            }
        }
    }

    private void autoSave() {
        System.out.println("작업파일이 자동저장되었습니다.");
    }
}
