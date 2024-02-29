package chapter08;

public class FinallyTest2 {

    public static void main(String[] args) {
        try {
            startInstall();     // 프로그램 설치에 필요한 준비
            copyFiles();        // 파일 복사
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            deleteTempFiles();  // 설치에 사용된 임시파일들 삭제
        }
    }

    private static void deleteTempFiles() {
        // 설치에 사용된 임시파일들 삭제
    }

    private static void copyFiles() {
        // 파일 복사
    }

    private static void startInstall() {
        // 프로그램 설치에 필요한 준비를 코드 작성
    }
}
