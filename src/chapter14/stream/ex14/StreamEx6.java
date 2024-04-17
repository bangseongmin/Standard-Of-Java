package chapter14.stream.ex14;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamEx6 {

    public static void main(String[] args) {
        Student[] students = {
                new Student("이자바", 3, 300),
                new Student("김자바", 2, 150),
                new Student("박자바", 1, 200),
                new Student("전자바", 2, 200),
                new Student("구자바", 3, 100),

                new Student("기자바", 2, 150),
                new Student("나자바", 1, 270),
                new Student("하자바", 2, 200),
                new Student("선자바", 3, 100),
                new Student("미자바", 2, 140),
        };

        // 학생 이름만 뽑아서 List<String>에 저장
        List<String> names = Stream.of(students).map(Student::getName)
                .collect(toList());
        System.out.println(names);

        // Convert Stream to Array
        Student[] studentArray = Stream.of(students).toArray(Student[]::new);

        for(Student s : studentArray) {
            System.out.println(s);
        }

        // Stream을 Map<String, Student>로 변환
        Map<String, Student> studentMap = Stream.of(students)
                .collect(toMap(s -> s.getName(), s -> s));

        for(String name : studentMap.keySet()) {
            System.out.println(name + " - " + studentMap.get(name));
        }

        long count = Stream.of(students).collect(counting());
        long totalScore = Stream.of(students).collect(summingInt(Student::getTotalScore));

        System.out.println("count="+count);
        System.out.println("totalScore=" + totalScore);

        totalScore = Stream.of(students).collect(reducing(0, Student::getTotalScore, Integer::sum));
        System.out.println("totalScore="+totalScore);

        Optional<Student> topStudent = Stream.of(studentArray)
                .collect(maxBy(Comparator.comparingInt(Student::getTotalScore)));
        System.out.println("topStudent=" + topStudent);

        IntSummaryStatistics stat = Stream.of(students).collect(summarizingInt(Student::getTotalScore));
        System.out.println(stat);

        String stuNames = Stream.of(students).map(Student::getName).collect(joining(",", "{", "}"));
        System.out.println(stuNames);
    }
}
