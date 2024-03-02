# Chatper 12. Generics, Enumeration, Annotation

## 1. 제네릭(Generics)

JDK 1.5에서 처음 도입된 제네릭은 JDK1.8부터 도입된 람다식만큼 큰 변화였다. 그 당시만 해도 제네릭은 선택적으로 사용하는 경우가 많았지만 이제는 제네릭을 모르고는 Java API문서조차 제대로 보기 어려울 만큼 중요한 위치를 차지하고 있다.

### 1.1 제네릭이란?

제네릭은 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입 체크(compile-time type check)를 해주는 기능이다. 객체의 타입을 컴파일 시에 체크하기 때문에 객체의 타입 안전성을 높이고 형변환의 번겅로움이 줄어든다.

타입 안정성을 높인다는 것은 의도하지 않은 타입의 객체가 저장되는 것을 막고, 저장된 객체를 꺼내올 때 원래의 타입과 다른 타입으로 잘못 형변환되어 발생할 수 있는 오류를 줄여준다는 뜻이다.

예를 들어, ArrayList와 같은 컬렉션 클래스는 다양한 종류의 객체를 담을 수 있긴 하지만 보통 한 종류의 객체를 담는 경우가 더 많다. 그런데도 꺼낼 때 마다 타입체크를 하고 형변환을 하는 것은 아무래도 불편할 수밖에 없다. 게다가 원하지 않는 종류의 객체가 포함되는 것을 막을 방법이 없다는 것도 문제다. 이러한 문제들을 제네릭이 해결해 준다.

> **제네릭의 장점**
>
> 1. 타입 안전성 제공
> 2. 타입체크와 형변환을 생략할 수 있으므로 코드가 간결해 진다.


### 1.2 제네릭 클래스의 선언

제네릭 타입은 클래스와 메서드에 선언할 수 있는데, 먼저 클래스에 선언하는 제네릭 타입에 대해서 알아보자. 예를 들어 클래스 Box가 다음과 같이 정의되어 있다고 가정하자.

```JAVA
class Box {
    Object item;

    void setItem(Object item) { this.item = item; }
    Object getItem() { return this.item; }
}
```

이 클래스를 제네릭 클래스로 변경하면 다음과 같이 클래스 옆에 `<T>`를 붙이면 된다. 그리고 `Object`를 모두 `T`로 바꾼다.

```JAVA
class Box<T> {
    T item;

    void setTime(T item) { this.item = item; }
    T getItem() { return this.item; }
}
```

`Box<T>`에서 T를 `타입 변수(type variable)`라고 하며, `Type`의 첫 글자에서 따온 것이다. 타입 변수는 T가 아닌 다른 것을 사용해도 된다. `ArrayList<E>`의 경우, 타입변수 E는 `Element(요소)`의 첫 글자를 따서 사용했다. 타입 변수가 여러 개인 경우에는 `Map<K, V>`와 같이 콤마(,)로 구분자로 나열하면 된다. K는 KEy(키)를 의미하고, V는 Value(값)을 의미한다. 무조건 T를 사용하기보단 가능하면, 이처럼 상황에 맞게 의미있는 문자를 선택해서 사용하는 것이 좋다.

이들은 **기호의 종류만 다를 뿐 '임의의 참조형 타입'을 의미한다는 것은 모두 같다**. 마치 수학식 `f(x, y) = x + y` 가 `f(k, v) = k + v`와 다르지 않은 것처럼 말이다.

기존에는 다양한 종류의 타입을 다루는 메서드의 매개변수나 리턴타입으로 Object의 참조변수를 많이 사용했고, 그로 인해 형변환이 불가피했지만, 이젠 Object 타입 대신 원하는 타입을 지정하기만 하면 된다.

제네릭 클래스가 된 Box클래스의 객체를 생성할 때는 다음과 같이 참조변수와 생성자에 타입 T대신에 사용될 실제 타입을 지정해주어야 한다.

```JAVA
Box<String> b = new Box<String>();
b.setItem(new Object());        // 예외. String이외에 타입은 지정불가
b.setItem("ABC");               // OK.
String item = b.getItem();      // 형변환 필요없음
```

위의 코드에서 타입 T대신에 String 타입을 지정해줬으므로, 제네릭 클래스 Box<T>는 다음과 같이 정의된 것과 같다.

```JAVA
class Box<String> {
    String item;

    void setTime(String item) { this.item = item; }
    String getItem() { return this.item; }
}
```

만일 Box클래스에 String만을 담을 거라면, 타입 변수를 선언하지 않고 위와 같이 직접 타입을 적어주는 것도 가능하다. 단, `Box<String>`클래스는 String타입만 담을 수 있다. 반면에 `Box<T>`클래스는 어떤 타입이든 한 가지 타입을 정의해 담을 수 있다.

제네릭이 도입되기 이전의 코드와 호환을 위해, 제네릭 클랙스인데도 예전의 방식으로 클래스를 생성하는 것이 허용된다. 다만 제네릭 타입을 지정하지 않아서 않전하지 않다는 경고가 발생한다.

```JAVA
Box<Object> b = new Box<Object>();
b.setItem("ABC");           // 경고발생 안함
b.setItem(new Object());    // 경고발생 안함
```

제네릭이 도입되기 이전의 코드와 호환성을 유지하기 위해서 제네릭을 사용하지 않은 코드를 허용하는 것일 뿐, 앞으로 제네릭 클래스를 사용할 때는 반드시 타입을 지정해서 제네릭과 관련된 경고가 나오지 않도록 하자.


#### 제네렉의 용어

제네릭에서 사용되는 용어들은 자칫 헷갈리기 쉽다. 다음과 같이 제네릭 클래스 Box가 선언되어 있을 때 

```JAVA
class Box<T> { }
```

> `BOX<T>` - 제네릭 클래스. 'T의 Box' 또는 'T Box'라고 읽는다.
> `T` - 타입 변수 또는 타입 매개변수(T는 타입변수)
> `Box` - 원시 타입(raw type)

타입 문자 T는 제네릭 클래스 `Box<T>`의 타입 변수 또는 타입 매개변수라고 하는데, 메서드의 매개변수와 유사한 면이 있기 때문이다. 그래서 아래와 같이 타입 매개변수에 타입을 지정하는 것을 '제네릭 타입 호출'이라고 하고, 지정된 타입 'String'을 '매개변수화된 타입(parameterized type)'이라고 한다. 매개변수화된 타입이라는 용어가 좀 길어서, 앞으로 이 용어 대신 '대입된 타입'이라는 용어를 사용할 것이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/f9f07484-0346-469c-818f-1acb2422474a)

예를 들어, `Box<String>`과 `Box<Integer>`는 제네릭 클래스는 `Box<T>`에 서로 다른 타입을 대입하여 호출한 것일 뿐, 이 둘이 별개의 클래스를 의미하는 것은 아니다. 이는 마치 메서드으 매개변수의 값이 다른 메서드 호출, 즉 add(3,5)와 add(2,4)가 서로 다른 메서드를 호출하는 것이 아닌 것과 같다.

컴파일 후에 `Box<String>`과 `Box<Integer>`는 이들의 '원시 타입'인 Box로 바뀐다. 즉, 제네릭 타입이 제거된다. 


#### 제네릭의 제한

제네릭 클래스 Box의 객체를 생성할 때, 객체별로 다른 타입을 지정하는 것은 적절하다. 제네릭은 이처럼 인스턴스별로 다르게 동작하도록 하려고 만든 기능이다.

```JAVA
Box<Apple> appleBox = new Box<Apple>();
Box<Grape> appleBox = new Box<Grape>();
```

그러나 모든 객체에 대해 동일하게 동작해야하는 static멤버에 타입 변수 T를 사용할 수 없다. T는 인스턴스 변수로 간주되기 때문이다. 이미 알고 있는 것처럼 static멤버는 인스턴스변수를 참조할 수 없다.

```JAVA
class Box<T> {
    static T item;      // 에러
    static int compare(T t1, T t2) { ... }      // 에러
}
```

static 멤버는 타입 변수에 지정된 타입, 즉 대입되 ㄴ타입의 종류에 관계없이 동일한 것이어야 하기 때문이다. 그리고 제네릭 타입의 배열을 생성하는 것도 허용되지 않는다. 제네렉 배열 타입의 참조변수를 선언하는 것은 가능하지만, 'new T[10]`과 같이 배열을 생성하는 것은 안 된다는 뜻이다.

```JAVA
class Box<T> {
    T[] itemArr;        // OK. T타입의 배열을 위한 참조변수

    T[] toArray() {
        T[] tmpArr = new T[itemArr.length];     // 에러. 제네릭 배열 생성불가
        ...
        return tmpArr;
    }
}
```

제네릭 배열을 생성할 수 없는 것은 new 연산자 때문인데, 이 연산자는 컴파일 시점에 타입 T가 무엇인지 정확히 알아야 한다. 그런데 위의 코드에 정의된 `Box<T>`클래스를 컴파일하는 시점에서는 T가 어떤 타입이 될지 전혀 알 수 없다. instanceof연산자도 new 연산자와 같은 이유로 T로 피연산자로 사용할 수 없다.

제네릭 배열을 생성해야할 필요가 있을 때는 new 연산자 대신 'Reflection API'의 newInstace()와 같이 동적으로 객체를 생성하는 메서드로 배열을 생성하거나, Object배열을 생성해서 복사한 다음에 'T[]`로 형변환하는 방법 등을 사용한다.


### 1.3 제네릭 클래스의 객체 생성과 사용

제네릭 클래스 `Box<T>`가 다음과 같이 정의도어 있다고 가정하자. 이 `Box<T>`의 객체에는 한 가지 종류, 즉 T타입의 객체만 저장할 수 있다. 전과 달리 ArrayList를 이용해서 여러 객체를 저장할 수 있도록 하였다.

```JAVA
class Box<T> {
    ArrayList<T> list = new ArrayList<T>();

    void add(T item) { list.add(item); }
    T get(int i) { return list.get(i); }
    ArrayList<T> getList() { return list; }
    int size() { return list.size(); }
    public String toString() { return list.toString(); }
}
```

`Box<T>`의 객체를 생성할 때는 다음과 같이 한다. 참조변수와 생성자에 대입된 타입(매개변수화된 타입)이 일치해야 한다. 일치하지 않으면 에러가 발생한다.

```JAVA
Box<Apple> appleBox = new Box<Apple>();     // OK
Box<Apple> appleBox = new Box<Grape>();     // 에러
```

두 타입이 상속관계에 있어도 마찬가지이다. Apple이 Fruit의 자손이라고 가정하자.

```JAVA
Box<Fruit> appleBox = new Box<Apple>();     // 에러, 대입된 타입이 다르다.
```

단, 두 제네릭 클래스의 타입이 상속관게에 있고, 대입된 타입이 같은 것은 괜찮다. FruitBox는 Box의 자손이라고 가정하자.

```JAVA
Box<Apple> appleBox = new FruitBox<Apple>();     // OK. 다형성
```

JDK 1.7부터는 추정이 가능한 경우 타입을 생략할 수 있게 되었다. 참조변수의 타입으로부터 Box가 Apple타입의 객체만 저장한다는 것을 알 수 있기 때문에, 생성자에 반복해서 타입을 지정해주지 않아도 되는 것이다. 따라서 아래의 두 문장은 동일하다.

```JAVA
Box<Apple> appleBox = new Box<Apple>();
Box<Apple> appleBox = new Box<>();      // OK. JDK 1.7부터 생략가능
```

생성된 `Box<T>`의 객체에 `void add(T item)`으로 객체를 추가할 때, 대입된 타입과 다른 타입의 객체는 추가할 수 없다.

```JAVA
Box<Apple> appleBox = new Box<Apple>();
appleBox.add(new Apple());      // OK
appleBox.add(new Grape());      // 에러. Box<Apple>에는 Apple객체만 추가가능
```

그러나 타입 T가 `Fruit`인 경우, void add(Fruit item)가 되므로 Fruit의 자손들은 이 메서드의 매개변수가 될 수 있다. Apple이 Fruit의 자손이라고 가정하였다.

```JAVA
Box<Fruit> fruitBox = new Box<Fruit>();
fruitBox.add(new Fruit());      // OK
fruitBox.add(new Apple());      // OK, void add(Fruit item)
```

### 1.4 제한된 제네릭 클래스

타입 문자로 사용할 타입을 명시하면 한 종류의 타입만 저장할 수 있도록 제한할 수 있지만, 그래도 여전히 모든 종류의 타입을 지정할 수 있다는 것에는 변함이 없다. 그렇다면 타입 매개변수 T에 지정할 수 있는 타입의 종류를 제한할 수 있는 방법은 없을까?

```JAVA
FruitBox<Toy> fruitBox = new FruitBox<Toy>();
fruitBox.add(new Toy());                        // OK. 과일상자에 장난감을 담을 수 있다.
```

다음과 같이 제네릭 타입에 `extends`를 사용하면 ,특정 타입의 자손들만 대입할 수 있게 제한할 수 있다.

```JAVA
class FruitBox<T extends Fruit> {
    ArrayList<T> list = new ArrayList<T>();
    ...
}
```

여전히 한 종류의 타입만 담을 수 있지만, Fruit클래스의 자손들만 담을 수 있다는 제한이 더 추가된 것이다.

```JAVA
FruitBox<Apple> fruitBox = new FruitBox<Apple>();       // OK
fruitBox.add(new Apple());          // OK. Apple은 Fruit의 자손
fruitBox.add(new Grape());          // Ok. Grape은 Fruit의 자손
```

다형성에서 조상타입의 참조변수로 자손타입의 객체를 가리킬 수 있는 것처럼, 매개변수화된 타입의 자손 타입도 가능한 것이다. 타입 매개변수 T에 Object를 대입하면, 모든 종류의 객체를 저장할 수 있게 된다.

만일 클래스가 아니라 인터페이스를 구현해야 한다는 제약이 필요하다면, 이때도 `extends`를 사용한다. `implements`를 사용하지 않는 다는 점에 주의하자.

```JAVA
interface Eatable {} 
class FruitBox<T extends Eatable> { ... }
```

클래스 Fruit의 자손이면서 Eable 인터페이스도 구현해야한다면 아래와 같이 '&'기호로 연결한다.

```JAVA
class FruitBox<T extends Eatable & Eatable> { ... }
```


### 1.5 와일드 카드

매개변수에 과일박스를 대입하면 주스를 만들어서 반환하는 Juicer라는 클래스가 있고, 이클래스에는 과일을 주스로 만들어서 반환하는 makeJuice()라는 static메서드가 다음과 같이 정의되어 있다고 가정하자.

```JAVA
class Juicer {
    static Juice makeJuice(FruitBox<Fruit> box) {
        String tmp = "";
        for(Fruit f : box.getList()) tmp += f + "";
        return new Juice(tmp);
    }
}
```

Juicer클래스는 제네릭 클래스가 아닌데다, 제네릭 클래스라고 해도 static메서드에는 타입 매개변수 T를 매개변수에 사용할 수 없으므로 아예 제네릭을 적요하지 않던가, 우ㅚ와 같이 타입 매개변수 대신, 특정 타입을 지정해줘야 한다.

```JAVA
FruitBox<Fruit> fruitBox = new FruitBox<Fruit>();
FruitBox<Apple> appleBox = new FruitBox<Apple>();

System.out.println(Juicer.makeJuice(fruitBox));     // OK
System.out.println(Juicer.makeJuice(appleBox));     // 에러
```

이렇게 제네릭 타입을 `FruitBox<Fruit>`을 고정해 놓으면, 위의 코드에서 알 수 있듯이 `FruitBox<Apple>`타입의 객체는 makeJuice()의 매개변수가 될 수 없으므로, 다음과 같이 여러 가지 타입의 매개변수를 갖는 makeJuice()를 만들 수밖에 없다.

```JAVA
static Juice makeJuice(FruitBox<Fruit> box) {
    ...
}

static Juice makeJuice(FruitBox<Apple> box) {
    ...
}
```

그러나 위와 같이 오버로딩하면, 컴파일 에러가 발생한다. 제네릭 타입이 다른 것만으로는 오버로딩이 성립하지 않기 때문이다. 제네릭 타입은 컴파일러가 컴파일할 때만 사용하고 제거해버린다. 그래서 위의 두 메서드는 오버로딩이 아니라 '메서드 중복 정의'이다.

이럴 떄 사용하기 위해 고안된 것이 바로 `와일드 카드`이다. 와일드 카드는 기호 "?"로 표현하는데, 와일드 카드는 어떠한 타입도 될 수 있다.

"?"만으로는 Object타입과 다를게 없으므로, 다음과 같이 'extends'과 'super'로 상한(upper bound)과 하한(lower bound)을 제한할 수 있다.

> `<? extends T>` : 와일드 카드의 상한 제한. T와 그 자손들만 가능
> `<? super T>` : 와일드 카드의 하한 제한. T와 그 조상들만 가능
> <?> 제한 없음. 모든 타입이 가능. `<? extends Object>`와 동일

> [참고] 제네릭 클래스와 달리 와일드 카드에서 '&'를 사용할 수 없다. 즉 `<? extends T & E>`와 같이 할 수 없다.


와일드 카드를 사용해서 makeJuice()의 매개변수 타입을 `FruitBox<Fruit>`에서 `FruitBox <? extends Fruit>`으로 바꾸면 다음과 같이 된다.

```JAVA
static Juice makeJuice(FruitBox <? extends Fruit> box) {
    String tmp = "";
    for(Fruit f : box.getList()) tmp += f + "";
    return new Juice(tmp);
}
```

이 메서드의 매개변수로 `FruitBox<Fruit>`뿐만 아니라, `FruitBox<Apple>`와 `FruitBox<Grape>`도 가능하게 된다.

> [참고] 매개변수의 타입을 `FruitBox<? extends Object>`로 하면, 모든 종류의 fruitBox가 매개변수로 가능하다.


```JAVA
FruitBox<Fruit> fruitBox = new FruitBox<Fruit>();
FruitBox<Apple> appleBox = new FruitBox<Apple>();
...
System.out.println(Juicer.makeJuice(fruitBox));
System.out.println(Juicer.makeJuice(appleBox));
```

매개변수의 타입을 `FruitBox<? extends Object>`로 하면, 모든 종류의 FruitBox가 이 메서드의 매개변수로 가능해진다. 대신, 전과 달리 box의 요소가 Fruit의 자손이라는 보장이 없으므로 아래의 for문에서 box에 저장된 요소를 Fruit타입의 참조변수를 못받는다.

```JAVA
static Juice makeJuice(FruitBox<? extends Object> box) {
    String tmp = "";
    for(Fruit f : box.getList()) tmp += f + "";     // 에러. Fruit이 아닐 수 있음
    return new Juice(tmp);
}
```

그러나 실제로 테스트 해보면 문제없이 컴파일되는데 그 이유는 바로 제네릭 클래스 FruitBox를 제한했기 때문이다.

```JAVA
class FruitBox<T extends Fruit> extends Box<T> {}
```

컴파일러는 위 문장으로부터 모든 FruitBox의 요소들이 Fruit의 자손이라는 것을 알고 있으므로 문제 삼지 않는 것이다.


### 1.6 제네릭 메서드

메서드의 선언부에 제네릭 타입이 선언된 메서드를 제네릭 메서드라 한다. Collection.sort()가 바로 제네릭 메서드이며, 제네릭 타입의 선언 위치는 반환 타입 바로 앞이다.

```JAVA
static <T> void sort(List<T> list, Comparator<? super T> c)
```

제네릭 클래스에 정의된 타입 매개변수와 제네릭 메서드에 정의된 타입 매개변수는 전혀 별개의 것이다. 같은 타입 문자 T를 사용해도 같은 것이 아니라는 것에 주의해야 한다.

> [참고] 제네릭 메서드는 제네릭 클래스가 아닌 클래스에도 정의될 수 있다.

```JAVA
clss FruitBox<T> {
    ...
    static <T> void sort(List<T> list, Comparator<? super T> c) {
        ...
    }
}
```

위의 코드에서 제네릭 클래스 FruitBox에 선언된 타입 매개변수 T와 제네릭 메서드 sort()에 선언된 타입 매개변수 T는 타입 문자만 같을 뿐 서로 다른 것이다. 그리고 sort()가 static메서드라는 것에 주목하자. 앞서 설명한 것처럼, static멤버에는 타입 매개변수를 사용할 수 없지만, 이처럼 메서드에 제네릭 타입을 선언하고 사용하는 것은 가능하다.

메서드에 선언된 제네릭 타입은 지역 변수를 선언한 것과 같다고 생각하면 이해하기 쉬운데, 이 타입 매개변수는 메서드 내에서만 지역적으로 사용될 것이므로 메서드가 static이건 아니건 상관이 없다.

> [참고] 같은 이유로 내부 클래스에 선언된 타입 문자가 외부 클래스의 타입 문자와 같아도 구별될 수 있다.

앞서 나왔던 makeJuice()를 제네릭 메서드로 바꾸면 다음과 같다.

```JAVA
// AS-IS
static Juice makeJuice(FruitBox<? extends Fruit> box) {
    String tmp = "";

    for (Fruit f : box.getList())
        tmp += f + " ";
    return new Juice(tmp);
}

// TO-BE
static <T extends Fruit> Juice makeJuice(FruitBox<T> box) {
    String tmp = "";

    for (Fruit f : box.getList())
        tmp += f + " ";
    return new Juice(tmp);
}
```

이제 이 메서드를 호출할 대는 아래와 같이 타입 변수에 타입을 대입해야 한다.

```JAVA
FruitBox<Fruit> fruitBox = new FruitBox<Fruit>();
FruitBox<Grape> grapeBox = new FruitBox<Grape>();

System.out.println(Juicer.<Fruit>makeJuice(fruitBox));      
System.out.println(Juicer.<Grape>makeJuice(grapeBox));      
```

그러나 대부분의 경우 컴파일러가 타입을 추정할 수 있기 떄문에 생략해도 된다. 위의 코드에서 fruitBox와 grapeBox의 선언부를 통해 대입된 타입을 컴파일러가 추정할 수 있다.

```JAVA
System.out.println(Juicer.makeJuice(fruitBox));      
System.out.println(Juicer.makeJuice(grapeBox));      
```

한 가지 주의할 점은 제네릭 메서드를 호출하 때, 대입된 타입을 생략할 수 없는 경우에는 참조변수나 클래스 이름을 생략할 수 없다는 것이다.

```JAVA
System.out.println(<Fruit>makeJuice(fruitBox));             // 에러. 클래스 이름 생략 불가
System.out.println(this.<Fruit>makeJuice(fruitBox));        // OK
System.out.println(Juicer.<Fruit>makeJuice(fruitBox));      // OK
```

같은 클래스 내에 있는 멤버들끼리는 참조변수나 클래스이름, 즉 'this.'이나 '클래스이름.'을 생략하고 메서드 이름만으로 호출이 가능하지만, 대입된 타입이 있을 때는 반드시 써줘야 한다. 이것은 단지 기술적인 이유에 의한 규칙이므로 그냥 지키기만 하면 된다.

제네릭 메서드는 매개변수의 타입이 복잡할 때도 유용하다. 만일 아래와 같은 코드가 있다면 타입을 별도로 선언함으로써 코드를 간략히 할 수 있다.

```JAVA
// AS-IS
public static void printAll(ArrayList<? extends Product> list, ArrayList<? extends Product> list2) {

    for(Unit u : list) {
        System.out.println(u);
    }
}

// TO-BE
public static <T extends Product> void printAll(ArrayList<T> list, ArrayList<T> list2) {

    for(Unit u : list) {
        System.out.println(u);
    }
}
```

이번엔 좀 복잡하게 선언된 제네릭 메서드 하나를 예로 들어보겠다. 아래의 메서드는 Collections 클래스의 sort()인데, 좀 전에 소개한 sort()와 달리 매개변수가 하나짜리이다.

```JAVA
public static <T extends Comparable<? super T>> void sort(List<T> list)
```

매개변수로 지정한 `List<T>`를 정렬한다는 것은 알겠는데, 메서드에 선언된 제네릭 타입이 좀 복잡하다. 이럴 때는 와일드 카드를 걷어내자.

```JAVA
public static <T extends Comparable<? super T>> void sort(List<T> list)

// 1. 타입 T를 요소로 하는 List를 매개변수로 허용한다.
// 2. 'T'는 Comparable을 구현한 클래스이어야 하며 (<T extends Comparable>), 'T' 또는 그 조상의 타입을 비교하는 Comparable이어야한다는 것(Comparable<? super T>)을 의미한다. 만일 T가 Student이고, Person의 자손이라면, <? super T>는 Student, Person, Object가 모두 가능하다.
```


