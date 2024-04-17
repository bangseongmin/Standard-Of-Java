package chapter14.stream.ex11;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamEx4 {

    public static void main(String[] args) {
        Stream<String[]> strArrStream = Stream.of(
                new String[]{ "abc", "def", "jkl" },
                new String[]{ "ABC", "GHI", "JKL" }
        );

//        Stream<Stream<String>> strStreamStream = strArrStream.map(Arrays::stream);
        Stream<String> strStream = strArrStream.flatMap(Arrays::stream);

        strStream.map(String::toLowerCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println();

        String[] lineArr = {
                "Believe or not It is true",
                "Do or do not There is not try"
        };

        Stream<String> lineStream = Arrays.stream(lineArr);
        lineStream.flatMap(line -> Stream.of(line.split(" +")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println();

        Stream<String> strStream1 = Stream.of("AAA", "ABC", "bBb", "Dd");
        Stream<String> strStream2 = Stream.of("bbb", "aaa", "ccc", "dd");

        Stream<Stream<String>> strStreamStream = Stream.of(strStream1, strStream2);
        Stream<String> strStreamm = strStreamStream
                .map(s -> s.toArray(String[]::new))
                .flatMap(Arrays::stream);

        strStreamm.map(String::toLowerCase)
                .distinct()
                .forEach(System.out::println);
    }
}
