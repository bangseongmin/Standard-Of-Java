# Chapter 09. java.lang 패키지와 유용한 클래스

## 1. java.lang 패키지

java.lang패키지는 자바프로그래밍에 가장 기본이 되는 클래스들을 포함하고 있다. 그렇기 때문에 java.lang패키지의 클래스들은 import문 없이 사용할 수 있게 되어 있다.

그 동안 String 클래스나 System클래스를 import문 없이 사용할 수 있었던 이유가 바로 java.lang 패키지에 속한 클래스들이기 때문이다. 


### 1.1 Object 클래스

Object 클래스는 모든 클래스의 최고 조상이기 때문에 Object 클래스의 멤버들은 모든 클래스에서 바로 사용 가능하다.

|Object클래스의 메서드|설명|
|---|---|
|protected Object clone()| 객체 자신의 복사본을 반환한다. |
|public boolean equals(Object obj)| 객체 자신과 객체 obj가 같은 객체인지 알려준다.(같으면 true)|
|protected void finalize()| 객체가 소멸될 때 가비지 컬렉터에 의해 자동적으로 호출된다. 이 때 수행되어야하는 코드가 있을 때 오버라이딩한다(거의 사용안함)|
|public Class getClass()| 객체 자신의 클래스 정보를 담고 있는 Class인스턴스를 반환한다.|
| public int hashCode()| 객체 자신의 해시코드를 반환한다.|
| public String toString()| 객체 자신의 정보를 문자열로 반환한다.|
| public void notify()| 객체 자신을 사용하려고 기다리는 쓰레드를 하나만 깨운다.|
| public void notifyAll() | 객체 자신을 사용하려고 기다리는 모든 쓰레드를 깨운다.|
| public void wait(), public void wait(long timeout), public void wait(long timeout, int nanos) | 다른 쓰레드가 notify()나 notifyAll()을 호출할 때까지 현재 쓰레드를 무한히 또는 지정된 시간(timeout, nanos)동안 기다리게 한다.|


Object 클래스는 멤버변수는 없고 오직 11개의 메서드만 가지고 있다. 이 메서드들은 모든 인스턴스가 가져야 할 기본적인 것들이다.

> [참고] notify(), notifyAll(), wait()은 쓰레드(thread)와 관련된 것들이다.


#### equals(Object obj)

매개변수로 객체의 참조변수를 받아서 비교하여 그 결과를 boolean 값으로 알려주는 역할을 한다. 아래의 코드는 Object 클래스에 정의도어 있는 equals메서드의 실제 내용이다.

```JAVA
public boolean equals(Object obj) {
    return (this == obj);
}
```

위의 코드에서 알 수 있듯이 두 객체의 같고 다름을 참조변수의 값으로 판단한다. 그렇기 때문에 서로 다른 두 객체를 equals메서드로 비교하면 항상 false를 결과로 얻게 된다.

예제1를 보면 value라는 멤버변수를 갖는 Value 클래스를 정의하고, 두 개의 Value 클래스의 인스턴스를 생성한 다음 equals메서드를 이용해서 두 인스턴스를 비교하도록 했다. equals메서드는 주소값으로 비교를 하기 때문에, 두 Value 인스턴스의 멤버변수 value의 값이 10으로 서로 같을지라도 equals 메서드로 비교한 결과는 false일 수 밖에 없는 것이다.

하지만, `value2=value1;`을 수행한 후에는 참조변수 value2는 value1이 참조하고 있는 인스턴스의 주소 값이 저장되므로 value2는 value1과 같은 주소값이 저장된다. 

Object 클래스로부터 상속받은 equals메서드는 결국 두 개의 참조변수가 같은 객체를 참조하고 있는지, 즉 두 참조변수에 저장된 값(주소값)이 같은지를 판단하는 기능밖에 할 수 없다는 것을 알 수 있다. equals메서드로 Value 인스턴스가 가지고 있는 value 값을 비교하도록 할 수는 없을까? Value 클래스에서 equals메서드를 오버라딩하여 주소가 아닌 객체에 저장된 내용을 비교하도록 변경하면 된다. 


String 클래스 역시 Object 클래스의 equals메서드를 그대로 사용하는 것이 아니라 이처럼 오버라이딩을 통해서 String 인스턴스가 갖는 문자열의 값을 비교하도록 되어있다. 그렇기 때문에 같은 내용의 문자열을 갖는 두 String 인스턴스에 equals메서드를 사용하면 항상 true 값을 얻는 것이다.

> [참고] String 클래스뿐만 아니라, Date, File, wrapper클래스(Integer, Double 등)의 equals 메서드도 주소값이 아닌 내용을 비교하도록 오버라이딩되어 있다. 그러나 의외로 StringBuilder클래스는 오버라이딩되어 있지 않다.


#### hashCode()

이 메서드는 해싱(hashing)기법에 사용되는 '해시함수(hash function)'을 구현한 것이다.

해싱은 데이터관리기법 중의 하나인데 다량의 데이터를 저장하고 검색하는데 유용하다.

해시함수는 찾고자하는 값을 입력하면, 그 값이 저장된 위치를 알려주는 해시코드(hash code)를 반환한다.

일반적으로 해시코드가 같은 두 객체가 존재하는 것이 가능하지만, Object클래스에 정의된 hashCode메서드는 객체의 주소값을 이용해서 해시코드를 만들어 반환하기 때문에 서로 다른 두 객체는 결코 같은 해시코드를 가질 수 없다.

앞서 살펴본 것과 같이 클래스의 인스턴스변수 값으로 객체의 같고 다름을 판단해야하는 경우라면 equals메서드 뿐 만 아니라 hashCode메서드도 적절히 오버라이딩해야 한다.

같은 객체라면 hashCode메서드를 호출했을 때의 결과값인 해시코드도 같아야 하기 떄문이다. 만일 hashCode메서드를 오버라이딩하지 않는다면 Object 클래스에 정의된 대로 모든 객체가 서로 다른 해시코드값을 가질 것이다.

> [참고] 해싱기법을 사용하는 HashMap이나 HashSet과 같은 클래스에 저장할 객체라면 반드시 hashCode메서드를 오버라이딩 해야 한다. 

String 클래스는 문자열의 내용이 같으면, 동일한 해시코드를 반환하도록 hashCode메서드가 오버라이딩되어 있기 떄문에, 문자열의 내용이 같은 str1과 str2에 대해 hashCode()를 호출하면 항상 동일한 해시코드값을 얻는다.

반면에 System.identityHashCode(Object x)는 Object클래스의 hashCode메서드처럼 객체의 주소값으로 해시코드를 생성하기 때문에 모든 객체에 대해 항상 다른 해시코드값을 반환하는 것을 보장한다. 그래서 str1과 str2가 해시코드는 같지만 서로 다른 객체라는 것을 알 수 있다.

> [참고] System.identityHashCode(Object x)의 호출결과는 실행 할 때마다 달라질 수 있다.


#### toString()

이 메서드는 인스턴스에 대한 정보를 문자열(String)로 제공할 목적으로 정의한 것이다.

인스턴스의 정보를 제공한다는 것은 대부분의 경우 인스턴스 변수에 저장된 값들을 문자열로 표현한다는 뜻이다.

Object 클래스에 정의된 toString()은 아래와 같다.

```JAVA
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```

클래스를 작성할 때 toString()을 오버라이딩하지 않는다면, 위와 같은 내용이 그대로 사용될 것이다. 즉, toString()을 호출하면 클래스이름에 16진수의 해시코드를 얻게 될 것이다.


#### clone()

이 메서드는 자신을 복제하여 새로운 인스턴스를 생성하는 일을 한다. 어떤 인스턴스에 대해 작업을 할 때, 원래의 인스턴스는 보존하고 clone메서드를 이용해서 새로운 인스턴스를 생성하여 작업을 하면 작업이전의 값이 보존되므로 작업에 실패해서 원래의 상태로 되돌리거나 변경되기 전의 값을 참고하는데 도움이 될 것이다.

Object 클래스에 정의된 clone()은 단순히 인스턴스변수의 값만을 복사하기 때문에 참조 타입의 인스턴스 변수가 있는 클래스는 완전한 인스턴스의 복제가 이루어지지 않는다.

예를 들어 배열의 경우, 복제된 인스턴스도 같은 배열의 주소를 갖기 때문에 복제된 인스턴스의 작업이 원래의 영향을 미치게 된다. 이런 경우 clone메서드를 오버라이딩해서 새로운 배열을 생성하고 배열의 내용을 복사하도록 해야한다.

clone()을 사용하려면, 먼저 복제할 클래스가 Cloneable인터페이스를 구현해야하고, clone()을 오버라이딩하면서 접근 제어자를 protected에서 public으로 변경한다. 그래야만 상속관계가 없는 다른 클래스에서 clone()을 호출할 수 있다.

```JAVA
public class Object {
    protected native Object clone() throws CloneNotSupportedException;
}
```

> [참고] Object클래스의 clone()은 Cloneable을 구현하지 않은 클래스 내에서 호출되면 예외를 발생시킨다.

마지막으로 조상클래스의 clone()을 호출하는 코드가 포함된 try-catch문을 작성한다.

Cloneable인터페이스를 구현한 클래스의 인스턴스만 clone()을 통한 복제가 가능한데, 그 이유는 인스턴스의 데이터를 보호하기 위해서이다. Cloneable인터페이스가 구현되어 있다는 것은 클래스 작성자가 복제를 허용한다는 의미이다.


#### 공변 반환타입

JDK 1.5부터 `공변 반환타입(covariant return type)`이라는 것이 추가되었는데, 이 기능은 오버라이딩할 때 조상 메서드의 반환타입을 자손 클래스의 타입으로 변경을 허용하는 것이다. 글로 설명하는 것보다 코드를 직접 보는 쪽이 이해가 빠를 것이다.

아래의 코드는 예제 9-7의 clone()의 반환타입을 Object에서 Point로 타입 캐스팅한 것이다. 즉, 조상의 타입에서 자손의 타입으로 변경한 것이다. 그리고 return문에 Point타입으로 형변환도 추가하였다. 예전에는 오버라이딩할 때 조상에 선언된 메서드의 반환타입을 그대로 사용해야 했다.

```JAVA
@Override
public Point clone() {
    Object obj = null;

    try {
        obj = super.clone();
    } catch (CloneNotSupportedException e) {}

    return (Point) obj;
}
```


이처럼 `공변 반환타입`을 사용하면, 조상의 타입이 아닌, 실제로 반환되는 자손 객체의 타입으로 반환할 수 있어서 번거로운 형변환이 줄어든다는 장점이 있다.

```JAVA
// AS-IS
Point clone = (Point) original.clone();

// TO-BE
Point clone = original.clone();
```

배열 뿐 아니라 java.util 패키지의 Vector, ArrayList, LinkedList, HashSet, TreeSet, HashMap, TreeMap, Calender, Date와 같은 클래스들이 이와 같은 방식으로 복제가 가능하다.


#### 얕은 복사와 깊은 복사

clone()은 단순히 객체에 저장된 값을 그대로 복제할 뿐, 객체가 참조하고 있는 객체까지 복제하지는 않는다. 예제 9-8에서처럼 기본형 배열인 경우에는 아무런 문제가 없지만, 객체배열을 clone()을 복제하는 경우에는 원본과 복제본이 같은 객체를 공유하므로 완전한 복제라고 보기 어렵다. 이러한 복제(복사)를 `얕은 복사(Shallow Copy)`라고 한다. 얕은 복사에서는 원본을 변경하면 복사본도 영향을 받는다.

반면에 원본이 참조하고 있는 객체까지 복제하는 것을 `깊은 복사(Deep Copy)`라고 하며, 깊은 복사에서는 원본과 복사본이 서로 다른 객체를 참조하기 때문에 원본의 변경이 복사본에 영향을 미치지 않는다.

예를 들어 Circle 클래스가 아래와 같이 Point 타입의 참조변수를 포함하고 있고, clone()은 단순히 Object 클래스의 clone()을 호출하도록 정의도어 있을 때

```JAVA
class Circle implements Cloneable {
    Point p;
    double r;

    Circle(Point p, double r) {
        this.p = p;
        this.r = r;
    }

    public Circle clone() {
        Object obj = null;
        try {
            obj = super.clone();
        }catch (CloneNotSupportedException e) {

        }

    return (Circle)obj;
    }
}
```

Circle 인스턴스 c1을 생성하고, clone()으로 복제해서 c2를 생성하면,

```JAVA
Circle c1 = new Circle(new Point(1, 2), 2.0);
Circle c2 = c1.clone();
```

c1과 c2는 같은 Point 인스턴스를 가리키게 되므로 완전한 복제라고 볼 수 없다.

그러면 어떻게 해야 c1과 c2가 각각의 Point인스턴스를 가리키도록 할 수 있을까? 

예제 9-9를 보면 알 수 있듯이, 원본이 참조하고 있는 객체까지 복사하면 된다.


#### getClass()

이 메서드는 자신이 속한 클래스의 Class 객체를 반환하는 메서드인데, Class 객체는 이름이 'Class'인 클래스의 객체이다. Class 클래스는 아래와 같이 정의되어 있다.

```JAVA
public final class Class implements ... {   // Class 클래스
    ...
}
```

Class 객체는 클래스의 모든 정보를 담고 있으며, 클래스 당 1개만 존재한다. 그리고 클래스 파일이 `클래스 로더(ClassLoader)`에 의해서 메모리에 올라갈 때, 자동으로 생성된다.

클래스 로더는 실행 시에 필요한 클래스를 동적으로 메모리에 로드하는 역할을 한다. 먼저 기존에 생성된 클래스 객체가 메모리에 존재하는지 확인하고, 있으면 객체의 참조를 반환하고 없으면 클래스 패스(classpath)에 지정된 경로를 따라서 클래스 파일을 찾는다.

못 찾으면 ClassNotFoundException이 발생하고, 찾으면 해당 클래스 파일을 읽어서 Class객체로 변환한다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/041e6791-1f17-469b-9988-d17af2ee8679)

그러니까 파일 형태로 저장되어 있는 클래스를 읽어서 Class클래스에 정의된 형식으로 변환하는 것이다. 즉, 클래스 파일을 읽어서 사용하기 편한 형태로 저장해 놓은 것이 클래스 객체이다.

> [참고] 클래스 파일을 메모리에 로드하고 변환하는 일은 클래스 로더가 한다.


#### Class 객체를 얻는 방법

클래스의 정보가 필요할 때, 먼저 Class 객체에 대한 참조를 얻어 와야 하는데, 해당 Class 객체에 대한 참조를 얻는 방법은 여러 가지가 있다.

```JAVA
Class cObj = new Card().getClass();     // 생선된 객체로 부터 얻는 방법
Class cObj = Card.class;                // 클래스 리터럴로 부터 얻는 방법
Class cObj = Class.forName("Card");     // 클래스 이름으로 부터 얻는 방법
```

특히 forName()은 특정 클래스 파일, 예를 들어 데이터베이스 드라이버를 메모리에 올릴 때 주로 사용한다.

Class객체를 이용하면 클래스에 정의된 멤버의 이름이나 개수 등, 클래스에 대한 모든 정보를 얻을 수 있기 때문에 Class 객체를 통해서 객체를 생성하고 메서드를 호출하는 등 보다 동적인 코드를 작성할 수 있다.

```JAVA
Card c = new Card();                // new 연산자를 이용해서 객체 생성
Card c = Card.class.newInstance();  // Class 객체를 이용해서 객체 생성
```

> [참고] newInstance()는 InstantiationException이 발생할 수 있으므로 예외처리가 필요하다.

동적으로 객체를 생성하고 메서드를 호출하는 방법에 대해 더 알고 싶다면, '리플렉션 API(Reflection API)'로 검색하면 된다.


### 1.2 String 클래스

기존의 다른 언어에서는 문자열 char형의 배열로 다루었으나 자바에서는 문자열을 위한 클래스를 제공한다. 그것이 바로 String 클래스이인데, String 클래스는 문자열을 저장하고 이를 다루는데 필요한 메서드를 제공한다.


#### 변경 불가능한(immutable) 클래스

String 클래스에는 문자열을 저장하기 위해서 문자형 배열 변수(char[]) value를 인스턴스 변수로 정의해놓고 있다. 인스턴스 생성 시 생성자의 매개변수로 입력받는 문자열이 인스턴스변수(value)에 문자형 배열(char[])로 저장되는 것이다.

> [참고] String 클래스는 앞에 final 붙어 있으므로 다른 클래스의 조상이 될 수 없다.

한번 생성된 String 인스턴스가 갖고 있는 문자열은 읽어 올 수만 있고, 변경할 수는 없다.

예를 들어 아래의 코드와 같이 '+'연산자를 이용해서 문자열을 결합하는 경우 인스턴스내의 문자열이 바뀌는 것이 아니라 새로운 문자열("ab")이 담긴 String 인스턴스가 생성되는 것이다.

```JAVA
String a = "a";
String b = "b";
String a = a + b;
```

이처럼 덧셈 연사자'+'를 사용해서 문자열을 결합하는 것은 매 연산 시마다 새로운 문자열을 가진 String 인스턴스가 생성되어 메모리공간을 차지하게 되므로 가능한 한 결합횟수를 줄이는 것이 좋다.

문자열간의 결합이나 추출 등 문자열을 다루는 작업이 많이 필요한 경우에는 String 클래스 대신 StringBuffer클래스를 사용하는 것이 좋다. StringBuffer인스턴스에 저장된 문자열은 변경이 가능하므로 하나의 StringBuffer인스턴스만으로도 문자열을 다루는 것이 가능하다.


#### 문자열의 비교

문자열을 만들 때는 두 가지 방법, 문자열을 리터럴을 지정하는 방법과 String 클래스의 생성자를 사용해서 만드는 방법이 있다.

String클래스의 생성자를 이용한 경우에는 new 연산자에 의해서 메모리할당이 이루어지기 때문에 항상 새로운 String 인스턴스가 생성된다. 그러나 문자열 리터럴은 이미 존재하는 것을 재사용하는 것이다.

> [참고] 문자열 리터럴은 클래스가 메모리에 로드될 때 자동적으로 미리 생성된다.


#### 문자열 리터럴

자바 소스파일에 포함된 모든 문자열 리터럴은 컴파일 시에 클래스 파일에 저장된다. 이떄 같은 내용의 문자열 리터럴은 한번만 저장된다. 문자열 리터럴도 String 인스턴스이고, 한번 생성하면 내용을 변경할 수 없으니 하나의 인스턴스를 공유하면 되기 때문이다.

클래스 파일에는 소스파일에 포함된 모든 리터럴의 목록이 있다. 해당 클래스 파일이 클래스 로더에 의해 메모리에 올라갈 때, 이 리터럴의 목록에 있는 리터럴들이 JVM내에 있는 `상수 저장소(constant pool)`에 저장된다.


#### 빈 문자열(empty String)

길이가 0인 배열이 존재할 수 있을까? 답은 YES.

char형 배열도 길이가 0인 배열을 생성할 수 있고, 이 배열을 내부적으로 가지고 있는 문자열이 바로 빈 문자열이다.

"String s = "";"과 같은 문장이 있을 때, 참조변수 s가 참조하고 있는 String인스턴스는 내부에 'new char[0]'과 같이 길이가 0인 char형 배열을 저장하고 있는 것이다.


#### String클래스의 생성자와 메서드


|메서드|설명|
|---|---|
|String(String s)|주어진 문자열(s)를 갖는 String 인스턴스 생성
|String(char[] value)|주어진 문자열(value)를 갖는 String 인스턴스 생성
|String(StringBuffer buf)|StringBuffer인스턴스가 갖고 있는 문자열과 같은 내용의 String인스턴스를 생성한다.|
|char charAt(int index) | 지정된 위치(index)에 있는 문자를 알려준다.|
|int compareTo(String str)|문자열(str)과 사전순서로 비교한다. 같으면 0을, 사전순으로 이전이면 음수를, 이후면 양수를 반환한다|
|String concat(String str)|문자열(str)을 뒤에 덧붙인다.|
|boolean contains(String str)| 지정된 문자열(str)이 포함되었는지 검사한다|
|boolean endWith(String suffix)|지정된 문자열(suffix)로 끝나는지 검사한다|
|boolean equals(Object obj)| 매개변수로 받은 문자열(obj)과 String인스턴스의 문자열을 비교한다. obj가 String이 아니거나 문자열이 다르면 false를 반환한다.|
|boolean equalsIgnoreCase(String str)|문자열과 String인스턴스의 문자열을 대소문자 구분없이 비교한다.|
|int indexOf(int ch)| 주어진 문자(ch)가 문자열에 존재하는지 지정된 위치(pos)부터 확인하여 위치(index)를 알려준다. 못찾으면 -1을 반환한다.|
|int indexOf(String str)| 주어진 문자열이 존재하는지 홗인하여 그 위치(index)를 알려준다. 없으면 -1을 반환한다.|
|String intern()|문자열을 상수풀(constant pool)에 등록한다. 이미 상수풀에 같은 내용의 문자열이 있을 경우 그 문자열의 주소값을 반환한다.|
|int lastIndexOf(int ch)| 지정된 문자 또는 문자코드를 문자열의 오른쪽끝에서부터 찾아서 위치(index)를 알려준다. 없으면 -1을 반환한다.|
|int lastIndexOf(String str)| 지정된 문자열을 문자열의 오른쪽끝에서부터 찾아서 위치(index)를 알려준다. 없으면 -1을 반환한다.|
|int length()| 문자열의 길이를 반환한다.|
|String replace(char old, char nw)|문자열 중의 문자(old)를 문자(nw)로 바꾼 문자열을 반환한다|
|String replace(CharSequence old, CharSequence nw)|문자열 중의 문자열(old)를 문자열(nw)로 바꾼 문자열을 반환한다|
|String replaceAll(String regex, String replacement)|문자열 중에서 지정된 문자열(regex)과 일치하는 것을 새로운 문자열로 모두 변경한다|
|String replaceFirst(String regex, String replacement)|문자열 중에서 지정된 문자열(regex)과 일치하는 첫번째 것만 새로운 문자열로 변경한다|
|String[] split(String regex)| 문자열을 지정된 분리자(regex)로 나누어 문자열 배열에 담아 반환한다.|
|String[] split(String regex, int limit)| 문자열을 지정된 분리자(regex)로 나누어 문자열 배열에 담아 반환한다. 단, 문자열 전체를 지정된 수(limit)로 자른다.|
|boolean startWith(String prefix)| 주어진 문자열(prefix)로 시작하는지 검사한다.|
|String subString(int begin) | 주어진 시작위치(begin)부터 끝 범위에 포함된 문자열을 얻는다.|
|String subString(int begin, int end) | 주어진 시작위치(begin)부터 끝 범위(end)에 포함된 문자열을 얻는다. 이 때, 시작위치의 문자는 범위에 포함되지만, 끝 위치의 문자는 포함되지 않는다.|
|String toLowerCase()|String인스턴스에 저장되어있는 모든 문자열을 소문자로 변환하여 반환한다.|
|String toString()|String 인스턴스에 저장되어 있는 문자열을 반환한다.|
|String toUpperCase()|String인스턴스에 저장되어있는 모든 문자열을 대문자로 변환하여 반환한다.|
|String trim()|문자열의 왼쪽 끝과 오른쪽 끝에 있는 공백을 없앤 결과를 반환한다. 이 때 문자열 중간에 있는 공백은 제거되지 않는다.|
|statis String valueOf(Object obj)|지정된 값을 문자열로 변환하여 반환한다. 참조변수의 경우, toString()을 호출한 결과를 반환한다.|

> [참고]
> 1. CharSequence는 JDK1.4부터 추가된 인터페이스로 String, StringBuffer 등의 클래스가 구현하였다.
> 2. contains(CharSequence s), replace(CharSequence old, CharSequence nw)는 JDK1.5부터 추가되었다.


#### join()과 StringJoiner

join()은 여러 문자열 사이에 구분자를 넣어서 결합한다. 구분자로 문자열을 자르는 split()과 반대의 작업을 한다고 생각하면 이해하기 쉽다.

```JAVA
String animals = "dob,cat,bear";
String[] arr = animals.split(",");
String str = String.join("-", arr);
```

java.util.StringJoiner 클래스를 사용해서 문자열을 결합할 수도 있는데, 사용하는 방법은 간단하다.

```JAVA
StringJoiner sj = new StringJoiner(",", "[", "]");
String[] strArr = { "aaa", "bbb", "ccc" };

for(String s : strArr) {
    sj.add(t.toUpperCase());
}
```


#### 유니코드의 보충문자

문자를 다루는 메서드라서 매개변수의 타입이 char일 것 같은데 왜 int가 있을까? 그것은 확장된 유니코드를 다루기 위해서이다.

유니코드는 원래 2byte, 즉 16비트 문자체계인데, 이걸로도 모자라서 20비트로 확장하게 되었다. 그래서 하나의 문자를 char타입으로 다루지 못하고, int타입으로 다룰 수밖에 없다. 확장에 의해 새로 추가된 문자들을 '보통 문자(supplementary characters)'라고 하는데, String 클래스의 메서드 중에서는 보충 문자를 지원하는 것이 있고 지원하지 않는 것도 있다. 이들을 구별하는 방법은 쉽다. 매개변수가 'int ch'인 것들은 보충문자를 지원하는 것이고, 'char ch'인 것들은 지원하지 않는 것들이다.


> [참고] 확장된 유니코드(20bit)가 적용된 것은 JDK1.5부터이다.


#### 문자 인코딩 변환

getBytes(String charsetName)를 사용하면, 문자열의 문자 인코딩을 다른 인코딩으로 변경할 수 있다. 자바가 UTF-16을 사용하지만, 문자열 리터럴에 포함되는 문자들은 OS의 인코딩을 사용한다. 한글 윈도우즈의 경우 문자 인코딩으로 CP949를 사용하며, UTF-8로 변경하려면 아래와 같이 한다.

> [참고] 사용가능한 문자 인코딩은 목록은 `System.out.println(java.nio.charset.Charset.availableCharsets());`로 모두 출력할 수 있다.

```JAVA
byte[] utf8_str = "가".getBytes("UTF-8");       // 문자열을 UTF-8로 변환
String str = new String(utf8_str, "UTF-8");     // byte배열을 문자열로 변환
```

서로 다른 문자 인코딩을 사용하는 컴퓨터 간에 데이터를 주고받을 때는 적절한 문자인 코딩이 필요하다. 그렇지 않으면 알아볼 수 없는 내용의 문서를 보게 될 것이다.


UTF-9은 한글 한 글자로 3byte로 표현하고, CP949는 2byte로 표현한다. 그래서 문자 '가'는 UTF-8로 '0xEAB080'이고, CP949로 '0xB0A1'이다.

> [참고] 문자 인코딩은 CP949는 MS949라고 한다.


#### String.format()

format()은 형식화된 문자열을 만들어내는 간단한 방법이다. printf()하고 사용법이 완전히 똑같으므로 사용하는데 별 어려움은 없을 것이다.

#### 기본형 값을 String으로 변환

숫자로 이루어진 문자열을 숫자로, 또는 그 반대로 변환하는 경우가 자주 있다. 이미 나온 것과 같이 기본형을 문자열로 변경하는 방법은 간단하다. 숫자에 빈 문자열 ""을 더해주기만 하면된다. 이 외에도 valueOf()를 사용하는 방법도 있다. 성능은 valueOf()가 더 좋지만, 빈 문자열을 더하는 방법이 간단하고 편하기 때문에 성능향상이 필요한 경우에만 valueOf()를 사용하면 좋다.


#### String을 기본형 값으로 변환

반대로 String을 기본형으로 변환하는 방법도 간단하다. valueOf()를 쓰거나 parseInt()를 사용하면 된다.

```JAVA
int i = Integer.parseInte("100");
int i2 = Integer.valueOf("100");
```

원래 valueOf()의 반환 타입은 int가 아니라 Interger인데, 오토박싱(auto-boxing)에 의해 Integer가 int로 자동 변환된다.

예전에는 parseInt()와 같은 메서드를 많이 사용하였는데, 메서드의 이름을 통일하기 위해 valueOf()가 나중에 추가되었다. valueOf(String s)는 메서드 내부에서 그저 parseInt(String s)를 호출할 뿐이므로, 두 메서드는 반환 타입만 다르지 같은 메서드이다.

parseInt()나 parseFloat() 같은 메서드는 문자열에 공백 또는 문자가 포함되어 있는 경우 변환 시 예외(NumberFormatException)가 발생할 수 있으므로 주의해야 한다. 그래서 문자열 양끝의 공백을 제거해주는 trim()을 습관적으로 같이 사용하기도 한다.

그러나 부호를 의미하는 '+'나 소수점을 의미하는 '.'와 float형 값을 의미하는 f와 같은 자료형 접미사는 허용된다. 단, 자료형에 알맞은 변환을 하는 경우에만 허용된다.

만일 '1.0f'를 int형 변환 메서드인 Integer.pareseInt(String s)를 사용해서 변환하려하면 예외가 발생하지만, Float.parseFloat(String s)를 사용하면 아무런 문제가 없다.

이처럼 문자열을 숫자로 변환하는 과정에서는 예외가 발생하기 쉽기 때문에 주의를 기울여야 하고, 예외가 발생했을 때의 처리를 적절히 해주어야 한다.


### 1.3 StringBuffer 클래스와 StringBuilder 클래스

String 클래스는 인스턴스를 생성할 때 지정된 문자열을 변경할 수 없지만 StringBuffer클래스는 변경이 가능하다. 내부적으로 문자열 편집을 위한 버퍼(buffer)를 가지고 있으며, StringBuffer 인스턴스를 생성할 때 그 크기를 지정할 수 있다.

이 때, 편집할 문자열의 크기를 고려하여 버퍼의 길이를 충분히 잡아주는 것이 좋다. 편집 중인 문자열이 버퍼의 길이를 넘어서게 되면 버퍼의 길이를 늘려주는 작업이 추가로 수행되어야하기 때문에 작업효율이 떨어진다.

StringBuffer클래스는 String 클래스와유사한 점이 많다. 아래의 코드에서 알 수 있듯이, StringBuffer클래스는 String 클래스와 같이 문자열을 저장히기 위한 char형 배열의 참조변수를 인스턴스변수로 선언해 놓고 있다. StringBuffer인스턴스가 생성될 때, char형 배열이 생성되며 이 때 생성된 char형 배열을 인스턴스변수 value가 참조하게 된다.

```JAVA
public final class StringBuffer implements java.io.Serializable {
    private char[] value;
}
```

#### StringBuffer의 생성자

StringBuffer 클래스의 인스턴스를 생성할 때, 적절한 길이의 char형 배열이 생성되고, 이 배열은 문자열을 저장하고 편집하기 위한 공간(buffer)으로 사용된다.

StringBuffer 인스턴스를 생성할 때는 생성자 StringBuffer(int length)를 사용해서 StringBuffer인스턴스에 저장될 문자열의 길이를 고려하여 충분히 여유있는 크기로 지정하는 것이 좋다. StringBuffer 인스턴스를 생성할 때, 버퍼의 크기를 지정해주지 않으면 16개의 문자를 저장할 수 있는 크기의 버퍼를 생성한다.

```JAVA
public StringBuffer(int length) {
    value = new char[length];
    shared = false;
}

public StringBuffer() {
    this(16);       // 버퍼의 크기를 지정하지 않으면 버퍼의 크기는 16이 된다.
}

public StringBuffer(String str) {
    this(str.length() + 16);        // 지정한 문자열의 길이보다 16이 더 크게 버퍼를 생성한다.
    append(str);
}
```

아래의 코드는 StringBuffer 클래스의 일부인데, 버퍼의 크기를 변경하는 내용의 코드다. StringBuffer인스턴스로 문자열을 다루는 작업을 할 때, 버퍼의 크기가 작업하려는 문자열의 길이보다 작을 때는 내부적으로 버퍼의 크기를 증가시키는 작어비 수행된다.

배열의 길이는 변경될 수 없으므로 새로운 길이의 배열을 생성한 후에 이전 배열의 값을 복사해야 한다.

```JAVA
// 새로운 길이(neCapacity)의 배열 생성.
char newValue[] = new char[newCapacity];

System.arraycopy(vlaue, 0, newValue, count);
value = newValue;   // 새로 생성된 배열의 주소를 참조변수 value에 저장.
```

이렇게 함으로써 StringBuffer 클래스의 인스턴스변수 value는 길이가 증가된 새로운 배열을 참조하게 된다.


#### StringBuffer의 변경

String과 달리 StringBuffer는 내용을 변경할 수 있다. 예를 들어 아래와 같이 StringBuffer를 생성하였다고 가정하자.

```JAVA
StringBuffer sb = new StringBuffer("abc");
```

그리고 sb에 문자열 "123"을 추가하면

```JAVA
sb.append("123");
```

append()는 반환타입이 StringBuffer인데 자신의 주소를 반환한다. 그래서 아래와 같은 문장이 수행되면, sb에 새로운 문자열이 추가되고 sb자신의 주소를 반환하여 sb2에는 sb의 주소인 0x100이 저장된다.

```JAVA
StringBuffer sb = new StringBuffer("abc");
sb.append("123");
StringBuffer sb2 = sb.append("ZZ");
System.out.println(sb);     // abc123ZZ
System.out.println(sb2);    // abc123ZZ
```

#### StringBuffer의 비교

String  클래스에서는 equals메서드를 오버라이딩해서 문자열의 내용을 비교하도록 구현되어 있지만, StringBuffer클래스는 equals메서드를 오버라이딩하지 않아서 StringBuffer클래스의 euqals메서드를 사용해도 등가비교연사자(==)로 비교하는 것과 같은 결과를 얻는다.

반면에 toString()은 오버라이딩되어 있어서 StringBuffer인스턴스에 toString()을 호출하면, 담고있는 문자열을 String으로 반환한다.


#### StringBuilder란?

StringBuilder는 멀티쓰레드에 안전(thread safe)하도록 동기화되어 있다. 동기화가 StringBuffer의 성능을 떨어뜨린다는 문제로 인해 만들어진 것이다. 멀티쓰레드로 작성된 프로그램이 아닌 경우, StringBuffer의 동기화는 불필요하게 성능만 떨어뜨리게 된다.

그래서 StringBuffer에서 쓰레드의 동기화만 뺀 StringBuilder가 새로 추가되었다. StringBuilder는 StringBuffer와 완전 똑같은 기능으로 작성되어 있어서, 소스코드에서 StringBuffer대신 StringBuilder를 사용하도록 바꾸기만 하면 된다. 즉, StringBuffer 타입의 참조변수를 선언한 부분과 StringBuffer의 생성자만 바꾸면 된다는 말이다.

```JAVA
// AS-IS
StringBuffer sb = new StringBuffer();
sb.append("abc");

// TO-BE
StringBuilder sb = new StringBuilder();
sb.append("abc");
```

StringBuffer도 충분히 성능이 좋기 때문에 성능향상이 반드시 필요한 경우를 제외하고는 기존에 작성한 코드에서 StringBuffer를 StringBuilder로 굳이 바꿀 필요는 없다.


### 1.4 Math 클래스

Math클래스는 기본적인 수학계산에 유용한 메서드로 구성되어 있다.

Math클래스의 생성자는 접근 제어자가 private이기 때문에 다른 클래스에서 math 인스턴스를 생성할 수 없도록 되어있다. 그 이유는 클래스 내에 인스턴스변수가 하나도 없어서 인스턴스를 생성할 필요가 없기 때문이다. Math클래스의 메서드는 모두 static이며, 아래와 같이 상수만 정의해놓았다.

```JAVA
/**
 * The {@code double} value that is closer than any other to
 * <i>e</i>, the base of the natural logarithms.
 */
public static final double E = 2.7182818284590452354;

/**
 * The {@code double} value that is closer than any other to
 * <i>pi</i>, the ratio of the circumference of a circle to its
 * diameter.
 */
public static final double PI = 3.14159265358979323846;

/**
 * Constant by which to multiply an angular value in degrees to obtain an
 * angular value in radians.
 */
private static final double DEGREES_TO_RADIANS = 0.017453292519943295;

/**
 * Constant by which to multiply an angular value in radians to obtain an
 * angular value in degrees.
 */
private static final double RADIANS_TO_DEGREES = 57.29577951308232;
```

#### 올림, 버림, 반올림

소수점 n번째 자리에서 반올림한 값을 얻기 위해서는 round()를 사용해야 하는데, 이 메서드는 항상 소수점 첫번째짜리에서 반올림 해서 정수값(long)을 결과로 돌려준다.


#### 예외를 발생시키는 메서드

메서드 이름에 'Exact'가 포함된 메서드들이 JDK1.8부터 새로 추가되었다. 이들은 정수형간의 연산에서 발생할 수 있는 오버플로우(overflow)를 감지하기 위한 것이다.

```JAVA
int addExact(int x, int y);         // x + y
int subtractExact(int x, int y);    // x - y
int multiplayExact(int x, int y);   // x * y
int incrementExact(int a);          // a++;
int decrementExact(int a);          // a--
int negateExact(int a);             // -a
int toIntExact(long value);         // (int)value = -int로의 형변환
```

연산자는 단지 결과를 반환할 뿐, 오버플로우의 발생여부에 대해 알려주지 않는다. 그러나 위의 메서드들은 오버플로우가 발생하면, 예외(ArithmeticException)를 발생시킨다.

negateExact(int a)는 매개변수의 부호를 반대로 바꿔주는데 무슨 예외가 발생할까?라고 생각할지도 모르겠다. 부호를 반대로 바꾸는 식은 '~a+1'이다. '~a'의 결과가 int의 최대값이면, 여기에 1을 더하니까 오버플로우가 발생할 수 있는 것이다. 아래의 예제를 실행해보면 이 사실을 확인할 수 있다.


#### 삼각함수와 지수, 로그

Math 클래스에는 이름에서 알 수 있듯이 수학 관련 메서드들이 많이 있다.


#### StrictMath클래스

Math클래스는 최대한의 성능을 얻기 위해 JVM이 설치된 OS의 메서드를 호출해서 사용한다. 즉, OS에 의존적인 계산을 하고 있는 것이다. 예를 들어 부동소수점 계산의 경우, 반올림의 처리방법이 설정이 OS마다 다를 수 있기 때문에 자바로 작성된 프로그램임에도 불구하고 컴퓨터마다 결과가 다를 수 있다.

이러한 차이를 없애기 위해 성능은 다소 포기하는 대신, 어떤 OS에서 실행되어도 항상 같은 결과를 얻도록 Math클래스를 새로 작성하는 것이 StrictMath클래스이다.


#### Math클래스의 메서드

- abs: 주어진 값의 절대값 반환
- ceil: 주어진 값을 올림하여 반환
- floor: 주어진 값을 버림하여 반환
- max: 두 값을 비교하여 큰 쪽 반환
- min: 두 값을 비교하여 작은 쪽 반환
- random: 0.0~1.0범위의 임의의 double값을 반환
- rint: 주어진 double값과 가장 가까운 정수값을 double형으로 반환
- round: 소수점 첫째자리에서 반올림한 정수값(long)을 반환(매개변수의 값이 음수인 경우, round()와 rint()의 결과가 다르다는 것에 주의)


### 1.5 래퍼(wrapper) 클래스

객체지향 개념에서 모든 것은 객체로 다루어져야 한다. 그러나 자바에서는 8개의 기본형을 객체로 다루지 않는데 이것이 바로 자바가 완전한 객체지향 언어가 아니라는 얘기를 드는 이유이다. 그 대신 보다 높은 성능을 얻을 수 있었다.

떄로는 기본형(primitive type) 변수도 어쩔 수 없이 객체로 다뤄야 하는 경우가 있다. 예를 들면, 매개변수로 객체를 요구할 때, 기본형 값이 아닌 객체로 저장해야할 때, 객체간의 비교가 필요할 때 등등의 경우에는 기본형 값들을 객체로 변환하여 작업을 수행해야 한다.

이 떄 사용되는 것이 래퍼(wrapper)클래스이다. 8개의 기본형을 대표하는 8개의 래퍼클래스가 있는데, 이 클래스들을 이용하면 기본형 값을 객체로 다룰 수있다.

래퍼 클래스의 생성자는 매개변수로 문자열이나 각 자료형의 값들을 인자로 받는다. 이때 주의해야할 것은 생성자의 매개변수로 문자열을 제공할 때, 각 자료형에 알맞은 문자열을 사용해야한다는 것이다. 예를 들어 `new Integer("1.0");'과 같이 하면 NumberForamtException이 발생한다.

```JAVA
public final class Integer extends Number implements Comparable<Integer> {
    private final int value;
}
```

이처럼 래퍼클래서들은 객체생성 시에 생성자의 인자로 주어진 각 자료형에 알맞은 값을 내부적으로 저장하고 있으며, 이에 관련된 여러 메서드가 정의되어있다.

래퍼 클래서들은 모두 equals()가 오버라이딩되어 있어서 주소값이 아닌 객체가 가지고 있는 값을 비교한다. 그래서 실행결과를 보면 equals()를 이용한 두 Intger객체의 비교 결과가 true라는 것을 알 수 있다. 오토박싱이 된다고 해도 Integer 객체에 비교연산자를 사용할 수 없다. 대신 compareTo()를 제공한다.

그리고 toString()도 오버라이딩되어 있어서 객체가 가지고 있는 값을 문자열로 변환하여 반환한다. 이 외에도 래퍼 클래서들은 MAX_VALUE, MIN_VALUE, SIZE, BYTES, TYPE 등의 static 상수를 공통적으로 가지고 있다.

> [참고] BYTES는 JDK1.8부터 추가되었다.


#### Number 클래스

이 클래스는 추상클래스로 내부적으로 숫자를 멤버변수로 갖는 래퍼 클래스들의 조상이다. 아래의 그림은 래퍼 클래스의 상속계층도인데, 기본형 중에서 숫자와 관련된 래퍼 클래스들은 모두 Number 클래스의 자손이라는 것을 알 수 있다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/41cc1de3-e1c2-49a1-aa8a-9ae8f200bb23)


그 외에도 Number클래스 자손이므로 BigInteger와 BigDecimal 등이 있는데, BigInteger는 long으로 다룰 수 없는 큰 범위의 정수를, BigDecimal은 double로도 다룰 수 없는 큰 범위의 부동 소수점수를 처리하기 위한 것으로 연산자의 역할을 대신하는 다양한 메서드를 제공한다.

참고로 Number 클래스의 실제 소스는 아래와 ㅇ같다. 객체가 가지고 있는 값을 숫자와 관련된 기본형으로 변환하여 반환하는 메서드들을 정의하고 있다.

```JAVA
public abstract class Number implements java.io.Serializable {
    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code int}.
     */
    public abstract int intValue();

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code long}.
     */
    public abstract long longValue();

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code float}.
     */
    public abstract float floatValue();

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code double}.
     */
    public abstract double doubleValue();

    /**
     * Returns the value of the specified number as a {@code byte}.
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code byte}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code byte}.
     * @since   1.1
     */
    public byte byteValue() {
        return (byte)intValue();
    }

    /**
     * Returns the value of the specified number as a {@code short}.
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code short}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code short}.
     * @since   1.1
     */
    public short shortValue() {
        return (short)intValue();
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -8742448824652078965L;
}
```


#### 문자열로 숫자로 변환하기

다음은 문자열을 숫자로 변환하는 다양한 방법을 보여준다. 문자열로 숫자로 변환할 때는 아래의 방법 중 하나를 선택해서 사용하면 된다.

```JAVA
int i = new Integer("100").intValue();
int i2 = Integer.parseInt("100");
Integer i3 = Interger.valueOf("100");
```

래퍼 클래스의 '타입.parse타입()'형식의 메서드와 '타입.valueOf()'메서드를 정리하면 둘다 문자를 숫자로 바꿔주는 일을 하지만, 전자는 반환값이 기본형(primitive type)이고 후자는 반환값이 래퍼 클래스 타입이라는 차이가 있다.

JDK1.5부터 도입된 '오토박싱(autoboxing)'기능 때문에 반환값이 기본형일 때와 래퍼 클래스일 때의 차이가 없어졌다. 그래서 구별없이 valueOf()를 쓰는 것도 괜찮은 방법이다. 단, 성능은 valueOf()가 조금 더 느리다.

문자열이 10진수가 아닌 다른 진법(radix)의 숫자일 떄도 변환이 가능하도록 다음과 같은 메서드가 제공된다.

```JAVA
static int pareseInt(String s, int radix);  // 문자열 s를 radix진법으로 인식
```


#### 오토박싱 & 언박싱*(autoboxing & unboxing)

JDK1.5이전에는 기본형과 참조형 간의 연산이 불가능했기 떄문에, 래퍼 클래스로 기본형을 객체로 만들어야 연산해야 했다.

```JAVA
int i = 5;
Integer iObj = new Integer(7);
int sum = i + iObj;             // 에러. 기본형과 참조형 간의 덧셈 불가(JDK 1.5이전)
```

그러나 이제는 기본현과 참조형 간의 덧셈이 가능하다. 자바 언어의 규칙이 바뀐 것은 아니고, 컴파일러가 자동으로 변환하는 코드를 넣어주기 때문이다. 아래의 경우, 컴파일러가 Integer객체를 int타입의 값으로 변환해주는 intValue()를 추가해준다.

```JAVA
// 컴파일 전의 코드
int i = 5;
Intger iObj = new Integer(7);
int sum = i + iObj;

// 컴파일 후의 코드
int i = 5;
Intger iObj = new Integer(7);
int sum = i + iObj.intValue();
```

이 외에도 내부적으로 객체 배열을 가지고 있는 Vector클래스나 ArrayList클래스에 기본형 값을 저장해야할 때나 형변환이 필요할 때도 컴파일러가 자동적으로 코드를 추가해 준다. 기본형 값을 래퍼 클래스의 객체로 자동 변환해주는 것을 '오토박싱(autoboxing)'이라고 하고, 반대로 변환하는 것을 '언박싱(unboxing)'이라고 한다.



## 2. 유용한 클래스

### 2.1 java.util.Objects

Object 클래스의 보조 클래스로 Math클래스처럼 모든 메서드가 'static'이다. 객체의 비교나 널 체크(null check)에 유용하다.

isNull()은 해당 객체가 널인제 확인해서 null이면 true를 반환하고 아니면 flase를 반환한다.
nonNull()은 isNull()과 정반대의 일을 한다. 즉, !Objects.isNull(obj)와 같다.

```JAVA
static boolean isNull(Object obj);
static boolean nonNull(Object obj);
```

그리고 requireNonNull()은 해당 객체가 널이 아니어야 하는 경우에 생성한다. 만일 객체가 널이면, NullPointerException을 발생시킨다. 두 번째 매개변수로 지정하는 문자열은 에외의 메시지가 된다.

예전 같으면, 매개변수의 유효성 검사를 당므과 같이 해야하는데, 이제는 requireNonNull()의 호출만으로 간단히 끝낼 수 있다.

```JAVA
private void setName(String name) {
    if(name == null) {
        throw new NullPointerException("name must not be null");
    }
    
    this.name = name;
}

private void setName(String name) {
    Objects.requireNonNull(name, "name must not be null");
    this.name = name;
}
```

Object클래스에는 두 객체의 등가비교를 위한 equals()만 있고, 대소비교를 위한 메서드는 Objects에 comapre()가 추가되었다.


### 2.2 java.util.Random 클래스

난수를 얻는 방법은 Math.random() 이외에도 Random클래스를 사용하여 난수를 얻을 수 있다.

사실 Math.random()은 내부적으로 Random클래스의 인스턴스를 생성해서 사용하는 것이므로 둘 중 편한 것을 사용하면 된다.

아래의 두 문장은 동등하다.

```JAVA
double randNum = Math.random();
double randNum = new Random().nextDouble()
```

Math.random()과 Random의 가장 큰 차이점이라면, 종자값(seed)을 설정할 수 있다는 것이다. 종자값이 같은 Random인스턴스들은 항상 같은 난수를 같은 순서대로 반환하다. 종자값은 난수를 만드는 공식에 사용되는 값으로 같은 공식에 같은 값을 넣으면 같은 결과를 얻게 된다.


#### Random 클래스의 생성자와 메서드

생성자 Random()은 종자값을 System.currentTimeMills()로 하기 때문에 실행할 때마다 얻는 난수가 달라진다.



### 2.3 정슈식(Regular Expression) - java.util.regex 패키지

정규식이란 텍스트 데이터 중에서 원하는 조건(패턴, pattern)과 일치하는 문자열을 찾아 내기 위해 사용하는 것으로 미리 정의된 기호와 문자를 이용해서 작성한 문자열을 말한다. 원래 Unix에서 사용하던 것이고 Perl의 강력한 기능이었는데 요즘은 Java를 비롯해 다양한 언어에서 지원하고 있다.

정규식을 이용하면 많은 양의 텍스트 파일 중에서 원하는 데이터를 손쉽게 뽑아낼 수도 있고 입력된 데이터가 형식에 맞는지 체크할 수도 있다. 예를 들어 html문서에서 전화번호나 이메일 주소만을 따로 추출한다던가, 입력한 비밀번호가 숫자와 영문자의 조합으로 되어 있는지 확인할 수도 있다.

> 1. 정규식을 매개변수로 Pattern클래스의 static메서드인 Pattern compile(String regex)을 호출하여 Pattern인스턴스를 얻는다.
> 2. 정규식으로 비교할 대상으로 매개변수로 Pattern클래스의 Matcher matcher(CharSequence input)를 호출해서 Matcher인스턴스를 얻는다.
> 3. Matcher인스턴스에 boolean matches()를 호출해서 정규식에 부합하느지 확인한다.


> [참고] CharSequence는 인터페이스로, 이를 구현한 클래스 CharBuffer, String, StringBuffer가 있다.

정규식의 일부를 괄호로 나누어 묶어서 그룹화(grouping)할 수 있다. 그룹화된 부분은 하나의 다누이로 무낑는 셈이 되어서 한 번 또는 그 이상의 반복을 의미하는 '+'나 '*'가 뒤에 오면 그룹화된 부분이 적용대상이 된다. 그리고 그룹화된 부분은 group(int i)를 이용해서 나누어 얻을 수 있다.


### 2.4 java.util.Scanner 클래스

Scanner는 화면, 파일, 문자열과 같은 입력소스로부터 문자데이터를 읽어오는데 도움을 줄 목적으로 JDK1.5부터 추가되었다. Scanner에는 다음과 같은 생성자를 지원하기 때문에 다양한 입력소스로부터 데이터를 읽을 수 있다.

```JAVA
Scanner(String source)
Scanner(File source)
Scanner(InputStream source)
Scanner(Readable source)
Scanner(ReadableByteChannel source)
Scanner(Path source)
```

또한 Scanner는 정규식 표현(Regular expression)을 이용한 라인단위의 검색을 지원하며 구분자(delimiter)에도 정규식 표현을 사용할 수 있어서 복잡한 형태의 구분자도 처리가 가능하다.

```JAVA
Scanner useDelimiter(Pattern pattern)
Scanner useDelimiter(String pattern)
```

Scanner가 없던 JDK1.5 이전에는 자바는 화면으로부터 입력받는 것이 왜 이렇게 불편하냐는 개발자들의 문의가 많았으나 Scanner 덕분에 더 이상 문의는 받지 않게 되었다.

JDK1.6 부터는 화면 입출력만 전문적으로 담당하는 java.io.Console이 새로 추가되었다. Console은 이클립스와 같은 IDE에서 잘 동작하지 않기 때문에 이 책에서는 Scanner를 주로 사용했다. 이 두 클래스는 사용법이나 성능측면에서 거의 같기 떄문에 어떤 것을 사용해도 상관없다.

```JAVA
// JDK 1.5 이전
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

String input = br.readLine();

// JDK 1.5 이후(Scanner)
Scanner s = new Scanner(System.in);
String input = s.nextLine();

// JDK 1.6이후(Console)
COnsole console = System.console();
String input = console.readLine();
```

입력받을 값이 숫자라면 nextLine()대신 nextInt() 또는 nextLong()과 같은 메서드를 사용할 수 있다. Scanner에서는 이와 같은 메서드를 제공함으로써 입력받은 문자를 다시 변환하는 수고를 덜어 준다.


### 2.5 StringTokenizer 클래스

StringTokenizer는 긴 문자열을 지정된 구분자(delimiter)를 기준으로 토큰(token)이라는 여러 개의 문자열로 잘라내는 데 사용된다. 예를 들어 "100,200,300,400이라는 문자열이 있을 때 ','를 구분자로 잘라내면 "100", "200", "300", "400"이라는 4개의 문자열(토큰)을 얻을 수 있다.

StringTokenizer를 이용하는 방법 이외에도 아래와 같이 String의 split(String regex)이나 Scanner의 useDelimter(String pattern)를 사용할 수도 있지만, 

```JAVA
String[] result = "100,200,300,400".split(",");
Scanner sc2 = new Scanner("100,200,300,400").useDelimiter(",");
```

이 두가지 방법은 정규식 표현(Regular Expression)을 사용해야하므로 정규식 표현에 익숙하지 않은 경우 StringTokenizer를 사용하는 것이 간단하면서도 명확한 결과를 얻을 수 있을 것이다.

그러나 StringTokenizer는 구분자로 단 하나의 문자 밖에 사용하지 못하기 떄문에 보다 복잡한 형태의 구분자로 문자열을 나누어야 할 때는 어쩔 수 없이 정규식을 사용하는 메서드를 사용해야 할 것이다.


### 2.6 BigInteger 클래스

정수형으로 표현할 수 있는 값의 한계가 있다. 가장 큰 정수형 타입인 long으로 표현할 수 있는 값은 10진수로 19자리 정도이다. 이 값도 상당히 큰 값이지만, 과학적 계산에서는 더 큰 값을 다뤄야 할때가 있다. 그럴 때 사용하면 좋은 것이 BigInteger이다.

BingInteger는 내부적으로 int 배열을 사용해서 값을 다룬다. 그래서 long 타입보다 훨씬 큰 값을 다룰 수 있는 것이다. 대신 성능은 long타입보다 떨어질 수밖에 없다.

```JAVA
final int signum;       // 부호. 1(양수), 0, -1(음수)
final int[] mag;        // 값(magnitude)
```

BingInteger는 String처럼 불변(immutable)이다. 그리고 모든 정수형이 그렇듯이 BigInteger 역시 값을 '2의 보수'의 형태로 표현한다.

좀더 자세히 말하면, 부호를 따로 저장하고 배열에는 값 자체만 저장한다. 그래서 signum의 값이 -1, 즉 음수인 경우, 2의 보수법에 맞게 mag의 값을 변환해서 처리한다. 그래서 부호만 다른 두 값의 mag는 같고 signum은 다르다.


#### BigInteger의 생성

BigInteger를 생성하는 방법은 여러 가지가 있는데, 문자열로 숫자를 표현하는 것이 일반적이다. 정수형 리터럴로는 표현할 수 있는 값의 한계가 있기 때문이다.

```JAVA
BigInger val;
val = new BigInteger("12345678901234567890");   // 문자열로 생성
val = new BigIntger("FFFF", 16);                // n진수(radix)의 문자열로 생성
val = BigInteger.valueOf(1234567890L);          // 숫자로 생성
```

#### 다른 타입으로의 변환

BigInteger를 문자열, 또는 byte배열로 변환하는 메서드는 다음과 같다.

```JAVA
String toString()           // 문자열로 변환
String toString(int radix)  // 지정된 진법(radix)의 문자열로 반환
byte[] toByteArray()        // byte 배열로 전환
```

BigInteger도 Number로부터 상속받은 기본형으로 변환하는 메서드들을 가지고 있다.

```JAVA
int intValue();
long longValue();
float floatValue();
double doubleValue();
```

정수형으로 변환하는 메서드 중에서 이름 끝에 'Exact'가 붙은 것들은 변환한 결과가 변환한 타입의 범위에 속하지 않으면 ArithmeticException을 발생시킨다.


### 2.7 BigDecimal 클래스

double 타입으로 표현할 수 있는 값은 상당히 범위가 넓지만, 정밀도가 최대 13자리 밖에 되지 않고 실수형의 특성상 오차를 피할 수 없다. BigDecimal은 실수형과 달리 정수를 이용해서 실수를 표현한다. 앞에서 배운 것과 같이 실수의 오차는 10진 실수를 2진 실수로 정확히 변환할 수 없는 경우가 있기 때문에 발생하는 것이므로, 오차가 없는 2진 정수로 변환하여 다루는 것이다. 실수를 정수와 10의 제곱의 곱으로 표현한다.

```
정수 x 10^(-scale)
```

scale은 0부터 Integer.MAX_VALUE사이의 범위에 있는 값이다. 그리고 BigDecimal은 정수를 저장하는데 BigInteger를 사용한다.

> [참고] BigIntger처럼 BigDecimal도 불변이다.


#### BigDecimal의 생성

BigDecimal를 생서아흔 방법은 여러 가지가 있는데, 문자열로 숫자를 표현하는 것이 일반적이다. 기본형 리털로는 표현할 수 있는 값의 한계가 있기 때문이다.

그리고 한 가지 주의할 점은, double타입의 값을 매개변수로 갖는 생성자를 사용하면 오차가 발생할 수 있다.

#### 다른 타입으로의 변환

BigDecimal을 문자열로 변환하는 메서드는 다음과 같다.

```JAVA
String toPlainString()      // 어떤 경우에도 다른 기호없이 숫자로만 표현
String toString()           // 필요하면 지수형태로 표현할 수도 있음
```

