# Chapter 14. 람다와 스트림(Lambda & Stream)

## 1. 람다식(Lambda Expression)

자바가 1996년에 처음 등장한 이후로 두 번의 큰 변화가 있었는데, 한번은 JDK1.5부터 추가된 제네릭(Generics)의 등장이고, 또 한번은 JDK1.8부터 추가된 람다식(Lambda Expression)의 등장이다. 이 두가지 새로운 변환에 의해 자바는 더 이상 예쩐의 자바가 아니게 되었다.

특히 람다식의 도입으로 인해, 이제 자바는 객체지향언어인 동시에 함수형 언어가 되었다. 객체지향언어가 함수형 언어의 기능까지 갖추게 하는 일은 결코 쉬운 일이 아니었을텐데도 기존의 자바를 거의 변경하지 않고도 함수형 언어의 장점을 잘 접목시키는데 성공했다.

### 1.1 람다식이란?

람다식(Lambda expression)은 간단히 말해서 메서드를 하나의 '식(expression)'으로 표현한 것이다. 람다식은 함수를 간략하면서도 명확한 식으로 표현할 수 있게 해준다.

메서드를 람다식으로 표현하면 메서드의 이름과 반환값이 없어지므로, 람다식을 '익명 함수(anonymous function)'이라고도 한다.

```java
int[] arr = new int[5];
Arrays.setAll(arr, (i) -> (int)(Math.random() * 5) + 1);
```

위 람다식이 하는 일을 메서드로 표현하면 다음과 같다.

```java
int method() {
    return (int)(Math.random() * 5) + 1;
}
```

위의 메서드보다 람다식이 간결하면서도 이해하기 쉽다는 것에 이견이 없을 것이다. 게다가 모든 메서드는 클래스에 포함되어야 하므로 클래스도 새로 만들어야 하고, 객체도 생성해야만 비로소 메서드를 호출 할 수 있다. 그러나 람다식은 이 모든 과정없이 오직 람다식 자체만으로도 이 메서드의 역할을 대신할 수 있다.

게다가 람다식은 메서드의 매개변수로 전달되어지는 것이 가능하고, 메서드의 결과로 반환될 수도 있다. 람다식으로 인해 메서드를 변수처럼 다루는 것이 가능해진 것이다.

> Q. 메서드와 함수의 차이는?
> 
> A. 전통적으로 프로그래밍에서 함수라는 이름은 수학에서 따온 것입니다. 수학의 함수와 개념이 유사하기 때문이다. 그러나 객체지향개념에서는 함수(function)대신 객체의 행위나 동작을 의미하는 메서드(method)라는 용어를 사용한다. 메서드는 함수와 같은 의미이지만, 특정 클래스에 반드시 속해야 한다는 제약이 있기 때문에 기존의 함수와 같은 의미의 다른 용어를 선택해서 사용한 것입니다. 그러나 람다식을 통해 메서드가 하나의 독립적인 기능을 하기 때문에 함수라는 용어를 사용하게 되었습니다.

### 1.2 람다식 작성

람다식은 '익명 함수'답게 메서드에서 이름과 반환타입을 제거하고 매개변수 선언부와 몸통{} 사이에 '->'를 추가한다.

```java
// AS-IS
반환타입 메서드이름(매겨변수 선언) {

}

// TO-BE
(매개변수 선언) -> { }
```

예를 들어 두 값 중에서 큰 값을 반환하는 메서드 max를 람다식으로 변환하면, 아래의 오른쪽과 같이 된다.

```java
// AS-IS
int max(int a, int b) {
    return a > b ? a : b;    
}


// TO-BE
(int a, int b) -> { return a > b ? a : b; }
```

반환값이 있는 메서드의 경우, return 문 대신 '식(expression)'으로 대신할 수 있다. 식의 연산결과가 자동적으로 반환값이 된다. 이때는 '문장(expression)'이 아닌 '식'이므로 끝에 ';'을 붙이지 않는다.

```java
// AS-IS
(int a, int b) -> { return a > b ? a : b; }

// TO-BE
(int a, int b) -> a > b ? a : b
```

람다식에 선언된 매개변수의 타입은 추론이 가능한 경우는 생략할 수 있는데, 대부분의 경우에 생략이 불가능하다. 람다식에 반환타입이 없는 이유도 항상 추론이 가능하기 때문이다. 

```java
// AS-IS
(int a, int b) -> a > b ? a : b

(a, b) -> a > b ? a : b
```

> [주의] `(int a, b) -> a > b ? a : b`와 같이 두 매개변수 중 어느 하나의 타입만 생략하는 것은 허용되지 않는다.

아래와 같이 선언된 매개변수가 하나뿐인 경우에는 괄호()를 생략할 수 있다. 단, 매개변수의 타입이 있으면 괄호()를 생략할 수 없다.

```java
// AS-IS
(a) -> a * a
(int a) -> a * a

// TO-BE
a -> a * a          // OK
int a -> a * a      // ERROR
```

마찬가지로 괄호{} 안의 문장이 하나일 때는 괄호{}를 생략할 수 있다. 이 때 문장의 끝에 ';'을 붙이지 않아야 한다는 것에 주의하자.

```java
// AS-IS
(String name, int i) -> {
    System.out.println(name + " = " + i);    
}

// TO-BE
(String name, int i) -> System.out.println(name + " = " + i)
```

그러나 괄호{}안의 문장이 return문일 경우 괄호{}를 생략할 수 없다.

```java
(int a, int b) -> { return a > b ? a : b; }     // OK
(int a, int b) -> return a > b ? a : b          // ERROR
```

### 1.3 함수형 인터페이스(Functional Interface)

자바에서 모든 메서드는 클래스 내에 포함되어야 하는데, 람다식은 어떤 클래스에 포함되는 것일까? 지금까지 람다식이 메서드와 동등한 것처럼 설명해왔지만, 사실 람다식은 익명 클래스의 객체와 동등하다.

```java
// AS-IS
(int a, int b) -> a > b ? a : b

// TO-BE
new Object() {
    int max(int a, int b) {
        return a > b ? a : b;    
    }    
}
```

위의 두 번째 코드에서 메서드 이름 max는 임의로 붙인 것일 뿐 의미는 없다. 어쨌든 람다식으로 정의된 익명 객체의 메서드를 어떻게 호출할 수 있을 것인가? 이미 알고 있는 것처럼 참조변수가 있어야 객체의 메서드를 호출 할 수 있으니까 일단 이 익명 객체의 주소를 f라는 참조변수에 저장 해보자.

```java
타입 f = (int a, int b) -> a > b ? a : b;     // 참조변수의 타입을 무엇으로 해야 할까?
```

그러면, 참조변수 f의 타입은 어떤 것이어야 할까? 참조형이니까 클래스 또는 인터페이스가 가능하다. 그리고 람다식과 동등한 메서드가 정의되어 있는 것이어야 한다. 그래야만 참조변수로 익명 객체(람다식)의 메서드를 호출할 수 있기 때문이다. 

예를 들어 아래와 같이 max()라는 메서드가 정의된 MyFunction인터페이스가 정의되어 있다고 가정하자.

```java
interface MyFunction {
    public abstract int max(int a, int b);
}
```

그러면 이 인터페이스를 구현한 익명 클래스의 객체는 다음과 같이 생성할 수 있다.

```java
MyFunction t = new MyFunction() {
    public int max(int a, int b) {
        return a > b ? a : b;
    }
}
int big = f.max(5, 3);      // 익명 객체의 메서드를 호출
```

MyFunction 인터페이스에 정의된 메서드 amx()는 람다식'(int a, int b) -> a > b ? a : b'과 메서드의 선언부가 일치한다. 그래서 위 코드의 익명 객체를 람다식으로 아래와 같이 대체할 수 있다.

```java
MyFunction t = (int a, int b) -> a > b ? a : b
int big = f.max(5, 3);      // 익명 객체의 메서드를 호출
```

이처럼 MyFunction인터페이스를 구현한 익명 객체를 람다식으로 대체가 가능한 이유는, 람다식도 실제로는 익명 객체이고, MyFunction인터페이스를 구현한 익명 객체의 메서드 max()와 람다식의 매개변수의 타입과 개수 그리고 반환값이 일치하기 때문이다.

지금까지 살펴본 것처럼, 하나의 메서드가 선언된 인터페이스를 정의해서 람다식을 다루는 것은 기존의 자바의 규칙들을 어기지 않으면서도 자연스럽다.

그래서 인터페이스를 통해 람다식을 다루기로 결정되었으며, 람다식을 다루기 위한 인터페이스를 '함수형 인터페이스(functional interface)'라고 부르기로 했다.

```java
@FunctionalInterface
interface MyFunction {      // 함수형 인터페이스 MyFunction을 정의
    public abstract int max(int a, int b);
}
```

단, 함수형 인터페이스에는 오직 하나의 추상 메서드만 정의되어 있어야 한다는 제약이 있다. 그래야 람다식과 인터페이스의 메서드가 1:1로 연결될 수 있기 때문이다. 반면에 static메서드와 default메서드의 개수에는 제약이 없다.

> [참고] `@FunctionalInterface`를 붙이면, 컴파일러가 함수형 인터페이스를 올바르게 정의하였는지 확인해주므로, 꼭 붙이도록 하자.

기존에는 아래와 같이 인터페이스의 메서드 하나를 구현하는데도 복잡하게 해야 헀는데, 람다식으로 간단히 처리할 수 있게 되었다.

```java
// AS-IS
List<String> list = Arrays.asList("abc", "aaa", "bbb", "ddd", "aaa");

Collections.sort(list, new Comparator<String>() {
    public int compare(String s1, String s2) {
        return s2.compareTo(s1);    
    }
})

// TO-BE
List<String> list = Arrays.asList("abc", "aaa", "bbb", "ddd", "aaa");
Collections.sort(list, (s1, s2) -> s2.compareTo(s1));
```


#### 함수형 인터페이스 타입의 매개변수와 반환타입

함수형 인터페이스 MyFunction이 아래와 같이 정의되어 있을 때,

```java
@FunctionalInterface
interface MyFunction {
    void myMethod();            // 추상 메서드
}
```

메서드의 매개변수가 MyFunction타입이면, 이 메서드를 호출할 때 람다식을 참조하는 참조변수를 매개변수로 지정해야한다는 뜻이다.

```java
void aMethod(MyFunction t) {        // 매개변수의 타입이 함수형 인터페이스
    f.myMethod();                   // MyFunction에 정의된 메서드 호출
}
```

또는 참조변수 없이 아래와 같이 직접 람다식을 매개변수로 지정하는 것도 가능하다.

```java
aMethod(() -> System.out.println("myMethod()"))     // 람다식을 매개변수로 지정
```

그리고 메서드의 반환타입이 함수형 인터페이스타입이라면, 이 함수형 인터페이스의 추상메서드와 동등한 람다식을 가리키는 참조변수를 반환하거나 람다식을 직접 반환할 수 있다.

```java
MyFunction myMethod() {
    MyFunction f = () ->{};
    return f;               // 이 줄과 윗줄을 한 줄로 줄이면, return () -> {};
}
```

람다식을 참조변수로 다룰 수 있다는 것은 메서드를 통해 람다식을 주고받을 수 있다는 것을 의미한다. 즉, 변수처럼 메서드를 주고받는 것이 가능해진 것이다.

사실상 메서드가 아니라 객체를 주고받는 것이라 근본적으로 달라진 것은 아무것도 없지만, 람다식 덕분에 예전보다 코드가 간결하고 이해하기 쉬워졌다.

#### 람다식의 타입과 형변환

함수형 인터페이스로 람다식을 참조할 수 있는 것일 뿐, 람다식의 타입이 함수형 인터페이스의 타입과 일치하는 것은 아니다. 람다식은 익명 객체이고 익명 객체는 타입이 없다.

정확히는 타입이 있지만 컴파일러가 임의로 이름을 정하기 때문에 알 수 없는 것이다. 그래서 대입 연산자의 양변의 타입을 일치시키기 위해 아래와 같이 형변환이 필요하다.

> [참고] MyFunction은 `interface MyFunction{ void emthod(); }`와 같이 정의되었다고 가정하였다.

```java
MyFunction f = (MyFunction) (() -> {});     // 양변의 타입이 다르므로 형변환이 필요
```

람다식은 MyFunction인터페이스를 직접 구현하지 않았지만, 이 인터페이스를 구현한 클래스와 완전히 동일하기 때문에 위와 같은 형변환을 허용한다. 그리고 이 형변환은 생략가능하다.

람다식은 이름이 없을 뿐 분명한 객체인데도, 아래와 같이 Object타입으로 형변환할 수 없다. 람다식은 오직 함수형 인터페이스로만 형변환이 가능하다.

```java
Object obj = (Object)(() -> {});        // 에러. 함수형 인터페이스로만 형변환 가능
```

굳이 Object타입으로 형변환하려면, 먼저 함수형 인터페이스로 변환해야 한다.

```java
Object obj = (Object)(MyFunction) (() -> {});
String str = ((Object)(MyFunction) (() -> {})).toString();
```

예제 14-2의 실행결과를 보면, 컴파일러가 람다식의 타입을 어떤 형식으로 만들어내느지 알 수 있다. 일반적인 익명 객체라면, 객체의 타입이 `외부클래스이름$번호`와 같은 형식으로 타입이 결정되었을 텐데, 람다식의 타입은 `외부 클래스이름$$Lambda$번호`와 같은 형식으로 되어 있는 것을 확인할 수 있다.

#### 외부 변수를 참조하는 람다식

람다식도 익명 객체, 즉 익명 클래스의 인스턴스이므로 람다식에서 외부에 선언된 변수에 접근하는 규칙은 앞서 익명 클래스에 배운 것과 동일하다.

### 1.4 java.util.function 패키지

대부분의 메서드는 타입이 비슷하다. 매개변수가 없거나 한 개 또는 두 개, 반환 값은 없거나 한 개, 게다가 제네릭 메서드로 정의하면 매개변수나 반환 타입이 달라도 문제가 되지 않는다.

그래서 java.util.function패키지에 일반적으로 자주 쓰이는 형식의 메서드를 함수형 인터페이스로 미리 정의해 놓았다. 매번 새로운 함수형 인터페이스를 정의하지 말고, 가능하면 이 패키지의 인터페이스를 활용하는 것이 좋다.

그래야 함수형 인터페이스에 정의된 메서드 이름도 통일되고, 재사용성이나 유지보수 측면에서도 좋다. 자주 쓰이는 가장 기본적인 함수형 인터페이스는 다음과 같다.

| 함수형 인터페이스 | 메서드 | 설명 |
| --- | --- | --- |
| java.lang.Runnable | void run() | 매개변수도 없고, 반환값도 없음. |
| Supplier<T> | T get() | 매개변수는 없고, 반환값만 있음 |
| Consumer<T> | void accept(T t) | Supplier와 반대로 매개변수만 있고, 반환값이 없음 |
| Function<T, R> | R apply(T t) | 일반적인 함수. 하나의 매개변수를 받아서 결과를 반환 |
| Predicate<T> | boolean test(T t) | 조건식을 표현하는데 사용됨. 매개변수는 하나. 반환 타입은 boolean |

매개변수와 반환값의 유무에 따라 4개의 함수형 인터페이스가 정의되어 있고, Function의 변형으로 Predicate가 있는데, 반환값이 boolean이라는 것만 제외하면 Function과 동일하다. Predicate는 조겨ㅓㄴ식을 함수로 표현한느데 사용된다.

#### 조건식의 표현에 사용되는 Predicate

Predicate는 Function의 변형으로, 반환타입이 boolean이라는 것만 다르다. Predicate는 조건식을 람다식으로 표현하는데 사용된다.

> [참고] 수학에서 결과로 true 또는 false로 반환하는 함수를 Predicate라고 한다.


```java
Predicate<String> isEmptyStr = s -> s.length() == 0;
String s = "";
if(isEmptyStr.test(s))
    System.out.println("This is an empty String");
```

#### 매개변수가 두 개인 함수형 인터페이스

매개변수의 개수가 2개인 함수형 인터페이스는 이름 앞에 접두사 'Bi'가 붙는다.

> [참고] 매개변수의 타입으로 보통 'T'를 사용하므로, 알파벳에서 'T'의 다음문자인 'U', 'V', 'W'를 매개변수의 타입으로 사용하는 것일 뿐 별다른 의미는 없다.


| 함수형 인터페이스 | 메서드 | 설명 |
| --- | --- | --- |
| BiConsumer<T, U> | void accept(T t, U u) | 두 개의 매개변수만 있고 반환값이 없음 |
| BiPredicate<T, U> | boolean test(T t, U u) | 조건식을 표현하는데 사용됨. 매개변수는 둘, 반환값을 boolean |
| BiFunction<T, U, R> | R apply(T t, U u) | 두 개의 매개변수를 받아서 하나의 결과를 반환 | 

> [참고] Supplier는 매개변수는 없고 반환값만 존재하는데, 메서드는 두 개의 값을 반환할 수 없으므로 BiSupplier가 없는 것이다.

두 개 이상으 ㅣ매개변수를 갖는 함수형 인터페이스가 필요하다면 직접 만들어서 써야한다. 만일 3개의 매개변수를 갖는 함수형 인터페이스를 선언한다면 다음과 같을 것이다.

```java
@FunctionalInterface
interface TriFunction<T,U,V,R> {
    R apply(T t, U u, V v);
}
```

#### UnaryOperator와 BinaryOperator

Function의 또 다른 변형으로 UnaryOperator와 BinaryOperator가 있는데, 매개변수의 타입과 반환타입의 타입이 모두 일치한다는 점만 제외하고는 Function과 같다.

> [참고] UnaryOperator와 BinaryOperator의 조상은 각각 Function과 BiFunction이다.

| 함수형 인터페이스 | 메서드 | 설명 |
| --- | --- | --- |
| UnaryOperator<T> | T apply(T t) | Function의 자손, Function과 달리 매개변수와 결과의 타입이 같다. |
| BinaryOperator<T> | T apply(T t) | BiFunction의 자손, BiFunction과 달리 매개변수와 결과의 타입이 같다. |

#### 컬렉션 프레임워크와 함수형 인터페이스

컬렉션 프레임워크의 인터페이스에 다수의 디폴트 메서드가 추가되었는데, 그 중 일부는 함수형 인터페이스를 사용한다. 다음은 그 메서드들의 목록이다.

> [참고] 단순화하기 위해 와일드 카드는 생략하였다.

| 인터페이스 | 메서드 | 설명 |
| --- | --- | --- |
| Collection | boolean removeIf(Predicate<E> filter) | 조건에 맞는 요소를 삭제 |
| List | void replaceAll(UnaryOperator<E> operator) | 모든 요소를 변환하여 대체 |
| Iterable | void forEach(Consumer<T> action) | 모든 요소에 작업 action을 수행 | 
| Map | V compute(K key, BiFunction<K, V, V> f) | 지정된 키의 값에 작업 f를 수행 |
| Map | V computedIfAbsent(K key, BiFunction<K, V> f) | 키가 없으면, 작업 f 수행 후 추가 |
| Map | V computedIfPresent(K key, BiFunction<K, V, V> f) | 지정된 키가 있을 때, 작업 f 수행 |
| Map | V merge(K key, V value, BiFunction<V, V, V> f) | 모든 요소에 병합작업 f를 수행 |
| Map | void forEach(BiConsumer<K, V> action) | 모든 요소에 작업 action을 수행 |
| Map | void replaceAll(BiFunction<K, V, V> f) | 모든 요소에 치환작업 f를 수행 |


이름만 봐도 어떤 일을 하는 메서드인지 충분히 알 수 있을 것이다. Map인터페이스에 있는 'computed'로 시작하는 메서드들은 맵의 value를 변환하는 일을 하고 merge()는 Map을 병합하는 일을 한다. 이 메서드들을 어떤 식으로 사용하는 지는 다음의 예제를 보자.

#### 기본형을 사용하는 함수형 인터페이스

함수형 인터페이스는 매개변수와 반환값의 타입이 모두 제네릭 타입이었는데, 기본형 타입의 값을 처리할 때도 래퍼(wrapper)클래스를 사용해왔다. 그러나 기본형 대신 래퍼클래스를 사용하는 것은 당연히 비효율적이다. 그래서 보다 효율적으로 처리할 수 있도록 기본형을 사용하는 함수형 인터페스들이 제공된다.

| 함수형 인터페이스 | 메서드 | 설명 |
| --- | --- | --- |
| DoubleToIntFunction | int applyAsInt(Double d) | AToBFunction은 입력이 A타입 출력이 B 타입 |
| ToIntFunction<T> | int applyAsInt(T value) | ToBFunction은 출력이 B타입이다. 입력은 제네릭 타입 | 
| IntFunction<R> | R apply(T t, U u) | AFunction은 입력이 A타입이고 출력은 제네릭 타입 |
| ObjIntConsumer<T> | void accept(T t, U u) | ObjAFunction은 입력이 T, A 타입이고 출력은 없다. |


### 1.5 Function의 합성과 Predicate의 결합

java.util.function패키지의 함수형 인터페이스에는 추상메서드 외에도 디폴트 메서드와 static메서드가 정의되어 있다. 우리는 Function과 Predicate에 정의된 메서드에 대해서만 살펴볼 것인데, 그 이유는 다른 함수형 인터페이스의 메서드도 유사하기 때문이다.

> [참고] Function인터페이스는 반드시 두 개의 타입을 지정해 줘야하기 때문에, 두 타입이 같아도 Function<T>라고 쓸 수 없다. Function<T, T>라고 써야 한다.

```java
// **Function**
default <V> Function<T, V> andThen(Function<? super R, ? extends V> after);
default <V> Function<V, R> compose(Function<? super V, ? extends T> before);
default <T> Function<T, T> identity();

// **Predicate**
default Predicate<T> and(Predicate<? super T> other);
default Predicate<T> or(Predicate<? super T> other);
default Predicate<T> negate();
static <T> Predicate<T> isEqual(Object targetRef)
```


#### Function의 합성

수학에서 두 함수를 합성해서 하나의 새로운 함수를 만들어낼 수 있는 것처럼, 두 람다식을 합성해서 새로운 람다식을 만들 수 있다. 이미 알고 있는 것처럼, 두 함수의 합성은 어느 함수를 먼저 적용하느냐에 따라 달라진다. 함수 f, g가 있을 때, f.andThen(g)는 함수 f를 먼저 적용하고, 그다음에 함수 g를 적용한다. 그리고 f.compose(g)는 반대로 g를 먼저 적용하고 f를 적용한다.

```java
default <V> Function<T, V> andThen(Function<? super R, ? extends V> after)

default <V> Function<V, R> compose<Function<? super V, ? extends T> before)
```

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/61e6b7ca-86b9-4bd1-b62a-a77f0644c534)

예를 들어, 문자열을 숫자로 변환하는 함수 f와 숫자를 2진 문자열로 변환하는 함수 g를 andThen()으로 합성하여 새로운 함수 h를 만들어낼 수 있다.

```java
Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
Function<Integer, String> g = (i) -> Integer.toBinaryString(i);
Function<String, String> h = f.andThen(g);
```

 함수 h의 제네릭 타입이 `<String, String>`이다. 즉, String을 입력받아서 String을 결과로 반환한다. 예를 들어 함수 h에 문자열 'FF'를 입력하면, 결과로 '11111111'을 얻는다.

이번엔 compose()를 이용해서 두 함수를 반대의 순서로 합성해보자.

```java
Function<Integer, String> g = (i) -> Integer.toBinaryString(i);
Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
Function<Integer, Integer> h = f.compose(g);
```

이전과 달리 함수 h의 제네릭 타입이 `<Integer, Integer>`이다. 함수 h에 숫자 2를 입력하면, 결과를 16을 얻는다.

그리고 identity()는 함수를 적용하기 이전과 이후가 동일한 '항등 함수'가 필요할 때 사용한다. 이 함수를 람다식으로 표현하면 `x -> x`이다. 아래의 두 문장은 동등하다.

> [참고] 항등 함수는 함수에 x를 대입하면 결과가 x인 함수를 만한다.

```java
Function<String, String> f = x -> x;
FUnction<String, String> f = Function.identity();       // 위의 문장과 동일

System.out.println(f.apply("AAA"));
```


#### Predicate의 결합

여러 조건식을 논리 연산자인 &&(and), ||(or), !(not)으로 여결해서 하나의 식을 구성할 수 있는 것처럼, 여러 Predicate를 and(), or(), negate()로 연결해서 하나의 새로운 Predicate로 결합할 수 있다.

```java
Predicate<Integer> p = i -> i < 100;
Predicate<Integer> q = i -> i < 200;
Predicate<Integer> r = i -> i % 2 == 0;
Predicate<Integer> notP = p.negate();

Predicate<Integer> all = notP.and(q.or(r));
System.out.println(all.test(150)); 
```

이처럼 and(), or(), negate()로 여러 조건식을 하나로 합칠 수 있다. 물론 아래와 같이 람다식을 직접 넣어도 된다.

```java
Predicate<Integer> all = notP.and(i -> i < 200).or(i - > i % 2 == 0);
```

> [주의] Predicate의 끝에 negate()를 붙이면 조건식 전체가 부정이 된다.
 
그리고 static 메서드인 isEqual()은 두 대상을 비교하는 Predicate를 만들 때 사용한다. 먼저, isEqual()의 매개변수로 비교대상을 하나 지정하고, 또 다른 비교대상은 test()의 매개변수로 지정한다.

```java
Predicate<String> p = Predicate.isEqual(str1);
boolean result = p.test(str2);
```

위의 두 문장을 합치면 아래와 같다. 오히려 아래의 문장이 이해하기 더 쉬울 것이다.

```java
boolean result = Prediate.isEqual(str1).test(str2);
```

### 1.6 메서드 참조

람다식으로 메서드를 이처럼 간결하게 표현할 수 있다는 것에 충분히 감탄했을 텐데, 놀랍게도 람다식을 더욱 간결하게 표현할 수 있는 방법이 있다. 항상 그런 것은 아니고, 람다식이 하나의 메서드만 호출하는 경우에는 '메서드 참조(method reference)'라는 방법으로 람다식을 간략히 할 수 있다. 예를 들어 문자열을 정수로 변환하는 람다식은 아래와 같이 작성할 수 있다.

```java
Function<String, Integer> f = (String s) -> Integer.pareseInt(s);
```

보통은 이렇게 람다식을 작성하는데, 이 람다식을 메서드로 표현하면 아래와 같다.

> [참고] 람다식은 엄밀히 말하자면 익명클래스의 객체지만 간단히 메서드만 적었다.

```java
Integer wrapper(String s) {
    return Integer.pareseInt(s);
}
```

이 wrapper메서드는 별로 하는 일이 없다. 그저 값을 받아서 Integer.parseInt()에게 넘겨주는 일만 할 뿐이다. 차라리 이 거추장스러운 메서드를 벗겨내고 Integer.paresInt()를 직접 호출하는 것이 낫지 않을까?

```java
Function<String, Integer> f = (String s) -> Integer.parseInt(s);
Function<String, Integer> f = Integer::parseInt;        // 메서드 참조
```

위 메서드 참조에서 람다식의 일부가 생략되었지만, 컴파일러는 생략된 부분을 우변의 pareseInt메서드의 선언부로부터 또는 좌변의 Function인터페이스에 지정된 제네릭 타입으로부터 쉽게 알아낼 수 있다.

한 가지 예를 더 보자. 아래의 람다식을 메서드 참조로 변경하려면, 어떻게 되겠는가? 람다식에서 생략해도 좋을만한 부분이 어디인지 한번 생각해 보자.

```java
BiFunction<String, String, Boolean> f = (s1, s2) -> s1.equals(s2);
```

참조변수 f의 타입만 봐도 람다식이 두 개의 String타입의 매개변수를 받는 다는 것을 알 수 있으므로, 람다식의 매개변수들은 없어도 된다. 위의 람다식에서 매개변수들을 제거해서 메서드 참조로 변경하면 아래와 같다.

```java
BiFunction<String, String, Boolean> f = (s1, s2) -> s1.equals(s2);
BiFunction<String, String, Boolean> f = String::equals;     // 메서드 참조
```

매개변수 s1과 s2를 생략해버리고 나면 equals만 남는데, 두 개의 String을 받아서 Boolean을 반환하는 equals라는 이름의 메서드는 다른 클래스에도 존재할 수 있기 때문에 equals앞에 클래스 이름은 반드시 필요하다.

메서드 참조를 사용할 수 있는 경우가 한 가지 더 있는데, 이미 생성된 객체의 메서드를 람다식에서 사용한 경우에는 클래스 이름 대신 그 객체의 참조변수를 적어줘야 한다.

```java
MyClass obj = new MyClass();
Function<String, Boolean> f = (x) -> obj.equals(x);     // 람다식
Function<String, Boolean> f2 = obj::equals;             // 메서드 참조
```

> 하나의 메서드만 호출하는 람다식은 `클래스이름::메서드이름` 또는 `참조변수::메서드이름`으로 바꿀 수 있다.

#### 생성자의 메서드 참조

생성자를 호출하는 람다식도 메서드 참조로 변환할 수 있다.

```java
Supplier<MyClass> s = () -> new MyClass();
Supplier<MyClass> s = MyClass:new;
```

매개변수가 있는 생성자라면, 매개변수의 개수에 따라 알맞은 함수형 인터페이스를 사용하면 된다. 필요하다면 함수형 인터페이스를 새로 정의해야 한다.

```java
Function<Integer, MyClass> f = (i) -> new MyClass(i);       // 람다식
Function<Integer, MyClass> f2 = MyClass::new;               // 메서드 참조

BiFunction<Integer, String, MyClass> bf = (i, s) -> new MyClass(i, s);
BiFunction<Integer String, MyClass> bf2 = MyClass::new;     // 메서드 참조
```

그리고 배열을 생성할 때는 아래와 같이 하면 된다.

```java
Function<Integer, int[]> f = x -> new int[x];       // 람다식
Function<Integer, int[]> f2 = int[]::new;           // 메서드 참조
```

메서드 참조는 람다식을 마치 static변수처럼 다룰 수 있게 해준다. 메서드 참조는 코드를 간략히 하는데 유용해서 많이 사용된다.


## 2. 스트림(Stream)

### 2.1 스트림이란?

컬렉션이나 배열에 데이터를 담고 원하는 결과를 얻기 위해 for문과 Iterator를 이용해서 코드를 작성해왔다. 그러나 이러한 방식으로 작성된 코드는 너무 길고 알아보기 어렵다. 그리고 재사용성도 떨어진다.

또 다른 문제는 데이터 소스마다 다른 방식으로 다뤄야한다는 것이다. Collection이나 Iterator와 같은 인터페이스를 이용해서 컬렉션을 다루는 방식을 표준화하기는 했지만, 각 컬렉션 클래스에는 같은 기능의 메서드들이 중복해서 정의되어 있다. 예를 들어 List를 정렬할 때는 Collections.sort()를 사용해야하고, 배열을 정렬할 때는 Arrays.sort()를 사용해야한다.

이러한 문제점들을 해결하기 위해서 만든 것이 '스트림(Stream)'이다. 스트림은 데이터 소스를 추상화하고, 데이터를 다루는데 자주 사용되는 메서드들을 정의해 놓았다. 데이터 소스를 추상화하였다는 것은, 데이터 소스가 무엇이던 간에 같은 방식으로 다룰 수 있게 되었다는 것과 코드의 재사용성이 높아진다는 것을 의미한다.

스트림을 이용하면, 배열이나 컬렉션뿐만 아니라 파일에 저장된 데이터도 모두 같은 방식으로 다룰 수 있다.

예를 들어, 문자열 배열과 같은 내용의 문자열을 저장하는 List가 있을 때, 

```java
String[] strArr = { "aaa", "bbb", "ccc" };
List<String> strList = Arrays.asList(strArr);
```

이 두 데이터 소스를 기반으로 하는 스트림은 다음과 같이 생성한다.

```java
Stream<String> strStream1 = strList.stream();
Stream<String> strStream2 = Arrays.stream(strArr);
```

이 두 스트림으로 데이터 소스의 데이터를 읽어서 정렬하고 화면에 출력하는 방법은 다음과 같다. 데이터 소스가 정렬되는 것응은 아니라는 것에 유의하자.

```java
strStream1.sorted().forEach(System.out::println);
strStream2.sorted().forEach(System.out::println);
```

두 스트림의 데이터 소스는 서로 다르지만, 정렬하고 출력하는 방법은 완전히 동일하다.

이처럼 스트림을 사용한 코드가 간결하고 이해하기 쉬우며 재사용성도 높다는 것을 알 수 있다.

#### 스트림은 데이터 소스를 변경하지 않는다.

스트림은 데이터 소스로부터 데이터를 읽기만할 뿐, 데이터 소스를 변경하지 않는다는 차이가 있다. 필요하다면, 정렬된 결과를 컬렉션이나 배열에 담아서 반환할 수 있다. 

#### 스트림은 일회용이다.

스트림은 iterator처럼 일회용이다. Iterator로 컬렉션의 요소를 모두 읽고 나면 다시 사용할 수 없는 것처럼, 스트림도 한번 사용하면 닫혀서 다시 사용할 수 없다. 필요하다면 스트림을 다시 생성해야한다.

#### 스트림은 작업을 내부 반복으로 처리한다.

스트림을 이용한 작업이 간결할 수 있는 기려중의 하나가 바로 '내부 반복'이다. 내부 반복이라는 것은 반복문을 메서드의 내부에 숨길 수 있다는 것을 의미한다. forEach()는 스트림에 정의된 메서드 중의 하나로 매개변수에 대입된 람다식을 데이터 소스의 모든 요소에 적용한다.

#### 스트림의 연산

스트림이 제공하는 다양한 연산을 이용해서 복잡한 작업들을 간단히 처리할 수 있다. 마치 데이터베이스에 SELECT문으로 질의(쿼리, query)하는 것과 같은 느낌이다.

> [참고] 스트림에 정의된 메서드 중에서 데이터 소스를 다루는 작업을 수행하는 것을 연산(operation)이라고 한다.

스트림이 제공하는 연산은 중간 연산과 최종 연산으로 분류할 수 있는데, 중간 연산은 연산결과를 스트림으로 반환하기 때문에 중간 연산을 연속해서 연결할 수 있다. 반면에 최종 연산은 스트림의 요소를 소모하면서 연산을 수행하므로 단 한번만 연산이 가능하다.

> **중간 연산**: 연산 결과가 스트림인 연산. 스트림에 연속해서 중간 연산할 수 있음.
> 
> **최종 연산**: 연산 결과가 스트림이 아닌 연산. 스트림의 요소를 소모하므로 단 한번만 가능.

```java
stream.distinct().limit(5).sorted().forEach(System.out::println)
```

모든 중간 연산의 결과는 스트림이지만, 연산 전의 스트림과 같은 것은 아니다. 위의 문장과 달리 모든 스트림 연산을 나누어 쓰면 아래와 같다. 각 연산의 반환타입을 눈여겨보자.

```java
String[] strArr = { "dd", "aaa", "CC", "cc", "b" };
Stream<String> stream = Stream.of(strArr);                  // 문자열 배열이 소스인 스트림
Stream<String> filteredStream = stream.filter();            // 걸러내기(중간 연산)
Stream<String> distintedStream = stream.distint();          // 중복제거(중간 연산)
Stream<String> sortedStream = stream.sort();                // 정렬(중간 연산)
Stream<String> limitedStream = stream.limit(5);             // 스트림 자르기(중간 연산)
int total = stream.count();                                 // 요소 개수 세기(최종연산)
```

Stream에 정의된 연산을 정리하면 다음과 같다. 앞으로 하나씩 자세히 설명할 것이므로 지금은 어떤 것들이 있다는 정도만 가볍게 봐두자.

| 중간 연산                                                                                                                                                                                                                                                                                                                                                                                                       | 설명 |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| Stream<T> distint()                                                                                                                                                                                                                                                                                                                                                                                         | 중복을 제거 |
| Stream<T> filter(Predicate<T> predicate)                                                                                                                                                                                                                                                                                                                                                                    | 조건에 안 맞는 요소 제외 |
| Stream<T> limit(long maxSize)                                                                                                                                                                                                                                                                                                                                                                               | 스트림의 일부를 잘라낸다. |
| Stream<T> skip(long n)                                                                                                                                                                                                                                                                                                                                                                                      | 스트림의 일부를 건너뛴다. |
| Stream<T> sorted(), Stream<T> sorted(Comparator<T> comparator)                                                                                                                                                                                                                                                                                                                                              | 스트림의 요소를 정렬한다. |
| Stream<R> map(Function<T, R> mapper), DoubleStream mapToDouble(ToDoubleFunction<T> mapper), IntStream mapToInt(ToIntFunction<T> mapper), LongStream mapToLong(ToLongFunction<T> mapper), Stream<R> flatMap(Function<T, Stream<R>> mapper), DoubleStream flatMapToDouble(Function<T, DoubleStream> m), IntStream flatMapToInt(Function<T, IntStream> m), LongStream flatMapToLong(Function<T, LongStream> m) | 스트림의 요소를 변환한다. |
| void forEach(Consumer<? super T> action), void forEachOrdered(Consumer<? super T> action)                                                                                                                                                                                                                                                                                                                   | 각 요소에 지정된 작업 수행 |
| long count()                                                                                                                                                                                                                                                                                                                                                                                                | 스트림의 요소의 개수 반환 | 
| Optional<T> max(Comparator<? super T> comparator)                                                                                                                                                                                                                                                                                                                                                           | 스트림의 최대값/최소값 반환 |
| Optional<T> findAny(), Optional<T> findFirst()                                                                                                                                                                                                                                                                                                                                                              | 스트림의 요소 하나를 반환 |
| boolean allMatch(Predicate<T> p) // 모두 만족하는지, boolean anyMatch(Predicate<T> p) // 하나라도 만족하는지 boolean noneMatch(Predicate<T> p) // 모두 만족하지 않는지 | 주어진 조건을 모든 요소가 만족시키는지, 만족시키지 앟는지 확인 | 스트림의 모든 요소를 배열로 반환 |
| Optional<T> reduce(BinaryOperator<T> acculmulator), T reduce(T identity, BinaryOperator<T> acculmulator), U reduce(U identity, BiFunction<U, T, U> acculmulator, BinaryOperator<U> combiner) | 스트림의 요소를 하나씩 줄여가면서(리듀싱) 계산한다. |
| R collection<Collector<T, A, R> collector), R collector(Supplier<R> supplier, BiConsumer<R, T> accumulator, BiConsumer<R, R> combiner) | 스트림의 요소를 수집한다. 주로 요소를 그룹화하거나 분할한 결과를 컬레겻ㄴ에 담아 반환하는데 사용된다. |

중간 연산은 map()과 flatMap(), 최종연산은 reduce()와 collect()가 핵심이다.


#### 지연된 연산

스트림 연산에서 한 가지 중요한 점은 최종 연산이 수행되기 전까지 중간 연산이 수해오디지 않는다느 것이다. 스트림에 대해 distinct()나 sort()같은 중간 연산을 호출해도 즉각적인 ㅕㅇㄴ산이 수행되는 것은 아니라는 것이다. 중간 연산을 호출하는 것은 단지 어떤 작업이 수행되어야하는지를 지정해주는 것일뿐이다. 최종 연산이 수행되어야 스트릠의 요소들이 중간연산을 거쳐 최종 연산에서 소모된다.

#### Stream<Integer>와 IntStream

요소의 타입이 T인 스트림은 기본적으로 `Stream<T>`이지만, 오토박싱&언박싱으로 인한 비효율을 줄이기 위해 데이터 소스의 요소를 기본형으로 다루는 스트림, IntStream, LongStream, DoubleStream이 제공된다. 일반적으로 `Stream<Integer>`대신 IntStream을 사용하는 것이 더 효율적이고, IntStream에는 int타입의 값으로 자 ㄱ업하는데 유용한 메서드들이 포함되어 있다.

#### 병렬 스트림

스트림으로 데이터를 다룰 때의 장점 중 하나가 병렬 처리가 쉽다는 것이다. fork&join프레임워크으로 작업을 병렬처리하는 것을 13장에서 진행했었는데, 병렬 스트림은 내부적으로 이 프레임워크를 이용해서 자동적으로 연산을 병렬로 수행한다. 우리가 할일이라고는 그저 스트림에 parallel()이라는 메서드를 호출해서 병렬로 연산을 수행하도록 지 시하면 될 뿐이다. 반대로 병렬로 처리되지 않게 하려면 sequential()을 호출하면 된다. 모든 스트림은 기본적으로 병렬 스트림이 아니므로 sequential()을 호출하면 된다. 모든 스트림은 기본적으로 병렬 스트림이 아니므로 sequential()을 호출할 필요가 없다. 이 메서드는 parallel()을 호출한 것을 취소할 때만 사용한다.

> [참고] parallel()과 sequential()은 새로운 스트림을 생성하는 것이 아니라, 그저 스트림의 속성을 변경할 뿐이다.

### 2.2 스트림 만들기

스트림으로 작업을 하려면, 스트림이 필요하니까 일단 스트림을 생성하는 방법부터 먼저 시작하자. 스트림의 소스가 될 수 있는 대상은 배열, 컬렉션, 임의의 수 등 다양하며, 이 다양한 소스들로부터 스트림을 생성하는 방법에 대해서 배우게 될 것이다.

#### 컬렉션

컬렉션의 최고 조상인 Collection에 stream()이 정의되어 있다. 그래서 Collection의 자손인 List와 Set을 구현한 컬렉션 클래스들은 모두 이 메서드로 스트림을 생성할 수 있다. stream()은 해당 컬렉션을 소스(source)로 하는 스트림을 반환한다.

```java
Stream<T> Collection.stream()
```

예를 들어 List로부터 스트림을 생성하는 코드는 다음과 같다.

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);      // 가변인자
Stream<Integer> intStream = list.stream();
```

forEach는 지정된 작업을 스트림의 모든 요소에 대해 수행한다. 아래의 문장은 스트림의 모든 요소를 화면에 출력한다.

```java
intStream.forEach(System.out::println); // 스트림의 모든 요소를 출력
intStream.forEach(System.out::println); // 에러. 스트림이 모두 닫힘
```

한 가지 주의할 점은 forEach()가 스트릠의 요소를 소모하면서 작업을 수행하므로 같은 스트림에 forEach()를 두 번 호출할 수 없다는 것이다. 그래서 스트림의 요소를 한번 더 호출하려면 스트림을 새로 생성해야 한다. forEach()에 의해 스트림의 요소가 소모되는 것이지, 소스의 요소가 소모되는 것은 아니기 때문에 같은 소스로부터 다시 스트림을 생성할 수 있다. 


#### 배열

배열을 소스로 하는 스트림을 생성하는 메서드는 다음과 같이 Stream과 Arrays에 static 메서드로 정의되어 있다.

```java
Stream<T> Stream.of(T... values)
Stream<T> Stream.of(T[])
Stream<T> Arrays.stream(T[])
Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)
```

예를 들어 문자열 스트림은 다음과 같이 생성한다.


```java
Stream<String> strStream = Stream.of("a", "b", "c");
Stream<String> strStream = Stream.of(new String[]{"a", "b", "c"});
Stream<String> strStream = Arrays.stream(new String[]{"a", "b", "c"});
Stream<String> strStream = Arrays.stream(new String[]{"a", "b", "c"}, 0, 3);
```

그리고 int, long, double과 같은 기본형 배열을 소스로 하는 스트림을 생성하는 메서드도 있다.

```java
IntStream IntStream.of(int... values)
IntStream IntStream.of(int[])
IntStream Arrays.stream(int[])
IntStream Arrays.stream(int[] array, int startInclusive, int endExclusive)
```

이 외에도 long과 double타입의 배열로부터 LongStream과 DoubleStream을 반환하는 메서드들이 있지만, 일일이 나열하지 않아도 쉽게 유추해낼 수 있을 것이므로 생략한다.

#### 특정 범위의 정수

IntStream과 LongStream은 다음과 같이 지정된 범위의 연속된 정수를 스트림으로 생성해서 반환하는 range()와 rangeClosed()를 가지고 있다.

```java
IntStream IntStream.range(int begin, int end)
IntStream IntStream.rangeClosed(int begin, int end)
```

range()의 경우 경계의 끝인 end가 포함되지 않고, rangeClosed()의 경우는 포함된다.

int보다 큰 범위의 스트림을 생성하려면 LongStream에 있는 동일한 이름의 메서드들 사용하면 된다.

#### 임의의 수

난수를 생성하는데 사용하는 Random클래스에는 아래와 같은 인스턴스 메서드들이 포함되어 있다. 임 메서드들은 해당 타입의 난수들로 이루어진 스트림을 반환한다.

```java
IntStream ints()
LongStream longs()
DoubleStream doubles()
```

이 메서드들이 반환하는 스트림은 크기가 정해지지 않은 '무한 스트림(infinite stream)'이므로 limit()도 같이 사용해서 스트림의 크기를 제한해 주어야 한다. limit()은 스트림의 개수를 지정하느데 사용되며, 무한 스트림을 유한 스트림으로 만들어 준다.

```java
IntStream intStream = new Random().ints();              // 무한 스트림
intStream.limit(5).forEach(System.out::println);        // 5개의 요소만 출력
```

아래의 메서드들은 매개변수로 스트림의 크기를 지정해서 `유한 스트림`을 생성해서 반환하므로 limit()을 사용하지 않아도 된다.

```java
IntStream ints(long streamSize)
LongStream longs(long streamSize)
DoubleStream doubles(long streamSize)
```

위 메서드들에 의해 생성된 스트림의 난수는 아래의 범위를 갖는다.

```java
Integer.MIN_VALUE <= ints() <= Integer.MAX_VALUE
Long.MIN_VALUE <= longs() <= LONG.MAX_VALUE
0.0 <= doubles() <= 1.0
```

지정된 범위(begin~end)의 난수를 발생시키는 스트림을 얻는 메서드는 아래와 같다. 단, end는 범위에 포함되지 않는다.

```java
IntStream ints(int begin, int end);
LongStream longs(long begin, long end);
DoubleStream doubles(double begin, double end);

IntStream ints(long streamSize, int begin, int end);
LongStream longs(long streamSize, long begin, long end);
DoubleStream doubles(long streamSize, double begin, double end);
```

#### 람다식 - iterate(), generate()

Stream클래스의 iterate()와 generate()는 람다식을 배개변수로 받아서, 이 람다식에 의해 계산되는 값들을 요소로 하는 무한 스트림을 생성한다.

```java
static <T> Stream<T> iterate(T seed, UnaryOperator<T> t);
static <T> Stream<T> generate(Supplier<T> s);
```

iteate()는 씨앗값(seed)으로 지정된 값부터 시작해서, 람다식 f에 의해 계산된 결과를 다시 seed값으로 해서 계산을 반속한다. 아래의 eventStream은 0부터 시작해서 값이 2씩 계속 증가한다.

```java
Stream<Integer> evenStream = Stream.iterate(0, n -> n + 2);     // 0, 2, 4, 6, ...
```

generate()도 iterate()처럼, 람다식에 의해 계산되는 값을 요소로 하는 무한 스트림을 생성해서 반환하지만, iterate()와 달리, 이전 결과를 이용해서 다음 요소를 게산하지 않는다. 

```java
Stream<Double> randomStream = Stream.generate(Math::random);
Stream<Integer> oneStream = Stream.generate(() -> 1);
```

그리고 generate()에 정의된 매개변수의 타입은 Supplier<T>이므로 매개변수가 없는 람다식만 허용된다. 한 가지 주의할 점은 iterate()와 generate()에 의해 생성된 스트림을 아래와 같이 기본형 스트림 타입의 참조변수로 다룰 수 없다는 것이다.

```java
IntStream evenStream = Stream.iterate(0, n -> n + 2);      // 에러.
DoubleStream randomStream = evenStream.generate(Math::random);      // 에러.
```

굳이 필요하다면, 아래와 같이 mapToInt()와 같은 메서드로 변환을 해야 한다.

```java
IntStream evenStream = Stream.iterate(0, n -> n + 2).mapToInt(Integer::valueOf);      // 에러.
Stream<Integer> stream = evenStream.generate(Math::random).boxed();      // 에러.
```

반대로 IntStream타입의 스트림을 Stream<Integer>타입으로 변환하려면, boxed()를 사용하면 된다.

#### 파일

java.nio.file.Files는 파일을 다루는데 필요한 유용한 메서드들을 제공하는데, list()는 지정된 디렉토리(dir)에 있는 파이르이 목록을 소스로 하는 스트림을 생성해서 반환한다.

```java
Stream<Path> Files.list(Path dir)                                                                                                                                                                                                            
```