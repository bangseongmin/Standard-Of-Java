package chapter06;

public class MyMath2 {

    long a, b;

    long add() { return a + b; }
    long subtract() { return a - b; }
    long multiply() { return a * b; }
    double divide() { return a / b; }

    static long add(long a, long b)         { return a + b; }
    static long subtract(long a, long b)    { return a - b; }
    static long multiply(long a, long b)    { return a * b; }
    static double divide(double a, double b) { return a / b; }
}
