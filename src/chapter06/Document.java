package chapter06;

public class Document {

    static int count = 0;
    String name;                // 문서명(Document name)

    Document() {
        this("제목없음" + ++count);
    }

    Document(String name) {
        this.name = name;
        System.out.println("문서 " + this.name + "가 생성되었습니다.");
    }
}
