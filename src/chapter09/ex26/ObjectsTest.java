package chapter09.ex26;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static java.util.Objects.*;

public class ObjectsTest {

    public static void main(String[] args) {
        String[][] str2D = new String[][]{{"aaa", "bbb"}, {"AAA", "BBB"}};
        String[][] str2D_2 = new String[][]{{"aaa", "bbb"}, {"AAA", "BBB"}};

        System.out.println("str2D = " + Arrays.toString(str2D));
        System.out.println("str2D_2 = " + Arrays.toString(str2D_2));

        System.out.println("equals(str2D, str2D_2)" + Objects.equals(str2D, str2D_2));
        System.out.println("deepEquals(str2D, str2D_2)" + deepEquals(str2D, str2D_2));


        System.out.println("isNull > " + isNull(null));
        System.out.println("nonNull > " + nonNull(null));
        System.out.println("hashCode > " + Objects.hashCode(null));
        System.out.println("toString > " + Objects.toString(null));

        Comparator c = String.CASE_INSENSITIVE_ORDER;

        System.out.println("compare(\"aa\", \"bb\") = " + compare("aa", "bb", c));
        System.out.println("compare(\"bb\", \"aa\") = " + compare("bb", "aa", c));
        System.out.println("compare(\"ab\", \"AB\") = " + compare("ab", "AB", c));
    }
}
