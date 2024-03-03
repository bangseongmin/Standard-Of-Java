package chapter12.ex13;

public @interface Test {

    int count();
    String testedBy();
    String[] testTools();
    TestType testType();        // enum TestType { FIRST, FINAL }
    DateTime testDate();        // 자신이 아닌 다른 어노테이션(@DateTime)을 포함할 수 있음.
}
