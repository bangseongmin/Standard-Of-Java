package chapter12.ex11;

import java.util.ArrayList;

public class AnnotationEx3 {

    @SuppressWarnings("deprecations")
    public static void main(String[] args) {
        NewClass nc = new NewClass();

        nc.oldField = 13;                                   // Deprecated가 붙은 대상 사용
        System.out.println(nc.getOldField());               // Deprecated가 붙은 대상 사용

        @SuppressWarnings("unchcked")
        ArrayList<NewClass> list = new ArrayList<>();       // 제네릭 관련 경고 억제
        list.add(nc);
    }
}

class NewClass {
    int newField;

    public int getNewField() {
        return newField;
    }

    @Deprecated
    int oldField;

    @Deprecated
    public int getOldField() {
        return oldField;
    }
}
