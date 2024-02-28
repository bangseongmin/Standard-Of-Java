package chapter8.chainedException;

import chapter8.customException.MemoryException;
import chapter8.customException.SpaceException;

public class ChainedExceptionEx {

    public static void main(String[] args) {
        try {
            install();
        } catch (InstallException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void install() throws InstallException {
        try {
            startInstall();
            copyFiles();
        } catch (SpaceException e) {
            InstallException ie = new InstallException("설치 중 예외발생!");
            ie.initCause(e);
            throw ie;
        } catch (MemoryException e) {
            InstallException ie = new InstallException("설치 중 예외발생");
            ie.initCause(e);
            throw ie;
        } finally {
            deleteTemplates();
        }
    }

    private static void copyFiles() {
        System.out.println("called copyFiles");
    }

    private static void startInstall() throws SpaceException, MemoryException {
        System.out.println("called startInstall");
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


    private static void deleteTemplates() {
        System.out.println("called deleteTemplates");
    }
}
