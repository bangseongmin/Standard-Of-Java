package chapter06;

public class TvTest4 {

    public static void main(String[] args) {
        TV[] tvArr = new TV[3];

        // TV객체를 생성해서 TV객체 배열의 각 요소에 저장
        for (int i = 0; i < tvArr.length; i++) {
            tvArr[i] = new TV();
            tvArr[i].channel = i + 10;
        }

        for(int i = 0; i < tvArr.length; i++) {
            tvArr[i].channelUp();
            System.out.printf("tvArr[%d].channel=%d\n", i, tvArr[i].channel);
        }
    }
}