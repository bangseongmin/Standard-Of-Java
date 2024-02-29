package chapter09.ex02;

public class EqualsEx2 {

    public static void main(String[] args) {
        Person p1 = new Person(10L);
        Person p2 = new Person(10L);

        if(p1 == p2) {
            System.out.println("p1과 p2는 같은 사람입니다.");
        }else {
            System.out.println("p1과 p2는 다른 사람입니다.");
        }

        if(p1.equals(p2)) {
            System.out.println("p1과 p2는 같은 사람입니다.");
        }else {
            System.out.println("p1과 p2는 다른 사람입니다.");
        }
    }
}
