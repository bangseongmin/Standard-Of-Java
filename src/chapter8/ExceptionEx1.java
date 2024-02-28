package chapter8;

public class ExceptionEx1 {

    public static void main(String[] args) {
        try {
            try {} catch (Exception e) { }
        } catch (Exception e) {
//            try {} catch (Exception e) { }  // 에러. 변수 e가 중복 선언되었습니다.
        }

        try {

        } catch (Exception e) {

        }
    }
}
