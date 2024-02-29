package chapter07.example20;


public class BindingTest3 {

    public static void main(String[] args) {
        Parent parent = new Parent();
        Child child = new Child();

        System.out.println("parent.x = " + parent.x);
        parent.method();

        System.out.println();

        System.out.println("child.x = " + child.x);
        child.method();
    }
}
