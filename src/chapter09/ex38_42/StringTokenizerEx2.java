package chapter09.ex38_42;

import java.util.StringTokenizer;

public class StringTokenizerEx2 {

    public static void main(String[] args) {
        String expression = "x=100*(200+300)/2";
        // 단 한 문자의 구분자만 사용할 수 있기 때문에, '+-*/=()' 전체가 하나의 구분자가 아니라 각각의 문자가 모두 구분자가 된다.
        StringTokenizer st = new StringTokenizer(expression, "+-*/=()", true);

        while(st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
