# Chatper 15. 입출력 I/O

## 1. 자바에서의 입출력

### 1.1 입출력이란?

I/O란 Input과 Output의 약자로 입력과 출력, 간단히 줄여서 입출력이라고 한다. 입출력은 컴퓨터 내부 또는 외부의 장치와 프로그램간의 데이터를 주고받는 것을 말한다.

예를 들어 키보드로부터 데이터를 입력받는다는가 System.out.println()을 이용해서 화면에 출력한다던가 하는 것이 가장 기본적인 입출력의 예이다.

### 1.2 스트림(stream)

자바에서 입출력을 수행하려면, 즉 어느 한쪽에서 다른 쪽으로 데이터를 전달하려면, 두 대상을 연결하고 ㅇ데이터를 전송할 수 있는 무언가가 필요한데 이것을 스트림(stream)이라고 정의했다.

> [참고] 스트림은 TV와 DVD를 연결하는 입력선과 출력선과 같은 역할을 한다.

스트림은 연속적인 데이터의 흐름을 물에 비유해서 붙여진 이름인데, 여러 가지로 유사한 점이 많다. 물이 한쪽 방향으로만 흐르는 것과 같이 스트림은 단방향통신만 가능하기 때문에 하나의 스트림으로 입력과 출력을 동시에 처리할 수 없다. 그래서 입력과 출력을 동시에 수행하려면 입력을 위한 입력스트림(inptu stream)과 출력을 위한 출력스트림(output stream), 모두 2개의 스트림이 필요하다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/4d4e99bc-8a66-4c3c-b068-734f189e2b24)

스트림을 먼저 보낸 데이터를 먼저 받게 되어 있으며 중간에 건너뜀 없이 연속적으로 데이터를 주고받는다. 큐(queue)와 같은 FIFO(First In First Out)구조로 되어 있다고 생각하면 이해하기 쉬울 것이다.

### 1.3 바이트기반 스트림 - InputStream, OutputStream

스트림은 바이트단위로 데이터를 전송하며 입출력 대상에 따라 다음과 같은 입출력스트림이 있다.

- 표 15-1

| 입력 스트림 | 출력 스트림 | 입출력 대상의 종류 |
| --- | --- | --- |
| FileInputStream | FileOutputStream | 파일 |
| ByteArrayInputStream | ByteArrayOutputStream | 메모리(byte배열) |
|PipedInputStream | PipedOutputStream | 프로세스(프로세스간의 통신) |
| AudioInputStream | AudioOutputStream | 오디오 장치 |

표 15-1과 같이 여러 종류의 입출력 스트림이 있으며, 어떠한 대상에 대해서 작업을 할 것인지 그리고 입력을 할 것인지 출력을 할 것인지에 따라서 해당 스트림을 선택해서 사용하면 된다. 예를 들어 어떤 파일의 내용을 읽고자 하는 경우 FileInputStream을 사용하면 될 것이다.

이들은 모두 InputStream 또는 OutputStream의 자손들이며, 각각 읽고 쓰는데 필요한 추상메서드를 자신에 맞게 구현해 놓았다.

자바에서 java.io 패키징를 통해서 많은 종류의 입출려고간련 클래스들을 제공하고 있으며, 입출력을 ㅊ ㅓ리할 수 있는 표준화된 방법을 제공함으로써 입출력의 대상이 달라져도 동일한 방법으로 입출력이 가능하기 때문에 프로그래밍을 하기에 편리하다. 

- 표 15-2

| InputStream | OutputStream |
| --- | --- |
| abstract int read() | abstract void write(int b) |
| int read(byte[] b) | void write(byte[] b) |
| int read(byte[] b, int off, int len) | void write(byte[] b, int off, int len) |


> [참고] read()의 반환타입이 byte가 아니라 int인 이유는 read()의 반환값의 범위가 0~255와 -1이기 때문이다.

표 15-2에 나온 메서드의 사용법만 잘 알고 있어도 데이터를 읽고 쓰는 것은 입출력 대상의 종류에 관계없이 아주 간단한 일이 될 것이다.

InputStream의 read()와 OutputStream의 write(int b)는 입출력의 대상에 따라 읽고 쓰는 방법이 다를 것이기 때문에 각 상황에 알맞게 구현하라는 의미에서 추상메서드로 정의되어 있다.

read()와 write(int b)를 제외한 나머지 메서드들은 추상메서드가 아니니까 굳이 추상메서드인 read()와 write(int b)를 구현하지 않아도 이들을 사용하면 될 것이라고 생각할 수도 있겠지만 사실 추상메서드인 read()와 write(int b)를 이용해서 구현한 것들이기 때문에 read()와 write(int b)가 구현되어 있지 않으면 이들은 아무런 의미가 없다.

```java
public abstract class InputStream {
    
    ...
    // 입력스트림으로 부터 1 byte를 읽어서 반환한다. 읽을 수 없으면 -1 반환
    abstarct int read();
    
    // 입력스트림으로부터 len개의 byte를 읽어서 bytre배열 b의 off위치부터 저장
    int read(byte[] b, int off, int len) {
        ...
        for(int i = off; i < off + len; i++) {
            // read()을 호출해서 데이터를 읽어서 배열을 채운다.
            b[i] = (byte) read();
        }
    }
}
// 입력 스트림으로부터 byte배열 b의 크기만큼 데이터를 읽어서 배열 b에 저장한다.
int read(byte[] b) {
    return read(b, 0, b.length);
}
```

이 코드는 InputStream의 실제 소스코드의 일부를 이해하기 쉽게 약간 변경한 것인데 여기서 read(byte[] b, int off, int len)의 코드를 보면 read()를 호출하는 것을 알 수 있다. read()가 추상메서드이지만 이처럼 read(byte[] b, int off, int len)의 내에서 read()를 호출할 수 있다는 것은 이미 배운 바 있다.

read(byte[] b)도 read(byte[] b, int off, int len)를 호출하지만 read(byte[] b, int off, int len)가 다시 추상메서드 read()를 호출하기 때문에 read(byte[] b)도 추상메서드 read()를 호출한다고 할 수 있다.

메서드는 선언부만 알고 있어도 호출이 가능하기 때문에, 추상메서드를 호출하는 코드를 작성할 수 있다. 실제로는 추상클래스를 상속받아서 추상메서드를 구현한 클래스의 인스턴스에 대해서 추상메서드가 호출될 것이기 떄문에 추상메서드를 호출하는 코드를 작성해도 아무런 문제가 되지 않는다.

결론적으로 read()는 반드시 구현되어야하는 핵심적인 메서드이고, read()없이는 read(byte[] b, int off, int len)와 read(byte[] b)는 의미가 없다는 것을 확인할 수 있다.


### 1.4 보조 스트림


표 15-1에서 언급한 스트림 외에도 스트림의 기능을 보완하기 위한 보조스트림이 제공된다. 보조스트림은 실제 데이터를 주고받는 스트림이 아니기 떄문에 데이터를 입출력할 수 있는 기능은 없지만, 스트림의 기능을 향상시키거나 새로운 기능을 추가할 수 있다. 그래서 보조스트림만으로는 입출력을 처리할 수 없고, 스트림을 먼저 생성한 다음에 이를 이용해서 보조스트림을 생성해야 한다.

예를 들어 test.txt라는 파일을 읽기위해 FileInputStream을 사용할 때, 입력 성능을 향상시키 위해 버퍼를 사용하는 보조스트림인 BufferedInputStream을 사용하는 코드는 다음과 같다.

```java
// 1. 스트림 생성
FileInputStream fis = new FileInputStream("test.txt");

// 스트림을 이용해 보조 스트림 생성
BufferedInputStream bis = new BufferedInputStream(fis);

bis.read(); // 보조스트림인 BufferedInputStream으로부터 데이터를 읽는다.
```

코드 상으로는 보조스트림인 BufferedInputStream이 입력기능을 수행하는 것처럼 보이지만, 실제 입력기능은 BufferedInputStream과 연결된 FileInputStream이 수행하고, 보조스트림인 BufferedInputStream은 버퍼만을 제공한다. 버퍼를 사용한 입출력과 사용하지 않은 입출력간의 성능차이는 상당하기 때문에 대부분의 경우에 버퍼를 이용한 보조스트림을 사용한다.

BufferedInputStream, DataINputStream, DigestInputStream, LineNumberInputStream, PushbackInputStream은 모두 FilterInputStream의 자손들이고, FilterInputStream은 InputStream의 자손이라서 결국 모든 보조스틀미 역시 InputStream과 OutputStream의 자손들이므로 입출력방법이 같다.

| 입력                | 출력 | 설명 |
|-------------------| --- | --- |
| FilterInputstream | FilterOutputStream | 필터를 이용한 입출력 처리 |
| BufferedInputStream | BufferedOutputStream | 버퍼를 이용한 입출력 성능향상 | 
| DataInputStream | DataOutputStream | int, float와 같은 기본형 단위(primitive type)로 데이터를 처리하느 ㄴ기능 |
| SequencelInputStream | 없음 | 두 개의 스트림을 하나로 연결 |
| LineNumberINputStream | 없음 | 읽어 온 데이터의 라인 번호를 카운트(JDK1.1부터 LineNumberReader로 대체) |
| ObjectInputStream | ObjectOutputStream | 데이터를 객체단위로 읽고 쓰는데 사용. 주로 파일을 이용하며 객체 직렬화로 관련있음 |
| 없음 | PrintStream | 버퍼를 이용하며, 추가적인 print관련 기능(print, printf, println메서드) | 
| PushbackInputStream | 없음 | 버퍼를 이용해서 읽어 온 데이터를 다시 되돌리는 기능(unread, push back to buffer) |


### 1.5 문자기반 스트림 - Reader, Writer

지금까지 알아본 스트림은 모두 바이트기반의 스트림이었다. 바이트기반이라 함은 입출력의 단위가 1byte라는 뜻이다. 이미 알고 있는 것과 같이 C언어와 달리 Java에는 한 문자를 의미하는 char형이 1byte가 아니라 2byte이기 때문에 바이트 기반의 스트림으로 2byte인 문자를 처리하는 데는 어려움이 있다.

이 점을 보완하기 위해서 문자기반의 스트림이 제공된다. 문자데이터를 입출력할 때는 바이트기반 스트림 대신 문자기반 스트림을 사용하자.


> InputStream -> Reader
> OutputStream -> Writer


| 바이트기반 스트림 | 문자기반 스트림 |
| --- | --- |
| FileInputStream, FileOutStream | FileReader, FileWriter |
| ByteArrayInputStream, ByteArrayOutputStream | CharArrayReader, CharArrayWriter |
| PipedInputStream, PipedOutputStream | PipedReader, PipedWriter |
| StringBufferInputStream(deprecated), StringBufferOutputStream(depreacted) | StringReader, StringWriter |


문자기반 스트림의 이름은 바이트기반 스트림의 이름에서 InputStream은 Reader로 OutputStream은 Writer로만 바꾸면 된다. 단, ByteArrayInputStream에 대응하는 문자기반 스트림은 char배열을 사용한 CharArrayReader이다.

아래 표는 바이트기반 스트림과 문자기반 스트림의 읽기와 쓰기에 사용되는 메서드를 비교한 것인데 byte 배열대신 car배열을 사용한다는 것과 추상메서드가 달라졌다. Reader와 Writer에서도 역시 추상메서드가 아닌 메서드들은 추상메서드를 이용해서 작성되었으며, 프로그래밍적인 관점에서 볼 때 read()를 추상메서드로 하는 것보다 int read(char[] cbuf, int off, int len)를 추상메서드로 하는 것이 더 바람직하다.

바이트기반 스트림과 문자기반 스트림을 이름만 조금 다를 뿐 활용방법은 거의 같다.

![그림1](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/aa858bf3-0561-4195-a077-248be525e53a)

보조스트림 역시 다음과 같이 문자기반 보조스트림이 존재하며 사용목적과 방식은 바이트기반 보조스트림과 다르지 않다.

![그림2](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/e902bb44-1d44-4001-b4e6-24a11e942069)

## 2. 바이트기반 스트림

### 2.1 InputStream과 OutputStream

앞서 얘기한 바와 같이 InputStream과 OutputStream은 모두 바이트기반의 스트림의 조상이며 다음과 같은 메서드가 선언되어 있다.

![그림4](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/4b72758e-9047-464c-be5b-29db1b621966)

![그림3](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/29a49ef7-1230-4daa-9653-164862c521c1)

스트림의 종류에 따라서 mark()와 reset()을 사용하며 이이미 읽은 데이터를 되돌려서 다시 읽을 수 있다. 이 기능을 지원하는 스트림인지 확인하는 markSupported를 통해서 알 수 있다.

flush()는 버퍼가 있는 출력스트림의 경우에만 의미가 있으며, OutputStream에 정의된 flush()는 아무런 일도 하지 않는다.
프로그램이 종료될 때, 사용하고 닫지 않은 스트림을 JVM이 자동적으로 닫아 주기는 하지만, 스트림을 사용해서 모든 작업을 마치고 난 후에는 close90를 호출해서 반드시 닫아 주어야 한다. 그러나 ByteArrayInputStream과 같이 메모리를 사용하는 스트림과 System.in, Syttem.out과 같은 표준 입출력 스트림은 닫아 주지 않아도 된다.

### 2.2 ByteArrayInputStream과 ByteArrayOutputStream

ByteArrayInputStream과 ByteArrayOutputStream은 메모리, 즉 바이트 배열에 데이터를 입출력 하는데 사용되는 스트림이다. 주로 다른 곳에 입출력하기 전에 데이터를 임시로 바이트 배열에 담아서 변환 등의 작업을 하는데 사용된다.

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class IOEx1 {

    public static void main(String[] args) {
        byte[] inSrc = { 0, 1, 2,3, 4, 5, 6, 7, 8, 9 };
        byte[] outSrc = null;

        ByteArrayInputStream input = null;
        ByteArrayOutputStream output = null;

        input = new ByteArrayInputStream(inSrc);
        output = new ByteArrayOutputStream();

        int data = 0;

        while ((data = input.read()) != -1) {
            output.write(data);
        }

        outSrc = output.toByteArray();      // 스트림의 내용을 byte배열로 반환

        System.out.println("Input Source : " + Arrays.toString(inSrc));
        System.out.println("Ouput Source : " + Arrays.toString(outSrc));
    }
}
```

바이트 배열은 사용하는 자원이 메모리 밖에 없으므로 가비지컬렉터에 의해 자동적으로 자원을 반환하므로 close()를 이용해서 스트림을 닫지 않아도 된다. read()와 write(int b)를 사용하기 때문에 한 번에 1byte만 읽고 쓰므로 작업 효율이 떨어진다.

```java

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class IOEx2 {

    public static void main(String[] args) {
        byte[] inSrc = { 0, 1, 2,3, 4, 5, 6, 7, 8, 9 };
        byte[] outSrc = null;
        byte[] temp = new byte[10];

        ByteArrayInputStream input = null;
        ByteArrayOutputStream output = null;

        input = new ByteArrayInputStream(inSrc);
        output = new ByteArrayOutputStream();

        input.read(temp, 0, temp.length);
        output.write(temp, 5, 5);

        outSrc = output.toByteArray();

        System.out.println("Input Source : " + Arrays.toString(inSrc));
        System.out.println("temp         : " + Arrays.toString(temp));
        System.out.println("Output Source : " + Arrays.toString(outSrc));

    }
}
```

int read(byte[] b, int off, int len)와 void write(byte[] b, int off, int len)을 사용해서 입출력하는 방법을 보여주는 예제이다. 이전 예제와는 달리 byte배열을 ㅇ사용해서 한 번에 배열의 크기만큼 읽고 쓸 수 있다. 바구니(배열 temp)를 이용하면 한 번에 더 많은 물건을 옮길 수 있는 것과 같다고 이해하면 좋을 것이다.

byte배열 temp의 크기(temp.length)가 10이라서 10byte를 읽어왔지만 output에 출력할 때는 temp[5]부터 5byte만 출력하였다.

배열을 이용한 입출력은 작업의 효율을 증가시키므로 가능하면 입출력 대상에 따라 알맞ㄴ는 크기의 배열을 사용하는 것이 좋다.

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class IOEx3 {

    public static void main(String[] args) {
        byte[] inSrc = { 0, 1, 2,3, 4, 5, 6, 7, 8, 9 };
        byte[] outSrc = null;
        byte[] temp = new byte[4];

        ByteArrayInputStream input = new ByteArrayInputStream(inSrc);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.out.println("Input Source  : " + Arrays.toString(inSrc));

        try {
            while (input.available() > 0) {
                input.read(temp);
                output.write(temp);
//                System.out.println("temp          : " + Arrays.toString(temp));

                outSrc = output.toByteArray();
                printArrays(temp, outSrc);
            }
        }catch (IOException e) {}

    }

    static void printArrays(byte[] temp, byte[] outSrc) {
        System.out.println("temp          : " + Arrays.toString(temp));
        System.out.println("output Source : " + Arrays.toString(outSrc));
    }
}
```

read()나 write()이 IOException을 발생시킬 수 있기 때문에 try-catch문으로 감싸주었다. available()은 블락킹(blocking)없이 읽어 올  ㅅ수 있는 바이트으 ㅣ수를 반환한다.

아마도 예상과 다른 겨로가가 나왔을 텐데 그 이유는 마지막에 읽은 배열의 9번째와 10번째 요소의 값인 8과 9만을 출력해야하는데 temp에 남아 있는 6, 7까지 출력했기 때문이다.

보다 나은 성능을 위해서 temp에 담긴 내용을 지우고 쓰는 것이 아니라 그냥 기존의 내용 위에 덮어 쓴다. 그래서 temp의 내용은 '[4, 5, 6, 7]'이었는데, 8과 9를 읽고 난 후에는 '[8, 9, 6, 7]'이 된다.

원하는 결과를 얻기 위해서는 아래 왼쪽의 코드를 오른쪽과 같이 수정해야 한다. 왼쪽의 코드는 배열의 내용 전체를 출력하지만, 오른쪽의 코드는 읽어온 만큼(len)만 출력한다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/eedd255d-28dc-4808-a329-c905c99dc80c)

> [참고] 블락킹이란 데이터를 읽어올 때 데이터를 기다리기 위해 멈춰있는 것을 의미한다. 예를 들어 사용자가 데이터를 입력하기 전까지 기다리고 있을 때 블락킹 상태에 있다고 한다.

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class IOEx4 {

    public static void main(String[] args) {
        byte[] inSrc = { 0, 1, 2,3, 4, 5, 6, 7, 8, 9 };
        byte[] outSrc = null;
        byte[] temp = new byte[4];

        ByteArrayInputStream input = new ByteArrayInputStream(inSrc);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            while (input.available() > 0) {
                int len = input.read(temp);
                output.write(temp, 0, len);
            }
        }catch (IOException e) {}

        outSrc = output.toByteArray();

        System.out.println("Input Source : " + Arrays.toString(inSrc));
        System.out.println("temp         : " + Arrays.toString(temp));
        System.out.println("Output Source : " + Arrays.toString(outSrc));

    }
}
```

이전 예제의 문제점을 수정한 예지이다. 출력할 때, temp에 저장된 모든 내용을 출력하는ㄴ 대신 값을 읽어온 만큼만 출력하도록 변경하였다. 그래서 이전 예제와 달리 올바른 결과를 얻은 것을 확인할 수 있다.

### 2.3 FileInputStream과 FileOuputStream

FileInputStream/FileOutputStream은 파일에 입출력을 하기 위한 스트림이다. 실제 프로그래밍에서 많이 사용되는 스트림 중의 하나이다.

![그림5](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/022a6cdc-f906-4c99-8bde-77ec5b997e8e)

## 3. 바이트기반으 ㅣ보조스트림

### 3.1 FilterInputStream과 FilterOuputStream

FilterInputStream/FilterOutputStream은 InputStream/OuputStream의 자손이면서 모든 보조스트림의 조상이다. 보조스트림은 자체적으로 입출력을 수행할 수 없기 때문에 기반스트림을 필요로 한다. 다음은 FilterInputStream/FilterOuputStream의 생성자다.

```java
protected FilterInputStream(InputStream in)
public FilterOuputStream(OutputStream out)
```

FilterInputStream/FilterOutputStream의 모든 메서드는 단순히 기반스트림의 메서드를 그대로 호출할 뿐이다. FilterInputStream/FilterOutputStream자체로는 아무런 일도 하지 않음을 의미한다. FilterInputStream/FilterOutputStream은 상속을 통해 원하는 작업을 수행하도록 읽고 쓰는 메서드를 오버라이딩 해야한다.

```java
import java.io.IOException;
import java.io.InputStream;

public class FilterInputStream extends InputStream {
    protected volatile InputStream in;

    protected FilterInputStream(InputStream in) {
        this.in = in;
    }

    public int read() throws IOException {
        return in.read();
    }
}
```

생성자 FilterInputStream(InputStream in)는 접근 제어자가 protected이기 때문에 FilterInputStream의 인스턴스를 생성해서 사용할 수 없고 상속을 통해서 오버라이딩되어야 한다. FilterInputStream/FilterOutputStream을 상속받아서 기반스트림에 보조기능을 추가한 보조스트림 클래스는 다음과 같다.

> FileInputStream의 자손 - BufferedInputStream, DataInputStream, PushbackInputStream etc
> 
> FileOuputStream의 자손 - BufferedOutputStream, DataOutputStream, PrintStream etc


### 3.2 BufferedInputStream과 BufferedOuputStream

BufferedInputStream/BufferedOuputStream은 스트림의 입출력 효율을 높이기 위해 버퍼를 사용하는 보조스트림이다. 한 바이트씩 입출력하는 것보다 버퍼(바이트배열)를 이용해서 한 번에 여러 바이트를 입출력하는 것이 빠르기 때문에 대부분의 입출력 작업에 사용된다.

![그림6](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/0f1025a8-6760-446e-852f-b0254c7c58aa)

BufferedInputStream의 버퍼크기는 입력소스로부터 한 번에 가져올 수 있는 데이터의 크기로 지정하면 좋다. 보통 입력소스가 파일인 경우 4096 정도의 크기로 하는 것이 보통이며, 버퍼의 크기를 변경해가면서 테스트하면 최적의 버퍼크기를 알아낼 수 있다.

프로그램에서 입력소스로부터 데이터를 읽기 위해 처음으로 read메서드를 호출하면, BufferedInputStream은 입력소스로 부터 버퍼 크기만큼의 데이터를 읽어다 자신의 내부 버퍼에 저장한다. 이제 프로그램에서는 BufferedInputStream의 버퍼에 저장된 데이터를 읽으면 되는 것이다. 외부의 입력소스로 부터 읽는 것보다 내부의 버퍼로 부터 읽는 것이 훨씬 빠르기 때문에 그만큼 작업 효율이 높아진다.

프로그램에서 버퍼에 저장된 모든 데이터를 다 읽고 그 다음 데이터를 읽기위해 read메서드가 호출되면, BufferedInputStream은 입력소스로부터 다시 버퍼크기 만큼의 데이터를 읽어다 버퍼에 저장해 놓는다. 이와 같은 작업이 계속해서 반복된다.

![그림7](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/66ff9f86-1d12-4f0a-b0a9-0d17438d55bc)

BufferedOuputStream 역시 버퍼를 이용해서 출력소스와 작업을 하게 되는데, 입력 소스로부터 데이터를 읽을 떄와는 반대로, 프로그램에서 write메서드를 이용한 출력이 BufferedOutputStream의 버퍼에 저장된다. 버퍼가 가득 차면, 그 때 버퍼의 모든 내용을 출력소스에 출력한다. 그리고는 버퍼를 비우고 다시 프로그램으로부터의 출력을 저장할 준비를 한다.

버퍼가 가득 찼을 때만 출력소스에 출력을 하기 때문에, 마지막 출력부분이 출력소스에 쓰이지 못하고 BufferedOutputStream의 버퍼에 남아있는 채로 프로그램이 종료될 수 있다는 점을 주의해야한다.

그래서 프로그램에서 모든 출력작업을 마친 후 BufferedOutputStream에 close()나 flush()를 호출해서 마지막에 버퍼에 있는 모든 내용이 출력소스에 출력되도록 해야 한다.

> [참고] BufferedOutputStream의 clsoe()는 flush())를 호출하여 버퍼의 내용을 출력스트림에 쓰도록 한 후, BufferedOutputStream인스턴스의 참조변수에 null을 지정함으로써 사용하던 자원들이 반환되게 한다.

```java
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedOutputStreamEx1 {

    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("123.txt");
            // BufferedOutputStream의 버퍼 크기를 5로 한다.
            BufferedOutputStream bos = new BufferedOutputStream(fos, 5);
            for(int i = '1'; i <= '9'; i++) {
                bos.write(i);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

크기가 5인 BufferedOutputStream을 이용해서 파일 123.txt에 1부터 9까지 출력하는 예제인데 결과를 보면 5까지만 출력된 것을 알 수 있다. 그 이유는 버퍼에 남아있는 데이터가 출력되지 못한 상태로 프로그램이 종료되었기 때문이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/a71bfb0b-2f6b-4777-a909-a71c6cafdf2e)

이 예제에서 fos.close()를 호출해서 스트림을 닫아주기는 했지만, 이렇게 해서는 BufferedOuputStream의 버퍼에 있는 내용이 출력되지 않는다. bos.close();와 같이 BufferedOutputStream의 close()는 기반 스트림인 FileOutputStream의 close()를 호출하기 때문에 FileOutputStream의 close()는 따로 호출해지 않아도 된다.

아래의 코드는 BufferedOutputStream의 조상인 FilterOutputStream의 소스코드인데 FilterOutputStream에 정의된 close()는 flush()를 호출한 다음에 기반스트림의 close()를 호출하는 것을 알 수 있다. BufferedOutputStream는 FilterOutputStream의 close()를 오버라이딩 없이 그대로 상속받는다.

```java
import java.io.IOException;
import java.io.OutputStream;

public class FilterOutputStream extends OutputStream {
    protected OutputStream out;

    public FilterOutputStream(OutputStream out) {
        this.out = out;
    }
    
    ...

    public void close() throws IOException {
        try {
            flush();
        } catch (IOException ignored) { }
        
        out.close();
    }
}
```

이처럼 보조스트림을 사용한 경우에는 기반스트림의 close()나 flush()를 호출할 필요없이 단순히 보조스트림의 close()를 호출하기만 하면 된다.


### 3.3 DataInputStream과 DataOutputStream

DataInputStream/DataOuputStream도 각각 FilterInputStream/FilterOutputStream의 자손이며 DataInputStream은 DataInput인터페이스를, DataOuputStream은 DataOutput인터페이스를 각각 구현하였기 때문에, 데이터를 읽고 쓰는데 있어서 byte단위가 아닌, 8가지 기본 자료형의 단위로 읽고 쓸 수 있다는 장점이 있다.

DataOutputStream이 출력하는 형식은 각 기본 자료형 값을 16진수로 표현하여 저장한다. 예를 들어 int을 출력한다면, 4byte의 16진수로 출력된다.

각 자료형의 크기가 다르므로, 출력한 데이터를 다시 읽어올 때는 출력했을 때의 순서를 염두에 두어야 한다.

![그림8](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/04896604-547d-4728-afaf-494476aad6fc)


![그림9](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/c0c5400c-beed-49a5-9296-2ca047cac78e)

```java
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamEx1 {

    public static void main(String[] args) {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream("sample.dat");
            dos = new DataOutputStream(fos);
            dos.writeInt(10);
            dos.writeFloat(20.0f);
            dos.writeBoolean(true);

            dos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

FileOutputStream을 기반으로 하는 DataOutputStream을 생성한 후, DataOuputStream의 메서드들을 이용해서 sample.dat파일에 값들을 출력했다. 이 때 출력한 값들은 이진 데이터(binary data)로 저장된다. 문자 데이터(text data)가 아니므로 문서 편집코드로 볼 수 있는 UltraEdit과 같은 프로그램이나 ByteArrayOutputStream을 사용하면 이진 데이터를 확인할 수 있다.

```java
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DataOutputStreamEx2 {

    public static void main(String[] args) {
        ByteArrayOutputStream bos = null;
        DataOutputStream dos = null;

        byte[] result = null;

        try {
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);
            dos.writeInt(10);
            dos.writeFloat(20.0f);
            dos.writeBoolean(true);

            result = bos.toByteArray();

            String[] hex = new String[result.length];

            for(int i=0; i < result.length; i++ ) {
                if(result[i] < 0) {
                    hex[i] = String.format("%02x", result[i] + 256);
                }else {
                    hex[i] = String.format("%02x", result[i]);
                }
            }

            System.out.println("10진수: " + Arrays.toString(result));
            System.out.println("16진수: " + Arrays.toString(hex));

            dos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

이전의 예제를 변경해서 FileOutputStream대신 ByteArrayOutputStream를 사용하였다. 결과를 보면 첫 번째 4byte인 0, 0, 0, 10는 writeInt(10)에 의해서 출력된 값이고, 두 번째 4byte인 65, -96, 0, 0은 writeFloat(20.0f)에 의해서 출력된 것이다. 그리고 마지막 1byte인 1은 writeBoolean(true)에 의해서 출력된 것이다.

모든 비트 bit의 값이 1인 1byte의 데이터가 있다고 할 때, 왼쪽에서 첫 번째 비트를 부호로 인식하지 않으면 부호 없는 1byte가 되어 범위는 0 ~ 255이므로 이 값은 최대값이 255가 되지만, 부호로 인식하는 경우 범위는 -128~127이 되고, 이 값은 0보다 1작은 값인 -1이 된다.

결국 같은 데이터이지만 자바의 자료형인 byte의 범위가 부호 있는 1byte 정수의 범위인 -128~127이기 때문에 -1로 인식한다는 것이다. 그래서 이 값을 0~255사이의 값으로 변환하려면 256을 더해주어야 한다.

예를 들어 -1인 경우 -1 + 256 = 255가 된다. 그리고 반대의 경우 256을 빼면 된다. 그 다음에 String.format()을 사용해서 10진 정수를 16진 정수로 변환하여 출력했다.

이처럼 ByteArrayInputStream/ByteArrayOutputStream을 사용하면 byte단위의 데이터 변환 및 조작이 가능하다는 것을 알아두자.

> [참고] InputStream의 read()는 반환타입이 int이며 0~255의 값을 반환하므로 256을 더하거나 뺄 필요가 없다. 반면에 read(byte[] b)와 같이 byte배열을 사용하는 경우 상황에 따라 0~255범위의 값으로 변환해야할 필요가 있다.

사실 DataOutputStream에 의해서 어떻게 저장되는지 몰라도 DataOutputStream의 write메서드들로 기록한 데이터는 DataInputStream의 read메서드들로 읽기만 하면 된다.

이 때 한 가지 주의해야 할 것은 아래 예제와 같이 여러 가지 종류의 자료형으로 출력한 경우, 읽을 때는 반드시 쓰인 순서대로 일겅야 한다는 것이ㅏㄷ.

```java
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DataInputStreamEx1 {

    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream("sample.dat");
            DataInputStream dis = new DataInputStream(fis);

            System.out.println(dis.readInt());
            System.out.println(dis.readFloat());
            System.out.println(dis.readBoolean());
            dis.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

에제 15-8을 실행해서 만들어진 sample.dat를 읽어서 화면에 출력하는 예제이다.

sample.dat파일로부터 데이터를 읽어 올 때, 아무런 변환이나 자릿수를 셀 필요벗이 단순히 readInt()와 같이 읽어 올 데이터의 타입에 맞는 메서드를 사용하기만 하면 된다.

문자로 데이터를 저장하면, 다시 데이터를 읽어 올 때 문자들을 실제 값으로 변환하는, 예를 들면 문자열 "100"을 숫자 100으로 변환하는, 과정을 거쳐야 하고, 또 읽어야 할 데이터의 개수를 결정해야하는 번거로움이 있다.

하지만 이처럼 DataInputStream과 DataOutputStream을 사용하면, 데이터를 변환할 필요도 없고, 자리수를 세어서 따지지 않아도 되므로 편리하고 빠르게 데이터를 저장하고 읽을 수 있게 된다.

```java
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamEx3 {

    public static void main(String[] args) {
        int[] score = { 100, 90, 95, 85, 80 };

        try {
            FileOutputStream fos = new FileOutputStream("score.dat");
            DataOutputStream dos = new DataOutputStream(fos);

            for(int i = 0; i < score.length; i++) {
                dos.writeInt(score[i]);
            }

            dos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

int형 배열 score의 값들을 DataOutputStream을 이용해서 score.dat파일에 출력하는 예제이다. type 명령으로 score.dat의 내용을 보면 숫자가 아니라 문자들이 나타나는데, 그 이유는 type 명령이 파일의 내용을 문자로 변환해서 보여주기 때문이다. 파일에 실제 저장되 낸용은 다음과 같다.

> [참고] Editplus의 편집 메뉴에서 'Hex 뷰어'를 선택하면 파일 'score.dat'의 실제 저장된 내용을 볼 수 있다.

int의 크기가 4byte이므로 모두 20bytte의 데이터가 저장되어 있다. 참고로 16진수 두 자리가 1byte이다. 밑줄 아래의 숫자는 10진수로 변환한 겨로가이다.

```java
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class DataInputStreamEx2 {

    public static void main(String[] args) {
        int sum = 0;
        int score = 0;

        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream("score.dat");
            dis = new DataInputStream(fis);

            while (true) {
                score = dis.readInt();
                System.out.println(score);
                sum += score;
            }
        } catch (EOFException e) {
            System.out.println("정수의 총합은 " + sum + " 입니다.");
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                if(dis != null)
                    dis.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }
}
```

DataInputStream의 readInt()와 같이 데이터를 읽는 메서드는 더 이상 이 ㄺ을 데이터가 없으면 EOFException을 발생시킨다. 그래서 다른 입력스트림들과는 달리 문한 반복문과 EOFException을 처리하는 catch문을 이용해서 데이터를 읽는다.

원래 while문으로 작업을 마친 후에 스트림을 닫아 줘야 하는데, while문이 무한 반복문이기 때문에 finally블럭에서 스트림을 닫도록 처리하였다.

```java
finally {
    try {
        if(dis != null)
            dis.close();
    } catch (IOException ie) {
        ie.printStackTrace();
    }
}
```

참조변수 dis가 null일 때 close()를 호출하면 NullPointerException이 발생하므로 if문을 사용해서 dis가 null인지 체크한 후에 'close()'를 호출해야 한다. 그리고 'close()'는 IOException을 발생시킬 수 있으므로 try-catch블럭으로 감싸주었다.

지금까지는 try블럭 내에서 스트림을 닫아주었지만, 작업 도중에 예외가 발생해서 스트림을 닫지 못하고 try블럭을 빠져나갈 수 있기 때문에 이처럼 finally블럭을 이용해서 스트림을 닫아주는 것이 더확실한 방법이다.

사실 프로그램이 종료될 때, 가비지 컬렉터가 사용하던 자원들을 모두 해제 해주기 때문에 이렇게 간단한 예제에서는 스트림을 닫지 않아도 별문제가 되지는 않는다. 그래도 가능하면 스트림을 사용한 직후에 바로 닫아서 자원을 반환하는 것이 좋다.

JDK 1.7부터는 try-with-resources문을 이용해서 close()를 직접 호출하지 않아도 자동호출되도록 할 수 있다.

```java
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class DataInputStreamEx3 {

    public static void main(String[] args) {
        int sum = 0;
        int score = 0;

        try (
                FileInputStream fis = new FileInputStream("score.dat");
                DataInputStream dis = new DataInputStream(fis)
        ) {

            while (true) {
                score = dis.readInt();
                System.out.println(score);
                sum += score;
            }
        } catch (EOFException e) {
            System.out.println("정수의 총합은 " + sum + " 입니다.");
        } catch (IOException ie) {
            ie.printStackTrace();
        } 
    }
}
```

### 3.4 SequencelInputStream

SequenceInputStream은 여러 개의 입력스트림을 연속적으로 연결해서 하나의 스트림으로부터 데이터를 읽는 것과 같이 처리할 수 있도록 도와준다. SequenceInputStream의 생성자를 제외하고 나머지 작업은 다른 입력스트림과 다르지 않다. 큰 파일을 여러 개의 작은 파일로 나누었다가 하나의 파일로 합치는 것과 같은 작업을 수행할 떄 사용하면 좋을 것이다.

> [참고] SequenceInputStream은 다른 보조스트림들과는 달리 FilterInputStream의 자손이 아닌 InputStream을 바로 상속받아서 구현하였다.

| 메서드 / 생성자 | 설명 |
| --- | --- |
| SequenceInputStream(Enumeration e) | Enumeration에 저장된 순서대로 입력스트림을 하나의 스트림으로 연결한다. |
| SequenceInputStream(InputStream s1, InputStream s2) | 두 개의 입력스트림을 하나로 연결한다. |


Vector에 연결할 입력스트림들을 저장한 다음 Vector의 Enumeration elements()를 호출해서 생성자의 매개변수로 사용한다.

```java
// [사용 예 1]
Vector files = new Vector();
files.add(new FileInputStream("file.001"));
files.add(new FileInputStream("file.002"));
SequenceInputStream in = new SequenceInputStream(files.elements());

// [사용 예 2]
FileInputStream file1 = new FileInputStream("file.001");
FileInputStream file2 = new FileInputStream("file.002");
SequenceInputStream in = new SequenceInputStream(file1, file2);
```

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Vector;

public class SequenceInputStreamEx {

    public static void main(String[] args) {
        byte[] arr1 = { 0, 1, 2 };
        byte[] arr2 = { 3, 4, 5 };
        byte[] arr3 = { 6, 7, 8 };
        byte[] outSrc = null;

        Vector v = new Vector();
        v.add(new ByteArrayInputStream(arr1));
        v.add(new ByteArrayInputStream(arr2));
        v.add(new ByteArrayInputStream(arr3));

        SequenceInputStream input = new SequenceInputStream(v.elements());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        int data = 0;

        try {
            while ((data = input.read()) != -1) {
                output.write(data);
            }
        }catch (IOException e) {}

        outSrc = output.toByteArray();

        System.out.println("Input Source1   : " + Arrays.toString(arr1));
        System.out.println("Input Source2   : " + Arrays.toString(arr2));
        System.out.println("Input Source3   : " + Arrays.toString(arr3));
        System.out.println("Output Source1  : " + Arrays.toString(outSrc));
    }
}
```

3개의 ByteArrayInputStream을 Vector와 SequenceInputStream을 이용해서 하나의 입력스트림처럼 다룰 수 있다. Vector에 저장된 순서대로 입력되므로 순서에 주의하도록 하자.


### 3.5 PrintStream

PrintStream은 데이터를 기반스트림에 다양한 형태로 출력할 수 있는 print, println, printf와 같은 메서드를 오버로딩하여 제공한다.

PrintStream은 데이터를 적절한 문자로 출력하는 것이기 때문에 문자기반 스트림의 역할을 수행한다. 그래서 JDK1.1에서 부터 PrintStream보다 향상된 기능의 문자기반 스트림인 PrintWriter가 추가되었으나 그 동안 매우 빈번히 사용되던 System.out이 PrintStream이다 보니 둘 다 사용할 수 밖에 없게 된다.

PrintStream과 PrintWriter는 거의 같은 기능을 가지고 있지만 PrintWriter가 PrintStream에 비해 다양한 언어의 문자를 처리하는데 적합하기 때문에 가능하면 PrintWriter를 사용하는 것이 좋다.

> [참고] PrintStream은 우리가 지금까지 알게 모르게 많이 사용해 왔다. System클래스의 static멤버인 out과 err. 즉 System.out, System.err이 printStream이다.

![그림1](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/115ab161-d0a3-436b-805a-963c82878bb1)

print()나 println()을 이용해서 출력하는 중에 PrintStream의 기반스트림에서 IOException이 발생하면 checkError()를 통해서 인지할 수 있다. println()이나 print()는 예외를 더닞지 않고 내부에서 처리하도록 정의하였는데, 그 이유는 println()과 같은 메서드가 매우 자주 사용되는 것이기 때문이다.

```java
import java.io.Closeable;
import java.io.FilterOutputStream;
import java.io.IOException;

public class PrintStream extends FilterOutputStream implements Appendable, Closeable {
    private boolean trouble = false;

    public void print(int i) {
        write(String.valueOf(i));
    }

    private void write(String s) {
        try {
            ...
        } catch (IOException e) {
            trouble = true;
        }
    }
    
    public boolean checkError() {
        if(out != null) flush();
        return trouble;
    }
}
```

> [참고] i+""와 String.valueOf(i)는 같은 결과를 얻지만, String.valueOf(i)가 더 성능이 좋다.


printf()는 JDK1.5부터 추가된 것으로, C언어와 같이 편리한 형식화된 출력을 지원하게 되었다. printf()에 사용될 수 있는 옵션은 꽤나 다양한데 그에 대한 자세한 내용은 Java API에서 찾을 수 있다. 만일 Java API문서에서 Formatter클래스를 참고하면 된다. 우선 자주 사용되는 옵션들만을 골라서 정리해보았다.

![그림2](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/25057d46-3077-4122-976b-2e8319fb43cb)

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/b5d67fe8-3686-46b9-9df9-b2a91ec9e614)
![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/92b1aa8e-9b9a-4224-9d28-c826f5d850f3)

![그림3](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/fbab6b77-ec93-4067-b985-8206eef0267f)

```java
import java.util.Date;

public class PrintStreamEx1 {

    public static void main(String[] args) {
        int i = 65;
        float f = 1234.56789f;

        Date d = new Date();

        System.out.printf("문자 %c의 코드는 %d\n", i, i);
        System.out.printf("%d는 8진수로 %o, 16진수로 %x\n", i, i, i);
        System.out.printf("%3d%3d%3d\n", 100, 90, 80);
        System.out.println();

        System.out.printf("123456789012345678901234567890\n");
        System.out.printf("%s%-5s%5s\n", "123", "123", "123");
        System.out.println();

        System.out.printf("%-8.1f%8.1f %e\n", f, f, f);
        System.out.println();

        System.out.printf("오늘은 %tY년 %tm월 %td일 입니다.\n", d, d, d);
        System.out.printf("지금은 %tH시 %tM분 %tS초 입니다.\n", d, d, d);
        System.out.printf("지금은 %1$tH시 %1$tM분 %1$tS초 입니다.\n", d, d, d);
    }
}
```

옵션을 변경해가면서 테스트하고 그 결과를 확인하도록 하자. 한 가지 덧붙여 설명할 것은 매개변수(argument)의 개수에 대한 것인데, 형식화된 문자열에 사용된 옵션의 개수와 매개변수의 개수가 일치하도록 신경 써야 한다.

```java
System.out.printf("지금은 %tH시 %tM분 %tS초 입니다.\n", d, d, d);
System.out.printf("지금은 %1$tH시 %1$tM분 %1$tS초 입니다.\n", d, d, d);
```

위의 두 문장은 같은 결과를 출력하는데, 두 번째 문장의 경우 형식화된 문자열에 사용된 옵션의 개수와 매개변수의 개수가 일치하지 않는다는 것을 알 수 있다. 이처럼 '숫자$'를 옵션 앞에 붙여 줌으로써 출력된 매개변수를 지정해 줄 수 있다. 예를 들어 '1$'라면 첫 번째 매개변수를 의미한다.

## 4. 문자기반 스트림

문자데이터를 다루는데 사용된 다는 것을 제외하고는 바이트기반 스트림과 문자기반의 스트림에서는 Reader/Wrtier가 그와 같은 역할을 한다. 다음은 Reader/Writer의 메서드인데 byte배열 대신 char배열을 사용한다는 것 외에는 InputStream/OutputStream의 메서드와 다르지 않다.

![그림4](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/a42079b4-4b24-41f8-9615-d5cb8b2bb9a1)

![그림5](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/b97e4e20-71a6-4e1e-b007-5d3e635ef852)

한 가지 더 얘기하고 싶은 것은 문자기반 스트림이라는 것이 단순히 2byte로 스트림을 처리하는 것만을 의미하지는 않는다는 것이다. 문자 데이터를 다루는데 필요한 또 하나의 정보는 인코딩이다.

문자기반 스트림, 즉 Reader/Writer 그리고 그 자손들은 여러 종류의 인코딩과 자바에서 사용하는 유니코드(UTF-16)간의 변환을 자동적으로 처리해준다. Reader는 특정 인코딩을 읽어서 유니코드로 변환하고 Writer는 유니코드를 특정 인코딩으로 ㅂ변환하여 저장한다.

### 4.2 FileReader와 FileWriter

FileReader/FileWriter는 파일로부터 텍스트데이터를 익고, 파일에 쓰는데 사용된다. 사용방법은 FileInputStream/FileOutputStream과 다르지 않으므로 자세한 내용은 생략한다.

```java
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileConversion {

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader(args[0]);
            FileWriter fw = new FileWriter(args[1]);

            int data = 0;

            while ((data = fr.read()) != -1) {
                if(data != '\t' && data != '\n' && data != ' ' && data != '\r')
                    fw.write(data);
            }

            fr.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

파일의 공백을 몯 ㅜ없애는 예제인데 입력스트림으로부터 읽은 데이터를 변환해서 출력 스트림에 쓰는 작업의 예를 보여주기 위한 것이다. 


### 4.3 PipedReader와 PipedWriter

PipedReader/PipedWriter는 쓰레드 간에 데이터를 주고받을 때 사용된다. 다른 스트림과는 달리 입력과 출력스트림을 하나의 스트림으로 연결(connect)해서 데이터를 주고받는다는 특징이 있다.

스트림을 생성한 다음에 어느 한쪽 쓰레드에서 connect()를 호출해서 입력스트림과 출력스트림을 연결한다. 입출력을 마친 후에는 어느 한쪽 스트림만 닫아도 나머지 스트림은 자동으로 닫힌다. 이 점을 제외하고는 일반 입출력방법과 다르지 않다.

PipedReaderWriter 예제를 보면 두 쓰레드가 PipedReader/PipedWriter를 이용해서 서로 메시지를 주고받는 예제이다. 쓰레드를 시작하기 전에 PipedReader와 PipedWriter를 연결해야한다는 것에 유의하자.

StringWriter는 CharArrayWriter처럼 메모리를 사용하는 스트림인데 내부적으로 StringBuffer를 가지고 있어서 출력하는 내용이 여기에 저장된다.


### 4.4 StringReader와 StringWriter

StringReader/StringWriter는 CharArrayReader/CharArrayWriter와 같이 입출력 대상이 메모리인 스트림이다. StringWriter에 출력되는 데이터는 내부의 StringBuffer에 저장되면 StringWriterㅇ의 다음과 같은 메서드를 이용해서 저장된 데이터를 얻을 수 있다.

> StringBuffer getBuffer() : StringWriter에 출력한 데이터가 저장된 StringBuffer를 반환한다.
> String toString(): StringWriter에 출력된 (StringBuffer에 저장된) 문자열을 반환한다.

근본적으로 String도 char배열이지만, 아무래도 char배열보다는 String으로 처리하는 것이 여러모로 편리한 경우가 더 많을 것이다.


## 5. 문자기반의 보조스트림

### 5.1 BufferedReader와 BufferedWriter

BufferedReader/BufferedWriter는 버퍼를 이용해서 입출력의 효율을 높일 수 있또록 해주는 역할을 한다. 버퍼를 이용하면 입출력의 효율이 비교할 수 없을 정도로 좋아지기 때문에 사용하는 것이 좋다.

BufferedReader의 readLine()을 사용하면 데이터를 라인단위로 읽을 수 있고 BufferedWriter는 newLine()이라는 줄바꿈 해주는 메서드를 가지고 있다.

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderEx1 {

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("BufferedReaderEx1.java");
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            for(int i = 1; (line = br.readLine()) != null; i++) {
                if(line.contains(";")) {
                    System.out.println(i + ":" + line);
                }
            }

            br.close();
        }catch (IOException e){}
    }
}
```

BufferedReader의 readLine()을 이용해서 파일을 라인단위로 라인단위로 읽은 다음 contains()를 이용해서 ';'를 포함하고 있는지 확인하여 출력하는 예제이다. 파일에서 특정 문자 또는 문자열을 포함한 라인을 쉽게 찾아낼 수 있음을 보여준다.

### 5.2 InputStreamReader와 OutputStreamWriter

InputStreamReader/OutputStreamWriter는 이름에서 알 수 있는 것과 같이 바이트기반 스트림을 문자기반 스트림으로 연결시켜주는 역할을 한다. 그리고 바이트기반 스트림의 데이터를 지정된 인코딩의 문자데이터로 변환하는 작업을 수행한다.

> [참고] InputStreamReader/OutputStreamWriter는 Reader/Writer의 자손이다.

![그림6](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/b9eb6910-c8b8-4172-9a5d-27b467ab848e)

한글 윈도우에서 중국어로 작성된 파일을 읽을 때 InputStreamReader(InputStream in, String encoding)을 이용해서 인코딩이 중국어로 되어있다는 것을 지정해주어야 파일의 내용이 깨지지 않고 올바르게 보일 것이다. 인코딩을 지정해 주징 않는다면 OS에서 사용하는 인코딩을 사용해서 파일을 해석해서 보여주기 때문에 원래 작성된 데로 볼 수 없을 것이다.

이와 마찬가지로 OutputStreamWriter를 이용해서 파일에 텍스트데이터를 저장할 때 생성자OutputStreamWriter(OutputStream in, String encoding)를 이용해서 인코딩을 지정하지 않으면 OS에서 사용하는 인코딩으로 데이터를 저장할 것이다.

> [참고] 시스템 속성에서 sun.jnu.encoding의 값을 보면 OS에서 사용하는 인코딩의 종류를 알 수 있다.


## 6. 표준입출력과 File

### 6.1 표준입출력 -System.in, System.out, System.err

표준입출력은 콘솔(console, 도스창)을 통한 데이터 입력과 콘솔로의 데이터 출력을 의미한다. 자바에서는 표준 입출력(Standard I/O)을 위해 3가지 입출력 스트림, Sytstem.in, System.out, Sytstem.err을 제공하는데, 이 들은 자바 어플리케이션의 실행과 동시에 사용할 수 있게 자동적으로 생성되기 때문에 개발자가 별도로 스트림을 생성하는 코드를 작성하지 않고도 사용가 가능하다.

> System.in - 콘솔로부터 데이터를 입력받는데 사용
> 
> System.out - 콘솔로 데이터를 출력하는데 사용
> 
> System.err - 콘솔로 데이터를 출력하는데 사용


![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/9b0ce3cc-1277-43d7-87c8-f77fb2ab3b47)

System클래스의 소스에서 알 수 있듯이 in, out, err은 System클래스에 선언된 클래스변수(static변수)이다. 선언부분만을 봐서는 out, err, in의 타입은 InputStream과 PrintStream이지만 실제로는 버퍼를 이용하는 BufferedInputStream과 BufferedOutputStream의 인스턴스를 사용한다.

```java
public final class System {
    public final static InputStream in = nullInputStream();
    public final static PrintStream out = nullInputStream();
    public final static PrintStream err = nullInputStream();
}
```

```java
import java.io.IOException;

public class StandardIOEx1 {

    public static void main(String[] args) {
        try {
            int input = 0;

            while ((input = System.in.read()) != -1) {
                System.out.println("input: " + input + " , (char)input: " + (char)input);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
 
화면에 커서가 입력을 기다리고 있을 것이다. hello라고 입력하고 '^Z'를 누르거나 Enter를 누르면, 입력한 문자들이 출력되고 프로그램이 종료된다.

예제를 실행하여 System.in.read()가 호출되면, 코드의 진행을 멈추고 콘솔에 커서가 깜빡이며 사용자의 입력을 기다린다.

콘솔입력은 버퍼를 가지고 있기 때문에 Backspace키를 이용해서 편집이 가능함며 한 번에 버퍼의 크기만큼 입력이 가능해진다. 그래서 Enter키나 입력의 끝을 알리는 '^z'를 누르기 전까지는 아직 데이터가 입력 중인 것으로 간주되어 커서가 입력을 계속 기다리는 상태에 머무리게 된다.

콘솔에 데이터를 입력하고 Enter키를 누르면 입력대기상태에서 벗어나 입력된 데이터를 읽기 시작하고 입력된 뎅치터를 모두 읽으면 다시 입력대기 상태가 된다.

이러한 과정이 반복되다가 사용자가 '^z'를 입력하면, read()는 입력이 종료되었음을 인식하고 -1을 반환하여 while문을 벗어나 프로그램이 종료된다.


위의 결과에서 알 수 있듯이 Enter키를 누르는 것은 두 개의 특수문자 '\r'과 '\n'이 입력된 것으로 간주된다. 'r'은 캐리지리턴, 즉 커서를 현재 라인의 첫 번쨰 컬럼으로 이동시키고 '\n'은 커서를 다음 줄로 이동시키는 줄바꿈(new line)을 한다.

그래서 Enter키를 누르면, 캐리지리턴과 줄바꿈이 수행되어 다음 줄의 첫 번째 칼럼으로 커서가 이동하는 것이다.


여기서 한 가지 문제는 Enter키도 사용자입력으로 간주되어 매 입력마다 '\r'과 '\n'이 붙기 때문에 이 들을 제거해주어야 하는 불편함이 있다는 것이다. 이러한 불편함을 제거하려면 전에 살펴본 것과 같이 System.in에 BufferedReader를 이용해서 readLine()을 통해 라인단위로 데이터를 입력받으면 된다.

텍스트기반의 사용자인터페이스 시대에 탄생한 C언어는 콘솔이 데이터를 입력받는 주요 수단이었지만, 자바의 탄생한 그래픽기반의 사용자인터페이스 시대는 콘솔을 통해서 데이터를 입력받은 경우는 드물기 때문에 Java에서 콘솔을 통한 입력에 대한 지원이 미약했다.


### 6.2 표준입출력의 대상 변경 - setOut(), setErr(), setIn()

초기에는 System.in, System.out, System.err의 입출력대상이 콘솔화면이지만, setIn(), setOut(), setErr()를 사용하면 입출력을 콘솔 이외에 다른 입출력 대상으로 변경하는 것이 가능하다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/3cdc0ff9-ae46-4fa3-8558-9bcb6762bb65)

그러나 JDK1.5부터 Scanner클래스가 제공되면서 System.in으로부터 데이터를 입력받아 작업하는 것이 편리해졌다.

```java
public class StandardIOEx2 {

    public static void main(String[] args) {
        System.out.println("out: Hello World!");
        System.err.println("err: Hello World!");
    }
}
```

System.out, System.err 모두 출력대상이 콘솔이기 때문에 System.out대신 System.err을 사용해도 같은 결과를 얻는다.




