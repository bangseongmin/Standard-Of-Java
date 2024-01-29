package chapter2;

/**
 * 형식화된 출력 printf()
 * p. 37
 */
public class PrintfEx1 {

    public static void main(String[] args) {
        byte b = 1;
        short s = 2;
        char c = 'A';

        int finger = 10;
        long big = 100_000_000_000L;
        long hex = 0xFFFF_FFFF_FFFF_FFFFL;

        int octNum = 010;           //  8진수 10 -> 10진수 8
        int hexNum = 0x10;          // 16진수 10 -> 10진수 16
        int binNum = 0b10;          //  2진수 10 -> 10진수 2

        System.out.printf("b=%d\n", b);
        System.out.printf("s=%d\n", s);
        System.out.printf("c=%c, %d\n", b, (int)c);

        System.out.printf("finger=[%5d]\n", finger);
        System.out.printf("finger=[%-5d]\n", finger);
        System.out.printf("finger=[%05d]\n", finger);

        System.out.printf("big=%d\n", big);
        System.out.printf("hex=%#x\n", hex);        // '#'은 접두사
        System.out.printf("octNum=%o, %d\n", octNum, octNum);
        System.out.printf("hexNum=%x, %d\n", hexNum, hexNum);
        System.out.printf("binNum=%s, %d\n", Integer.toBinaryString(binNum), binNum);
    }
}
