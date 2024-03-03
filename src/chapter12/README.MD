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

### 1.7 제네릭 타입의 형변환

제네릭 타입과 원시 타입(primitive type)간의 형변환이 가능할까?

```JAVA
Box box = null;
Box<Object> objBox = null;

box = (Box) objBox;             // OK. 제네릭 타입 to 원시 타입(경고 발생)
objBox = (Box<Obejct>) box;     // OK. 원시 타입 to 제네릭 타입(경고 발생)
```

위에서 알 수 있듯이, 제네릭 타입과 넌 제네릭 타입간의 형변환은 항상 가능하다. 다만 경고가 발생할 뿐이다. 그러면, 대입된 타입이 다른 제네릭 타입 간에는 형변환이 가능할까?

```JAVA
Box<Object> objBox = null;
Box<String> strBox = null;

objBox = (Box<Object>) strBox;
strBox = (Box<String>)box;
```

불가능하다. 대입된 타입이 Object일지라도 말이다. 이 사실은 이미 배웠다. 아래의 문장이 안 된다는 얘기는 `Box<String>`이 `Box<Object>`로 형변환될 수 없다는 사실을 간접적으로 알려주는 것이기 때문이다.

```JAVA
Box<Object> objBox = new Box<String>();     // 에러. 형변환 불가능
```

그러면 다음의 문장은 어떨까? `Box<String>`이 `Box<? extends Object>`로 형변환될까?

```JAVA
Box<? extends Object> wBox = new Box<String>;
```

형변환이 된다. 그래서 전에 배운 makeJuice메서드의 매개변수에 다형성이 적용될 수 있었던 것이다.

```JAVA
// 매개변수로 FruitBox<Fruit>, FruitBox<Apple>, FruitBox<Grape>
FruitBox<? extends Fruit> box = new FruitBox<Fruit>();
FruitBox<? extends Fruit> box = new FruitBox<Fruit>();
FruitBox<? extends Fruit> box = new FruitBox<Fruit>();
```

반대로의 형변환도 성립하지만, 확인되지 않은 형변환이라는 경고가 발생한다. `FruitBox<? extends Fruit>`에 대입될 수 있는 타입이 여러개 인데다, `FruitBox<Apple>`를 제외한 다른 타입은 `FruitBox<Apple>`로 형변환될 수 없기 때문이다.

```JAVA
FruitBox<? extend Fruit> box = null;
// OK. 미확인 타입으로 형변환 경고
FruitBox<Apple> appleBox = (FruitBox<Apple>)box;
```

실질적인 예로 java.util.Optioinal클래스의 실제 소스의 일부이다.

```JAVA
public final class Optional<T> {
    private static final Option<?> EMPTY = new Optional<>();
    private final T value;

    ...

    public static<T> Optional<T> empty() {
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }
}
```

static 상수 EMPTY는 비어있는 Optional객체를 생성해서 저장했다가 empty()를 호출하면 EMPTY를 형변환해서 반환한다. 먼저 상수를 선언하는 문자을 단계별로 분석해보면 다음과 같다. 편의상 제어는 생략하였다.

```JAVA
Optional<?> EMPTY = new Optional<>();
Optional<? extends Object> EMPTY = new Optional<>();
Optional<? extends Object> EMPTY = new Optional<Object>();
```

`<?>`는 `<? extends Object>`를 줄여 쓴 것이며, `<>`안에 생략된 타입은 '?'가 아니라 'Object'이다.

```JAVA
Optional<?> EMPTY = new Optional<?>();                  // 에러. 미확인 타입의 객체는 생성불가
Optional<?> EMPTY = new Optional<Object>();            // OK
Optional<?> EMPTY = new Optional<>();                 // OK
```

> [주의] `class Box<T extends Object>`의 경우 `Box<?> b= new Box<>;`는 `Box<?> b = new Box<Fruit>`이다.

위의 문장에서 EMPTY의 타입을 `Optional<Object>`가 아닌 `Optional<?>`로 한 이유는 `Optional<T>`로 형변환이 가능하기 때문이다.

```JAVA
Optional<?>         wopt = new Optional<Object>();
Optional<Object>    oopt = new Optional<Object>();

Optional<String>    sopt = (Optional<String>) wopt;     // OK. 형변환 가능 
Optional<String>    sopt = (Optional<String>) oopt;     // 에러. 형변환 불가
```

empty()의 반환 타입이 `Optional<T>`이므로 EMPTY를 `Optional<T>`로 형변환해야 하는데, 위의 코드에서 알 수 있는 것처럼 `Optional<Object>`는 `Optional<T>`로 형변환이 불가능하다.

```JAVA
public static<T> Optional<T> empty() {
    Optional<T> t = (Optional<T>) EMPTY;
    return t;
}
```

정리하면, `Optional<Object>`를 `Optional<String>`으로 직접 형변환하는 것은 불가능하지만, 와일드 카드가 포함된 제네릭 타입으로 형변환하면 가능하다. 대신 확ㄷ인되지 않은 타입으로의 형변환이라는 경고가 발생한다. 와일드 카드가 사용된 제네릭 타입끼리도 형변환이 가능하다.

```JAVA
FruitBox<? extends Object> objBox = null;
FruitBox<? extends String> strBox = null;

strBox = (FruitBox<? extends String>) objBox; 
objBox = (FruitBox<? extends String>) strBox;
```

형변환이 가능하긴 하지만, 와일드 카드는 타입이 확정된 타입이 아니므로 컴파일러는 미확정된 타입으로 형변환하는 것이라고 경고한다.


### 1.8 제네릭 타입의 제거

컴파일러는 제네릭 타입을 이용해서 소스파일을 체크하고, 필요한 곳에 형변환을 넣어준다. 긜고 제네릭 타입을 제거한다. 즉, 컴파일된 파일(*.class)에는 제네릭 타입에 정보가 없는 것이다.

이렇게 하는 주된 이유는 제네릭이 도입되기 이전의 소스 코드와의 호환성을 유지하기 위해서이다. JDK1.5부터 제네릭이 도입되었지만, 아직도 원시 타입을 사용해서 코드를 작성하는 것을 허용한다. 그러나 앞으로 가능하면 원시 타입을 사용하지 않도록 하자. 언제가는 분명히 새로운 기능을 하위 호한성을 포기하게 될 때가 올 것이기 때문이다.

1. 제네릭 타입의 경계(bound)를 제거한다.
    
    제네릭 타입이 `<T extend Fruit>`라면 T는 Fruit으로 치환된다. `<T>`인 경우는 T는 Object로 치환된다. 그리고 클래스 옆의 선언은 제거된다.

   ```JAVA
   // AS-IS
   class Box<T extends Fruit {
        void add(T t)
   }

   // TO-BE
   class Box {
        void add(Fruit t) {

        }
   }
   ```

2. 제네릭 타입을 제거한 후에 타입이 일치하지 않으면, 형변환을 추가한다.

    List의 get()은 Object타입을 반환하므로 형변환이 필요하다.

    ```JAVA
    // AS-IS
    static Juice makeJuice(<FruitBox<? extends Fruit> box> box) {
        String tmp = "";
        for(Fruit f : box.getList()) tmp += f + "";
        return tmp;
    }

    // TO-BE
    static Juice makeJuice(FruitBox box) {
        String tmp = "";
        Iterator it = box.getList().iterator();
        while(it.hasNext()) {
            tmp += (Fruit)it.next() + "";
        }
        return tmp;
    }
    ```


## 2. 열거형

### 2.1 열거형이란? 

자바는 C언어와 달리 열거형이라는 것이 존재하지 않았으나 JDK 1.5부터 새로 추가되었다. 자바의 열거형은 C언어의 열거형보다 더 향상된 것으로 열거형이 갖는 값뿐만 아니라 타입까지 관리하기 때문에 보다 논리적인 오류를 줄일 수 있다.

```JAVA
public class Card {
    
    static final int CLOVER = 0;
    static final int HEART = 1;
    static final int DIAMOND = 2;
    static final int SPADE = 3;
    
    static final int TWO = 0;
    static final int THREE = 1;
    static final int FOUR = 2;
    
    final int kind;
    final int num;
}
```

```JAVA
public class Card {
    
    enum Kind { CLOVER, HEART, DIAMOND, SPADE };
    enum Value { TWO, THREE, FOUR };

    final Kind kind;
    final Value num;
}
```

기존의 많은 언어들, 예를 들어 C언에서는 타입이 달라도 값이 같으면 조건식 결과가 참이였으나, 자바의 '타입에 안전한 열거형(typesafe enum)'에서는 실제 값이 같아도 타입이 다르면 조건식의 결과가 false가 된다. 이처럼 값뿐만 아니라 타입까지 체크하기 때문에 타입에 안전하다고 하는 것이다.

그리고 더 중요한 것은 상수의 값이 바뀌면, 해당 상수를 참조하는 모든 소스를 다시 컴파일해야 한다는 것이다. 하지만 열거형 상수를 사용하면, 기존의 소스를 다시 컴파일하지 않아도 된다.


### 2.2 열거형의 정의와 사용

열거형을 정의하는 방법은 간단하다.

```JAVA
enum 열거형이름 {
    상수명1, 상수명2, ...
}
```

열거형 사수간의 비교에는 `==` 를 사용할 수 있다. equals()가 아닌 `==`으로 비교가 가능하다는 것은 그만큼 빠른 성능을 제공한다는 의미이다. 그러나 `<`, `>`와 같은 비교연산자는 아용할 수 없고 compareTo()는 사용가능하다. 

#### 모든 열거형의 조상 - java.lang.Enum

values()는 모든 상수를 배열에 담아 반환한다. 이 메서드는 모든 열거형이 가지고 있는 것으로 컴파일러가 자동으로 추가해 준다. 그리고 ordinal()은 모든 열거형의 조상인 java.lang.Enum클래스에 정의된 것으로, 열거형 상수가 정의된 순서(0부터 시작)를 정수로 반환한다.

Enum 클래스에는 그 밖에도 다음과 같은 메서드가 정의되어 있다.

| 메서드 | 설명 |
| --- | --- |
| Class<E> getDeclaringClass() | 열거형의 Class객체 반환 |
| String name() | 열거형 상수의 이름을 문자열로 반환 |
| int ordinal() | 열거형 상수가 정의된 순서를 반환 |
| T valueOf(Class<T> enumType, String name) | 지정된 열거형에서 name과 일치하는 열거형 상수를 반환 |

이외에도 values()처럼 컴파일러가 자동으로 추가해주는 메서드가 하나 더 있다.

```JAVA
static E values()
static E valueOf(String name)
```

이 메서드는 열거형 상수의 이름으로 문자열 상수에 대한 참조를 얻을 수 있게 해준다.


### 2.3 열거형에 멤버 추가하기

Enum 클래스에 정의된 ordinal()이 열거형 상수가 정의된 순서를 반환하지만, 이 값을 열거형 상수의 값으로 사용하지 않는 것이 좋다. 이 값은 내부적인 용도로만 사용되기 위한 것이기 때문이다.

열거형 상수의 값이 불연속적인 경우에는 이때는 다음과 같이 열거형 상수의 이름 옆에 원하는 값을 괄호()와 함께 적어주면 된다.

```JAVA
class Direction {
    EAST(1),
    SOUTH(2),
    WEST(-1),
    NORTH(10)
}
```

그리고 지정된 값을 저장할 수 있는 인스턴스 변수와 생성자를 새로 추가해 주어야 한다. 이때 주의할 점은, 먼저 열거형 상수를 모두 정의한 다음에 다른 멤버들을 추가해야한다는 것이다. 그리고 열거형 상수의 마지막에 ';'도 잊지 말아야 한다.

```JAVA
public enum Direction {
    EAST(1),
    SOUTH(5),
    WEST(-1),
    NORTH(10);

    private final int value;

    Direction(int value) {
        this.value = value;
    }
    
    public int getValue() { return value; }
}
```

#### 열거형에 추상 메서드 추가

열거형 Tranporation은 운송 수단의 종류 별로 상수를 정의하고 있으며, 각 운송 수단에는 기본요금(BASIC_FARE)이 책정되어 있다.

```JAVA
enum Transportation {
	BUS(100)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	TRAIN(150)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	SHIP(100)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	AIRPLANE(300)	{ int fare(int distance) { return distance*BASIC_FARE; } };
	
	private final int BASIC_FARE;	// protected로 해야 각 상수에서 접근 가능
	
	private Transportation(int basicFare) {	// private Transportation(int basicFare) {
		BASIC_FARE = basicFare;
	}
	
	int fare(int distance);	// 거리에 따른 요금 계산
}
```

그러나 이것만으로 부족하다. 거리에 따른 요금을 계산하는 방식이 각 운송 수단마다 다를 것이기 때문이다. 이럴 때, 열거형에 추상 메서드 `fare(int distance)`를 선언하면 각 열거형 상수가 이 추상 메서드를 반드시 구현해야 한다.

```JAVA
enum Transportation {
	BUS(100)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	TRAIN(150)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	SHIP(100)		{ int fare(int distance) { return distance*BASIC_FARE; } },
	AIRPLANE(300)	{ int fare(int distance) { return distance*BASIC_FARE; } };
	
	protected final int BASIC_FARE;	// protected로 해야 각 상수에서 접근 가능
	
	Transportation(int basicFare) {	// private Transportation(int basicFare) {
		BASIC_FARE = basicFare;
	}
	
	public int getBasicFare() { return BASIC_FARE; }
	
	abstract int fare(int distance);	// 거리에 따른 요금 계산
}
```

### 2.4 열거형의 이해

열거형이 내부적으로 어떻게 구현되었는지에 대해 설명하고자 한다.

만약 열거형이 Direction이 다음과 같이 정의되어 있을 때,

```JAVA
enum Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH
}
```

사실은 열거형 상수 하나가 Direction 객체이다. 위의 문장을 클래스로 정의하면 다음과 같을 것이다.

```JAVA
class Direction {
    static final Direction EAST = new Direction("EAST");
    static final Direction SOUTH = new Direction("SOUTH");
    static final Direction WEST = new Direction("WEST");
    static final Direction NORTH = new Direction("NORTH");

    private String name;

    private Direction(String name) {
        this.name = name;
    }
}
```

Direction클래스의 static상수 EAST, SOUTH, WEST, NORTH의 값은 객체의 주소이고, 이 값은 바뀌지 않는 값이므로 '=='로 비교가 가능한 것이다.

모든 열거형은 추상 클래스 Enum의 자손이므로, Enum을 흉내 내어 MyEnum을 작성하면 다음과 같다.

```JAVA
public abstract class MyEnum<T extends MyEnum<T>> implements Comparable<T> {

    static int id = 0;  // 객체에 붙일 일련번호(0부터 시작)

    int ordinal;
    String name = "";


    public MyEnum(String name) {
        this.name = name;
    }
    
    public int compareTo(T t) {
        return ordinal - t.ordinal;
    }
}
```

앞서 6장에서 배운것과 같이 객체가 생성될 때마다 번호가 붙여서 인스턴스변수 ordinal에 저장한다. 그리고 Comparable인터페이스를 구현해서 열거형 상수간의 비교가 가능하도록 되어 있다. 구현 내용은 간단하다. 두 열거형 상수의 ordinal값을 서로 빼주기만 하면 된다. 만일 클래스 `MyEnum<T>`와 같이 선언하였다면, compareTo()를 위와 같이 간단히 작성할 수 없었을 것이다. 타입 T에 ordinal()이 정의도어 있는지 확인할 수 없기 때문이다.

```JAVA
public abstract class MyEnum<T> implements Comparable<T> {

    static int id = 0;  // 객체에 붙일 일련번호(0부터 시작)

    int ordinal;
    String name = "";


    public MyEnum(String name) {
        this.name = name;
    }

    public int compareTo(T t) {
        return ordinal - t.ordinal;     // 에러 타입 T에 ordnial() 있나>
    }
}
```

그래서 `MyEnum<T extends<MyEnum<T>>`와 같이 선언한 것이며, 이것은 타입 T가 `MyEnum<T>`의 자손이어야 한다는 의미이다. 타입 T가 MyEnum의 자손이므로 ordinal()이 정의도어 있는 것은 분명하므로 형변환 없이도 에러가 나지 않는다.

그리고 추상 메서드를 새로 추가하면, 클래스 앞에도 `abstract`를 붙여줘야 하고, 각 static 상수들도 추상 메서드를 구현해주어야 한다. 아래의 코드에서는 익명 클래스의 형태로 추상 메서드를 구현하였다.

```JAVA
public abstract class Direction extends MyEnum {

    static final Direction EAST = new Direction("EAST") {
        Point move(Point p) { }
    };

    static final Direction SOUTH = new Direction("SOUTH") {
        Point move(Point p) { }
    };

    static final Direction WEST = new Direction("WEST") {
        Point move(Point p) { }
    };

    static final Direction SOUTH = new Direction("SOUTH") {
        Point move(Point p) { }
    };

    private String name;

    public Direction(String name) {
        this.name = name;
    }
    
    abstract Point move(Point p);
}
```


## 3. 어노테이션(annotation)

### 3.1 어노테이션이란? 

자바를 개발한 사람들은 소스코드에 대한 문서를 따로 만들기보다 소스코드와 문서를 하나의 파일로 관리하는 것이 낫다고 생각했다. 그래서 소스코드의 주석 `/** ~ */`에 소스 코드에 대한 정보를 저장하고, 소스코드의 주석으로부터 HTML문서를 생성해내는 프로그램(javadoc.exe)을 만들어서 사용했다. 다음은 모든 어노테이션의 조상인 Annotation인터페이스의 소스코드의 일부이다.

```JAVA
/**
 * The common interface extended by all annotation types.  Note that an
 * interface that manually extends this one does <i>not</i> define
 * an annotation type.  Also note that this interface does not itself
 * define an annotation type.
 *
 * More information about annotation types can be found in section 9.6 of
 * <cite>The Java&trade; Language Specification</cite>.
 *
 * The {@link java.lang.reflect.AnnotatedElement} interface discusses
 * compatibility concerns when evolving an annotation type from being
 * non-repeatable to being repeatable.
 *
 * @author  Josh Bloch
 * @since   1.5
 */
public interface Annotation {
    ...
}
```

'/**'으로 시작하는 주석 안에 소스코드에 대한 설명들이 있고, 그 안에 '@'이 붙은 태그 들이 눈에 띌 것이다. 미리 정의된 태그들을 이용해서 주석 안에 정보를 저장하고, javadoc.exe라는 프로그램이 이 정보를 읽어서 문서를 작성하는데 사용한다.

이 기능을 응용하여, 프로그램의 소스코드 안에 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨 것이 바로 어노테이션이다. 어노테이션은 주석(comment)처럼 프로그래밍 언어에 영향을 미치지 않으면서도 다른 프로그램에게 유용한 정보를 제공할 수 있다는 장점이 있다.

> [참고] 어노테이션의 뜻은 주석, 주해, 메모이다.

예를 들어, 자신이 작성한 소스코드 중에서 특정 메슫만 테스트하기를 원한다면, 다음과 같이 '@Test'라는 어노테이션을 메서드 앞에 붙인다. '@Test'는 '이 메서드를 테스트 해야 한다'는 것을 테스트 프로그램에게 알리는 역할을 할 뿐, 메서드가 포함된 프로그램 자체에는 아무런 영향을 미치지 않는다. 주석처럼 존재하지 않는 것이나 다름 없다.

```JAVA
@Test
public void method() {
    ...
}
```

테스트 프로그램에게 테스트할 메서드를 일일이 알려주지 않고, 해당 메서드 앞에 어노테이션만 붙이면 된다니 얼마나 편리한가. 그렇다고 모든 프로그램에게 의미가 있는 것은 아니고, 해당 프로그램에 미리 정의된 종류와 형식으로 작성해야만 의미가 있다. '@TEST'는 테스트 프로그램을 제외한 다름 프로그램에게는 아무런 의미가 없는 정보일 것이다.

어노테이션은 JDK에서 기본적으로 제공하는 것과 다른 프로그램에서 제공하는 것들이 있는데, 어느 것이든 그저 약속된 형식으로 제공하기만 하면 될 뿐이다.

JDK에서 제공하는 표준 어노테이션은 주로 컴파일러를 위한 것으로 컴파일러에게 유용한 정보를 제공한다. 그리고 새로운 어노테이션을 정의할 때 사용하는 메타 어노테이션을 제공한다.


### 3.2 표준 어노테이션

자베에서 기본적으로 제공하는 어노테이션은 몇 개 없다. 그나마 이들의 일부는 '메타 어노테이션(meta annotaion)'으로 어노테이션을 정의하는데 사용되는 어노테이션의 어노테이션이다.


| 어노테이션 | 설명 |
| --- | --- |
| @Override | 컴파일러에게 오버라이딩하는 메서드라는 것을 알린다. |
| @Deprecated | 앞으로 사용하지 않을 것을 권자앟는 대상에 붙인다. |
| @SuppressWarnings | 컴파일러의 특정 경고메시지가 나타나지 않게 해준다. |
| @SafeVarargs | 제네릭 타입의 가변인자에 사용한다(JDK 1.7) |
| @FunctionalInterface | 함수형 인터페이스라는 것을 알린다(JDK 1.8) |
| @Native | native 메서드에서 참조되는 상수 앞에 붙인다(JDK 1.8) |
| @Target* | 어노테이션이 적용가능한 대상을 지정하는데 사용한다. |
| @Documented | 어노테이션 정보가 javadoc으로 작성된 문서에 포함되게 한다. |
| @Inherited* | 어노테이션이 자손 클래스에 상속되도록 한다. |
| @Retention* | 어노테이션이 유지되는 범위는 지정하는데 사용한다. |
| @Repeatable | 어노테이션을 반속해서 적용할 수 있게 한다(JDK 1.8) |


#### @Override

메서드 앞에만 붙일 수 있는 어노테이션으로, 조상의 메서드를 오버라이딩하는 것이라는 것을 컴파일러에게 알려주는 역할을 한다. 아래의 코드에서와 같이 오버라이딩할 때 조상 메서드의 이름을 잘못 써도 컴파일러는 이것이 잘못된 것인지 알지 못한다.

```JAVA
class Parent {
    void parentMethod();
}

class Child extends Parent {
    void parentMethod();        // 오버라이딩하려 했으나 실수로 이름을 잘못적음
}
```

오버라이딩할 때는 이처럼 메서드의 이름을 잘못 적는 경우가 많은데, 컴파일러는 그저 새로운 메서드가 추가된 것으로 인식할 뿐이다. 게다가 실행 시에도 오류가 발생하지 않고 조상의 메서드가 호출되므로 어디서 잘못되었는지 알아내기 어렵다.

그러나 메서드 앞에 '@Override' 어노테이션 사용하면 컴파일러가 같은 이름의 메서드가 조상에 있는지 확인하고 없으면, 에러메시지를 출력한다. 오버라이딩할 떄 메서드 앞에 '@Override'를 붙이는 것이 필수는 아니지만, 알아내기 어려운 실수를 미연에 방지해주므로 반드시 붙여주도록 하자.


#### Deprecated

새로운 버전의 JDK가 소개될 때, 새로운 기능이 추가될 뿐만 아니라 기존의 부족했던 기능들을 개선하기도 한다. 이 가ㅘ정에서 기존의 기능을 대체할 것들이 추가되어도, 이미 여러 곳에서 사용되고 있을지 모르는 기존의 것들을 함부로 삭제할 수 없다.

그래서 생각해낸 방법이 더 이상 사용되지 않는 필드나 메서드에 '@Deprecated'를 붙이는 것이다. 이 어노테이션이 붙은 대상을 다른 것으로 대체되었으니 더 이상 사용하지 않을 것을 권한다는 의미이다. 예를 들어 'java.util.Date'클래스의 대부분의 메서드에는 '@Deprecated'가 붙어있는데, Java API에서 Date클래스의 getDate()를 보면 아래와 같이 적혀있다.

```JAVA
 /**
 * Returns the day of the month represented by this {@code Date} object.
 * The value returned is between {@code 1} and {@code 31}
 * representing the day of the month that contains or begins with the
 * instant in time represented by this {@code Date} object, as
 * interpreted in the local time zone.
 *
 * @return  the day of the month represented by this date.
 * @see     java.util.Calendar
 * @deprecated As of JDK version 1.1,
 * replaced by {@code Calendar.get(Calendar.DAY_OF_MONTH)}.
 */
@Deprecated
public int getDate() {
    return normalize().getDayOfMonth();
}
```

이 메서드 대신에 JDK 1.1부터 추가된 Calendar클래스의 get()을 사용하라는 이야기다. 기존의 것 대신 새로 추가된 개선된 기능을 사용하도록 유도하는 것이다. 굳이 기존의 것을 사용하겠다면, 아무도 못 말리겠지만 가능하면 '@Deprecated'가 붙은 것들은 사용하지 않아야 한다.

만일 '@Deprecated'가 붙은 대상을 사용하는 코드를 작성하면, 컴파일 할 때 아래와 같은 메서지가 나타난다.

```JAVA
Note: AnnotationEx2.java uses or overrides a deprecated API.
Note: Recompile with -Xlinkt:deprecation for details
```

해당 소스파일이 'deprecated'된 대상을 사용하고 있으며, '-Xlint:deprecation'옵션을 붙여서 다시 컴파일하면 자세한 내용을 알 수 있다는 뜻이다.


#### @FunctionalInterface

'함수형 인터페이스'를 선언할 때, 이 어노테이션을 붙이면 컴파일러가 '함수형 인터페이스'를 올바르게 선언했는지 확인하고, 잘못된 경우 에러를 발생시킨다. 필수는 아니지만, 붙이면 실수를 방지할 수 있으므로 '함수형 인터페이스'를 선언할 때는 이 어노테이션을 반드시 붙이도록 하자.

> [참고] 함수형 인터페이스는 추상 메서드가 하나뿐이어야 한다는 제약이 있다.

```JAVA
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

#### @SuppressWarnings

컴파일러가 보여주는 경고메시지가 나타나지 않게 억제해준다. 이전 예제에서처럼 컴파일러의 경고메시지는 무시하고 넘어갈 수도 있지만, 모두 확인하고 해결해서 컴파일 후에 어떠한 메시지도 나타나지 않게 해야 한다.

그러나 경우에 따라서는 경고가 발생할 것을 알면서도 묵인해야 할 때가 있는데, 이 경고를 그대로 놔두면 컴파일할 때마다 메시지가 나타난다. 이전 예제에서 확인한 것과 같이 '-Xlint'옵션을 붙이지 않으면 컴파일러는 경고의 자세한 내용은 보여주지 않으므로 다른 경고들을 놓치기 쉽다. 이 때는 묵인해야하는 경고가 발생하는 대상에 반드시 '@SuppressWarinings'를 붙여서 컴파일 후에 어떤 경고 메시지도 나타나지 않게 해야한다.

'@SuppressWarnings'로 억제할 수 있는 경고 메시지의 종류는 여러 가지가 있는데, JDK의 버전이 올라가면서 앞으로 계속 추가될 것이다. 이 중에서 주로 사용되는 것은 'deprecation', 'unchecked', 'rawtypes', 'varargs'정도이다.

'deprecation'은 '@Depreacte'가 부은 대상을 사용해서 발생하는 경고를, 'unchecked'는 제네릭으로 타입을 지정하지 않았을 때 발생하는 경고를, 'rawtypes'는 제네릭을 사용하지 않아서 발생하는 경고를, 그리고 'varargs'는 가변인자의 타입이 제네릭 타입일 떄 발생하는 경고를 억제할 때 사용한다.

억제하려는 경고메시지를 어노테이션의 뒤에 괄호 안에 문자열로 지정하면 된다.

```JAVA
@SuppressWarnings("unchecked")
ArrayList list = new ArrayList();
list.add(obj);

@SupressWarnings({ "deprecation", "unchecked", "varargs" })
```

'@SuppressWarings'로 억제할 수 있는 경고 메시지의 종류는 JDK의 버전이 올라가면서 계속 추가될 것이기 때문에, 이전 버전에서는 발생하지 않던 경고가 새로운 버전에서는 발생할 수 있다. 새로 추가된 경고 메시지를 억제하려면, 경고 메시지의 종류를 알아야하는데, -Xlint옵션으로 컴파일해서 나타나는 경고의 내용 중에서 대괄호[]안에 있는 것이 바로 메시지의 종류이다.

> [참고] -Xlint:unchecked와 같이 하면 unchecked와 관련된 경고만 표시한다.


#### @SafeVarargs

메스데어 선언된 가변인자의 타입이 non-reifiable타입일 경우, 해당 메서드를 선언하는 부분과 호출하는 부분에서 'unchecked'경고가 발생한다. 해당 코드에 문제가 없다면 이 경고를 억제하기 위해 '@SafeVarargs'를 사용해야 한다.

이 어노테이션은 생성자와 static이나 final이 붙은 메서드에만 붙일 수 있다. 즉, 오버라이딩될 수 있는 메서드에는 사용할 수 없다는 뜻이다.

제네릭에서 살펴본 것과 같이 어떤 타입들은 컴파일 이후에 제거된다. 컴파일 후도 제거되지 않는 타입을 reifiable타입이라고 하고, 제거되는 타입을 non-reifiable타입이라고 한다. 제네릭 타입들은 대부분 컴파일 시에 제거되므로 non-reifiable타입이다.

> [참고] reifiable은 're(다시)' + '-ify(~화 하다) + '-able(~할 수 있는)'의 합성어로 직역하면, '다시 ~화 할수 있는'이라는 뜻이다. 컴파일 후에도 타입정보가 유지되면 reifiable타입이다.


예를 들어, java.util.Arrays의 asList()는 다음과 같이 정의되어 있으며, 이 메서드는 매개변수로 넘겨받은 값들로 배열을 만들어서 새로운 ArrayList객체를 만들어서 반환하는데 이 과정에서 경고가 발생한다.

> [주의] 아래의 코드에 사용된 ArrayList는 Arrays클래스의 내부 클래스이다. java.util.ArrayList와 혼동하지 말자.

```JAVA
/**
 * Returns a fixed-size list backed by the specified array.  (Changes to
 * the returned list "write through" to the array.)  This method acts
 * as bridge between array-based and collection-based APIs, in
 * combination with {@link Collection#toArray}.  The returned list is
 * serializable and implements {@link RandomAccess}.
 *
 * <p>This method also provides a convenient way to create a fixed-size
 * list initialized to contain several elements:
 * <pre>
 *     List&lt;String&gt; stooges = Arrays.asList("Larry", "Moe", "Curly");
 * </pre>
 *
 * @param <T> the class of the objects in the array
 * @param a the array by which the list will be backed
 * @return a list view of the specified array
 */
@SafeVarargs
@SuppressWarnings("varargs")
public static <T> List<T> asList(T... a) {
    return new ArrayList<>(a);
}
```

asList()의 매개변수가 가변인자인 동시에 제네릭 타입이다. 메서드에 선언된 타입 T는 컴파일 과정에서 Object로 바뀐다. 즉, Object[]가 되는 것이다. Object[]에는 모든 타입의 객체가 들어있을 수 있으므로, 이 배열은 `ArrayList<T>`를 생성하는 것은 위험하다고 경고하는 것이다. 그러나 asList()가 호출되는 부분을 컴파일러가 체크해서 타입 T가 아닌 다른 타입이 들어가지 못하게 할 것이므르 위의 코드는 아무런 문제가 없다.

이럴 떄는 메서드 앞에 '@SafeVarargs'를 붙여서 '이 메서드의 가변인자는 타입 안전성이 있다'고 컴파일러에게 알려서 경고가 발생하지 않도록 해야 한다.

메서드를 선언할 때 `@SafeVarargs`를 붙이면 이 메서드를 호출하는 곳에서 발생하는 경고도 억제된다. 반면에 `@SafeVarargs`대신, `@SuprressWarings("unchcked")`로 경고를 억제하려면, 메서드 선언뿐만 아니라 메서드가 호출되는 곳에도 어노테이션을 붙여야한다.

그리고 `@SafeVarargs`로 'unchecked'경고는 억제할 수 있지만, 'varargs'경고는 억제할 수 없기 때문에 습관적으로 `@SafeVarargs`와 `@SuppressWarings("varargs")`를 같이 붙여야한다.

@SuppressWarnings("varargs")를 붙이지 않아도 경고 없이 컴파일 된다. 그러나 -Xlint옵션을 붙여서 컴파일 해보면, 'varargs'경고가 발생한 것을 확인할 수 있다. 그래서 가능하면 이 두 어노테이션을 항상 같이 사용하는 것이 좋다.


### 3.3 메타 어노테이션

메타 어노테이션은 '어노테이션을 위한 어노테이션', 즉 어노테이션에 붙이는 어노테이션으로 어노테이션을 정의할 때, 어노테이션의 적용대상(target)이나 유지기간(retention)등을 지정하는데 사용된다.


#### @Target

어노테이션이 적용가능한 대상을 지정하는데 사용도니다. 아래는 '@SuprressWarnings'를 정의한 것인데, 이 어노테이션에 적용할 수 있는 대상을 '@Target'으로 지정하였다. 앞서 언급한 것과 같이 여러 개의 값을 지정할 때는 배열에서처럼 중괄호를 사용해야한다.

```JAVA
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressWarnings {
    /**
     * The set of warnings that are to be suppressed by the compiler in the
     * annotated element.  Duplicate names are permitted.  The second and
     * successive occurrences of a name are ignored.  The presence of
     * unrecognized warning names is <i>not</i> an error: Compilers must
     * ignore any warning names they do not recognize.  They are, however,
     * free to emit a warning if an annotation contains an unrecognized
     * warning name.
     *
     * <p> The string {@code "unchecked"} is used to suppress
     * unchecked warnings. Compiler vendors should document the
     * additional warning names they support in conjunction with this
     * annotation type. They are encouraged to cooperate to ensure
     * that the same names work across multiple compilers.
     * @return the set of warnings to be suppressed
     */
    String[] value();
}
```

'@Target'으로 지정할 수 있는 어노테이션 적용대상의 종류는 아래와 같다.

| 대상 타입 | 의미 |
| --- | --- |
| ANNOTATION_TYPE | 어노테이션 |
| CONSTRUCTOR | 생성자 |
| FIELD | 필드(멤버변수, enum상수) |
| LOCAL_VARIABLE | 지역변수 |
| METHOD | 메서드 |
| PACKAGE | 패키지 |
| PARAMETER | 매개변수 |
| TYPE | 타입(클래스, 인터페이스, enum) |
| TYPE_PARAMETER | 타입 매개변수(JDK 1.8) |
| TYPE_USE | 타입이 사용되는 모든 곳(JDK 1.8) |

'TYPE'은 타입을 선언할 때, 어노테이션을 붙일 수 있다는 뜻이고 "TYPE_USE"는 해당 타입의 변수를 선언할 때 붙일 수 있다는 의미이다.


#### @Retention

어노테이션이 유지(retention)되는 기간을 지정하는데 사용된다. 어노테이션의 유지 정책(retention policy)의 종류는 다음과 같다.

| 유지 정책 | 의미 |
| --- | --- |
| SOURCE | 소스 파일에만 존재. 클래스파일에는 존재하지 않음 |
| CLASS | 클래스 파일에 존재. 실행시에 사용불가. 기본 값 |
| RUNTIME | 클래스 파일에 존재. 실행시에 사용가능 |

'@Override'나 '@SuppressWarnings'처럼 컴파일러가 사용하는 어노테이션은 유지 정책이 'SOURCE'이다. 컴파일러를 직접 작성할 것이 아니면, 이 유지정책은 필요없다.

```JAVA
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {}
```

유지 정책을 'RUNTIME'으로 하면, 실행 시에 '리플렉션'을 통해 클래스 파일에 저장된 어노테이션의 정보를 읽어서 처리할 수 있다. '@FunctionalInterface'는 '@Override'처럼 컴파일러가 체크해주는 어노테이션이지만, 실행 시에도 사용되므로 유지 정책이 'RUNTIME'으로 되어 있다. 

```JAVA
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionalInterface { }
```

유지 정책 'Class'는 컴파일러가 어노테이션의 정보를 클래스 파일에 저장할 수 있게는 하지만, 클래스 파일이 JVM에 로디오딜 때는 어노테이션의 정보가 무시되어 실행 시에 어노테이션에 대한 정보를 얻을 수없다. 이것이 'CLASS'가 유지정책의 기본값임에도 불구하고 잘 사용되지 않은 이유이다.

> [참고] 지역 변수에 부은 어노테이션은 컴파일러만 인식할 수 있으므로, 유지정책이 'RUNTIME'인 어노테이션을 지역변수에 붙여도 실행 시에는 인식되지 않는다.


#### @Documents

어노테이션에 대한 정보가 javadoc으로 작성된 문서에 포함되도록 한다. 자바에서 제공하는 기본 어노테이션 중에 '@Override'와 '@SuppressWarings'를 제외하고는 모두 이 메타 어노테이션이 붙어 있다.


#### @Inherited

어노테이션이 자손 클래스에 상속되도록 한다. '@Inherited'가 붙은 어노테이션을 조상 클래스에 붙이면, 자손 클래스도 이 어노테이션이 붙은 것과 같이 인식된다.

```JAVA
@Inherited          // @SuperAnno가 자손까지 영향을 미치게 
@Interface SupperAnno { }
```

#### @Repaeatable

보통은 하나의 대상에 한 종류의 어노테이션을 붙이는데, '@Repeatable'이 붙은 어노테이션은 여러 번 붙일 수 있다.

> [참고] 괄호안의 'ToDos.class'는 반복된 어노테이션을 저장할 어노테이션을 지정한 것으로 곧 설명할 것이다.


```JAVA
@Repeatable(ToDos.class)        /TODO어노테이션을 여러번 반복해서 쓸 수 있게 한다.
@interface ToDO {
    String value();
}
```

예를 들어 '@ToDo'라는 어노테이션이 위와 같이 정의되어 있을 때, 다음과 같이 MyClass 클래스에 '@Todo'를 여러 번 붙이는 것이 가능하다.

```JAVA
@ToDO("delete test code")
@ToDo("override inherited methods")
class MyClass { 

}
```

일반적인 어노테이션과 달리 같은 이름의 어노테이션이 여러 개가 하나의 대상에 적용될 수 있기 때문에, 이 어노테이션들을 하나로 묶어서 다룰 수 있는 어노테이션도 추가로 정의해야 한다.

```JAVA
// 여러 개의 ToDO어노테이션을 담을 컨테이너 어노테이션 ToDos
@interface ToDos {
    ToDo[] value();     // Todo어노테이션 배열타입의 요소를 선언. 이름이 반드시 value이어야 함.
}

@Repeatable(Todos.class)
@interface ToDo {
    String value();
}
```

#### @Native

네이티브 메서드(native method)에 의해 참조되는 '상수 필드(constant field)'에 붙이는 어노테이션이다. 아래는 java.lang.Long클래스에 정의된 상수이다.

```JAVA
public final class Long extends Number implements Comparable<Long> {
    /**
     * A constant holding the minimum value a {@code long} can
     * have, -2<sup>63</sup>.
     */
    @Native public static final long MIN_VALUE = 0x8000000000000000L;
}
```

네이티브 메서드는 JVM이 설치된 OS의 메서드를 말한다. 네이티브 메서드는 보통 C언어로 작성되 있는데, 자바에서는 메서드의 선언부만 정의하고 구현은 하지 않는다. 그래서 추상 메서드처럼 선언부만 있고 몸통이 없다.

```JAVA
public class Object {
        private static native void registerNatives();
    static {
        registerNatives();
    }

    @HotSpotIntrinsicCandidate
    public final native Class<?> getClass();
    
    public native int hashCode();
    
    @HotSpotIntrinsicCandidate
    protected native Object clone() throws CloneNotSupportedException;

    @HotSpotIntrinsicCandidate
    public final native void notify();
    ...
}
```


이처럼 모든 클래스의 조상인 Object클래스의 메서드들은 대부분 네이티브 메서드이다. 네이티브 메서드는 자바로 정의도어 있기 때문에 호출하는 방법은 자바의 일반 세더으와 다르지 않지만 실제로 호출되는 것은 OS의 메서드이다.

그냥 아무런 내용도 없는 네이티브 메서드를 선언해 놓고 호출한다고 되는 것은 아니고, 자바에 정의된 네이티브 메서드와 OS의 메서드를 연겨해주는 작업이 추가로 필요하다. 이 역할은 JNI(Java Native Interface)가 담당한다.



#### 3.4 어노테이션 타입 정의하기

```JAVA
@interface 어노테이션이름 {
    타입 요소이름();        // 어노테이션의 요소를 선언
    ...
}
```

엄밀히 말해서 '@Override'는 어노테이션이고 'Override'는 어노테이션의 타입이다.

#### 어노테이션의 요소

어노테이션 내의 선언된 메서드를 '어노테이션의 요소(element)'라고 하며, 아래에 선언된 TestInfo어노테이션은 다섯 개의 요소를 갖는다.

```JAVA
public @interface Test {
    
    int count();
    String testedBy();
    String[] testTools();
    TestType testType();        // enum TestType { FIRST, FINAL }
    DateTime testDate();        // 자신이 아닌 다른 어노테이션(@DateTime)을 포함할 수 있음.
}

public @interface DateTime {
    String yymmdd();
    String hhmmss();
}
```

어노테이션의 요소는 반환값이 있고 매개변수는 없는 추상 메서드의 형태를 가지며, 상속을 통해 구현하지 않아도 된다. 다만, 어노테이션을 적용할 때 이 요소들의 값을 빠짐없이 지정해주어야 한다. 요소의 이름도 같이 적어주므로 순서는 상관없다.

```JAVA
@TestInfo {
    count = 3, testedBy = "Kim",
    testTools = { "JUnit", "AutoTester" }
    testType = TestType.FIRST,
    testDate = @DateTime(yyyymmdd="240303", hhmmss="235959")
}

public class newClass { ... }
```

어노테이션의 각 요소는 기본값을 가질 수 있으며, 기본값이 있는 요소는 어노테이션을 적용할 때 값을 지정하지 않으면 기본값이 사용된다.

> [참고] 기본값으로 null을 제외한 모든 리터럴이 가능하다.


```JAVA
@interface TestInfo {
    int count() default 1;      // 기본값을 1로 지정
}

@TestInfo       // @TestInfo(count = 1)
```

어노테이션 요소가 오직 하나뿐이고 이름이 value인 경우, 어노테이션을 적용할 때 요소의 일 므을 생략하고 값만 적어도 된다.

```JAVA
@interface TestInfo {
    String value();
}

@TestInfo("passed")
```

요소의 타입이 배열인 경우, 괄호{}를 사용해서 여러 개의 값을 지정할 수 있다.

```JAVA
@interface TestInfo {
    String[] testTools();
}

@Test(testTools = { "JUnit", "AutoTester" })    // 값이 여러 개인 경우
@Test(testTools = "JUnit")                      // 값이 하나일 때는 괄호 생략가능
@Test(testTools = {})                           // 값이 없을 때는 괄호가 반드시 필요
```

기본값을 지정할 때도 마찬가지로 괄호를 사용할 수 있다.

```JAVA
@interface TestInfo {
    String[] info() default { "aaa", "bbb" };
    String[] info2() default "ccc";
}

@TestInfo       // @TestInfo(info = { "aaa", "bbb" }, info = "ccc")와 동일
@TestInfo(info2= {})       // @TestInfo(info = { "aaa", "bbb" }, info = {})와 동일
```

요소의 타입이 배열일 때도 요소의 이름이 value이면, 요소의 이름을 생략할 수 있다. 예를 들어, @SuppressedWarnings의 경우, 요소의 타입이 String배열이고 이름이 value이다.

```JAVA
@interface SuppressWarings {
    String[] value();
}
```

그래서 어노테이션을 적용할 때 요소의 이름을 생략할 수 있는 것이다.

#### java.lang.annotation.Annotaion

모든 어노테이션의 조상은 Annotation이다. 그러나 어노테이션은 상속이 허용되지 않으므로 아래와 같이 명시적으로 Annotation을 지정할 수 없다.

```JAVA
@interface TestInfo extends Annotation {        // 에러. 허용되지 않는 표현

}
```

Annotation은 어노테이션이 아니라 일반적인 인터페이스로 정의되어 있다.

```JAVA
public interface Annotation {
    boolean equals(Object obj);
    int hashCode();
    String toString();
    Class<? extends Annotation> annotationType();
}
```

모든 어노테이션의 조상인 Annotation 인터페이스가 위와 같이 정의되어 있기 때문에, 모든 어노테이션 객체에 대해 equals(), hashCode(), toString()과 같은 메서드를 호출하는 것이 가능하다.

```JAVA
Class<AnnotationTest> cls = AnnotationTest.class;
Annotation[] annotations = AnnotationTest.class.getAnnotations();

for(Annotation annotation : annotations) {
    System.out.println("toString : " + annotation.toString());
    System.out.println("hashCode : " + annotation.hashCode());
    System.out.println("equals : " + annotation.equals(annotation));
    System.out.println("annotationType : " + annotation.annotationType());
}
```

#### 마커 어노테이션(Marker Annotation)

값을 지정할 필요가 없는 경우, 어노테이션의 요소를 하나도 정의하지 않을 수 있다. Serializable이나 Cloneable인터페이스처럼, 요소가 하나도 정의되지 않은 어노테이션을 마커 어노테이션이라고 한다.

```JAVA
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Test {
}

```

#### 어노테이션 요소의 규칙

어노테이션의 요소를 선언할 때 반드시 지켜야 하는 규칙은 다음과 같다.

> - 요소의 타입은 기본형, String, enum, 어노테이션, Class만 허용됨
> - ()안에 매개변수를 선언할 수 없다.
> - 예외를 선언할 수 없다.
> - 요소를 타입 매개변수로 정의할 수 없다.
