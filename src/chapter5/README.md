# Chapter 05. 배열

## 1. 배열(Array)

### 1.1 배열(Array)이란?

같은 타입의 여러 변수를 하나의 묶음으로 다루는 것을 '배열(array)'이라고 한다. 많은 야으이 데이터를 저장하기 위해서, 그 데이터의 숫자만큼 변수를 선언해야 한다면, 매우 혼란스러울 것이다. 10,000개의 데이터를 저장하기 위해 같은 수의 변수를 선언해야한다면 상상하는 것만으로도 상당히 곤혹스러울 것이다.

이런 경우에 배열을 사용하면 많은 야으이 데이터를 손쉽게 다룰 수 있다.

> "배열은 같은 타입의 여러 변수를 하나의 묶음으로 다루는 것"

여기서 중요한 것은 '같은 타입'이어야 한다는 것이며, 서로 다른 타입의 변수들로 구성된 배열은 만들 수 없다.

변수와 달리, 배열은 각 저장공간이 연속적으로 배치되어 있다는 특징이 있다.

### 1.2 배열의 선언과 생성

원하는 타입의 변수를 선언하고 변수 또는 타입에 배열임을 의미하는 대괄호[]를 붙이면 된다. 대괄호[]는 타입 뒤에 붙여도 되고 변수이름 뒤에 붙여도 된다.

```java
// 타입[] 변수이름;
int[] score;

// 타입 변수이름[];
int score[];
```

#### 배열의 생성

배열을 선언한 다음에는 배열을 생성해야한다. 배열을 선언하는 것은 단지 생성된 배열을 다루기 위한 참조변수를 위한 공간이 만들어질 뿐이고, 배열을 생성해야만 비로소 값을 저장할 수 있는 공간이 만들어지는 것이다. 배열을 생성하기 위해서는 연산자 'new'와 함께 배열의 타입과 길이를 지정해 주어야 한다.

```java
// 타입[] 변수이름;
변수이름 = new 타입[길이];
```

### 1.3 배열의 길이와 인덱스

생성된 배열의 각 저장공간을 '배열의 요소'라고 하며, '배열이름[인덱스]'의 형식으로 배열의 요소에 접근한다. `인덱스(index)는 배열의 요소마다 붙여진 일련번호`로 각 요소를 구별하는데 사용된다.

> `인덱스(index)의 범위는 0부터 배열의 길이 -1까지`

#### 배열의 길이

> 배열의 길이는 int 범위의 양의 정수(0도 포함)이어야 한다.

#### 배열의 길이 변경

배열은 한번 선언되고 나면 길이를 변경할 수 없다. 배열에 저장할 공간이 부족한 경우에는 어떻게 해야 할까? 이때는 더 큰 길이의 배열을 생성한 다음, 기존의 배열에 저장된 값들을 새로운 배열에 복사하면 된다.

이러한 작업들은 꽤나 비용이 많이 들기 때문에, 처음부터 배열의 길이를 넉넉하게 잡아줘서 새로 배열을 생성해야하는 상황이 가능한 적게 발생하도록 해야 한다. 그렇다고 배열의 길이를 너무 크게 잡으면 메모리를 낭비하게 되므로, 기존의 2배정도의 길이로 생성하는 것이 좋다.

### 1.4 배열의 초기화

배열은 생성과 동시에 자동적으로 자신의 타입에 해당하는 기본값으로 초기화되므로 배열을 사용하기 전에 따로 초기화를 해주지 않아도 되지만, 원하는 값을 저장하려면 아래와 같이 각 요소마다 값을 지정해 줘야한다.

### 1.5 배열의 복사

배열은 한번 생성하면 그 길이를 변경할 수 없기 때문에 더 많은 저장공간이 필요하다면 보다 큰 배열을 새로 만들고 이전 배열로부터 내용을 복사해야한다고 했다.

#### System.arraycopy()를 이용한 배열의 복사

for문 대신 System 클래스의 arraycopy()를 사용하면 보다 간단하고 빠르게 배열을 복사할 수 있다. for문은 배열의 요소 하나하나에 접근해서 복사하지만, arraycopy()는 지정된 범위의 값들을 한 번에 통째로 복사한다. 각 요소들이 연속적으로 저장되어 있다는 배열의 특성때문에 이렇게 처리하는 것이 가능한 것이다.

> 배열의 복사는 for문보다 System.arraycopy()를 사용하는 것이 효율적이다.

## 2. String 배열

### 2.1 String 배열의 선언과 생성

배열의 타입이 String인 경우에도 int배열의 선언과 생성방법은 다르지 않다. 예를 들어 3개의 문자열(String)을 담을 수 있는 배열을 생성하는 문장은 다음과 같다.

```java
String[] name = new String[3];
```

위의 문장을 수행한 결과를 그림으로 표현하면 다음과 같다. 3개의 String 타입의 참조변수를 저장하기 위한 공간이 마련되고 참조형 변수의 기본값은 null값이므로 각 요소의 값은 null로 초기화 된다.

> [참고] null은 어떠한 객체도 가리키고 있지 않다는 뜻이다.

![String 배열](https://github.com/bang-star/Vue/assets/63120360/017da190-7d42-4ee0-a907-03202a879d7a)

참고로 변수의 타입에 따른 기본값은 다음과 같다.

![타입에 따른 변수의 기본값(default value)](https://github.com/bang-star/Vue3/assets/63120360/14b95928-425d-4639-b23b-ee29f0a646d1)

### 2.2 String 배열의 초기화

초기화 역시 int 배열과 동일한 방법으로 한다. 아래와 같이 배열의 각 요소에 문자열을 지정하면 된다.

```java
String[] name = new String[3];
name[0] = "Kim";
name[1] = "Park";
name[2] = "Yi";
```

또는 괄호 {}를 사용해서 다음과 같이 간단히 초기화 할 수도 있다.

```java
String[] name = new String[]["Kim", "Park", "Yi"];
String[] name = { "Kim", "Park", "Yi" };
```

String 클래스만 "Kim"과 같이 큰따옴표만으로 간략히 표현하는 것이 허용되지만, 원래 String은 클래스이므로 아래의 왼쪽처럼 new 연산자를 통해 객체를 생성해야한다.

![간략한 그림](https://github.com/bang-star/Vue3/assets/63120360/ba120225-df39-40ac-b51c-b3fa42ee0b5f)

배열에 실제 객체가 아닌 객체의 주소가 저자오디어 있는 것을 볼 수 있다. 기본형 배열이 아닌 경우, 즉, 참조형 배열의 경우 배열에 저장되는 것은 객체의 주소이다.

> [참고] 참조형 변수를 간단히 참조변수라고도 하며, 모든 참조형 변수에는 객체가 메모리에 저장된 주소인 4 byte의 정수 값(0x0 ~ 0xffffffff) 또는 null이 저장된다.

16진수를 2진수로 변환하는 예제이다. 먼저 변환하고자 하는 16진수를 배열 hex에 나열한다. 16진수에는 A~F까지 6개의 문자가 포함되므로 char배열로 처리하였다. 그리고 문자열 배열 binary에는 이진수 '0000'부터 '1111'(16진수로 0~F)까지 모두 16개의 값을 문자열로 저장하였다.

for문을 이용해서 배열 hex에 저장된 문자를 하나씩 읽어서 그에 해당하는 이진수 표현을 배열 binary에서 얻어 result에 덧붙이고 그 결과를 화면에 출력한다.

```java
result += binary[hex[i] - 'A' + 10];
```

### 2.3 char배열과 String 클래스

여러 문자, 즉 문자열을 저장할 때 String 타입의 변수를 사용했다. 문자열이라는 용어는 '문자를 연이어 늘어놓은 것'을 의미하므로 문자배열인 char배열과 같은 뜻이다.

그런데 자바에서는 char배열이 아닌 String 클래스를 이용해서 문자열을 처리하는 이유는 String 클래스가 char배열에 여러 가지 기능을 추가하여 확장한 것이기 때문이다.

char 배열을 사용하는 것보다 String 클래스를 사용하는 것이 문자열을 다루기 더 편리하다.

> String 클래스는 char 배열에 기능(메서드)을 추가한 것이다.

C 언어에서는 문자열을 char배열로 다루지만, 객체지향언어인 자바에서는 char배열과 그에 관련된 기능들을 함께 묶어서 클래스에 정의한다. 객체지향개념이 나오기 이전의 언어들은 데이터와 기능을 따로 다루었지만, 객체지향언어에서는 데이터와 그에 관련된 기능을 하나의 클래스에 묶어서 다룰 수 있게 한다. 즉, 서로 관련된 것들끼리 데이터와 기능을 구분하지 않고 함께 묶는 것이다.

'기능'은 함수를 의미하며, 메서드는 객체지향 언어에서 '함수' 대신 사용하는 용어일 뿐 함수와 같은 뜻이다. '기능'이나 '함수'대신 '메서드'라는 용어를 사용할 것이다.

char배열과 String 클래스의 한 가지 중요한 차이가 있는데, String 객체(문자열)는 읽을 수만 있을 뿐 내용을 변경할 수 없다는 것이다.

```java
String str = "Java";    // "Java" 이라는 문자열 생성
str = str + "8";        // "Java8" 이라는 새로운 문자열 생성
System.out.println(str);
```

문자열 str의 내용이 변경되는 것 같지만, 문자열은 변경할 수 없으므로 새로운 내용의 문자열이 생성된다.

> [참고] 변경 가능한 문자열을 다루려면, StringBuffer클래스를 사용하면 된다.

#### String 클래스의 주요 메서드

- char charAt(int index) : 문자열에서 해당 위치(index)에 있는 문자를 반환
- int length(): 문자열의 길이 반환
- String substring(int from, int to): 문자열에서 해당 범위(from ~ to)에 있는 문자열을 반환한다.
- boolean equals(String str): 문자열의 내용이 같은지 확인한다. 같으면 결과는 true, 다르면 false가 된다.
- char[] toCharArray(): 문자열을 문자배열(char[])로 변환해서 반환한다.

#### char 배열과 String 클래스의 변환

```java
char[] chArr = { 'A', 'B', 'C' };
String str = new String(chArr);     // char 배열 to String
char[] tmp = str.toCharArray();     // String to char 배열
```

문자열(String)을 모르스(morse) 부호로 변환하는 예제이다. 이전의 16진수를 2진수로 변환하는 예제와 같지만, char배열 대신 String을 사용했다.

String의 문자의 개수는 length()를 통해서 얻을 수 있고, charAt(int i)메서드는 String의 i번째 문자를 반환한다. 그래서 for문의 조건식에 length()를 사용하고 charAt(int i)를 통해서 source에서 한 문자씩 차례대로 읽어올 수 있다.


### 2.4 커맨드 라인을 통해 입력받기

Scanner 클래스의 nextLine()외에도 화면을 통해 사용자로부터 값을 입력받을 수 있는 간단한 방법이 있다. 커맨드라인을 이용한 방법인데, 프로글매을 실행할 때 클래스 이름 뒤에 공백문자로 구분하여 여러 개의 문자열을 프로그램에 전달 할 수 있다.

## 3. 다차원 배열

다차원(multi-dimensional) 배열도 선언하여 사용할 수 있다. 메모리의 용량이 허용하는 한, 차원의 제한은 없지만, 주로 1, 2차원 배열이 사용된다.

### 3.1 2차원 배열의 선언과 인덱스

```java
// 1. 타입[][] 변수이름;
int[][] score;

// 2. 타입 변수이름[][];
int score[][];

// 3. 타입[] 변수이름[];
int[] score[];
```

2차원 배열은 주로 테이블 형태의 데이터를 담는데 사용된다. 

#### 2차원 배열의 인덱스

2차원 배열은 행(row)와 열(column)로 구성되어 있기 때문에 index도 행과 열에 각각 하나씩 존재한다. 

### 3.2 2차원 배열의 초기화

2차원 배열도 괄호{}를 써서 생성과 초기화를 동시에 할 수 있다.

```java
int[][] arr = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 } };
```

### 3.3 가변 배열

자바에서는 2차원 이상의 배열을 '배열의 배열'의 형태로 처리한다는 사실을 이용하면 보다 자유로운 형태의 배열을 구성할 수 이싿.

2차원 이상의 다차원 배열을 생성할 때 전체 배열 차수 중 마지막 차수의 길이를 지정하지 않고, 추후에 각기 다른 길이의 배열을 생성함으로써 고정된 형태가 아닌 보다 유동적인 가변 배열을 구성할 수 있다.

