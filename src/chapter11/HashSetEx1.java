package chapter11;

import java.util.HashSet;
import java.util.Set;

public class HashSetEx1 {

    public static void main(String[] args) {
        Object[] objArr = { "1", new Integer(1), "2", "2", "3", "3","3", "4", "4", "4", "4"};
        Set set = new HashSet();

        for(int i=0; i<objArr.length; i++) {
            boolean result = set.add(objArr[i]);
            System.out.printf("add(%s) : %s\n", objArr[i], result);
        }

        System.out.println(set);
    }
}
