package chapter14.collector.ex17;

import java.util.Arrays;
import java.util.stream.Stream;

public class CollectorEx1 {

    public static void main(String[] args) {
        String[] strArr = { "aaa", "bbb", "ccc" };

        Stream<String> strStream = Stream.of(strArr);

        String result = strStream.collect(new ConcatCollector());

        System.out.println(Arrays.toString(strArr));
        System.out.println("result="+result);
    }
}
