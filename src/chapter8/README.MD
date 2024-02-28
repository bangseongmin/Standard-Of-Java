# Chapter 08. 예외처리

## 1. 예외처리(Exception handling)

### 1.1 프로그램 오류

프로그램이 실행 중 어떤 원인 에 의해서 오작동 하거나 비정상적으로 종료되는 경우가 있다. 이러한 결과를 초래하는 원인은 프로그램 에러 또는 오류라고 한다.

이를 발생시점에 따라 `컴파일 에러(compile-time-error)`와 `런타임 에러(runtime-error)`로 나눌 수 있는데, 글자 그대로 `컴파일 에러`는 컴파일 할 때 발생하는 에러이고 프로그램의 실행도중에 발생하는 에러를 `런타임 에러`라고 한다. 이 외에도 `논리적 에러(logical error)`가 있는데, 컴파일도 잘되고 실행도 잘되지만 의도한 것과 다르게 동작하는 것을 말한다. 예를 들어, 차오그이 재고가 음수가 된다던가, 게임 프로그램에서 비행기가 총알을 맞아도 죽지 않는 경우가 이에 해당된다.

> **컴파일 에러** 컴파일 시에 발생하는 에러
> **런타임 에러** 실행 시에 발생하는 에러
> **논리적 에러** 실행은 되지만, 의도와 다르게 동작하는 것


소스코드를 컴파일 하면 컴파일러가 소스코드(*.java)에 대해 오타나 잘못된 구문, 자료형 체크 등의 기본적인 검사를 수행하여 오류가 있는지를 알려준다. 컴파일러가 알려 준 에러들을 모두 수정해서 컴파일을 성공적으로 마치고 나면, 클래스 파일(*.class)이 생성되고, 생성된 클래스 파일을 실행할 수 있게 되는 것이다.

하지만 컴파일을 에러없이 성공적으로 마쳤다고 해서 프로그램의 실행 시에도 에러가 발생하지 않는 것은 아니다. 컴파일러가 소스코드의 기본적인 사항은 컴파일 시에 모두 걸러 줄 수는 있지만, 실행동중에 발생할 수 있는 잠재적인 오류까지 검사할 수 없기 때문에 컴파일은 잘되었어도 실행 중에 에러에 의해서 잘못된 결과를 얻거나 프로그램이 비정상적으로 종료될 수 있다. 

런타임 에러를 발지하기 위해서는 프로그램의 실행도중 발생할 수 있는 모든 경우의 수를 고려하여 이에 대한 대비를 하는 것이 필요하다. 자바에서는 실행 시(runtime) 발생할 수 있는 프로그램 오류를 `에러(error)`와 `예외(exception)`, 두 가지로 구분하였다.

에러는 메모리 부족(OutOfMemoryError)이나 스택오버플로우(StackOverflowError)와 같이 일단 발생하면 복구할 수 없는 심각한 오류이고, 예외는 발생하더라도 수습될 수 있는 비교적 덜 심각한 것이다.

에러가 발생하면, 프로그램의 비정상적인 종료를 막을 길이 없지만, 예외는 발생하더라도 프로그래머가 이에 대한 적절한 코드를 미리 작성해 놓음으로써 프로그램의 비정상적인 종료를 막을 수 있다.

> **에러(error)** 프로그램 코드에 의해서 수습될 수 없는 심각한 코드
> **예외(exception)** 프로그램 코드에 의해서 수습될 수 있는 다소 미약한 오류


### 1.2 예외 클래스의 계층구조

자바에서 실행 시 발생할 수 있는 오류(Exception과 Error)를 클래스로 정의하였다. 모든 클래스의 조상은 Object 클래스이므로 Exception과 Error클래스 역시 Object 클래스의 자손들이다.

![예외클래스 계층도](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/a97fee9c-5e2c-45cd-a8de-8a94c9876c31)


모든 예외의 최고 조상은 Exception 클래스이며, 상속계층도를 Exception 클래스로부터 도식화하면 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/27aeafca-ab02-4a81-9a68-942b9e10a9b2)

위 그림에서 볼 수 있듯이 예외 클래스들은 다음과 같이 두 그룹으로 나눠질 수 있다.

> 1. Exception 클래스와 그 자손들(그림 8-2의 윗부분, RumtimeException과 자손들 제외)
> 2. RuntimeException 클래스와 그 자손들(그림 8-2의 아랫부분)


RuntimeException 클래스들은 주로 프로그래머의 실수에 의해서 발생될 수 있는 예외들로 자바의 프로그래밍 요소들과 관계가 깊다. 예를 들면, 배열의 벗어난다던가(IndexOutOfBoundsException), 값이 null인 참조변수의 멤버를 호출하려 했다던가(NullPointException), 클래스간의 형변환을 잘못했다던가(ClassCastException), 정수를 0으로 나누려고(ArithmeticException)하는 경우에 발생한다.

Exception클래스들은 주로 외부의 영향으로 발생할 수 있는 것들로서, 프로그램의 사용자들의 동작에 의해서 발생하는 경우가 많다. 예를 들면, 존재하지 않는 파일의 이름을 입력했다던가(FileNotFoundException), 실수로 클래스의 이름을 잘못 적었다던가(ClassNotFoundException), 또는 입력한 데이터 형식이 잘못된(DataFormatException) 경우에 발생한다.

> **Exception클래스들** 사용자의 실수와 같은 외적인 요인에 의해 발생하는 예외
> **RuntimeException클래스들** 프로그래머의 실수로 발생하는 예외


### 1.3 예외처리하기 - try-catch문

프로그램의 실행도중에 발생하는 에러는 어쩔 수 없지만, 예외는 프로그래머가 이에 대한 처리를 미리 해주어야 한다.

예외처리(Exception Handling)란, 프로그램 실행 시 발생할 수 있는 예기치 못한 예외의 발생에 대비한 코드를 작성하는 것이며, 예외처리의 목적은 예외의 발생으로 인한 실행 중인 프로그램의 갑작스런 비정상 종료를 막고, 정상적인 실행상태를 유지할 수 있도록 하는 것이다.

> **예외처리(Exception handling)**
> **정의** - 프로그램 실행 시 발생할 수 있는 예외의 발생에 대비한 코드를 작성하는 것
> **목적** - 프로그램의 비정상 종료를 막고, 정상적인 실행상태를 유지하는 것


발생한 예외를 처리하지 못하면, 프로그램을 비정상적으로 종료되며, 처리되지 못한 예외(uncaught exception)은 JVM의 `예외처리기(UncaughtExceptionHandler)`가 받아서 예외의 원인을 화면에 출력한다.

예외를 처리하기 위해서는 try-catch문을 사용하며, 그 구조는 다음과 같다.

```java
try {
    // 예외가 발생할 가능성이 있는 문장들을 넣는다.
} catch(Exception1 e1) {
    // Exception1이 발생했을 경우, 이를 처리하기 위한 문장을 적는다.
} catch(Exception2 e2) {
    // Exception2이 발생했을 경우, 이를 처리하기 위한 문장을 적는다.
} catch(ExceptionN e3) {
    // ExceptionN이 발생했을 경우, 이를 처리하기 위한 문장을 적는다.
}
```

하나의 try블럭 다음에는 여러 종류의 예외를 처리할 수 있도록 하나 이상의 catch블럭이 올 수 있으며, 이 중 발생한 예외의 종류와 일치하는 단 한 개의 catch블럭만 수행된다. 발생한 예외의 종류와 일치하는 catch블럭이 없으면 예외는 처리되지 않는다.


### 1.4 try-catch문에서의 흐름

try-catch문에서, 예외가 발생한 경우와 발생하지 않았을 때의 흐름(문장의 실행순서)이 달라지는데, 아래에 이 두가지 경우에 따라 문장 실행순서를 정리하였다.

> ▶ try 블럭 내에서 예외가 발생한 경우,
> 1. 발생한 예외와 일치하는 catch블럭이 있는지 확인한다.
> 2. 일치하는 catch블럭을 찾게 되면, 그 catch 블럭 내의 문장들을 수행하고 전체 try-catch문을 빠져나가서 그 다음 문장을 계속해서 수행한다. 만일 일치하는 catch블럭을 찾지 못하면, 예외는 처리되지 않는다.
>
> ▶ try 블럭 내에서 예외가 발생하지 않은 경우
> 1. catch 블럭을 거치지 않고 전체 try-catch문을 빠져나가서 수행을 계속한다.


### 1.5 예외의 발생과 catch블럭

catch 블럭은 괄호()와 블럭{} 두 부분으로 나뉘져 있는데, 괄호()내에는 처리하고자 하는 예외의 같은 타입의 참조변수 하나를 선언해야 한다.

예외가 발생하면, 발생한 예외에 해당하는 클래스의 인스턴스가 만들어 진다. 예제 ExceptionEx5에서는 ArithmeticException이 발생했으므로 ArithmeticException인스턴스가 생성된다. 예외가 발생한 문장이 try 블럭에 포함되어 있다면, 이 예외를 처리할 수 있는 catch블럭이 있는지 찾게 된다.

첫 번째 catch블럭부터 차례로 내려가면서 catch블럭의 괄호 내에 선언된 참조변수의 종류와 생성된 예외클래스의 인스턴스에 instanceof연산자를 이용해서 검사하게 되는데, 검사결과가 true인 catch블럭을 만날 때까지 검사는 계속된다.

검사 결과가 true인 catch블럭을 찾게 되면 블럭에 있는 문장들을 모두 수행한 후에 try-catch문을 빠져나가고 예외는 처리되지만, 검사결과가 true인 catch블럭이 하나도 없으면 예외는 처리되지 않는다. 

모든 예외 클래스는 Exception의 자손이므로, catch블럭의 괄호()에 Exception 클래스 타입의 참조변수를 선언해 놓으면 어떤 종류의 예외가 발생하더라도 이 catch 블럭에 의해서 처리된다.


#### printStackTrace()와 getMessage()

예외가 발생했을 때 생성되는 예외 클래스의 인스턴스에는 발생한 예외에 대한 정보가 담겨져 있으며, getMessage()와 printStackTrance()를 통해서 이 정보들을 얻을 수 있다. catch블럭의 괄호()에 선언된 참조변수를 통해 이 인스턴스에 접근할 수 있다. 이 참조변수는 선언된 catch블럭 내에서만 사용가능하며, 자주 사용되는 메서드는 다음과 같다.

> **printStackTrace()**: 예외발생 당시의 호출스택(Call Stack)에 있었던 메서드의 정보와 예외 메시지를 화면에 출력한다.
> **getMessage()**: 발생한 예외클래스의 인스턴스에 저장된 메시지를 얻을 수 있다.


#### 멀티 catch블럭

JDK 1.7부터 여러 catch블럭을 '|'기호를 이용해서, 하나의 catch블럭으로 합칠 수 있게 되었으며, 이를 '멀티 catch블럭'이라 한다. 아래의 코드에서 알 수 있듯이 '멀티 catch블럭'을 이용하면 중복된 코드를 줄일 수 있다. 그리고 '|'기호로 연결할 수 있는 예외 클래스의 개수에는 제한이 없다.

```JAVA
try {
    ...
} catch(ExceptionA e) {
    e.printStackTrace();
} catch(ExceptionB e) {
    e.printStackTrace();
}

try {
    ...
} catch(ExceptionA | ExceptionB e) {
    e.printStackTrace();
} 
```

만일 멀티 catch블럭의 '|'기호로 연결된 예외 클래스가 조상과 자손의 관게에 있다면 컴파일 에러가 발생한다.

왜냐하면, 두 예외 클래스가 조상과 자손의 관계에 있다면, 조상 클래스만 써주는 것과 동일하기 때문이다. 불필요한 코드는 제거하라는 의미에서 에러가 발생하는 것이다.

그리고 멀티 catch는 하나의 catch 블럭으로 여러 예외를 처리하는 것이기 때문에, 발생한 예외를 멀티 catch 블럭으로 처리하게 되었을 때, 멀티 catch 블럭 내에서는 실제로 어떤 예외가 발생한 것인지 알 수 없다. 그래서 참조변수 e로 멀티 catch 블럭에 '|'기호로 연결된 예외 클래스들의 공통 분모인 조상 예외 클래스에 선언된 멤버만 사용할 수 있다.

```JAVA
try {
    ...
} catch (ExceptionA | ExceptionB e) {
    e.methodA();        // 에러. ExceptionA에 선언된 methodA()는 호출불가

    if(e instanceof ExceptionA) {
        ExceptionA e1 = (ExceptionA)e;
        e1.methodA();
    } else {
        ...
    }
}
```

필요하다면, 위와 같이 instanceof로 어떤 에외가 발생한 것인지 확인하고 개별적으로 처리할 수는 있다. 그러나 이렇게까지 해가면서 catch블럭을 합칠 일은 거의 없을 것이다.

마지막으로 멀티 catch 블럭에 선언된 참조변수 e는 상수이므로 값을 변경할 수 없다는 제약이 있는데, 이것은 여러 catch블럭이 하나의 참조변수를 공유하기 때문에 생기는 제약으로 실제로 참조변수의 값을 바꿀 일은 없을 것이다.


### 1.6 예외 발생시키기

키워드 throw를 사용해서 프로그래머가 고의로 예외를 발생시킬 수 있으며, 방법은 아래의 순서를 따르면 된다.

> 1. 연산자 new를 이용해서 발생시키려는 예외 클래스의 객체를 만든다.
> 2. 키워드 throw를 이용해서 예뢰를 발생시킨다.


### 1.7 메서드에 예외 선언하기

예외 처리하는 방법에는 try-catch문을 사용하는 것 외에, 예외를 메서드에 선언하는 방법이 있다.

메서드에 예외를 선언하려면, 메서드의 선언부와 키워드 throws를 사용해서 메서드 내에서 발생할 수 있는 예외를 적어주기만 하면 된다. 그리고 예외가 여러 개일 경우에는 쉼표(,)로 구분한다.

```JAVA
void method() throws Exception1, Exception2, ... ExceptionN {

}
```

> [참고] 예외를 발생시키는 키워드 throw와 예욀를 메서드에 선언할 때 쓰이는 throws를 잘 구별하자.


만일 아래와 같이 모든 예외의 최고조상인 Exception 클래스를 메서드에 선언하면, 이 메서드는 모든 종류의 예외가 발생할 가능성이 있다는 뜻이다.

```Java
void method() throws Exception {

}
```

이렇게 예외를 선언하면, 이 예외뿐만 아니라 그 자손타입의 예외까지도 발생할 수 있다는 점에 주의하자. 앞서 오버라이딩에서 살펴본 것과 같이, 오버라이딩할 때는 단순히 선언된 예외의 개수가 아니라 상속관계까지 고려해야 한다.

메서드의 선언부에 예외를 선언함으로써 메서드를 사용하려는 사람이 메서드의 선언부를 보았을 때, 이 메서드를 사용하기 위해서는 어떠한 예외들이 처리되어야 하는지 쉽게 알 수 있다.

기존의 많은 언어들에서는 메서드에 예외선언을 하지 않기 때문에, 경험 많은 프로그래머가 아니고서는 어떤 상황에 어떤 종류의 예외가 발생할 가능성이 있는지 충분히 예측하기 힘들기 때문에 그에 대한 대비를 하는 것이 어려웠다.

그러나 자바에서는 메서드를 작성할 때 메서드 내에서 발생할 가능성이 있는 예외를 메서드의 선언부에 명시하여 이 메서드를 사용하는 쪽에서는 이에 대한 처리를 하도록 강요하기 때문에, 프로그래머들의 짐을 덜어 주는 것은 물론이고 보다 견고한 프로그램 코드를 작성할 수 있도록 도와준다.

예외를 메서드의 throws에 명시하는 것은 예외를 처리하는 것이 아니라, 자신(예외가 발생할 가능성이 있는 메서드)을 호출하는 메서드에게 예외를 전달하여 예외처리를 떠맡기는 것이다.

예외를 전달받은 메서드가 또다시 자신을 호출한 메서드에게 전달할 수 있으며, 이런식으로 계속 호출스택에 있는 메서드들을 따라 전달되다가 제일 마지막에 있는 main메서드에서도 예외가 처리되지 않으면, main메서드마저 종료되어 프로그램이 전체가 종료된다.

ExceptionEx12 예제 실행결과를 보면, 프로그램의 실행도중 java.lang.Exception이 발생하여 비정상적으로 종료했다는 것과 예외가 발생했을 때 호출스택(call stack)의 내용을 알 수 있다.

> 1. 예외가 발생했을 때, 모두 3개의 메서드(main, method1, method2)가 호출스택에 있었으며
> 2. 예외가 발생한 곳은 제일 윗줄에 있는 method2()라는 것과
> 3. main메서드가 method1()을, 그리고 method1()은 method2()를 호출했다는 것을 알 수 있다.


위의 예제를 보면, method2()에서 `throw new Exception();`문장에 의해 예외가 강제적으로 발생했으나 try-catch문으로 예외처리를 해주지 않았으므로, method2()는 종료되면서 예외를 자신을 호출한 method1()에게 넘겨준다. method1()에서도 역시 예외처리를 해주지 않았으므로 종료되면서 main메서드에게 예외를 넘겨준다.

그러나 main 메서드에서 조차 예외처리를 해주지 않았으므로 main메서드가 종료되어 프로그램이 예외로 인해 비정상적으로 종료되는 것이다.

이처럼 예외가 발생한 메서드에게 예외처리를 하지 않고 자신을 호출한 메서드에게 예외를 넘겨줄 수는 있지만, 이것으로 예외가 처리된 것은 아니고 예외를 단순히 전달만 하는것이다. 결국 어느 한 곳에서는 반드시 try-catch문으로 예외처리를 해주어야 한다.

ExceptionEx13 예제에서 예외는 처리되었지만, printStackTrace()를 통해 예외에 대한 정보를 화면에 출력하였다. 예외가 method1()에서 발생했으며, main메서드가 method1()을 호출했음을 알 수 있다.

ExceptionEx13과 14예제 모두 main메서드가 method1()을 호출하여, method1()에서 예외가 발생한다. 차이점은 예외처리 방법에 있다.

예제 8-13은 method1()에서 예외처리를 했고, 예제 8-14는 method1()에서 예외를 선언하여 자신을 호출하는 메서드(main)에 예외를 전달했으며, 호출한 메서드(main)메서드에서는 try-catch문으로 예외처리를 했다.

예제 8-13처럼 예외가 발생한 메서드(method1)내에서 처리되어지면, 호출한 메서드(main)에서는 method1()을 호출한 라인에서 예외가 발생한 것으로 간주되어 이에 대한 처리를 하게 된다.

이처럼 예외가 발생한 메서드 'method1()'에서 예외를 처리할 수도 있고, 예외가 발생한 메서드를 호출한 'main(메서드)'에서 처리할 수도 있다. 또는 두 메서드가 예외처리를 분담할 수도 있다.


### 1.8 finally 블럭

finally 블럭은 try-catch문과 함께 예외의 발생여부에 상관없이 실행되어야할 코드를 포함시킬 목적으로 사용된다. try-catch문의 끝에 선택적으로 덧붙여 사용할 수 있으며, try-catch-finally의 순서로 구성된다.

```JAVA
try {
    // 예외가 발생할 가능성이 있는 코드 작성
} catch(Exception e) {
    // 예외처리를 위한 코드 작성
} finally {
    // 예외의 발생여부에 관계없이 항상 수행되어야하는 코드 작성
}
```

### 1.9 자동 자원 반환 - try-with-resources문

JDK 1.7부터 try-with-resource문이라는 try-catch문의 변형이 새로 추가되었다. 이 구문은 주로 '입출력(I/O)'과 관련된 클래스를 사용할 때 유용하다.

주로 입출력에 사용되는 클래스 중에서는 사용한 후에 꼭 닫아 줘야 하는 것들이 있다. 그래야 사용했던 자원(resources)이 반환되기 때문이다.

```JAVA
try {
    fis = new FileInputStream("score.dat");
    dis = new DataInputStream(fis);
    ...
} catch(IOException ie) {
    ie.printStackTrace();
} finally {
    dis.close();
}
```

위의 코드는 DataInputStream을 사용해서 파일로부터 데이터를 읽는 코드인데, 데이터를 읽는 도중에 예외가 발생하더라도 DataInputStream이 닫히도록 finally블럭안에 close()를 넣었다. 여기까지는 별 문제가 없어 보이는데, 진짜 문제는 close()가 예외를 발생시킬 수 있다는데 있다. 그래서 위의 코드는 아래와 같이 해야 올바른 것이 된다.

```JAVA
try {
    fis = new FileInputStream("score.dat");
    dis = new DataInputStream(fis);
    ...
} catch(IOException ie) {
    ie.printStackTrace();
} finally {
    try {
        if(dis != null) {
            dis.close();
        }
    }catch(IOException ie) {
            ie.printStackTrace();
    }
}
```

finally 블럭 안에 try-catch문을 추가해서 close()에서 발생할 수 있는 예외를 처리하도록 변경했는데, 코드가 복잡해져서 별로 보기에 좋지 않다. 더 나쁜 것은 try블럭과 finally 블럭에서 모두 예외가 발생하면, try블럭의 예외는 무시된다는 것이다.

이러한 점을 개선하기 위해서 try-with-resources문이 추가된 것이다. 위의 코드를 try-with-resources문으로 바꾸면 다음과 같다.

```JAVA
try (
    FileInputStream fis = new FileInputStream("score.dat");
    DataInputStream dis = new DataInputStream(dis);
){
    while(true) {
        score = dis.readInt();
        System.out.println(score);
        sum += score;
    }
} catch(EOFException e) {
    System.out.println("점수의 총합은 " + sum + "입니다");
} catch(IOException ie) {
    ie.printStckTrace();
};
```

try-with-resources문의 괄호()안에 객체를 생성하는 문장을 넣으면, 이 객체는 따로 close()를 호출하지 않아도 try-블럭을 벗어나는 순간 자동적으로 close()가 호출된다. 그다음에 try-catch 블럭 또는 finally블럭이 수행된다.

> [참고] try블럭의 괄호()안에 변수를 선언하는 것도 가능하며, 선언된 변수는 try 블럭 내에서만 사용할 수 있다.

이처럼 try-with-resources문에 의해 자동으로 객체의 close()가 호출될 수 있으려면, 클래스가 AutoCloseable이라는 인터페이스를 구현한 것이어야만 한다.

```JAVA
public interface AutoCloseable {
    void close() throws Exception;
}
```

AutoCloseable 인터페이스는 각 클래스에서 적절히 자원 반환작업을 하도록 구현되어 있다. 그런데, 위의 코드를 잘보면 close()도 Exception을 발생시킬 수 있다. 만일 자동 호출된 close()에서 에외가 발생하면 어떻게 될까?


예제 TryWithResourceEx를 보면 main메서드에  두 개의 try-catch문이 있는데, 첫 번째 것은 close()에서만 예외를 발생시키고, 두 번째 것은 exceptionWork()와 close()에서 모두 예외를 발생시킨다.

```BASH
called exceptionWork (false)
called close()

called exceptionWork (true)
called close()
chapter8.try_with_resource.CloseException: occurred CloseException
	at chapter8.try_with_resource.CloseableResource.close(CloseableResource.java:18)
	at chapter8.try_with_resource.TryWithResourceEx.main(TryWithResourceEx.java:10)
chapter8.try_with_resource.WorkException: occurred WorkException
	at chapter8.try_with_resource.CloseableResource.exceptionWork(CloseableResource.java:11)
	at chapter8.try_with_resource.TryWithResourceEx.main(TryWithResourceEx.java:19)
	Suppressed: chapter8.try_with_resource.CloseException: occurred CloseException
		at chapter8.try_with_resource.CloseableResource.close(CloseableResource.java:18)
		at chapter8.try_with_resource.TryWithResourceEx.main(TryWithResourceEx.java:18)
```

첫 번째는 일반적인 예외가 발생했을 때와 같은 형태로 출력되었지만, 두 번째는 출력형태가 다르다. 먼저 exceptionWork()에서 발생한 예외에 대한 내용이 출력되고, close()에서 발생한 예외는 '억제된(suppressed)'이라는 의미의 머리말과 함께 출력되었다.

두 예외가 동시에 발생할 수는 없기 때문에, 실제 발생한 예외를 WorkException으로하고, CloseException은 억제된 예외로 다룬다. 억제된 예외에 대한 정보는 실제로 발생한 예외인 WorkException에 저장된다.

Throwable에는 억제된 예외와 관련된 다음과 같은 메서드가 정의되어 있다.

> void addSupressed(Throwable exception)        // 억제된 예외를 추가
> Throwable[] getSupressed()                    // 억제된 예외(배열)을 반환


### 1.10 사용자정의 예외

기존의 정의된 예외 클래스 외에 필요에 따라 프로그래머가 새로운 예외 클래스를 정의하여 사용할 수 있다. 보통 Exception 클래스로부터 상속받는 클래스를 만들지만, 필요에 따라서 알맞은 예외 클래스를 선택할 수 있다.

> [참고] 가능하면 새로운 예외 클래스를 만들기보다 기존의 예외클래스를 활용하자.


```JAVA
class MyException extends Exception {
    MyException(String msg) {
        super(msg);
    }
}
```

Exception클래스로부터 상속받아서 MyException클래스를 만들었다. 필요하다면, 멤버변수나 메서드를 추가할 수 있다. Exception 클래스는 생성 시에 String 값을 받아서 메시지로 저장할 수 있다.

```JAVA
class MyException extends Exception {
    // 에러 코드
    private final int ERR_CODE;

    MyException(String msg, int errCode) {
        super(msg);
        ERR_CODE = errCode;
    }

    MyException(String msg) {
        super(msg);
        ERR_CODE = 100;
    }

    public int getErrCode() {
        return ERR_CODE;
    }
}
```

이전의 코드를 좀더 개선하여 메시지뿐만 아니라 에러코드 값도 저장할 수 있또록 ERR_CODE와 getErrCOde()를 MyException클래스의 멤버로 추가했다.

이렇게 함으로써 MyException이 발생했을 때, catch블럭에서 getMessage()와 getErrCode()를 사용해서 에러코드와 메시지를 모두 얻을 수 있을 것이다.

기존의 예외 클래스는 주로 Exception을 상속받아서 'checked예외'로 작성하는 경우가 많았지만, 요즘은 예외처리를 선택적으로 할 수 있도록 RuntimeException을 상속받아서 작성하는 쪽으로 바뀌어가고 있다. 'checked예외'는 반드시 예외처리를 해주어야 하기 때문에 예외처리가 불필요한 경우에도 try-catch문을 넣어서 코드가 복잡해지기 때문이다.

예외처리를 강제하도록 한 이유는 프로그래밍경험이 적은 사람들도 보다 견고한 프로그램을 작성할 수 있게 유도하기 위한 것이었는데, 요즘은 자바가 탄생하던 20년 전과 달리 프로그래밍 환경이 많이 달려졌다. 그때 자바를 설계하던 사람들은 자바가 주로 소형 가전기기나, 데스크탑에서 실행될 것이라고 생각했지만 현재 자바는 모바일이나 웹 프로그래밍에서 주로 쓰이고 있다. 이처럼 프로그래밍 환경이 달라진 만큼 필수적으로 처리해야만 할 것 같았던 예외들이 선택적으로 처리해도 되는 상황으로 바뀌는 경우가 종종 발생하고 있다. 그래서 필요에 따라 예외처리의 여부를 선택할 수 있는 'unchecked예외'가 강제적인 'checked예외'보다 더 환영받고 있다.


## 1.11 예외 되던지기(exception re-throwing)

한 메서드에서 발생할 수 있는 예외가 여럿인 경우, 몇 개는 try-catch문을 통해서 메서드 내에서 자체적으로 처리하고, 그 나머지는 선언부에 지정하여 호출한 메서드에서 처리하도록 함으로써, 양쪽에서 나눠서 처리되도록 할 수 있다.

그리고 심지어는 단 하나의 예외에 대해서도 예외가 발생한 메서드와 호출한 메서드, 양쪽에서 처리하도록 할 수 있다.

이것은 예외를 처리한 후에 인위적으로 다시 발생시키는 방법을 통해서 가능한데, 이것을 '예외 되던지기(exception re-throwing)'라고 한다.

먼저 예외가 발생할 가능성이 있는 메서드에서 try-catch문을 사용해서 예외를 처리해주고 catch문에서 필요한 작업을 행한 후에 throw문을 사용해서 예외를 다시 발생시킨다. 다시 발생한 예외는 이 메서드를 호출한 메서드에게 전달되고 호출한 메서드의 try-catch문에서 예외를 또다시 처리한다.

이 방법은 하나의 예외에 대해서 에외가 발생한 메서드와 이를 호출한 메서드 양쪽 모두에서 처리해줘야 할 작업이 있을 때 사용된다. 이때 주의할 점은 예외가 발생할 메서드에서는 try-catch문을 사용해서 예외처리를 해줌과 동시에 메서드의 선언부에 발생할 예외를 throws에 지정해주어야 한다는 것이다.


## 1.12 연결된 예외(Chained Exception)

한 예외가 다른 예외를 발생시킬 수도 있다. 예를 들어 예외 A가 예외 B를 발생시켰다면, A를 B의 `원인 예외(Cause Exception)`라고 한다. 아래의 코드는 예제 NewExceptionTest의 일부를 변경한 것으로, SpaceException을 원인 예외로 하는 InstallException을 발생시키는 방법을 보여준다.

```JAVA
try {
    startInstall();
    copyFiles();
} catch (SpaceException e) {
    InstallException ie = new InstallException("설치중 예외발생");
    ie.initCause(e);
    throw ie;
} catch (MemoryException e) {
    ...
} 
```

먼저 InstallException을 생성한 후에, initCause()로 SpaceException을 InstallException의 원인 예외로 등록한다. 그리고 'throw'로 예외를 발생시킨다.

initCause()는 Exception 클래스의 조상인 Throwable클래스에 정의되어 있기 때문에 모든 예외에서 사용가능하다.

```JAVA
Throwable initCause(Throwable cause)        // 지정한 예외를 원인 예외로 등록
Throwable getCause()                        // 원인 예외를 반환
```

발생한 예외를 그냥 처리하면 될 텐데, 원인 예외로 등록해서 다시 예외를 발생시키는지 궁금할 것이다. 그 이유는 여러가지 예외를 하나의 큰 분류의 예외로 묶어서 다루기 위해서이다.

그렇다고 아래와 같이 InstallException을 SpaceException과 MemoryException의 조상으로 해서 catch블럭을 작성하면, 실제로 발생한 예외가 어떤 것인지 알 수 없다는 문제가 발생한다. 그리고 SpaceException과 MemoryException의 상속관계를 변경해야하는 것도 부담이다.

그래서 생각한 것이 예외가 원인 예외를 포함시킬 수 있게 한 것이다. 이렇게 하면, 두 예외는 상속관계가 아니어도 상관없다.

```JAVA
public class Throwable implements Serializable {
    private Throwable cause = this; // 객체 자신(this)을 원인 예외로 등록
}
```

또 다른 이유는 checked예외를 unchecked예외로 바꿀 수 있도록 하기 위해서이다. checked예외로 예외처리를 강제한 이유는 프로그래밍 경험이 적은 사람도 보다 견고한 프로그램을 작성할 수 있도록 유도하기 위한 것이었는데, 지금은 자바가 처음 개발되던 1990년대와 컴퓨터 환경이 많이 달라졌다.

그래서 checked 예외가 발생해도 예외를 처리할 수 없는 상황이 하나둘 발생하기 시작했다. 이럴 때 할 수 있는 일이라곤 그저 의미없는 try-catch문을 추가하는 것뿐인데, checked예외를 unchecked예외로 바꾸면 예외처리가 선택적이 되므로 억지로 예외처리를 하지 않아도 된다.

```JAVA
// AS-IS
static void statInstall() throws SpaceException, MemoryException {
    if(!enoughSpace()) {
        throw new SpaceException("설치할 공간이 부족");
    }
    if(!enoughMemory()){
        throw new MemoryException("메모리 부족");
    }
}

// TO-BE
static void statInstall() throws SpaceException {
    if(!enoughSpace()) {
        throw new SpaceException("설치할 공간이 부족");
    }
    if(!enoughMemory()){
        throw new RuntimeException(new MemoryException("메모리 부족"));
    }
}
```

MemoryException은 Exception의 자손이므로 반드시 예외를 처리해야하는데, 이 예외를 RuntimeException으로 감싸버렸기 때문에 unchecked예외가 되었다. 그래서 더 이상 startInstall()의 선언부에 MemoryException을 선언하지 않아도 된다. 참고로 위의 코드에서 initCause()대신 RuntimeException의 생성자를 사용했다.

