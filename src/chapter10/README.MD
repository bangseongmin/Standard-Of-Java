# Chatper 10. 날짜와 시간 & 형식화

## 1. 날짜와 시간

### 1.1 Calender와 Date

Date는 날짜와 시간을 다룰 목적으로 JDK1.0부터 제공되어온 클래스이다. JDK1.0이 제공하는 클래스의 수와 기능은 지금과 비교할 수 없을 정도로 비약했다. Date 클랙시 역시 기능이 부족했기 때문에, Calendar라는 새로운 클래스를 그 다음 버전인 JDK1.1부터 제공하기 시작했다. Calendar는 Date보다는 훨씬 나았지만 몇 가지 단점들이 발견되었다. 늦은 감이 있지만 JDK1.8부터 'java.time패키지'로 기존의 단점들을 개선한 새로운 클래스들이 추가되었다.


#### Calendar와 GregorianCalendar

Calendar는 추상클래스이기 때문에 직접 객체를 생성할 수 없고, 메서드를 통해 완전히 구현된 클래스의 인스턴스를 얻어야 한다.

```JAVA
Calendar cal = new Calendar();      // 에러. 추상클래스는 인스턴스를 생성할 수 없다.

Calendar cal = Calendar.getInstance();
```

Calendar를 상속받아 완전히 구현한 클래스로는 GregorianCalendar와 BuddhistCalendar가 있는데 getInstance()는 시스템의 국가와 지역설정을 확인해서 태국인 경우에는 BuddihistCalendar의 인스턴스를 반환하고, 그 외에는 GregorianCalendar의 인스턴스를 반환한다.

GregorianCalendar는 Calendar를 상속받아 오늘날 전세계 공통으로 사용하고 있는 그레고리력에 맞게 구현한 것으로 태국을 제외한 나머지 국가에서는 GregorianCalendar를 사용하면 된다.

인스턴스를 직접 생성해서 사용하지 않고 이처럼 메서드를 통해서 인스턴스를 반환받게 하는 이유는 최소한의 변경으로 프로그램이 종작할 수 있도록 하기 위한 것이다.

```JAVA
class MyApplication {
    public static void main(String[] args) {
        Calendar cal = new GregorianCalendar();     // 경우에 따라 이 부분을 변경해야한다.
    }
}
```

만일 위와 같이 특정 인스턴스를 생성하도록 프로그램이 작성되어 있다면, 다른 종류의 역법(calendar)을 사용하는 국가에서 실행한다던가, 새로운 역법이 추가된다던가 하는 경우, 즉 다른 종류의 인스턴스를 필요로 하는 경우에 MyApplication을 변경해야 하는데 비해 아래와 같이 메서드를 통해서 인스턴스를 얻어오도록 하면 MyApplication을 변경하지 않아도 된다.

```JAVA
class MyApplication {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();     // 경우에 따라 이 부분을 변경해야한다.
    }
}
```

대신 getInstance()의 내용은 달라져야 하겠지만, MyApplication이 변경되지 않아도 된다는 것이 중요하다. getInstance()메서드가 static인 이유는 메서드 내의 코드에서 인스턴스 변수를 사용하거나 인스턴스 메서드를 호출하지 않기 때문이며, 또 다른 이유느 getInstance()가 static이 아니라면 위와 같이 객체를 생성한 다음에 호출해야하는데 Calendar는 추상클래스이기 때문에 객체를 생성할 수 없기 때문이다.


#### Date와 Calendar간의 변환

Calendar가 새로 추가되면서 Date는 대부분의 메서드가 `deprecated`되었으므로 잘 사용되지 않는다. 그럼에도 불구하고 여전히 Date를 필요로 하는 메서드들이 있기 때문에 Calendar를 Date로 또는 그 반대로 변환한 일이 생긴다.

```JAVA
// 1. Calendar to Date
Calendar cal = Calendar.getInstace();

Date d = new Date(cal.getTimeInMillis());   // Date(long date)

// 2. Date to Calendar
Date d = new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(d);
```


## 2. 형식화 클래스

성적처리 프로그램을 작성했을 때 각 점수의 평균을 소수점 2자리로 일정하게 맞춰서 출력하려면 어떻게 해야할까 고민해본 적이 있을 것이다.

평균값에 100을 곱하고 int형으로 형변환한 다음에 다시 100f로 나누고 반올림하려면 Math.round()도 써야하고 등등 생각만 해도 머리가 복잡하다.

날짜를 형식에 맞게 출력하려면 숫자보다 더 복잡해진다. Calendar를 이용해서 년, 월, 일, 시, 분, 초를 각각 별도로 얻어서 조합을 해야 하는 과정을 거쳐야 한다.

자바에서는 이러한 문제들을 쉽게 해결할 수 있는 방법을 제공하는데 그 것이 바로 형식화 클래스이다. 이 클래스는 java.text패키지에 포함되어 있으며 숫자, 날짜, 텍스트 데이터를 일정한 형식에 맞게 표현할 수 있는 방법을 객체지향적으로 설계하여 표준화하였다.

형식화 클래스는 형식화에 사용될 패턴을 정의하는데, 데이터를 정의된 패턴에 맞춰 형식화할 수 있을 뿐만 아니라 역으로 형식화된 데이터에서 원래의 데이터를 얻어낼 수도 있다.

이것은 마치 "123"과 같은 문자열을 Integer.pareseInt()를 사용해서 123이라는 숫자로 변환하는 것과 같은 일이 가능하다는 것을 의미한다. 즉, 형식화된 데이터의 패턴만 정의해주면 복잡한 문자열에서도 subString()을 사용하지 않고도 쉽게 원하는 값을 얻어낼 수 있다는 것이다.

### 2.1 DecimalForamt

형식화 클래스 중에서 숫자를 형식화 하는데 사용되는 것이 DecimalFormat이다. DecimalFormat을 이용하면 숫자 데이터를 정수, 부동소수점, 금액 등의 다양한 형식으로 표현할 수 있으며, 반대로 일정한 형식의 텍스트 데이터를 숫자로 쉽게 변환하는 것도 가능하다.

형식화 클래스에서는 원하는 형식으로 표현 또는 변환하기 위해서 패턴을 정의하는데, 형식화 클래스에서는 패턴을 정의하는 것이 전부라고 해도 과언이 아니다.

아래의 표에 DecimalFormat의 패턴의 작성에 사용되는 기호와 설명, 그리고 자주 사용될 만한 패턴들을 예로 들었다. 여기에 제시된 패턴들만 잘 이해하고 응용하면 DecimalFormat의 패턴을 작성하는 것은 그리 어려운 일이 아닐 것이다.


| 기호 | 의미 | 패턴 | 결과(1234567.89) |
| --- | ---| --- | --- |
| 0 | 10진수(값이 없을 때는 0) | 0, 0.0, 0000000000.0000 | 1234568, 1234567.9, 0001234567.8900 |
| # | 10진수 | #, #.#, ##########.#### | 1234567.89 |
| . | 소수점 | #.# | 1234567.9 |
| - | 음수부호 | #.#-, -#.# | 1234567.9-, -1234567.9 |
| . | 단위 구분자 | #.###.##, #.####.## | 1.234.567.89, 123.4567.89 |
| E | 지수기호 | #E0, 0E0, ##E0, 00E0, ####E0, 0000E0, #.#E0, 0.0E0, 0.000000000E0, 00.0000000E0, #.#########E0, ##.#######E0, ###.#######E0 | .1E7, 1E6, 1.2E6, 12E5, 123.5E4, 1235E3, 1.2E6, 1.2E6, 1.234567890E6, 12.34567890E6, 12.34567890E5, 123.4567890E4, 1.23456789E6, 1.1234569E6, 1.23456789E6 |
| ; | 패턴구분자 | #.###.##+;#.###.##- | 1,234,567,89+(양수일 때), 1,234,567,89-(음수일 때) |
| % | 퍼센트 | #.#% | 123456789% |
| \u2030 | 퍼밀(퍼센트 x 10) | #.#\u2030 | 1234567890% |
| \u00A4 | 통화 | \u00A4 #.#### | ₩ 1,234,568 |
| ' | escape문자 | '#'#.###, "#.### | #1,234,568, '1,234,568 |

DecimalFormat을 사용하는 방법은 간단하다. 먼저 원하는 출력형식의 패턴을 작성하여 DecimalForamt인스턴스를 생성한 다음, 출력하고자 하는 문자열로 format메서드를 호출하면 원하는 패턴에 맞게 변환된 문자열을 얻게 된다. 

```JAVA
double number = 1234567.89;
DecimalFormat df = new DecimalForamt("#.#E0");
String result = df.format(number);
```

DecimalFormatEx1, DecimalFormatEx2를 살펴보자. 패턴을 이용해서 숫자를 다르게 변환하는 예제이다. parse 메서드를 이용하면 기호와 문자가 포함된 문자열을 숫자로 쉽게 변환할 수 있다.

> [참고] Integer.parseInt메서드는 콤마(,)가 포함된 문자열을 숫자로 변환하지 못한다.

parse(String source)는 DecimalFormat의 조상인 NumberForamt에 정의된 메서드이며, 이 메서드의 선언부는 다음과 같다.

```JAVA
public Number parse(String source) throws ParseException
```

Number 클래스는 Integer, Double과 같은 숫자를 저장하는 래퍼 클래스의 조상이며, doubleValue()는 Number에 저장된 값을 double형의 값으로 변환하여 변환한다. 이 외에도 intValue(), floatValue()등의 메서드가 Number클래스에 정의되어 있다.


### 2.2 SimpleDateForamt

Date와 Calendar만으로 날짜 데이터를 원하는 형태로 다양하게 출력하는 것은 불편하고 복잡하다. 그러나 SimpleDateFormat을 사용하면 이러한 문제들이 간단히 해결된다.

> [참고] DateFormat은 추상클래스로 SimpleDateFormat의 조상이다. DateFormat는 추상클래스이므로 인스턴스를 생성하기 위해서는 getDateInstance()와 같은 static메서드를 이용해야 한다. getDateInstance()에 의해서 반환되는 것은 DateFormat을 상속받아 완전하게 구현한 SimpleDateFormat인스턴스이다.


| 기호 | 의미 | 보기 |
| --- | --- | --- |
| G | 연대(BC, AD) | AD |
| y | 년도 | 2006 |
| M | 월(1~12 또는 1월~12월) | 10 또는 10월, OCT |
| w | 년의 몇 번째 주(1~53) | 50 |
| W | 월의 몇 번째 주(1~5) | 4 |
| D | 년의 몇 번째 일(1~356) | 100 |
| d | 월의 몇 번째 일(1~31) | 15 |
| F | 월의 몇 번째 요일(1~5) | 1 |
| E | 요일 | 월 |
| a | 오전/오후(AM, PM) | PM |
| H | 시간(0~23) | 20 |
| k | 시간(1~24) | 13 |
| K | 시간(0~11) | 10 |
| h | 시간(1~12) | 11 |
| m | 분(0~59) | 35 |
| s | 초(0~59) | 55 |
| S | 천분의 일초(0~999) | 253 |
| z | Time zone(General time zone) | GMT+9:00 |
| Z | Time zone(RFC 822 time zone) | +0900 |
| ' | escape문자(특수문자를 표현하는데 사용) | 없음 |


SimpleDateForamt을 사용하는 방법은 간단하다. 먼저 원하는 출력형식의 패턴을 작성하여 SimpleDateFormat인스턴스를 생성한 다음, 출력하고자 하는 Date 인스턴스를 가지고 format(Date d)를 호출하면 지정한 출력형식에 맞게 변환된 문자열을 얻게 된다.

```JAVA
Date today = new Date();
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

// 오늘 날짜를 yyyy-MM-dd형태로 변환하여 반환한다.
String result = df.format(today);
```

DateFormatEx2는 Date 인스턴스만 format메서드에 사용될 수 있기 때문에 Calendar인스턴스를 Date인스턴스로 변환하는 방법을 보여주는 예제이다.

> [참고] Date인스턴스를 Calendar인스턴스로 변환할 때는 Calendar클래스의 setTime()을 사용하면 된다.

DateFormatEx3는 parse(String source)를 사용하여 날짜 데이터의 출력형식을 변환하는 방법을 보여주는 예제입니다. Integer의 parseInt()가 문자열을 정수로 변환하는 것처럼 SimpleDateFormat의 parse(String source)는 문자열 source을 날짜 Date인스턴스로 변환해주기 때문에 매우 유용하게 쓰일 수 있다.

예를 들어 사용자로부터 날짜 데이터를 문자열로 입력받을 때, 입력받은 문자열을 날짜로 인식하기 위해서는 substring메서드를 이용해서 년, 월, 일을 뽑아내야 하는데 parse(String source)은 이러한 수고를 덜어 준다.

> [참고] parse(String source)는 SimpleDateFormat의 조상인 DateFormat에 정의되어 있다.
> [참고] 지정된 형식과 입력된 형식이 일치하지 않는 경우에는 예외가 발생하므로 적절한 예외처리가 필요하다.


### 2.3 ChoiceFormat

ChoiceFormat은 특정 범위에 속하는 값을 문자열로 변환해준다. 연속적 또는 불연속적인 범위의 값들을 처리하는 데 있어서 if문이나 switch문은 적절하지 못한 경우가 많다.

이럴 때 ChoiceFormat을 잘 사용하면 복잡하게 처리될 수밖에 없었던 코드를 간단하고 직관적으로 만들 수 있다.

예제 ChoiceFormatEx1를 보면 두 개의 배열이 사용되었는데 하나(limits)는 범위의 경계값을 저장하는데 사용하였고, 또 하나(grades)는 범위에 포함된 값을 치환할 문자열을 저장하는데 사용되었다.

경계값은 double형으로 반드시 모두 오름차순으로 정렬되어 있어야 하며, 치환될 문자열의 개수는 경계값에 의해 정의된 범위의 개수와 일치해야한다. 그렇지 않으면 IllegalArgumentException이 발생한다.

예제에서는 4개의 경계값에 의해 '60-69', '70-79', '80-89', '90-'의 범위가 정의되었다.

예제 ChoiceFormatEx2는 이전 예제를 패턴을 사용하도록 변경한 것이다. 이처럼 배열 대신 패턴을 사용해서 보다 간결하게 처리할 수도 있다. 패턴은 구분자로 '#'와 '<' 두 가지를 제공하는데 'limit#value'의 형태로 사용한다. '#'는 경계값을 범위에 포함시키지만, '<'는 포함시키지 않는다. 위의 결과에서 90은 A지만, 80가 아닌, C인 것에 주목하자.

> [참고] 경계값을 포함하지 않는다고 해서 `61#D|71#C|81#B|91#A`와 같이 하는 것보다는 `60<D|70<C|80<B|90<A`과 같이 하는 것이 낫다.


### 2.4 MessageFormat

MessageFormat은 데이터를 정해진 양식에 맞게 출력할 수 있도록 도와준다. 데이터가 들어간 자리를 마련해 놓은 양식을 미리 작성하고 프로그램을 이용해서 다수의 데이터를 같은 양식으로 출력할 때 사용하면 좋다.

예를 들어 고객들에게 보낼 안내문을 출력할 때 같은 안내문 양식에 받는 사람의 이름과 같은 데이터만 달라지도록 출력할 때, 또는 하나의 데이터를 다양한 양식으로 출력할 때 사용한다. 그리고 SimpleDateFormat의 parse를 이용하면 지정된 양식에서 필요한 데이터만을 손쉽게 추출해 낼 수도 있다.

예제 MessageFormatEx1을 보면 MessageFormat에 사용할 양식인 문자열 msg를 작성할 때 `{숫자}`로 표시된 부분이 데이터가 출력될 자리이다. 이 자리는 순차적일 필요는 없고 여러 번 반복해서 사용할 수도 있다. 여기에 사용되는 순자는 배열처럼 인덱스가 0부터 시작하며 양식에 들어갈 데이터는 객체배열인 arguments에 지정되어 있음을 알 수 있다.

객체배열이기 때문에 String이외에도 다른 객체들이 지정될 수 있으며, 이 경우 보다 세부적인 옵션들이 사용될 수 있다.

예제 MessageFormatEx2를 보면 이전 예제를 보다 발전시키셔 반복문으로 하나 이상의 데이터 집합을 출력하는 예제이다. 다수의 데이터를 Database에 저장하기 위한 Insert문을 변환하는 경우 등에 사용하면 좋을 것이다.

홑따옴표(')는 MessageFormat의 양식에 escape문자로 사용되기 때문에 문자열 msg내에서 홑따옴표를 사용하려면 홑따옴표를 연속으로 두 번 사용해야 한다.

MessageFormatEx2는 데이터를 양식에 넣어서 출력했지만 MessageFormatEx3에서는 parse(String source)를 이용해서 출력된 데이터로부터 필요한 데이터만을 뽑아내는 방법을 보여 준다.


이전의 예제에서는 데이터를 객체배열에 직접 초기화하였는데, MessageFormatEx4와 같이 하면 데이터가 바뀔 때마다 매번 배열을 변경해야하고 그리고는 다시 컴파일을 해줘야하므로 불편하다.

이러한 불편함을 없애기 위해 이번에 Scanner를 통해 파일로부터 데이터를 라인별로 읽어서 처리하도록 변경했다. 이렇게 파일로부터 데이터를 제공받으면 데이터가 변경되어도 다시 컴파일을 하지 않아도 된다.

> [참고] 실행 시에 입력받을 데이터가 저장된 파일명도 지정하도록 변경하면, 파일의 이름이 바뀌어도 다시 컴파일하지 않아도 되므로 더 편리할 것이다.


## 3. java.time 패키지

Java의 탄생부터 지금까지 날짜와 시간을 다루는데 사용했던, Date와 Calendar가 가지고 있던 단점들을 해소하기 위해 JDK1.8부터 'java.time패키지'가 추가되었다. 이 패키지는 다음과 같이 4개의 하위 패키지를 가지고 있다.


| 패키지 | 설명 |
| --- | --- |
| java.time | 날짜와 시간을 다루는데 필요한 핵심 클래스들을 제공 |
| java.time.chrono | 표준(ISO)이 아닌 달력 시스템을 위한 클래스들을 제공 |
| java.time.format | 날짜와 시간을 파싱하고, 형식화하기 위한 클래스들을 제공 |
| java.time.temporal | 날짜와 시간의 필드(field)와 단위(unit)를 위한 클래스들을 제공 |
| java.time.zone | 시간대(time-zone)와 관련된 클래스들을 제공 |


패키지의 개수는 많지만 실제로 사용되는 핵심적인 클래스들은 그리 많지 않다. 위의 패키지들에 속한 클래스들의 가장 큰 특징은 String 클래스처럼 '불변(immutable)'이라는 것이다. 그래서 날짜나 시간을 변경하는 메서드들은 기존의 객체를 변경하는 대신 항상 변경된 새로운 객체를 반환한다. 기존 Calendar클래스는 변경 가능하므로, 멀티 쓰레드 환경에서 안전하지 못하다.

멀티 쓰레드 환경에서는 동시에 여러 쓰레드가 같은 객체에 접근할 수 있기 떄문에, 변경 가능한 객체는 데이터가 잘못될 가능성이 있으며, 이를 쓰레드에 안전(thread-safe)하지 않다고 한다.

새로운 패키지가 도입되었음에도 불구하고, 앞으로도 기존에 작성된 프로그램과의 호환성 때문에 Date와 Calendar는 여전히 사용될 것이다.


### 3.1 java.time패키지의 핵심 클래스

날짜와 시간을 하나로 표현하는 Calendar클래스와 달리, java.time패키지에서는 날짜와 시간을 별도의 클래스로 분리해 놓았다. 시간을 표현할 때는 LocalTime 클래스를 사용하고, 날짜를 표현할 때는 LocalDate 클래스를 사용한다. 그리고 날짜와 시간이 모두 필요할때는 LocalDateTime 클래스를 사용하면 된다.

> LocalDate(날짜) + LocalTime(시간) -> LocalDateTime(날짜 & 시간)

여기에 시간대(time-zone)까지 다뤄야 한다면, ZoneDateTime 클래스를 사용하자.

> LocalDateTime + 시간대 -> ZonedDateTime


Calendar는 ZonedDateTime처럼, 날짜와 시간 그리고 시간대까지 모두 가지고 있다. Date와 유사한 클래스로는 Instant가 있는데, 이 클래스는 날짜와 시간을 초 단위(정확히는 나노초)로 표현한다. 날짜와 시간을 초단위로 표현한 값을 타음스탬프(time-stamp)라고 부르는데, 이 값은 날짜와 시간을 하나의 정수로 표현할 수 있으므로 날짜와 시간의 차이를 계산하거나 순서를 비교하는데 유리해서 데이터베이스에 많이 사용된다.

이외에도 날짜를 더 세부적으로 다룰 수 있는 Year, YearMonth, MonthDay와 같은 클래스도 있다.


#### Period와 Duration

날짜와 시간의 간격을 표현하기 위한 클래스도 있는데, Period는 두 날짜간의 차이를 표현하기 위한 것이고, Duration은 시간의 차이를 표현하기 위한 것이다.

> 날짜 - 날짜 = Period
> 시간 - 시간 = Duration

#### 객체 생성하기 - now(), of()

java.time 패키지에 속한 클래스의 객체를 생성하는 가장 기본적인 방법은 now()와 of()를 사용하는 것이다. now()는 현재 날짜와 시간을 저장하는 객체를 생성한다.

```JAVA
LocalDate date = LocalDate.now();                   // YYYY-MM-DD
LocalTime time = LocalTime.now();                   // HH:mm:ss.sss
LocalDateTime dateTime = LocalDateTime.now();       // YYYY-MM-DD HH:mm:ss.sss
ZonedDateTime dateTimeInkr = ZonedDateTime.now();   // YYYY-MM-DD HH:mm:ss.sss[Asia/Seoul]
```

of()는 단순히 해당 필드의 값을 순서대로 지정해 주기만 하면 된다. 각 클래스마다 다양한 종류의 of()가 정의되어 있다.

```JAVA
LocalDate date = LocalDate.of(2024, 1, 1);                      // 2024년 1월 1일
LocalTime time = LocalTime.of(23, 59, 59);                      // 23시 59분 59초
LocalDateTime datetime = LocalDateTime.of(date, time);          // 2024년 1월 1일 23시 59분 59초
ZonedDateTime zDateTime = ZonedDateTime.of(datetime, ZoneId.of("Asia/Seoul"));
```

#### Temporal과 TemporalAmount

LocalDate, LocalTime, LocalDateTime, ZonedDateTime 등 날짜와 시간을 표현하기 위한 클래스들은 모두 Temporal, TemporalAccessor, TemporalAdjustor인터페이스를 구현했고, Duration과 Period는 TemporalAmount인터페이스를 구현하였다. 앞으로 소개할 메서드 중에서 매개변수의 타입이 Temporal로 시작하는 것들이 자주 등장할텐데 대부분 날짜와 시간을 위한 것이므로, TemporalAmount인지 아닌지만 확인하면 된다.

> [참고] 'temporal'과 'chrono'의 의미가 모두 시간(time)인데도, 'time'대신 굳이 이런 어려운 용어를 쓴 이유는 시간(시분초)과 더 큰 개념의 시간(년월시분초)을 구분하기 위해서이다.


> **Temporal, TemporalAccessor, TemporalAdjuster를 구현한 클래스**
> - LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Instant 등
>
> **TemporalAmount를 구현한 클래스**
> - Period, Duration


#### TemporalUnit과 TemporalField

날짜와 시간의 단위를 정의해 놓은 것이 TemporalUnit 인터페이스이고, 이 인터페이슬 구현한 것이 열겨형 ChronoUnit이다. 그리고 TemporalField는 년, 월, 일 등 날짜와 시간의 필드를 정의해 놓은 것으로, 열겨형 ChronoFiled가 이 인터페이스를 구현하였다.

> [참고] 열거형(enumeration)은 서로 관련된 상수를 묶어서 정의해 놓은 것이다.


```JAVA
LocalTime now = LocalTime.now();        // 현재 시간
int minute = now.getMinute();           // 현재 시간의 분
int minute = not.get(ChronoField.MINUTE_OF_HOUR);
```

날짜와 시간에서 특정 필드의 값만을 얻을 때는 get()이나, get으로 시작하는 이름의 메서드를 이용한다. 그리고 아래와 같이 특정 날짜와 시간에서 지정된 단위의 값을 더하거나 뺄 때는 plus() 또는 minus()에 값과 함께 열거형 ChronoUnit을 사용한다.

```JAVA
LocalDate today = LocalDate.now();
LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);        // 1일 추가
LocalDate tomorrow = today.plusDays(1);                     // 위와 동일
```

참고로 get()과 plus()의 정의는 아래와 같다.

```JAVA
int get(TemporalField field);
LocalDate plus(long amountToAdd, TemporalUnit unit)
```

특정 TemporalField나 TemporalUnit을 사용할 수 있는지 확인하는 메서드는 다음과 같다. 이 메서드들은 날짜와 시간을 표현하는 데 사용하는 모든 클래스에 포함되어 있다.

```JAVA
boolean isSupported(TemporalUnit unit);     // Temporal에 정의
boolean isSupported(TemporalUnit unit);     // TemporalAccessor에 정의
```


### 3.2 LocalDate와 LocalTime

LocalDate와 LocalTime은 java.time 패키지의 가장 기본이 되는 클래스이며, 나머지 클래스들은 이들의 확장이므로 이 두 클래스만 잘 이해하고 나면 나머지는 아주 쉬워진다.

객체를 생성하는 방법은 현재의 날짜와 시간을 LocalDate와 LocalTime으로 각각 반환하는 now()와 지정된 날짜와 시간으로 LocalDateTime와 LocalTime객체를 생성하는 of()가 있다. 둘다 static 메서드이다.

```JAVA
LocalDate today = LocalDate.now();      // 오늘의 날짜
LocalTime now = LocalTime.now();        // 현재 시간

LocalDate birthDate = LocalDate.of(1999, 12, 31);
LocalTime birthTime = Localtime.of(23, 59, 59);
```

of()는 다음과 같이 여러 가지 버전이 제공된다.

```JAVA
public static LocalDate of(int year, Month month, int dayOfMonth)
public static LocalDate of(int year, int month, int dayOfMonth)

public static LocalTime of(int hour, int minute)
public static LocalTime of(int hour, int minute, int second)
public static LocalTime of(int hour, int minute, int second, int nanoOfSecond)
```

참고로 다음과 같이 일 단위나 초 단위로도 지정할 수 있다.


#### 특정 필드의 값 가져오기 - get(), getXXX()

LocalDate와 LocalTime의 객체에서 특정 필드의 값을 가져올 때는 아래의 표에 있는 메서드를 사용한다. 주의할 점은 Calendar와 달리 월(month)의 범위가 1~12이고, 요일의 범위는(1~7)이라는 것이다.

> [참고] Calendar는 1월을 0으로 표현하고, 일요일은 1, 월요일은 2 ... 토요일은 7로 표현한다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/aae2afac-a43b-437c-b792-b756efdd9af6)

위의 표에 소개된 메서드 외에도 get()과 getLong()이 있는데, 원하는 필드를 직접 지정할 수 있다. 대부분의 필드는 int 타입의 범위에 속하지만, 몇몇 필드는 int 타입의 범위를 넘을 수 있다. 그럴 때는 get()대신 getLong()을 사용해야 한다. getLong()을 사용해야 하는 필드는 아래 표에서 이름 옆에 '*'표시가 되어 있다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/cda44761-80d9-4320-90df-61e71d40c056)

이 목록은 ChronoFiled에 정의된 모든 상수를 나열한 것일 뿐, 사용할 수 있는 필드는 클래스마다 다르다. 예를 들어 LocalDate는 날짜를 표현하기 위한 것이므로, MINUTE_OF_HOUR와 같이 시간에 관련된 필드는 사용할 수 없다.

> [참고] 만일 해당 클래스가 지원하지 않는 필드를 사용하면, UnsupportedTemporalTypeException이 발생한다.


```JAVA
LocalDate today = LocalDate.now();                              // 오늘의 날짜
System.out.println(today.get(ChronoFiled.MINUTE_OF_HOUR));      // 예외 발생
```

### 필드의 값 변경하기 - with(), plus(), minus()

날짜와 시간에서 특정 필드 값을 변경하려면, 다음과 같이 with로 시작하는 메서드를 사용하면 된다.

```JAVA
LocalDate withYear(int year);
LocalDate withMonth(int month);
LocalDate withDayOfMonth(int dayOfMonth);
LocalDate withDayOfYear(int dayOfYear);

LocalTime withHour(int hour);
LocalTime withMinute(int minute);
LocalTime withSecond(int second);
LocalTime withNano(int nanoSecond);
```

with()를 사용하면, 원하는 필드를 직접 지정할 수 있다. 위의 메서드들은 모두 with()로 작성된 것이라는 것을 짐작할 수 있다.

```JAVA
LocalDate with(TemporalField field, Long newValue)
```

앞서 언급한 것과 같이 필드를 변경하는 메서드들은 항상 새로운 객체를 생성해서 반환하므로 아래와 같이 대입 연산자를 같이 사용해야한다는 것을 잊으면 안 된다.

```JAVA
date = date.withYear(2000);
time = time.withHour(12);
```

이 외에도 특정 필드에 값을 더하거나 빼는 plus()와 minus()가 있는데, 아래에는 plus()만 표시하였다.

```JAVA
LocalTime plus(TemporalAmount amountToAdd);
LocalTime plus(long amountToAdd, TemporalUnit unit);
LocalDate plus(TemporalAmount amountToAdd);
LocalDate plus(long amountToAdd, TemporalUnit unit);
```

plus()로 만든 다음과 같은 메서드들도 있다.

```JAVA
LocalDate plusYears(long yearsToAdd)
LocalDate plusMonths(long monthsToAdd)
LocalDate plusDays(long daysToAdd)
LocalDate plusWeeks(long weeksToAdd)

LocalTime plusHours(long hour)
LocalTime plusMinutes(long minute)
LocalTime plusSeconds(long second)
LocalTime plusNanos(long nanas)
```

그리고 LocalTime의 truncatedTo()는 지정된 것보다 작은 단위의 필드를 0으로 만든다.

```JAVA
LocalTime time = LocalTime.of(12, 34, 56);
time = time.truncatedTo(ChronoUnit.HOURS);
System.out.println(time);
```

LocalTime과 달리 LocalDate에는 truncatedTo()가 없는데, 그 이유는 LocalDate의 필드인 년, 월, 일은 0이 될 수 없기 때문이다. 그리고 이 메서드의 매개변수로는 아래의 표중에서 기간과 관련된 필드만 사용가능하다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/c5b4c410-89e9-41ba-871f-e1cb052be3dc)


#### 날짜와 시간의 비교 - isAfter(), isBefore(), isEquals()

LocalDate와 LocalTime도 compareTo()가 적절히 오버라이딩되어 있어서, 아래와 같이 compareTo()로 비교할 수 있다.

```JAVA
int result = date1.compareTo(date2);
```

그런데도 보다 편리하게 비교할 수 있는 메서드들이 추가로 제공된다.

```JAVA
boolean isAfter(ChronoLocalDate other)
boolean isBefore(ChronoLocalDate other)
boolean isEqual(ChronoLocalDate other)
```

equals()가 있는데도, isEquals()를 제공하느 이유는 연표(chronology)가 다른 두 날짜를 비교하기 위해서이다. 모든 필드가 일치해야하는 equals()와 달리 isEqual()은 오직 날짜만 비교한다. 그래서 대부분의 경우 equals()와 isEquals()의 결과는 같다.

```JAVA
LocalDate kDate = LocalDate.of(1999, 12, 31);
JapaneseDate jDate = JapaneseDate.of(1999, 12, 31);

System.out.println(kDate.equals(jDate));        // false, 연대가 다름
System.out.println(kDate.isEqual(jDate));       // true
```


### 3.3 Instant

Instant는 에포크 타임(EPOCH TIME, 1970-01-01 00:00:00 UTC)부터 경과된 시간을 나노초 단위로 표현한다. 사람에게 불편하지만, 단일 진법으로만 다루기 때문에 계산하기 쉽다. 사람이 사용하는 날짜와 시간은 여러 진법이 섞여있어서 계산하기 어렵다.

```JAVA
Instant now = Instant.now();
Instant now2 = Instant.ofEpochSecond(now.getEpochSecond());
Instant now3 = Instant.ofEpochSecond(now.getEpochSecond(), now.getNano());
```

Instant를 생성할 때는 위와 같이 now()와 ofEpochSecond()를 사용한다. 그리고 필드에 저장된 값을 가져올 때는 다음과 같이 한다.

```JAVA
long epochSec = now.getEpochSecond();
int nano = now.getNano();
```

위의 코드에서 짐작할 수 있듯이, Instant는 시간을 초 단위와 나노초 단위로 나누어 저장한다. 오라클 데이터베이스의 타임스탬프(timestamp)처럼 밀리초 단위의 EPOCH TIME을 필요로 하는 경우를 위해 toEpochmilli()가 정의되어 있다.

```JAVA
long toEpochMilli();
```

Instant는 항상 UTC(+00:00)를 기준으로 하기 때문에, LocalTime과 차이가 있을 수 있다. 예를 들어 한국어 시간대가 '09:00'이므로 Instant와 LocalTime간에는 9시간의 차이가 있다. 시간대를 고려해야하는 경우 OffsetDateTime을 사용하는 것이 더 나은 선택일 수 있다.

UTC는 'Coordinated Universal Time'의 약어로 '세계 협정시'이라고 하며, 1972년 1월 1일부터 시행된 국제 표준시이다. 이전에 사용되던 GMT(Greenwich Mean Time)와 UTC는 거의 같지만, UTC가 좀 더 정확하다.

> [참고] CUT가 아니라 UTC가 된 이유는 영어와 프랑스어 표기의 중간 형태를 선택했기 때문이다.

#### Instant와 Date간의 변환

Instant는 기존의 java.util.Date를 대체하기 위한 것이며, JDK1.8부터 Date에 Instant로 변환할 수 있는 새로운 메서드가 추가되었다.


### 3.4 LocalDateTime과 ZonedDateTime

LocalDate와 LocalTime을 합쳐 놓은 것이 LocalDateTime이고, LocalDateTime에 시간대(time zone)를 추가한 것이 ZonedDateTime이다.

> LocalDate + LocalTime -> LocalDateTime
> LocalDateTime + 시간대 -> ZonedDateTime

기본적인 것들은 이미 LocalDate와 LocalTime에서 설명했기 때문에, 추가적인 부분에 대해서만 배우면 된다.

#### LocalDate와 LocalTime으로 LocalDateTime 만들기

LocalDate와 LocalTime으로 합쳐서 하나의 LocalDateTime을 만들 수 있다. 다음은 LocalDateTime을 만들 수 있는 다양한 방법을 보여준다.

```JAVA
LocalDate date = LocalDate.of(2015, 12, 31);
LocalTime time = LocalTime.of(12, 34, 46);

LocalDateTime dt = LocalDateTime.of(date, time);
LocalDateTime dt2 = date.atTime(time);
LocalDateTime dt3 = time.atDate(date);
LocalDateTime dt4 = date.atTime(12, 34, 56);
LocalDateTime dt5 = time.atDate(LocalDate.of(2015, 12, 31));
LocalDateTime dt6 = date.asStartOfDate();       // dt6 = date.atTime(0, 0, 0);
```

물론 LocalDateTime에도 날짜와 시간을 직접 지정할 수 있는 다양한 버전의 of와 now()가 정의되어 있다.

```JAVA
LocalDateTime dateTime = LocalDateTime.of(2015, 12, 31, 12, 34, 56);
LocalDateTime today = LocalDateTime.now();
```

#### LocalDateTime의 변환

반대로 LocalDateTime을 LocalDate 또는 LocalTime으로 변환할 수 있다.

```JAVA
LocalDateTime dt = LocalDateTime.of(2015, 12, 31, 12, 34, 56);
LocalDate date = dt.toLocalDate();
LocalTime time = dt.toLocalTime();
```

#### LocalDateTime으로 ZoneDateTime 만들기

LocalDateTime에 시간대(time-zone)를 추가하면, ZonedDateTime이 된다. 기존의 TimeZone클래스로 시간대를 다뤘지만 새로운 시간 패키지에는 ZoneId라는 클래스를 사용한다. ZondId는 일광 절약시간(DST, Daylight Saving Time)을 자동적으로 처리해주므로 더 편리하다.

LocalDate에 시간 정보를 추가하는 atTime()을 LocalDateTime을 얻을 수 있는 것처럼, LocalDateTime에 atZone()으로 시간대 정보를 추가하면, ZonedDateTime을 얻을 수 있다.

> [참고] 사용가능한 ZonedId의 목록은 ZonedId.getAvailableZoneIds()로 얻을 수 있다.


#### ZoneOffset

UTC로부터 얼마만큼 떨어져 있는지를 ZoneOffSet으로 표현한다. 서울은 '+9'이다. 즉, UTC 보다 9시간(32400초 = 60x60x9초)이 빠르다.

```JAVA
ZoneOffSet krOffset = ZonedDateTime.now().getOffset();
// ZoneOffset krOffset = ZoneOffset.of("+9");
int krOffsetInSec = krOffset.get(ChronoFiled.OFFSET_SECONDS);
```

#### OffsetDateTime

ZonedDateTime은 ZonedId로 구역을 표현하는데, ZoneId가 아닌 ZoneOffset을 사용하는 것이 OffsetDateTime이다. ZoneId는 일광절약시간처럼 시간대와 관련된 규칙들을 포함하고 있는데, ZoneOffset은 단지 시간대를 시간의 차이로만 구분한다. 컴퓨터에게 일광절약시간처럼 계절별로 시간을 더했다 뺐다하는 것과 같은 행위는 위험하기 그지없다. 아무런 변화 없이 일관된 시간체계를 유지하는 것이 더 안전한다. 같은 지역 내의 컴퓨터 간에 데이터를 주고받을 때, 전송시간을 표현하기에 LocalDateTime이면 충분하겠지만, 서로 다른 시간대에 존재하는 컴퓨터간의 통신에는 OffsetDateTime이 필요하다.

```JAVA
ZonedDateTime zdt = ZondedDateTime.of(date, time, zid);
OffsetDateTime odt = OffsetDateTime.of(date, time, krOffset);
OffsetDateTime odt = zdt.toOffsetDateTime();
```

OffsetDateTime은 ZonedDateTime처럼, LocalDate와 LocalTime에 ZoneOffset을 더하거나, ZonedDateTime에 toOffsetDateTime()을 호출행서 얻을수도 있다.

#### ZonedDateTIme의 변환

ZonedDateTime도 LocalDateTime처럼 날짜와 시간에 관련된 다른 클래스로 변환하는 메서드들을 가지고 있다.

```JAVA
LocalDate toLocalDate()
LocalTime toLocalTime()
LocalDateTime toLocalDateTime()
OffsetDateTime toOffsetDateTime()
long toEpochSecond()
Instant toInstant()
```

GregorianCalendar와 가장 유사한 것이 ZonedDateTime이다. GregorianCalendar와 ZonedDateTime간의 변환방법만 알면, 그 다음엔 위에 나열ㄷ한 메서드를 이용해서 다른 날짜와 시간 클래스들로 변환할 수 있다.


### 3.5 TemporalAdjusters

앞서 plus(), minus()와 같은 메서드로 날짜와 시간을 계산할 수 있다는 것을 배웠다. 지난 주 토요일이 며치인지, 또는 이번 달의 3번째 금요일은 며칠인지와 같은 날짜계산을 plus(), minus()로 하기엔 좀 불편하다. 그래서 자주 쓰일만한 날짜 계산들을 대신 해주는 메서드를 정의해놓은 것이 TemporalAdjusters클래스이다. 

```JAVA
LocalDate today = LocalDate.now();
LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.Monday));
```

위의 코드는 다음 주 월요일의 날짜를 계산할 때 TemporalAdjusters에 정의된 next()를 사용하였다. 이 외에도 다음과 같이 많은 유용한 메서드들이 TemporalAdjusters에 정의되어 있다.

| 메서드 | 설명 |
| --- | --- |
| firstDayOfNextYear() | 다음 해의 첫 날 |
| firstDayOfNextMonth() | 다음 달의 첫 날 |
| firstDayOfYear() | 올 해의 첫 날 |
| firstDayOfMonth() | 이번 달의 첫 날 |
| lastDayOfYear() | 올 해의 마지막 날 |
| lastDayOfMonth() | 이번 달의 마지막 날 |
| firstInMonth(DayOfWeek dayOfWeek) | 이번 달의 첫 번쨰 ? 요일 |
| lastInMonth(DayOfWeek dayOfWeek) | 이번 달의 마지막 ? 요일 |
| previous(DayOfWeek dayOfWeek) | 지난 ?요일(당일 미포함) |
| previousOrSame(DayOfWeek dayOfWeek) | 지난 ?요일(당일 포함) |
| next(DayOfWeek dayOfWeek) | 다음 ?요일(당일 미포함) |
| nextOrSame(DayOfWeek dayOfWeek) | 다음 ?요일(당일 포함) |
| dayOfWeekInMonth(int ordinal, DayOfWeek dayOfWeek) | 이번 달의 n번째 ? 요일 |

#### TemporalAdjuster 직접 구현하기

보통은 TemporalAdjusters에 정의된 메서드로 충분하겠지만, 필요하면 자주 사용되는 날짜계산을 해주는 메서드를 직접 만들 수도 있다. LocalDate의 with()는 다음과 같이 정의되어있으며, TemporalAdjuster인터페이스를 구현한 클래스의 객체를 매개변수로 제공해야한다.

```JAVA
LocalDate with(TemporalAdjuster adjuster)
```

with()는 LocalTime, LocalDateTime, ZonedDateTime, Instant 등 대부분의 날짜와 시간에 관련된 클래스에 포함되어 있다.

TemporalAdjustor 인터페이스는 다음과 같이 추상 메서드 하나만 정의되어 있으며, 이 메서드만 구현하면 된다.

```JAVA
@FunctionalInterface
public interface TemporalAdjuster {
    Temporal adjustInto(Temporal temporal);
}
```

실제로 구현해야하는 것은 adjustInto()지만, 우리가 TemporalAdjuster와 같이 사용해야 하는 메서드는 with()이다. with()와 adjustInto() 중에서 어느 쪽을 사용해도 되지만, adjustInto()는 내부적으로만 사용할 의도로 작성된 것이기 때문에, with()를 사용하도록 하자.

앞서 언급한 것과 같이 날짜와 시간에 관련된 대부분의 클래스는 Temporal인터페이스를 구현하였으므로 adjustInto()의 매개변수가 될 수 있다.

예를 들어, 특정 날짜로부터 2일 후의 날짜를 게산하는 DayAfterTomrrow는 다음과 같이 작성할 수 있다.

```JAVA
class DayAfterTomorrow implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.plust(2, ChronoUnit.DAYS);      // 2일을 더한다.
    }
}
```

### 3.6 Period와 Duration

#### between()

두 날짜 date1과 date2의 차이를 나타내는 Period는 between()으로 얻을 수 있다.

```JAVA
LocalDate date1 = LocalDate.of(2024, 1, 1);
LocalDate date2 = LocalDate.of(2025, 1, 1);

Period pe = Period.between(date1, date2);
```

date1이 date2보다 날짜 상으로 이전이면 양수로, 이후면 음수로 Period에 저장된다.
그리고 시간차이를 구할 때는 Duration을 사용한다는 것을 제외하고는 Period와 똑같다.

```JAVA
LocalTime time1 = LocalTime.of(00, 00, 00);
LocalTime time2 = LocalTime.of(12, 34, 56);

Duration du = Duration.between(date1, date2);
```

Period, Duration에서 특정 필드의 값을 얻을 때는 get()을 사용한다.

```JAVA
long year = pe.get(ChronoUnit.YEARS);           // int getYears()
long month = pe.get(ChronoUnit.MONTHS);         // int getMonths()
long day = pe.get(ChronoUnit.DAYS);             // int getDays()

long sec = du.get(ChronoUnit.SECONDS);          // long getSeconds()
int nano = du.get(ChronoUnit.NANOS);            // int getNano()
```

그런데, Period와 달리 Duration에는 getHours(), getMinites() 같은 메서드가 없다. 믿기 힘든 사실이니 적접 확인해 보자. getUnits()라는 메서드로 get()에 사용할 수 있는 ChronoUnit의 종류를 확인할 수 있다.

```JAVA
System.out.println(pe.getUnits());          // [Years, Months, Days]
System.out.println(du.getUnits());          // [Seconds, Nanos]
```

실망스럽게도 Duration은 Chrono.SECONDS와 Chrono.NANOS 밖에 사용할 수 없다는 결과가 나왔다. 좀 불편하겠지만 어쩔 수 없이 다음과 같이 직접 계산해 보았다.

```JAVA
long hour = du.getSeconds() / 3600;
long min = (du.getSeconds() - hour * 3600) / 60;
long sec = (du.getSeconds() - hour * 3600 - min * 60) % 60;
int nano = du.getNano();
```

이 방법은 어쩐지 좀 불편하고 불안하기도 하다. 좀더 안전하고 간단한 방법이 없을까? 고민 끝에 Duration을 LocalTime으로 변환한 다음에, LocalTime이 가지고 있는 get메서드들을 사용하면 더 간단하다는 것을 알아냈다.

```JAVA
LocalTime tmpTime = LocalTime.of(0, 0).plusSeconds(du.getSeconds());

int hour = tmpTime.getHour();
int min = tmpTime.getMinute();
int sec = tmpTime.getSecond();
int nano = du.getNano();
```

#### between()과 until()

until()은 between()과 거의 같은 일을 한다. between()은 static 메서드이고, until()은 인스턴스 메서드라는 차이가 있다.

```JAVA
Period pe = Period.between(today, myBirthDay);
Period pe = today.until(myBirthDay);
long today = today.until(myBirthDay, ChronoUnit.DAYS);
```

Period는 년월일을 분리해서 저장하기 때문에, D-day를 구하려는 경우에는 두 개의 매개변수를 받는 until()을 사용하는 것이 낫다. 날짜가 아닌 시간에도 until()을 사용할 수 있지만, Duration을 반환하는 until()은 없다.


#### of(), with()

Periood에는 of(), ofYears(), ofMonths(), ofWeeks(), ofDays()가 있고, Duration에는 of(), ofDays(), ofHours(), ofMinutes(), ofSeconds() 등이 있다. 사용법은 앞서 LocalDate와 LocalTime에서 배운것과 같다.

```JAVA
Period pe = Period.of(1, 12, 31);                       // 1년 12개월 31일
Duration du = Duration.of(60, ChronoUnit.SECONDS);      // 60초
// Duration du = Duration.ofSeconds(60);                   // 위의 문장과 동일
```

특정 필드의 값을 변경하는 with()도 있다.

```JAVA
pe = pe.withYears(2);           // 1년에서 2년으로 변경
du = du.withSeconds(120);       // 60초에서 120초로 변경
```

#### 사칙연산, 비교연산, 기타 메서드

plus(), minus()외에 곱셈과 나눗셈을 위한 메서드도 있다.

```JAVA
pe = pe.minusYears(1).multipliedBy(2);          // 1년을 빼고, 2배를 곱한다.
du = du.plusHours(1).diviedBy(60);              // 1시간을 더하고, 60으로 나눈다.
```

Period에 나눗셈을 위한 메서드가 없는데, Period는 날짜의 기간을 표현하기 위한 것이므로 나눗셈을 위한 메서드가 별로 유용하지 않기 때문에 넣지 않은 것이다.

그리고 음수인지 확인하는 isNegative()와 0인지 확인하는 isZero()가 있다. 두 날짜 또는 시간을 비교할 때, 사용하면 어느 쪽이 앞인지 또는 같은지 알아낼 수 있다.

```JAVA
boolean sameDate = Period.between(date1, date2).isZero();
boolean isBefore = Duration.between(time1, time2).isNegative();
```

부호를 반대로 변경하는 negate()와 부호를 없애는 abs()가 있다. 아래 양쪽의 코드는 동일하다. Period에는 abs()가 없어서 대신 아래의 오른쪽과 같은 코드를 사용해야 한다.

```JAVA
// AS-IS
du = du.abs();

// TO-BE
if(du.isNegative()) {
    du = du.negated();
}
```

Period에 normalized()라는 메서드가 있는데, 이 메서드는 월(month)의 값이 12를 넘지 않게, 즉 1년 3개월을 2년 1개월로 바꿔준다. 일(day)의 길이는 일정하지 않으므로 그대로 놔둔다.

```JAVA
pe = Period.of(1, 13, 32).normalized();
```

#### 다른 단위로 변환 - toTotalMonths(), todays(), toHours(), toMinuts()

이름이 'to'로 시작하는 메서드들이 있는데, 이들은 Period와 Duration을 다른 단위의 값으로 변환하는데 사용된다. get()은 특정 필드의 값을 그대로 가져오는 것이지만, 아래의 메서드들은 특정 단위로 변환한 결과를 반환한다는 차이가 있다.

| 클래스 | 메서드 | 설명 |
| --- | --- | --- |
| Period | long toTotalMonths() | 년월일을 월단위로 변환해서 반환(일 단위는 무시) |
| Duration | long toDays() | 일단위로 변환해서 반환 |
| Duration | long toHours() | 시간단위로 변환해서 반환 |
| Duration | long toMinuts() | 분단위로 변환해서 반환 | 
| Duration | long toMillis() | 천분의 일초 단위로 변환해서 반환 |
| Duration | long toNanos() | 나노초 단위로 변환해서 반환 |


참고로 LocalDate의 toEpochDay()라는 메서드는 EpochDay인 '1970-01-01'부터 날짜를 세어서 반환한다. 이 메서드를 이용하면 Period를 사용하지 않고도 두 날짜간의 일수를 편리하게 계산할 수 있다. 단, 두 날짜 모두 Epoch Day 이후의 것이어야 한다.


### 3.7 파싱과 포맷

날짜와 시간을 웒나느 형식으로 출력하고 해석(파싱, parsing)하는 방법에 대해서 배울 차례이다.

형식화(formatting)과 관련된 클래스들은 java.time.format패키지에 들어있는데, 이중에서 DateTimeFormatter가 핵심이다. 이 클래스에는 자주 쓰이는 다양한 형식들을 기본적으로 정의하고 있으며, 그 외의 형식이 필요하다면 직접 정의해서 사용할 수도 있다.

```JAVA
LocalDate date = LocalDate.of(2016, 1, 2);
String yyyymmdd = DateTimeFormatter.ISO_LOCAL_DATE.format(date);        // "2016-01-02"
String yyyymmdd = date.format(DateTimeFormatter.ISO_LOCAL_DATE);        // "2016-01-02"
```

날짜와 시간의 형식화에는 위와 같이 format()이 사용되는데, 이 메서드는 DateTimeFormatter뿐만 아니라 LocalDate나 LocalTime같은 클래스에도 있다. 같은 기능을 하니까 상황에 따라 편한 쪽을 사용하면 된다.

아래의 표는 DateTimeFormatter에 상수로 정의된 형식들의 목록이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/177d19d3-2348-4b53-9028-e45bf5527c42)


#### 로케일에 종속된 형식화

DateTimeFormatter의 static메서드 ofLocalizedDate(), ofLocalizedTime(), ofLocalized DateTime()은 로케일(locale)에 종속적인 포맷터를 생성한다.

```JAVA
DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
String shortFormat = formatter.format(LocalDate.now());
```

FormatStyle의 종류에 따른 출력 형태는 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/57bf3542-16fd-46be-a44e-8c48f3a120e1)

#### 출력형식 직접 정의하기

DateTimeFormatter의 ofPattern()으로 원하는 출력형식을 직접 작성할 수도 있다.

```JAVA
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
```

출력형식에 사용되는 기호의 목록은 다음과 같다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/46f2f1bc-8d2e-4256-9678-42046fd14d13)

아래의 예제는 자주 사용될만한 패턴의 예를 보여준다.


#### 문자열을 날짜와 시간으로 파싱하기

문자열을 날짜 또는 시간으로 변환하려면 static 메서드로 parse()를 사용하면 된다. 날짜와 시간을 표현하는데 사용되는 클래스에는 이 메서드가 거의 다 포함되어 있다. parse()는 오버로딩된 메서드가 여러 개 있는데, 그 중에서 다음의 2개가 자주 쓰인다.

```JAVA
static LocalDateTime parse(CharSequence text)
static LocalDateTime parse(CharSequence text, DateTimeFormatter formatter)
```

DateTimeFormatter에 상수로 정의된 형식을 사용할 때는 다음과 같이 한다.

```JAVA
LocalDate date = LocalDate.parse("2016-01-02", DateTimeFormatter.ISO_LOCAL_DATE);
```

자주 사용되는 기본적인 형식의 문자열은 ISO_LOCAL_DATE와 같은 형식화 상수를 사용하지 않고도 파싱이 가능하다.

```JAVA
LocalDate newDate = LocalDate.parse("2001-01-01");
LocalTime newTime = LocalTime.parse("23:59:59");
LocalDateTime newDateTime = LocalDateTime.parse("2000-01-01T23:59:59");
```

다음과 같이 ofPattern()을 이용해서 파싱을 할 수도 있다.

```JAVA
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
LocalDateTime endOfYear = LocalDateTime.parse("2015-12-31 23:59:59", pattern);
```


