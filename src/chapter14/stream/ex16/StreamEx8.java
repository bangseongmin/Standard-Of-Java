package chapter14.stream.ex16;

import chapter14.stream.ex15.LEVEL;
import chapter14.stream.ex15.Student;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamEx8 {

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


        System.out.println("1. 단순그룹화(반별로 그룹화)");
        Map<Integer, List<Student>> stuByBan = Stream.of(stuArr)
                .collect(groupingBy(Student::getBan));

        for(List<Student> ban : stuByBan.values()) {
            for(Student s : ban) {
                System.out.println(s);
            }
        }


        System.out.println();

        System.out.println("2. 단순 그룹화(성적별로 그룹화)");
        Map<LEVEL, List<Student>> stuByLevel = Stream.of(stuArr).collect(groupingBy(s -> {
            if (s.getScore() >= 200) return LEVEL.HIGH;
            else if (s.getScore() >= 100) return LEVEL.MID;
            else return LEVEL.LOW;
        }));

        TreeSet<LEVEL> ketSet = new TreeSet<>(stuByLevel.keySet());

        for(LEVEL key : ketSet) {
            System.out.println("[" + key + "]");

            for(Student s : stuByLevel.get(key)) {
                System.out.println(s);
            }
            System.out.println();
        }

        System.out.println();

        System.out.println("3. 단순그룹화 + 통계(성적별 학생수)\n");
        Map<LEVEL, Long> stuCntByLevel = Stream.of(stuArr).collect(groupingBy(s -> {
            if (s.getScore() >= 200) return LEVEL.HIGH;
            else if (s.getScore() >= 100) return LEVEL.MID;
            else return LEVEL.LOW;
        }, counting()));

        for(LEVEL key : stuCntByLevel.keySet()) {
            System.out.printf("[%s] - %d명\n", key, stuCntByLevel.get(key));
        }

        System.out.println("4. 다중그룹화(학년별, 반별)");
        Map<Integer, Map<Integer, List<Student>>> stuByHakAndBan = Stream.of(stuArr)
                .collect(groupingBy(Student::getHak,
                        groupingBy(Student::getBan))
                );

        for(Map<Integer, List<Student>> hak : stuByHakAndBan.values()) {
            for(List<Student> ban: hak.values()) {
                System.out.println();
                for(Student student : ban) {
                    System.out.println(student);
                }
            }
        }

        System.out.printf("\n 5. 다중그룹화 + 통계(학년별, 반별 1등)\n");
        Map<Integer, Map<Integer, Student>> topStuByHakAndBan = Stream.of(stuArr)
                .collect(groupingBy(Student::getHak,
                        groupingBy(Student::getBan,
                                collectingAndThen(
                                        maxBy(comparingInt(Student::getScore))
                                        , Optional::get
                                )))
                );

        for(Map<Integer, Student> ban : topStuByHakAndBan.values()) {
            for(Student s : ban.values()) {
                System.out.println(s);
            }
        }

        System.out.printf("\n6.다중그룹화 + 통계(학년별, 반별 성적그룹)\n");

        Map<String, Set<LEVEL>> stuByScoreGroup = Stream.of(stuArr)
                .collect(groupingBy(s -> s.getHak() + "-" + s.getBan(),
                        mapping(s -> {
                            if (s.getScore() >= 200) return LEVEL.HIGH;
                            else if (s.getScore() >= 100) return LEVEL.MID;
                            else return LEVEL.LOW;
                        }, toSet())
                ));

        Set<String> ketSet2 = stuByScoreGroup.keySet();

        for(String key : ketSet2) {
            System.out.println("[" + key + "] " + stuByScoreGroup.get(key));
        }
    }
}
