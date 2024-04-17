package chapter14.stream.ex8;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamEx1 {

    public static void main(String[] args) {
        Stream<Student> studentStream = Stream.of(
                new Student("김자바", 3, 300),
                new Student("나자바", 2, 200),
                new Student("박자바", 1, 100),
                new Student("안자바", 4, 400),
                new Student("자자바", 3, 300),
                new Student("바자바", 2, 200),
                new Student("이자바", 1, 300),
                new Student("임자바", 2, 100),
                new Student("여자바", 3, 200)
        );

        studentStream.sorted(Comparator.comparing(Student::getBan)
                .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }
}
