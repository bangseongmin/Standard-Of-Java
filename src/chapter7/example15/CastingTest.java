package chapter7.example15;

public class CastingTest {

    public static void main(String[] args) {
        Car car = null;
        FireEngine fe = new FireEngine();
        FireEngine fe2 = null;

//        fe.water();
        car = fe;       // car = (Car)fe에서 형변환이 생략된 형태이다.
        // car.water();
        fe2 = (FireEngine) car; // 자손타입 <- 조상타입
//        fe2.water();
    }
}
