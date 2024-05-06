package chapter15.file;

import java.io.File;
import java.io.IOException;

public class FileEx2 {

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("USAGE : java FileEx2 DIRECTORY");
            System.exit(0);
        }

        File f = new File(args[0]);

        if(!f.exists() || !f.isDirectory()) {
            System.out.println("유효하지 않은 디렉토리입니다.");
            System.exit(0);
        }

        File[] files = f.listFiles();

        for(int i=0; i < files.length; i++) {
            String fileName = files[i].getName();
            System.out.println(files[i].isDirectory() ? "["+fileName+"]" : fileName);
        }
    }
}
