package chapter09.ex01;

public class EqualsEx1 {

    public static void main(String[] args) {
        Value value1 = new Value(10);
        Value value2 = new Value(10);

        if(value1.equals(value2)) {
            System.out.println("value 1 과 value 2는 같습니다.");
        }else {
            System.out.println("value 1 과 value 2는 다릅니다.");
        }

        value1 = value2;

        if(value1.equals(value2)) {
            System.out.println("value 1 과 value 2는 같습니다.");
        }else {
            System.out.println("value 1 과 value 2는 다릅니다.");
        }
    }
}
