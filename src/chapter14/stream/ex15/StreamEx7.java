package chapter14.stream.ex15;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamEx7 {

    public static void main(String[] args) {
        Student[] stuArr = {
                new Student("나자바", true, 1, 1, 300),
                new Student("다자바", false, 2, 2, 200),
                new Student("가자바", true, 3, 2, 80),
                new Student("라자바", false, 2, 1, 120),
                new Student("마자바", true, 1, 1, 300),
                new Student("바자바", false, 1, 2, 200),
                new Student("사자바", true, 2, 3, 240),
                new Student("아자바", false, 1, 2, 40),
                new Student("자자바", false, 3, 2, 180),
                new Student("카자바", false, 1, 1, 40),
                new Student("타자바", true, 2, 1, 180),
                new Student("파자바", false, 1, 1, 300),
                new Student("하자바", true, 3, 1, 40),
                new Student("김자바", false, 1, 1, 120),
                new Student("이자바", true, 2, 2, 80),
                new Student("박자바", false, 2, 2, 150),
                new Student("박자바", false, 3, 1, 40),
                new Student("김자바", false, 1, 2, 150),
                new Student("구자바", false, 2, 3, 40),
                new Student("기자바", false, 2, 3, 300),
                new Student("나자바", false, 3, 2, 200),
                new Student("디자바", false, 2, 1, 150),
                new Student("리자바", false, 1, 2, 180),
                new Student("미자바", false, 1, 1, 120),
                new Student("비자바", false, 1, 2, 240),
                new Student("시자바", false, 2, 3, 200),
                new Student("이자바", false, 3, 2, 180),
                new Student("지자바", false, 2, 1, 300),
                new Student("치자바", false, 1, 2, 200),
                new Student("히자바", false, 1, 3, 240)
        };

        System.out.println("1. 단순분할(성별로 분할)");
        Map<Boolean, List<Student>> stuBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale));

        List<Student> maleStudents = stuBySex.get(true);
        List<Student> femaleStudents = stuBySex.get(false);

        for (Student student : maleStudents) System.out.println(student);
        for (Student student : femaleStudents) System.out.println(student);

        System.out.println();

        System.out.println("2. 단순분할 + 통계(성별 학생수)");
        Map<Boolean, Long> stuNumBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, counting()));

        System.out.println("남학생 수 : " + stuNumBySex.get(true));
        System.out.println("여학생 수 : " + stuNumBySex.get(false));

        System.out.println();

        System.out.println("3. 단순분할 + 통계(성별 1등)");
        Map<Boolean, Optional<Student>> topScoreBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, maxBy(comparingInt(Student::getScore))));

        System.out.println("남학생 1등 : " + topScoreBySex.get(true));
        System.out.println("여학생 1등 : " + topScoreBySex.get(false));

        System.out.println();

        Map<Boolean, Student> topScoreBySex2 = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, collectingAndThen(
                        maxBy(comparingInt(Student::getScore)), Optional::get)
                ));

        System.out.println("남학생 1등 : " + topScoreBySex2.get(true));
        System.out.println("여학생 1등 : " + topScoreBySex2.get(false));

        System.out.println();

        System.out.println("4. 다중분할(성별 불합격자, 100점 이하)");
        Map<Boolean, Map<Boolean, List<Student>>> failedStuBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, partitioningBy(s -> s.getScore() <= 100)));

        List<Student> failedMaleStudents = failedStuBySex.get(true).get(true);
        List<Student> failedFeMaleStudents = failedStuBySex.get(false).get(true);

        for (Student student : failedMaleStudents) System.out.println(student);
        for (Student student : failedFeMaleStudents) System.out.println(student);

    }
}
