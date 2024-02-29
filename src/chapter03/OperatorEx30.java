package chapter03;

/**
 * 비트 연산자(시프트 연산자)
 * p. 127
 */
public class OperatorEx30 {

    public static void main(String[] args) {
        int dec = 8;

        System.out.printf("%d >> %d = %4d \t%s%n", dec, 0, dec>>0, toBinaryString(dec>>0));
        System.out.printf("%d >> %d = %4d \t%s%n", dec, 1, dec>>1, toBinaryString(dec>>1));
        System.out.printf("%d >> %d = %4d \t%s%n", dec, 2, dec>>2, toBinaryString(dec>>2));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 0, dec<<0, toBinaryString(dec<<0));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 1, dec<<1, toBinaryString(dec<<1));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 2, dec<<2, toBinaryString(dec<<2));
        System.out.println();

        dec = -8;

        System.out.printf("%d >> %d = %4d \t%s%n", dec, 0, dec>>0, toBinaryString(dec>>0));
        System.out.printf("%d >> %d = %4d \t%s%n", dec, 1, dec>>1, toBinaryString(dec>>1));
        System.out.printf("%d >> %d = %4d \t%s%n", dec, 2, dec>>2, toBinaryString(dec>>2));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 0, dec<<0, toBinaryString(dec<<0));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 1, dec<<1, toBinaryString(dec<<1));
        System.out.printf("%d << %d = %4d \t%s%n", dec, 2, dec<<2, toBinaryString(dec<<2));
        System.out.println();

        dec = 8;

        System.out.printf("%d >> %2d = %4d \t%s%n", dec, 0, dec>>0, toBinaryString(dec>>0));
        System.out.printf("%d >> %2d = %4d \t%s%n", dec, 32, dec>>32, toBinaryString(dec>>32));
    }

    static String toBinaryString(int x) {	// 10진 정수를 2진수로 변환하는 메서드
        String zero = "0000000000000000000000000000000";
        String tmp = zero + Integer.toBinaryString(x);
        return tmp.substring(tmp.length()-32);
    }
}
