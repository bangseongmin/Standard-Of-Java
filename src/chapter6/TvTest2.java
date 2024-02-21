package chapter6;

public class TvTest2 {

    public static void main(String[] args) {
        TV tv1 = new TV();
        TV tv2 = new TV();

        System.out.println("tv1의 channel의 값은 " + tv1.channel + " 입니다.");
        System.out.println("tv2의 channel의 값은 " + tv2.channel + " 입니다.");

        tv1.channel = 7;
        System.out.println("tv1의 channel의 값을 7로 변경하였습니다.");

        System.out.println("tv1의 channel의 값은 " + tv1.channel + " 입니다.");
        System.out.println("tv2의 channel의 값은 " + tv2.channel + " 입니다.");
    }
}