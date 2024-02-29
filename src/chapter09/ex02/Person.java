package chapter09.ex02;

public class Person {
    long id;

    public Person(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Person) {
            return id == ((Person) obj).id;
        }else {
            return false;
        }
    }
}
