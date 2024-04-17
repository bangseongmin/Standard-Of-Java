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

이 외에도 Files 클래스에는 Path를 요소로 하는 스트림을 생성하는 메서드가 더 있지만, 이 장의 주제를 벗어나므로 설명을 생략한다.
그리고 파일의 한 행(line)을 요소로 하는 스트림을 생성하는 메서드도 있다. 아래의 세 번째 메서드는 BufferedReader클래스에 속한 것인데, 파일 뿐만 아니라 다른 입력대상으로부터도 데이터를 행단위로 읽어올 수 있다.
> [참고] BufferedReader에 대한 자세한 내용은 p.904 참고
```Java
Stream<String> Files.lines(Path path)
Stream<String> Files.lines(Path path, Charset cs)
Stream<String> lines()      // BufferedReader 클래스의 메서드
```
#### 빈 스트림
요소가 하나도 없는 비어있는 스트림을 생성할 수도 있다. 스트릠에 연산을 수행한 겨로가가 하나도 없을 때, null보다 빈 스트림을 반환하는 것이 낫다.
```angular2html
Stream emptyStream = Stream.empty();
long count = emptyStream.count();
```
count()는 스트림 요소의 개수를 반환하며, 위의 문장에서 변수 count의 값은 0이 된다.
#### 두 스트림의 연결
Stream의 static 메서드인 concat()을 사용하면, 두 스트림을 하나로 연결할 수 있다. 물론 연결하려는 두 스트림의 요소는 같은 타입이어야 한다.
```java
String[] str1 = {“123”, “456", “789”};
String[] str2 = {“ABC”, “abc”, “DEF”};
Stream<String> strs1 = Stream.of(str1);
Stream<String> strs2 = Stream.of(str2);
Stream<String> strs3 = Stream.concat(strs1, strs2);
```
### 2.3 스트림의 중간 연산
#### 스트림 자르기 - skip(), limit()
skit()과 limit()은 스트림의 일부를 잘라낼 때 사용하며, 사용법은 아주 간단하다. skip(3)은 처음 3개의 요소를 건너뛰고, limit(5)는 스트림의 요소를 5개로 제한한다.
```java
Stream<T> skip(long n)
Stream<T> limit(long maxSize)
```
예를 들어 10개의 요소를 가진 스트림에 skip(3)과 limit(5)을 순서대로 적용하면 4번째 요소부터 5개의 요소를 가진 스트림이 반환된다.
```java
IntStream intStream = IntStream.rangeClosed(1, 10); // 1 ~ 10의 요소를 가진 스트림
intStream.skip(3).limit(5).forEach(System.out::println);
```
기본형 스트림에도 skip()과 limit()이 정의되어 있는데, 반환 타입이 기본형 스트림이라는 점만 다르다.
```java
IntStream skip(long n)
IntStream limit(long maxSize)
```
#### 스트림 요소 걸러내기 - filter, distinct()
distinct()는 스트림에서 중복된 요소들을 제거하고, filter()는 주어진 조건(Predicate)에 맞지 않는 요소를 걸러낸다.
```java
Stream<T> filter(Predicate<? super T> predicate);
Stream<T> distinct()
```
distinct()의 사용 방법은 간단하다.
```java
IntStream intStream = IntStream.of(1, 2, 3, 4, 5, 5);
intStream.distinct().forEach(System.out::print);      // 12345
```
filter는 매개변수로 Predicate를 필요로 하는데, 아래와 같이 연산결과가 boolean인 람다식을 사용해도 된다.
```java
IntStream intStream = IntStream.of(1, 2, 3, 4, 5, 5);
intStream.filter(i -> i % 2 == 0).forEach(System.out::print);      // 24
```
#### 정렬 - sorted()
스트림을 정렬할 때는 sorted()를 사용하면 된다.
```java
Stream<T> sorted()
Stream<T> sorted(Comparator<? super T> comparator)
```
sorted()는 지정된 Comparator로 스트림을 정렬하는데, Comparator대신 int값을 반환하는 람다식을 사용하는 것도 가능하다. Comparator를 지정하지 않으면 스트림의 요소의 기본 정렬 기준(Comparator)으로 정렬한다. 단, 스트림의 요소가 Comparable을 구현한 클래스가 아니라면 예외가 발생한다.
```java
Stream<String> strStream = Stream.of(“dd”, “aaa”, “CC”, “cc”, “b”);
strStream.sorted().forEach(System.out::print);  // CCaaabccdd
```
위의 코드는 문자열 스트림을 String에 정의된 기본 정렬(사전순 정렬)로 정렬해서 출력한다. 아래의 표는 위의 문자열 스트림(strStream)을 다야한 방법으로 정렬한 후에 forEach(System.out::println)로 출력한 겨로가를 보여준다.
> [참고] String.CASE_INSENSITIVE_ORDER는 String클래스에 정의한 Comparator이다.
| 문자열 스트림 정렬 방법                                                                                                                                                                                       | 출력 결과 |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| - strStream.sorted() // 기본 정렬 - strStream.sorted(Comparator.naturalOrder()) // 기본 정렬 - strStream.sorted((s1, s2)-> s1.compareTo(s2)) // 람다식도 가능 - strStream.sorted(String::compareTo); // 위의 문장과 동일 | CCaaabccdd |
| - strStream.sorted(Comparator.reverseOrder()) // 기본 정렬의 역순, strStream.sorted(Comparator.<String>naturalOrder().reversed())                                                                          | ddccbaaaCC |
| strStream.sorted(String.CASE_INSENSITIVE_ORDER)   // 대소문자 구분 안함                                                                                                                                     | aaaCCccdd |
| strStream.sorted(String.CASE_INSENSITIVE_ORDER.reveresed())                                                                                                                                         | ddCCccbaaa |
| strStrean.sorted(Comparator.comparing(String::length)), strStream.sorted(Comparator.comparingInt(String::length))                                                                                   | bddCCccaaa |
| strStream.sorted(Comparator.comparing(String::length).reversed()) | aaaddCCccb|
JDK1.8부터 Comparator 인터페이스에 static메서드와 default 메서드가 많이 추가되었는데, 이 메서드들을 이용하면 정렬이 쉬워진다. 이 메서드들은 모두 Comparator<T>를 반환하며, 아래의 메서드 목록은 제네릭에서 와일드 카드를 제거하며 간단히 한 것이다. 보다 정확한 메서드의 선언을 보려면 Java API 문서를 참조하자.
```java
// Comparator의 default 메서드
reversed()
thenComparing(Comparator<T> other)
thenComparing(Function<T, U> keyExtrator)
thenComparing(Function<T, U> keyExtrator, Compartor<U> keyComp)
thenComparingInt(ToIntFunction<T> keyExtrator)
thenComparingLong(ToLongFunction<T> keyExtrator)
thenComparingDouble(ToDoubleFunction<T> keyExtrator)
// Comparator의 static 메서드
naturalOrder()
reverseOrder()
comparing(Function<T, U> keyExtractor)
comparing(Function<T, U> keyExtractor, Compartor<U> keyComparator)
comparingInt(ToIntFunction<T> keyExtractor)
comparingLong(ToLongtFunction<T> keyExtractor)
comparingDouble(ToDoubleFunction<T> keyExtractor)
nullsFirst(Compartor<T> compartor)
nullsLast(Compartor<T> compartor)
```
정렬에 사용되는 메서드의 개수가 많지만, 가장 기본적인 메서드는 comparing()이다.
```java
comparing(Function<T, U> keyExtrator)
comparing(Function<T, U> keyExtrator, Comparator<U> keyComparator)
```
스트림의 요소가 Comparable을 구현한 경우, 매개변수 하나짜리를 사용하면 되고 그렇지 않은 경우, 추가적인 매개변수로 정렬기준(Comparator)을 따로 지정해 줘야한다.
```java
comparingInt(ToIntFunction<T> keyExtrator)
comparingLong(ToLongFunction<T> keyExtrator)
comparingDouble(ToDoubleFunction<T> keyExtrator)
```
비교대상이 기본형인 경우, comparing()대신 위의 메서드를 사용하면 오토박싱과 언박싱과정이 없어서 더 효율적이다. 그리고 정렬 조건을 추가할 때는 thenComparing()을 사용한다.
```java
thenComparing(Comparator<T> other)
thenComparing(Function<T, U> keyExtractor)
thenComparing(Function<T, U> keyExtractor, Comparator<U> keyComp)
```
예를 들어 학생 스트림(studentStream)을 반별, 성적순, 그리고 이름순으로 정렬하여 출력하려면 다음과 같이 한다.
```java
studentStream.sorted(Compartor.comparing(Student::getBan)
                            .thenComparing(Student::getTotalScore))
                            .thenComparing(Student::getName)
                            .forEach(System.out::println);
```
예제 14-8는 학생의 성적을 반별 오름차순, 총점별 내림차순으로 정렬하여 출력한다.

#### 변환 - map()
스트릠의 요소에 저정된 값 중에서 원하는 필드만 뽑아내거나 특정 형태로 변환해야 할 때가 있다. 이 때 사용하는 것이 바로 map()이다. 이 메서드의 선언부는 아래와 같으며, 매개변수로 T타입을 R타입으로 변환해서 반환하는 함수를 지정해야 한다.

```java
Stream<R> map(Function<? super T, ? extends R> mapper)
```
예를 들어 File의 스트릠에서 파일의 이름만 뽑아서 출력하고 싶을 때, 아래와 같이 map()을 이용하면 File객체에서 파일의 이름(String)만 간단히 뽑아낼 수 있다.

```java
Stream<File> fileStream = Stream.of(new File("Ex1.java"), new File("Ex1"), new File("Ex1.bak"), new File("Ex2.java"), new File("Ex1.txt"));

// map()으로 Stream<File>을 Stream<String>으로 변환
Stream<String> filenameStream = fileStream.map(File::getName);
filenameStream.forEach(System.out::println);    // 스트림의 모든 파일이름을 출력
```

map() 역시 중간 연산이므로, 연산결과는 String을 요소로 하는 스트림이다. map()으로 Stream<File>을 Stream<String>으로 변환했다고 볼 수 있다.

그리고 map()도 filter()처럼 하나의 스트림에 여러 번 적용할 수 있다. 다음의 문장은 File의 스트림에서 파일의 확장자만을 뽑은 다음 중복을 제거해서 출력한다.

```java
fileStream.map(File::getName)
        .filter(s -> s.indexOf(".") != -1)
        .map(s -> s.substring(s.indexOf(".") + 1))
        .map(String::toUpperCase)
        .distinct()
        .forEach(System.out::print);
```

#### 조회 - peek()

연산과 연산 사이에 올바르게 처리되었는지 확인하고 싶다면, peek()을 사용하자. forEach()와 달리 스트림의 요소를 소모하지 않으므로 연산 사이에 여러 번 끼워 넣어도 문제가 되지 않는다.

```java
fileStream.map(File::getName)
        .filter(s -> s.indexOf(".") != -1)
        .peek(s -> System.out.printf("fileName = %s%n", s))
        .map(s -> s.substring(s.indexOf(".") + 1))
        .peek(s -> System.out.printf("extension = %s%n", s))
        .forEach(System.out::print);
```

filter()나 map()의 결과를 확인할 때 유용하게 사용될 수 있다.


#### mapToInt(), mapToLong(), mapToDouble()

map()은 연산의 결과로 Stream<T> 타입의 스트림을 반환하는데, 스트림의 요소를 숫자로 변환하는 경우 IntStream과 같은 기본현 스트림으로 변환하는 것이 더 유용할 수 있다. Stream<T>타입의 스트림을 기본형 스트림으로 변환할 때 사용하는 것이 아래의 메서드들이다.

```java
DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
IntStream mapToInt(ToIntFunction<? super T> mapper)
LongStream mapToLong(ToLongFunction<? super T> mapper)
```

앞서 사용했던 studentStream에서, 스트림에 포함된 모든 학생의 성적을 합산해야 한다면, map()으로 학생의 총점을 뽑아서 새로운 스트림을 만들어 낼 수 있다.

```java
Stream<Integer> studentScoreStream = studentStream.amp(Student::getTotalScore);
```

그러나 이럴 때는 애초부터 mapToInt()를 사용해서 Stream<Integer>가 아닌 IntStream타입의 스트림을 생성해서 사용하는 것이 더 효율적이다. 성적을 더할 때, Interger를 int로 변환할 필요가 없기 때문이다.

```java
IntStream studentScoreStream = studentStream.mapToInt(Student::getTotalScore);
int allTotalScore = studentScoreStream.sum();
```

count()만 지원하는 Stream<T>와 달리 IntStream과 같은 기본형 스트림은 숫자를 다루는데 편리한 메서드들을 제공한다.

```java
int sum()
OptionalDouble average()
OptionalInt max()
OptionalInt min()
```

스트림의 요소가 하나도 없을 때, sum()은 0을 반환하면 그만이지만 다른 메서드들은 단순히 0을 반환한ㄹ 수 없다. 여러 요소들을 합한 평균이 0일 수도 있기 때문이다. 이를 구분하기 위해 단순휘 dobule값을 반환하는 대신, dobule타입의 값을 내부적으로 가지고 있는 OptionalDouble을 반환하는 것이다. OptionalInt, OptionalDouble 등은 일종의 래퍼클래스로 각각 int값과 Double값을 내부적으로 가지고 있다.

그리고 이 메서드들은 최종연산이기 때무에 호출 후에 스트림이 닫힌다는 점을 주의해야 한다. 아래의 코드에서처럼 하나의 스트림에 sum()과 average()를 연산해서 호출할 수 없다.

```java
IntStream scoreStream = studentStream.mapToInt(Student::getTotalScore);

long totalScore = scoreStream.sum();
OptionalDouble average = scoreStream.average();

double d = average.getAsDouble();
```

sum()과 average()를 모두 호출해야할 때, 스트림을 또 생성해야하므로 불편하다. 그래서 summaryStatistics()라는 메서드가 따로 제공된다.

```java
IntSummaryStatistics stat = scoreStream.summaryStatistics();
long totalCount = stat.getCount();
long totalScore = stat.getSum();
double avgScore = stat.getAverage();
int minScore = stat.getMin();
int maxScore = stat.getMax();
```

IntSummaryStatistics는 위와 같이 다양한 종류의 메서드를 제공하며, 이 중에서 필요한 것만 골라서 사용하면 된다.

기본형 스트림 LongStream과 DoubleStream도 IntStream도 IntStream과 같은 연산(반환타입은 다름)을 지원한다. 반대로 IntStream을 Stream<T>로 변환할 때는 mapToObj()를, Stream<Integer>로 변환할 때는 boxed()를 사용한다.

```java
Stream<U> mapToObj(IntFunction<? extends U> mapper)
Stream<Integer> boxed()
```

아래의 로또번호를 생성해서 출력하는 코드인데, mapToObj()를 이용해서 IntStream을 Stream<String>으로 변환하였다.

```java
IntStream intStream = new Random().ints(1, 46);
Stream<String> lottoStream = intStream.distinct().limit(6).sorted().mapToObj(i -> i + ",");
lottoStream.forEach(System.out::print);
```

참고로 CharSequence에 정의된 chars()는 String이나 StringBuffer에 저장된 문자들을 IntStream으로 다룰 수 있게 해준다.

```java
IntStream charStream = "12345".chars();
int charSum = charStream.map(ch -> ch - '0').sum();
```

위의 코드에서 사용된 map()은 IntStream에 정의된 것으로 IntStream을 결과로 반환한다. 그리고 mapToInt()와 함께 자주 사용되는 메서드로는 Integer의 parseInt()나 valueOf()가 있다는 것도 알아두자.

```java
Stream<String> -> IntStream 변환 할 때, mapToInt(Integer::parseInt)
Stream<Integer> -> IntStream 변환 할 때, mapToInt(Integer::intValue)
```

#### flatMap() - Stream<T[]>를 Stream<T>로 변환

스트림의 요소가 배열이거나 map()의 연산결과가 배열인 경우, 즉 스트림의 타입이 Stream<T[]>인 경우, Stream<T>로 다루는 것이 더 편리할 때가 있다. 그럴 때는 map() 대신 flatMap()을 사용하면 된다.

예를 들어 아래와 같이 요소가 문자열 배열(String[])인 스트림이 있을 때, 

```java
Stream<String[]> strArrStream = Stream.of(
        new String[]{"abc", "def", "ghi"},
        new String[]{"ABC", "DEF", "GFG"}
);
```

각 요소의 문자열들을 합쳐서 문자열이 요소인 스트림, 즉 Stream<String>으로 만들려면 어떻게 해야 할까?

먼저 스트림의 요소를 변환해야하니까 일단 map()을 써야할 것이고 여기에 배열을 스트림으로 만들어주는 Arrays.stream(T[])를 함께 사용해보자.

```java
Stream<String<String>> strStream = strArrStream.map(Arrays::stream);
```

예상한 것과 달리, Stream<String[]>을 'map(Arrays::stream)' 으로 변환한 결과는 Stream<String>이 아닌, Stream<Stream<String>>이다. 즉 스트림의 스트림인 것이다. 이 상황을 그림으로 그려보면 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/4eacc283-d65d-49a6-9f56-f39ec9dea45a)

각 요소의 문자열들이 합쳐지지 않고, 스트림의 스트림 형태로 되어버렸다. 이 때, 간단히 map()을 아래와 같이 faltMap()으로 바꾸기만 하면 우리가 원하는 결과를 얻을 수 있다.

```java
Stream<Stream<String>> strStrStream = strArrStream.map(Arrays::stream);
Stream<String> strStream = strArrStream.flatMap(Arrays::stream);
```

위의 코드를 그림으로 표현하면 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/1bfbe220-cc85-49a2-ae98-a67a74d1aade)

flatMap()은 map()과 달리 아래의 그림처럼, 스트림의 스트림이 아닌 스트림으로 만들어 준다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/f824bc57-a318-4623-a8b2-3848c4993722)

또 다른 경우를 예로 들어보자. 아래와 같이 여러 문장을 요소로 하는 스트림이 있을 때, 이 문장들을 split()으로 나눠서 요소가 단어인 스트림을 만들고 싶다면 어떻게 해야할까?

```java
String[] lineArr = {
    "Beliver or not It is true",
    "Do or do not There is no try",
};

Stream<String> lineStream = Arrays.stream(lineArr);
Stream<String[]> strArrStream = lineStream.map(line -> Stream.of(line.split(" +")));
```

위의 문장에서 알 수 있는 듯이, map()은 Stream<String>이 아니라 Stream<String[]>을 결과로 돌려준다. 이럴 때도 map()대신 flatMap()으로 원하는 결과를 얻을 수 있다.

```java
Stream<String[]> strArrStream = lineStream.map(line -> Stream.of(line.split(" +")));
```

map()과 flatMap()의 차이를 간단히 정리하면 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/8f8f2a54-d668-4b58-89c0-a4caa0386302)

strStream의 단어들을 모두 소문자로 변환하고, 중복된 단어들을 제거한 다음에 정렬해서 출력하는 문장은 다음과 같다.

```java
strStream.map(String::toLowerCase)          // 모든 문자를 소문자로 변경
        .distinct()                         // 중복된 단어 제거
        .sorted()                           // 사전순으로 정렬
        .forEach(System.out::println);      // 출력
```

드물지만, 스트림을 요소로 하는 스트림, 즉 스트림의 스트림을 하나의 스트림으로 합칠때도 flatMap()을 사용한다.

```java
Stream<String> strStream = Stream.of("abc", "def", "jklmn");
Stream<String> strStream2 = Stream.of("ABC", "GHI", "JKLMN");

Stream<Stream<String>>  strmStream = Stream.of(strStream, strStream2);
```

위와 같이 요소의 타입이 Stream<String>인 스트림(Stream<Stream<String>>>)이 있을 때, 이 스트림을 Stream<String>으로 변환하려면 다음과 같이 map()과 flatMap()을 함께 사용해야 한다.

```java
Stream<String> strStream = strStrm.map(s -> s.toArray(String[]::new))       // Stream<Stream<String>> => Stream<String[]>
        .flatMap(Arrays::stream);                                           // Stream<String[]> => Stream<String>
```

toArray()는 스트림을 배열로 변환해서 반환한다. 매개변수를 지정하지 않으면 Object[]을 반환하므로 위와 같이 특정 타입의 생성자를 지정해줘야 한다. 여기서는 String[] 배열의 생성자(String[]::new)를 지정하였다. 그 다음엔 flatMap()으로 Stream<String[]>을 Stream<String>으로 변환한다.


### 2.4 Optional<T>와 OptionalInt

Optional<T>은 제네릭 클래스로 "T타입의 객체"를 감싸는 래퍼 클래스이다. 그래서 Optional타입의 객체에는 모든 타입의 참조변수를 담을 수 있다.

> [참고] java.util.Optional은 JDK1.8부터 추가되었다.

```java
public final class Optional<T> {
    private final T value;      // T 타입의 참조변수
}
```

최종 연산의 결과를 그냥 반환하는 게 아니라 Optional객체에 담아서 반환하는 것이다.

이처럼 객체에 담아서 반환을 하면, 반환된 결과가 null인지 매번 if문으로 체크하는 대신 Optional에 정의된 메서드를 통해서 간단히 처리할 수 있다.

널 체크를 위한 if문 없이도 NullPointerException이 발생하지 않는 보다 간견하고 안전한 코드로 작성하는 것이 가능해진 것이다.

> [참고] Objects 클래스에는 isNull(), nonNull(), requireNonNull()과 같은 메서드가 있는 것도 널 체크를 위한 if문을 메서드 안으로 넣어서 코드의 복잡도를 낮추기 위한 것이다.


#### Optional객체 생성하기

Optional객체를 생성할 떄는 of() 또는 ofNullable()을 사용한다.

```java
String str = "abc";
Optional<String> optVal = Optional.of(str);
Optional<String> optVal = Optional.of("abc");
Optional<String> optVal = Optional.of(new String("abc"));
```

만일 참조변수의 값이 null일 가능성이 있으면, of()대신 ofNullable()을 사용해야한다. of()는 매개변수의 값이 null이면 NullPointerException이 발생하기 때문이다.

```java
Optional<String> optVal = Optional.of(null);            // NullPointerException 발생
Optional<String> optVal = Optional.ofNullable(null);    // OK
```

Optional<T> 타입의 참조변수를 기본값으로 초기화할 때는 empty()를 사용한다. null로 초기화하는 것이 가능하지만, empty()로 초기화하는 것이 바람직하다.

> [참고] empty()는 제네릭 메서드라서 앞에 <T>를 붙였다. 추정 가능하므로 생략할 수 있다.

```java
Optional<String> optVal = null;                         // 널로 초기화
Optional<String> optVal = Optional.<String>empty();     // 빈 객체로 초기화
```

#### Optional객체의 값 가져오기

Optional 객체에 저장된 값을 가져올 때는 get()을 사용한다. 값이 null일 때는 NoSuchElementException이 발생하며, 이를 대비해서 orElse()로 대체할 값을 지정할 수 있다.

```java
Optional<String> optVal = Optional.of("abc");
String str1 = optVal.get();          // optVal에 저장된 값을 반환. null이면 예외발생
String str2 = optVal.getElse("");    // optVal에 저장된 값이 null일 때는, ""반환 
```

orElse()의 변형으로는 null을 대체할 값을 반환하는 람다식을 지정할 수있는 orElseGet()과 null일 때 지정된 예외를 발생시키는 orElseThrow()가 있다.

```java
T orElseGet(Supplier<? extends T> other);
T orElseThrow(Supplier<? extends X> exceptionSupplier)
```

사용하는 방법은 아래와 같다.

```java
String str3 = optVal2.orElseGet(String::new);                       // () -> new String()와 동일
String str4 = optVal2.orElseThrow(NullPointerException::new);       // 널이면 예외 발생
```

Stream처럼 Optional객체에도 filter(), map(), flatMap()을 사용할 수 있다. map()의 연산결과가 Optional<Optional<T>>일 때, flatMap()을 사용하면 Optional<T>를 결과로 얻는다. 만일 Optional객체의 값이 null이면, 이 메서드들은 아무 일도 하지 않는다.

```java
int result = Optional.of("123")
        .filter(x -> x.length() > 0)
        .map(Integer::parseInt).orElse(-1);     // result = 123

result = Optional.of("")
        .filter(x -> x.length() > 0)
        .map(Integer::parseInt).orElse(-1);     // result = -1;
```

parseInt()는 예외가 발생하기 쉬운 메서드이다. 만일 예외처리된 메서드를 만든다면 다음과 같을 것이다.

```java
static int optStrToInt(Optional<String> optStr, int defaultValue) {
    try {
        return optStr.map(Integer::parseInt).get();        
    }catch(Exception e) {
        return defaultValue;
    }
}
```

isPresent()는 Optional객체의 값이 null이면 false를, 아니면 true를 반환한다. ifPresent(Consumer<T> block)은 값이 있으면 주어진 람다식을 실행하고, 없으면 아무일도 하지 않는다.

```java
if(str != null) {
    System.out.println(str);    
}
```

만일 위와 같은 조건문이 있다면, isPresent()를 다음과 같이 쓸 수 있다.

```java
if(Optional.ofNullable(str).isPresent()) {
    System.out.println(str);    
}
```

이 코드를 ifPresent()를 이용해서 바꾸면 더 간단히 할 수 있다. 아래의 문장은 참조변수 str이 null이 아닐때만 값을 출력하고, null이면 아무 일도 일어나지 않는다.

```java
Optional.ofNullable(str).ifPresent(System.out::println);
```

ifPresent()는 Optional<T>를 반환하는 findAny()나 findFirst()와 같은 최종 연산과 잘 어울린다. Stream클래스에 정의된 메서드 중에서 Optional<T>를 반환하는 것들은 다음과 같다.

```java
Optional<T> findAny()
Optional<T> findFirst()
Optional<T> max(Comparator<? super T> comparator)
Optional<T> min(Comparator<? super T> comparator)
Optional<T> reduce(BinaryOperator<T> acculmulator)
```

이처럼 Optional<T>를 결과로 반환하는 최종 연산 메서듣들은 몇 개 없다. 심지어 amx()와 min()같은 메서드들은 reduce()를 이용해ㅔ서 작성된 것이다.

#### OptionalInt, OptionalLong, OptionalDouble

IntStream과 같은 기본현 스트림에는 Optional도 기본형을 값으로 하는 OptionalInt, OptionalLong, OptionalDouble을 반환한다. 아래의 목록은 IntStream에 정의된 메서드들이다.

```java
OptionalInt findAny()
OptionalInt findFirst()
OptionalInt reduce(IntBinaryOperator op)
OptionalInt max()
OptionalInt min()
OptionalDouble average()
```

반환 타입이 Optional<T>가 아니라는 것을 제외하고는 Stream에 정의된 것과 비슷하다. 그리고 기본형 Optional에 저장된 값을 꺼낼 때 사용하는 메서드의 이름이 조금씩 다르다는 것에 주의하자.

| Optional클래스 | 값을 반환하는 메서드 |
| --- | --- |
| Optional<T> | T get() |
| OptionalInt | Int getAsInt() |
| OptionalLong | long getAsLong() |
| OptionalDouble | Double getAsDouble() |

OptionalInt는 다음과 같이 정의되어 있다. 앞서 래퍼 클래스에 대해서 배웠으므로, 이렇게 정의되어 있을 것이라고 짐작하는 것은 그리 어려운 일이 아니었을 것이다.

```java
public final class OptionalInt {
    private final boolean isPresent;        // 값이 저장되어 있으면 true
    private final int value;                // int 타입의 변수
}
```

기본형 int의 기본값은 0이므로 아무런 값도 갖지 않는 OptionalInt에 저장되는 값은 0일 것이다. 그러면, 아래의 두 OptionalInt객체는 같은 것일까?

```java
OptionalInt opt = OptionalInt.of(0);            // OptionalInt에 0을 저장
OptionalInt opt2 = OptionalInt.empty(0);        // OptionalInt에 0을 저장
```

다행이 저장된 값이 없는 것과 0이 저장된 것은 isPresent라는 인스턴스 변수로 구분이 가능하다. isPresent()는 이 인스턴스변수의 값을 반환한다.

```java
System.out.println(opt.isPresent());        // true
System.out.println(opt2.isPresent());       // false
        
System.out.println(opt.getAsInt());         // 0
System.out.println(opt2.getAsInt());        // NoSuchElementException 발생
        
System.out.println(opt.equals(opt2));       // false
```

그러나 Optional객체의 경우 null을 저장하면 비어있는 것과 동일하게 취급한다.

```java
Optional<String> opt = Optional.ofNullable(null);
Optional<String> opt2 = Optional.empty();

System.out.println(opt.equals(opt2));   // true
```


### 2.5 스트림의 최종 연산

최종 연산은 스트림의 요소를 소모해서 결과를 만들어낸다. 그래서 최종 연산후에는 스트림이 닫히게 되고 더 이상 사용할 수 없다. 최종 연산의 결과는 스트림 요소의 합과 같은 단일 값이거나, 스트림의 요소가 담긴 배열 또는 컬렉션일 수 있다.

#### forEach()

forEach()는 peek()과 달리 스트림의 요소를 소모하는 최종연산이다. 반환 타입이 void이므로 스트림의 요소를 출력하는 용도로 많이 사용된다.

```java
void forEach(Consumer<? super T> action)
```

#### 조건 검사 - allMatch(), anyMatch(), noneMatch(), findFirst(), findAny()

스트림의 요소에 대해 지정된 조건에 모든 요소가 일치하는 지, 일부가 일치하는지 아닌면 어떤 요소도 일치하지 않는지 확인하는데 사용할 수 있는 메서드들이다. 이 메서드들은 모두 매개변수로 Predicate를 요구하며, 연산결과로 boolean을 반환한다.

```java
boolean allMatch(Predicate<? super T> predicate)
boolean anyMatch(Predicate<? super T> predicate)
boolean noneMatch(Predicate<? super T> predicate)
```

예를 들어 학생들의 성적 정보 스트림 stuStream에서 총점이 낮게점(총점 100점이하)인 학생이 있는지 확인하는 방법은 다음과 같다.

```java
boolean noFailed = stuStream.anyMatch(s -> s.getTotalScore() <= 100)
```

이외에도 스트림의 요소 중에서 조건에 일치하는 첫 번째 것을 반환하는 findFirst()가 있는데, 주로 filter()와 함께 사용되어 조건에 맞는 스트림의 요소가 있는지 확인하는데 사용된다. 병렬 스트림인 경우에는 findFirst()대신 findAny()를 사용해야 한다.

```java
Optional<Student> stu = stuStream.filter(s -> s.getTotalScore() <= 100).findFirst();
Optional<Student> stu = parallelStream.filter(s -> s.getTotalScore() <= 100).findAny();
```

findAny()와 findFirst()의 반환 타입은 Optioanl<T>이며, 스트림의 요소가 없을 때는 비어있는 Optional객체를 반환한다.

> [참고] 비어있는 Optioanal 객체는 내부적으로 null을 저장하고 있다.


#### 통계 - count(), sum(), average(), max(), min()

IntStream과 같은 기본형 스트림에는 스트림의 요소들에 대한 통계 정보를 얻을 수 있는 메서드들이 있다. 그러나 기본형 스트림이 아닌 경우에는 통게와 관련된 메서드들이 아래의 3개뿐이다.

> [참고] 기본형 스트림의 min(), max()와 달리 매개변수로 Comparator를 필요로 한다는 차이가 있다.

```java
long count()
Optional<T> max(Comparator<? super T> comparator)
Optional<T> min(Comparator<? super T> comparator)
```

대부분의 경우 위의 메서들를 사용하기보다 기본형 스트림으로 변환하거나, 아니면 앞으로 배우게 될 reduce()와 collect()를 사용해서 통계 정보를 얻는다.

#### reduce()

reduce()s는 이름에서 짐작할 수 있듯이, 스트림의 요소를 줄여나가면서 연산을 수행하고 최종결과를 반환한다. 그래서 매개변수의 타입이 BinaryOperator<T> 인 것이다. 처음 두 요소를 가지고 연산한 결과를 가지고 그 다음 요소와 연산한다.

이 과정에서 스트림의 요소를 하나씩 소모하게 되며, 스트림의 모든 요소를 소모하게 되면 그 결과를 반환한다.

```java
Optional<T> reduce(BinaryOperator<T> acculmulator)
```

이외 에도 연산결과의 초기값(identity)을 갖는 reduce()도 있는데, 이 메서드들은 초기값과 스트림의 첫 번째 요소로 연산을 시작한다. 스트릠의 요소가 하나도 없는 경우, 초기값이 반환되므로 반환 타입이 Optional<T>가 아니라 T이다.

> [참고] BinaryOperator<T>는 BiFunction의 자손이며, BiFunction<T, T, T>와 동등하다.

```java
T reduce(T identtity, BinaryOperator<T> accumulator)
U redcue(U identity, BiFunction<U,T,U> accumulator, BinaryOperator<U> combiner);
```

위의 두 번째 메서드의 마지막 매개변수인 combiner는 병렬 스트림에 의해 처리된 결과를 합칠 때 사용하기 위해 사용하는 것이며, 후에 병렬 스트림에서 명할 것이다.

앞서 소개한 최종 연산 count()와 sum() 등은 내부적으로 모두 reduce()를 이용해서 아래와 같이 작성된 것이다.

```java
int count = intStream.reduce(0, (a, b) -> a + 1);       // count
int sum = intStream.reduce(0, (a, b) -> a + b);         // sum()
int max = intStream.reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b); // MAX
int min = intStream.reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a: b);  // MIN
```

사실 max()와 min()의 경우, 초기값이 필요없으므로 Optional<T>를 반환하는 매개변수 하나짜리 reduce()를 사용하는 것이 낫다. 단, intStream의 타입이 IntStream인 경우 OptionalInt를 사용해야한다. Stream<T>와 달리 IntStream에 정의된 reduce()의 반환 타입이 OptionalInt이기 때문이다.

```java
// OptionalInt reduce(IntBinaryOperator accumulator)
OptionalInt max = intStream.reduce((a, b) -> a > b ? a : b);        // MAX
OptionalInt min = intStream.reduce((a, b) -> a < b ? a : b);        // MIN
```

참고로 위의 문장들에서 람다식으로 Integer클래스의 static메서드 max()와 min()을 이용해서 메서드 참조로 바꾸면 다음과 같다.

```java
OptionalInt max = intStream.reduce(Integer::max);
OptionalInt min = intStream.reduce(Integer::min);
```

그리고 OptionalInt에 저장된 값을 꺼내려면 아래와 같이 하면 된다.

```java
int maxValue = max.getAsInt();      // OptionalInt에 저장된 값을 maxValue에 저장
```

reduce()가 내부적으로 어떻게 동작하는지 이해를 돕기 위해, reduce()로 스트림의 모든 요소를 다 더하는 과정을 for문으로 표현해 보았다.

```java
int a = identity;       // 초기값을 a에 저장한다.

for(int b : stream) {
    a = a + b;          // 모든 요소의 값을 a에 누적    
}
```

위의 for문을 보고 나면, reduce()가 아마도 다음과 같이 작성되어 있을 것이라고 추측하는 것은 그리 어려운 일이 아니다.

````java
T reduce(T identity, BInaryOperator<T> accumulator) {
    T a = identity;
    
    for(T b : stream)
        a = accumulator.apply(a, b);
    
    return a;
}
````

reduce()를 사용하는 방법은 간단하다. 그저 초기값(identity)과 어떤 연산(Binary Operator)으로 스트림의 요소를 줄여나갈 것인지만 결정하면 된다.

### 2.6 collect()

스트림의 최종 연산중에서 가장 복잡하면서도 유용하게 활용될 수 있는 부분이 collect()이다.

collect()는 스트릠의 요소를 수집하는 최종 연산으로 앞서 배운 reducing과 유사하다. collect()가 스트릠의 요소를 수집하려면, 어떻게 수집할 것인가에 대한 방법이 정의되어 있어야 하는데, 이 방법을 정의한 것이 바로 컬렉터(collector)이다.

컬렉터는 Collector인터페이스를 구현한 것으로, 직접 구현할 수도 있고 미리 작성된 것을 사용할 수 도 있다. Collectors클래스는 미리 작성된 다양한 종류의 컬렉터를 반환하는 static메서드를 가지고 있으며, 이 클래스를 통해 제공되는 컬렉터만으로도 많은 일들을 할 수 있다.

- collect() : 스트릠의 최종연산, 매개변수로 컬렉터를 필요로 한다.
- Collector : 인터페이스, 컬렉터는 이 인터페이스를 구현해야한다.
- Collctors : 클래스, static 메서드로 미리 작성된 컬렉터를 제공해야한다.


collect()는 매개변수의 타입이 Collector인데, 매개변수가 Collector를 구현한 클래스의 객체이어야 한다는 뜻이다. 그리고 collect()는 이 객체에 구현된 방법대로 스트림의 요소를 수집한다.

> [참고] sort()할 때, Comparator가 필요한 것처럼 collect()할 때는 Collector가 필요하다.

```java
Object collect(Collector collector)     // Collector를 구현한 클래스의 객체를 매개변수로
Object collect(Supplier supplier, BiConsumer accumulator, BiConsumer combiner)
```

그리고 매개변수가 3개나 정의된 collect()는 잘 사용하지 않지만, Collector인터페이스를 구현하지 않고 간단히 람다식으로 수집할 때 사용하면 편리하다.

#### 스트림을 컬렉션과 배열로 변환 - toList(), toSet(), toMap(), toCollection(), toArray()

스트림의 모든 요소를 컬렉션에 수집하려면, Collectors클래스의 toList()와 같은 메서드를 사용하면 된다. List나 Set이 아닌 특정 컬렉션을 지정하려면, toCollection()에 해당 컬렉션의 생성자 참조를 매개변수로 넣어주면 된다.

```java
List<String> names = stuStream.map(Student::getName)
        .collect(Collectors.toList());

ArrayList<String> list = names.stream()
        .collect(Collectors.toCollection(ArrayList::new));
```

Map은 키와 값의 쌍으로 저장해야하므로 객체의 어떤 필드를 키로 사용할지와 값으로 사용할지를 지정해줘야 한다.

```java
Map<String, Person> map = personStream.collect(Collectors.toMap(p -> p.getRegId(), p -> p));
```

위의 문장은 요소의 타입이 Person인 스트림에서 사람의 주민번호(regId)를 키로 하고, 값으로 Person객체를 그대로 저장한다.

> [참고] 항등 함수를 의미하는 람다식 'p -> p' 대신 Function.identity()를 쓸 수도 있다.

스트림에 저장된 요소들을 'T[]'타입의 배열로 변환하려면, toArray()를 사용하면 된다. 단, 해당 타입의 생성자 참조를 매개변수로 지정해줘야 한다. 만일 매개변수를 지정하지 않으면 반환되는 배열의 타입은 'Object[]'이다.

```java
Student[] stuNames = studentStream.toArray(Student[]::new); // OK
Student[] stuNames = studentStream.toArray();               // 에러
Object[] stuNames = studentStream.toArray();                // OK
```

#### 통계 - counting(), summingInt(), averagingInt(), maxBy(), minBy()

collect()를 사용하지 않고도 쉽게 얻을 수 있는데, 굳이 collect()를 사용한 방법을 보여주는 것은 collect()의 사용법을 보여주기 위한 것이다. 나중에 groupingBy()와 함꼐 사용할 때 비로소 이 메서드들이 왜 필요한지 알게 될 것이다. 보다 간결한 코드를 위해 Collectors의 static메서드를 호출할 때는 'Collectors.'를 생략하였다.

> [참고] summingInt()외에도 summingLong(), summingDouble()이 있다.

```java
long count = stuStream.count();
long count = stuStream.collect(counting());

long totalScore = stuStream.mapToInt(Student::getTotalScore).sum();
long totalScore = stuStream.collect(summingInt(Student::getTotalScore));

OptionalInt topScore = studentStream.mapToInt(Student::getTotalScore).max();

Optional<Student> topStudent = stuStream.max(Comparator.comparingInt(Student::getTotalScore));
Optional<Student> topStudent = stuStream.collect(maxBy(Comparator.comparingInt(Student::getTotalScore)));

IntSummaryStatistics stat = stuStream.mapToInt(Student::getTotalScore).summaryStatistics();
IntSummaryStatistics stat = stuStream.collect(summarizing(Student::getTotalScore));
```

#### 리듀싱 - reducing

IntStream에는 매개변수 3개짜리 collect()만 정의되어 있으므로 boxed()를 통해 IntStream을 Stream<Integer>로 변환해야 매개변수 1개짜리 collect()를 쓸수 있다.

```java
IntStream intStream = new Random().ints(1, 46).distinct().limit(6);

OptionalInt max = intStream.reduce(Integer::max);
Optional<Integer> max = intStream.boxed().collect(reducing(Integer::max));

long sum = intStream.reduce(0, (a, b) -> a + b);
long sum = intStream.boxed().collect(reducing(0, (a, b) -> a + b));

int grandTotal = stuStream.map(Student::getTotalScore).reduce(0, Integer::sum);
int grandTotal = stuStream.collect(reducing(0, Student::getTotalScore, Integer::sum));
```

Collectors.reducing()에는 아래와 같이 3가지 종류가 있다. 세 번째 메서드만 제외하고 reduce()와 같다. 세 번쨰 것은 위의 예에서 알 수 있듯이 map()과 reduce()를 하나로 합쳐놓은 것이다.

```java
Collector reducing(BinaryOperator<T> op)
Collector reducing(T identity, BinaryOperator<T> op)
Collector reducing(U identity, Function<T, U> mapper, BinaryOperator<U> op)
```

위의 메서드 목록 역시 와일드 카드를 제거하여 간략히 하였다.

#### 문자열 결합 - joining()

문자열 스트림의 모든 요소를 하나의 문자열로 연결해서 반환하다. 구분자를 지정해줄 수도 있고, 접두사와 접미사도 지정가능하다. 스트림의 요소가 String이나 StringBuffer처럼 CharSequence의 자손인 경우에만 결합이 가능하므로 스트림의 ㅅ요소가 문자열이 아닌 경우에는 먼저 map()을 이용해서 스트림의 요소를 문자열로 변환해야 한다.

```java
String studentNames = stuStream.amp(Student::getName).collect(joining());
String studentNames = stuStream.amp(Student::getName).collect(joining(","));
String studentNames = stuStream.amp(Student::getName).collect(joining(",", "[", "]"));
```

만일 map()없이 스트릠에 바로 joining()하면, 스트림의 요소에 toString()을 호출한 겨로가를 결합한다.

```java
// Student의 toString()으로 결합
String studentInfo = stuStream.collect(joining(","));
```

#### 그룹화와 분할 - groupingBy(), partitioningBy()

그룹화는 스트림의 요소를 특정 기준으로 그룹화하는 것을 의미하고, 분할은 스트림의 요소를 두 가지, 지정된 조건에 일치하는 그룹과 일치하지 않는 그룹으로의 분할을 의미한다. 아래의 메서드 정의에서 알 수 있듯이, groupingBy()는 스트림의 요소를 Function으로, partitioningBy()는 Predicate로 분류한다.

```java
Collector groupingBy(Function classifier)
Collector groupingBy(Function classifier, Collector downstream)
Collector groupingBy(Function classifier, Supplier mapFactory, Collector downstream)
        
Collector partitioningBy(Predicate predicate)
Collector partitioningBy(Predicate predicate, Collector downStream)
```

메서드의 정의를 보면 groupingBy()와 partitioningBy()가 분류를 Function으로 하는냐 Predicate로 하느냐의 차이만 있을 뿐 동일하다는 것을 알 수 있다. 스트림을 두 개의 그룹으로 나눠야 한다면, 당연히 partitioningBy()로 분할하는 것이 더 빠르다. 그 외에는 groupingBy()를 쓰면 된다. 그리고 그룹화와 분할의 결과는 Map에 담겨 반환된다.

#### partitioningBy()에 의한 분류

예제 14-15참고

#### groupingBy()에 의한 분류

예제 14-16참고


### 2.7 Collector 구현

Collector 인터페이스는 다음과 같이 정의되어 있다.

```java
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public interface Collector<T, A, R> {
    Supplier<A> supplier();

    BiConsumer<A, T> accumulator();

    BinaryOperator<A> combiner();
    Function<A, R> finisher();
    
    Set<Characteristics> characteristics();  // 컬렉터의 특성이 담긴 Set을 반환
    ...
}
```

직접 구현해야하는 것은 위의 5개의 메서드인데, characteristics()를 제외하면 모두 반환타입이 함수형 인터페이스이다. 즉, 4개의 람다식을 작성하면 되는 것이다.

> - supplier() : 작업 결과를 저장할 공간을 제공
> - accumulator() : 스트림의 요소를 수집(collect)할 방법 제공
> - combiner() : 두 저장공간을 병합할 방법 제공(병렬 스트림)
> - finisher() : 결과를 최종적으로 변환할 방법 제공

supplier()는 수집 결과를 저장할 공간을 제공하기 위한 것이고, accumulator()는 스트림의 요소를 어떻게 supplier()가 제공한 공간에 누적할 것인지를 정의한다. combiner()는 병렬 스트림인 경우, 여러 쓰레드에 의해 처리된 겨로가를 어떻게 합칠 것인가를 정의한다. 그리고, finisher()는 작업 결과를 변환하는 일을 하는 데 변환이 필요없다면, 항등 함수인 Function.identity()를 반환하면 된다.

```java
public Function finisher() {
    return Function.identity(); // 항등 함수를 반환
}
```

마지막으로 characteristics()는 컬렉터가 수행하는 작업의 속성에 대한 정보를 제공하기 위한 것이다.

- Characteristics.CONCURRENT: 병렬로 처리할 수 있는 작업
- Characteristics.UNORDERED: 스트림의 요소의 순서가 유지될 필요가 없는 작업
- Characteristics.IDENTRITY_FINISH: finisher()가 항등 함수인 작업

위의 3가지 속성 중에서 해당하는 것을 다음과 같이 Set에 담아서 반환하도록 구현하면 된다.

```java
public Set<Characteristics> characteristics() {
    return Collections.unmodifiableSet(EnumSet.of(
            Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED,
        )
    );
}
```

만일 아무런 속성도 지정하고 싶지 않으면 아래와 같이 하면 된다.

```java
public Set<Characteristics> characteristics() {
    return Collections.emptySet();
}
```

finisher()를 제외하고 supplier(), accumulator(), combiner()는 모두 앞서 리듀싱에 대해서 배울 때 등장했던 개념이다. 결국 Collector도 내부적으로 처리하는 과정이 리듀싱과 같다는 것을 의미한다. IntStream의 count(), sum(), max(), min()등이 reduce()로 구현되어 있다는 것을 기억할 것이다. 그리고 collect()로도 count()등의 메서드와 같은 일을 할 수 있었다.

```java
long count = stuStream.count();
long count = stuStream.collecot(counting());
```

이쯤에서 reduce()와 collect()의 차이에 대해서 궁금할 것인데, 이 둘은 근본적으로 하는 일이 같다. collect()는 앞서 살펴 본 것처럼, 그룹화와 분할, 집계 등에 유용하게 쓰이고, 병렬화에 있어서 reduce()보다 collect()가 더 유리하다. 






