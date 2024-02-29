package chapter04;

/**
 * 반복문
 * p. 162
 */
public class FlowEx16 {

    public static void main(String[] args) {
        for (int i=1; i<=5; i++) {
            for (int j=1; j<=10; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
