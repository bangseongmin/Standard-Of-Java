package chapter8.customException;

public class NewExceptionTest {

    public static void main(String[] args) {
        try {
            startInstall();
            copyFiles();
        } catch (SpaceException e) {
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            System.out.println("공간을 확보한 후에 다시 설치해주세요.");
        } catch (MemoryException e) {
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            System.gc();
            System.out.println("다시 설치를 시도해주세요.");
        } finally {
            deleteTempFiles();
        }
    }

    private static void deleteTempFiles() {
        System.out.println("deleteTempFiles");
    }

    private static void copyFiles() {
        System.out.println("copyFiles");
    }

    private static void startInstall() throws SpaceException, MemoryException {
        if(!enoughSpace()) {
            throw new SpaceException("설치할 공간이 부족");
        }
        if(!enoughMemory()){
            throw new MemoryException("메모리 부족");
        }
    }

    private static boolean enoughMemory() {
        return false;
    }

    private static boolean enoughSpace() {
        return true;
    }
}
