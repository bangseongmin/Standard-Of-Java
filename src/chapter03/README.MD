# Chapter 03. 연산자

## 1. 연산자(Operator)

연산자는 '연산을 수행하는 기호'를 말한다. 자바에서는 사칙연산(+, -, *, /)을 비롯해서 다양한 연산자들을 제공한다.

### 1.1 연산자와 피연산자

연산자가 연산을 수행하려면 반드시 연산의 대상이 있어야하는데, 이것을 '피연산자(operand)'라고 한다. 피연산자로 상수, 변수 또는 식등을 사용할 수 있다.

> 연산자(operator): 연산을 수행하는 기호(+, -, *, / 등)
> 
> 피연산자(operand): 연산자의 작업 대상(변수, 상수, 리터럴, 수식)

연산자는 피연산자로 연산을 수행하고 나면 항상 결과값을 반환한다.

### 1.2 식과 대입연산자

연산자와 피연산자를 조합하여 계산하고자하는 바를 표현하는 것을 '식(expression)'이라고 한다. 그리고 식을 계산하여 결과를 얻는 것을 '식을 평가(evaluation)한다'고 한다.

### 1.3 연산자의 종류

> 1. 산순연산자
>   
>   +, -, *, /, %, <<, >>
> 
>   사칙 연산과 나머지 연산(%)
> 
> 2. 비교 연산자
> 
>   `>`, `<`, `>=`, `<=`, `==`, `!=`
>   
>   크고 작음과 같고 다름을 비교
> 
> 3. 논리 연산자
> 
>   `&&`, `||`, `!`, `&`, `|`, `^`, `~`,
> 
>   AND와 OR으로 조건으로 연결
> 
> 4. 대입 연산자
>
>   `=`
> 
>   우변의 값을 좌변에 저장
>
> 5. 기타
> 
>   `(type) ? : Instanceof`
> 
>   형변환 연산자, 삼항 연산자, instanceof연산자


#### 피연산자의 개수에 의한 분류

피연산자의 개수로 연산자를 분류하기도 하는데, 피연산자의 개수가 하나면 '단항 연산자', 두 개면 '이항 연산자', 세 개면 '삼항 연산자'라고 분른다.

연산자를 기능별, 피연산자의 개수별로 나누어 분류하는 것은 '연산자의 우선순위' 때문이다.


### 1.4 연산자의 우선순위와 결합규칙

식에 사용된 연산자가 둘 이상인 경우, 연산자의 우선순위에 의해서 연산순서가 결정된다. 곱셈과 나눗셈(*, /)은 덧셈과 뺄셈(+, -)보다 우선순위가 높다는 것은 이미 수학에서 배워서 알고 있을 것이다.

이처럼 연산자의 우선순위는 대부분 상식적인 선에서 해결된다.


#### 연산자의 결합규칙

하나의 식에 같은 우선순위의 연산자들이 여러 개 있는 경우, 어떤 순서로 연산을 수행할까? 우선순위가 같다고 해서 아무거나 먼저 처리하는 것은 아니고 남름대로의 규칙을 가지고 있는데, 그 규칙을 '연산자의 결합규칙'이라고 한다.

연산자의 결합규칙은 연산자마다 다르지만, 대부분 왼쪽에서 오른쪽의 순서로 연산을 수행하고, 단항 연산자와 대입 연산자만 그 반대로, 오른쪽에서 왼쪽의 순서로, 연산을 수행한다.

> 1. 산술 > 비교 > 논리 > 대입. 대입은 제일 마지막에 수행된다.
> 2. 단항(1) > 이항(2) > 삼항(3). 단항 연산자의 우선순위가 이항 연산자보다 높다.
> 3. 단항 연산자와 대입 연산자를 제외한 모든 연산의 진행방향은 왼쪽에서 오른쪽이다.

![연산자의 우선순위와 결합규칙](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/fc17a77f-fbf4-4b8a-9e77-76fecdc0eed6)

### 1.5 산술 변환(usual arithmetic conversion)

이항 연산자는 두 피연산자의 타입이 일치해야 연산이 가능하므로, 피연산자의 타입이 서로 다르다면 연산 전에 형변환 연산자로 타입을 일치시켜야한다.

대부분의 경우, 두 피연산자의 타입 중에서 더 큰 타입으로 일치시큰데, 그 이유는 작은 타입으로 형변환하면 원래의 값이 손실될 가능성이 있기 때문이다. 

연산 전에 피연산자 타입의 일치를 위해 자동 형변환 되는 것을 '산술 변환' 또는 '일반 산술 변환'이라 하며, 이 변환은 이항 연산에서만 아니라 단항 연산에서도 일어난다. 

> 일반 산술 변환이란? 연산 수행 직전에 발생하는 피연산자의 자동 형변환
> 
> 1. 두 피연산자의 타입을 같게 일치시킨다(보다 큰 타입으로 일치)
> 2. 피연산자의 타입이 int보다 작은 타입이면 int로 변환된다.

## 2. 단항 연산자

### 2.1 증감 연산자(++, --)

증감연산자는 피연산자에 저장된 값을 1 증가 또는 감소시킨다. 증감연산자와 피연산자로 정수와 실수가 모두 가능하지만, 상수는 값을 변경할 수 없으므로 가능하지 않다.

### 2.2 부호 연산자(+, -)

부호 연산자는 '-'는 피연산자의 부호를 반대로 변경한 결과를 반환한다. 피연산자가 음수면 양수, 양수면 음수가 연산의 결가가 된다.

## 3. 산술 연산자

## 4. 비교 연산자

비교연산자의 두 피연산자를 비교하는 데 사용되는 연산자다. 주로 조건문과 반복문의 조건식에 사용되며, 연산결과는 오직 true와 false 둘 중 하나이다.

비교 연산자 역시 이항 연산자이므로 비교하는 피연산자의 타입이 서로 다를 경우에는 자료형의 범위가 큰 쪽으로 자동 형변환하여 피연산자의 타입을 일치시킨 후에 비교한다는 점을 주의해야 한다.

### 4.1 대소비교 연산자 (> < <= >=)

두 피연산자의 값의 크기를 비교하는 연산자이다.

### 4.2 등가비교 연산자 (== !=)

두 피연산자의 값이 같은지 또는 다른지를 비교하는 연산자이다. 대소비교 연산자와 달리, 기본현은 물론 참조형, 즉 모든 자료형에 사용할 수 있다.

기본형의 경우 변수에 저장되어있는 값이 같은지를 알 수 있고, 참조형의 경우 객체의 주소값을 저장하기 때문에 두 개의 피연산자(참조변수)가 같은 객체를 가리키고 있는지를 알 수 있다.

기본형과 참조형은 서로 형변환이 가능하지 않기 떄문에 등가비교 연산자로 기본형과 참조형을 비교할 수 없다.

## 5. 논리 연산자

### 5.1 논리 연산자 (&&, ||, !)

> || (OR결합) : 피 연산자 중 어느 한 쪽만 true이면 true를 결과로 얻는다.
> 
> && (AND결합) : 피 연산자 양쪽 모두 true이어야 true를 결과로 얻는다.


### 5.2 비트 연산자 (& | ^ ~ << >>)

비트 연산자는 피연산자를 비트단위로 논리 연산한다. 피연산자를 이진수로 표현했을 때의 각 자리를 아래의 규칙에 따라 연산을 수행하며, 피연사자로 실수는 허용하지 않는다. 정수(문자 포함)만 허용된다.

> `|(OR연산자)` - 피연산자 중 한 쪽의 값이 1이면, 1을 결과로 얻는다. 그 외에는 0을 얻는다.
> 
> `&(AND연산자)` - 피연산자 양 쪽이 모두 1이어야만 1을 결과로 얻는다. 그 외에는 0을 얻는다.
> 
> `^(XOR연산자)` - 피연산자의 값이 서로 다를 때만 1을 결과로 얻는다. 같을 때는 0을 얻는다.

비트 OR 연산자'|'는 주로 특정 비트의 값을 변경할 때 사용한다.

비트 AND 연산자 '&'는 주로 특정 비트의 값을 뽑아낼 때 사용한다.

비트 XOR 연산자 '^'는 두 피연산자의 비트가 다를 때만 1이 된다.





