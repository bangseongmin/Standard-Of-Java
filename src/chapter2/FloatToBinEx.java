package chapter2;

/**
 * 부동소수점의 오차
 * p. 73
 */
public class FloatToBinEx {

    public static void main(String[] args) {
        float f = 9.1234567f;
        int i = Float.floatToIntBits(f);

        System.out.printf("%f%n", f);
        System.out.printf("%X%n", i);
    }
}
