package chapter08;

public class FinallyTest3 {

    public static void main(String[] args) {
        FinallyTest3.method1();
        System.out.println("method1()의 수행을 마치고 main메서드로 돌아왔습니다.");
    }

    public static void method1() {
        try {
            System.out.println("method1() 호출");
            return;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("method1()의 finally블럭 실행");
        }
    }
}
