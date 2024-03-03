package chapter12.ex10;


public class AnnotationEx2 {
    /**
     * 컴파일 시
     * Note: AnnotationEx2.java uses or overrides a deprecated API.
     * Note: Recompile with -Xlint:deprecation for details.
     */
    public static void main(String[] args) {
        NewClass nc = new NewClass();
        nc.oldField = 10;
        System.out.println(nc.getOldField());
    }
}

class NewClass {

    int newField;

    int getNewField() {
        return this.newField;
    }

    @Deprecated
    int oldField;

    @Deprecated
    public int getOldField() {
        return oldField;
    }
}