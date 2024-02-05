package chapter5;

/**
 * 배열 Array - 커맨드 라인을 통해 입력받기
 * p. 212
 */
public class ArrayEx16 {

    public static void main(String[] args) {
        System.out.println("매개변수의 개수 : " + args.length);
        for (int i=0; i<args.length; i++) {
            System.out.println("args[" + i + "] = \"" + args[i] + "\"");
        }
    }
}
