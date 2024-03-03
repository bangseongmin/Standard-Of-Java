# Chatper  13. 쓰레드(Thread)

## 1. 프로세스와 쓰레드

프로세스(process)란 간단히 말해서 '실행 중인 프로그램(program)'이다. 프로그램을 실행하면 OS로부터 실행에 필요한 자원(메모리)을 할당받아 프로세스가 된다.

프로세스는 프로그램을 수행하는 데 필요한 데이터와 메모리 등의 자원 그리고 쓰레드로 구성되어 있으며 프로세스의 자원을 이용해서 실제로 작업을 수행하는 것이 바로 쓰레드이다.

그래서 모든 프로세스에는 최소한 하나 이상의 쓰레드가 존재하며, 둘 이상의 쓰레드를 가진 프로세스를 '멀티쓰레드 프로세스(multi-threaded process)'라고 한다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/a129e51e-5644-4c0e-8d10-7677e443991d)

> [참고] 쓰레드를 프로세스라는 작업공간(공장)에서 작업을 처리하는 일꾼(worker)으로 생각하면 이해하기 쉬울 것이다.


하나의 프로세스가 가질 수 있는 쓰레드의 개수는 제한되어 있지 않으나 쓰레드가 작업을 수행하는데 개별적인 메모리 공간(호출스택)을 필요로 하기 때문에 프로세스의 메모리 한계에 따라 생성할 수 있는 쓰레드의 수가 결정된다. 실제로는 프로세스의 메모리 한계에 다다를 정도로 많은 쓰레드를 생성하는 일은 없을 것이다.

#### 멀티태슽킹과 멀티쓰레딩

우리가 사용하고 있는 윈도우나 유닉스를 포함한 대부분의 OS는 멀티태스킹(multi-tasing, 다중작업)을 지원하기 때문에 여러 개의 프로세스가 동시에 실행될 수 있다.

이와 마찬가지로 멀티쓰레딩은 하나의 프로세스 내에서 여러 쓰레드가 동시에 작업을 수행하는 것이다. CPU의 코어(core)가 한 번에 단 하나의 작업만 수행할 수 있으므로, 실제로 동시에 처리되는 작업의 개수는 코어의 개수와 일치한다. 그러나 처리해야하는 쓰레드의 수는 언제나 코의 개수보다 훨씬 많기 떄문에 각 코어가 아주 짧은 시간 동안 여러 작업을 번갈아 가며 수행함으로써 여러 작업들이 모두 동시에 수행되는 것처럼 보이게 한다.

그래서 프로세스의 성능이 단순히 쓰레드의 개수에 비례하는 것이 아니며, 하나의 쓰레드를 가진 프로세스 보다 두 개의 쓰레드를 가진 프로세스가 오히려 더 낮은 성능을 보일 수도 있다.


#### 멀티 쓰레딩의 장단점

도스(DOS)와 같이 한 번에 한 가지 작업만 할 수 있는 OS와 윈도우와 같은 멀티태스킹이 가능한 OS의 차이는 이미 경험으로 잘 알고 있을 것이다. 싱글쓰레드 프로그램과 멀티 쓰레드 프로그램의 차이도 이와 같다고 생각하면 된다.

멀티쓰레드의 장점은 다음과 같다.

> **멀티쓰레딩의 장점**
> 
> - CPU의 사용률을 향상시킨다.
> - 자원을 보다 효율적으로 사용할 수 있다.
> - 사용자에 대한 응답성이 향상된다.
> - 작업이 분리되어 코드가 간결해진다.

메신저로 채팅하면서 파일을 다운로드 받거나 음성대화를 나눌 수 있는 것이 가능한 이유가 바로 멀티쓰레드로 작성되어 있기 때문이다. 만일 싱글쓰레드로 작성되어 있다면 파일을 다운로드 받는 동안에는 다른 일(채팅)을 전혀 할 수 없을 것이다.

여러 사용자에게 서비스를 해주는 서버 프로그램의 경우 멀티쓰레드로 작성한는 것은 필수적이어서 하나의 서버 프로세스가 여러 개의 쓰레드를 생성해서 쓰레드와 사용자의 요청이 일대일로 처리되도록 프로그래밍해야 한다.

만일 싱글쓰레드로 서버 프로그램을 작성한다면 사용자의 요청 마다 새로운 프로세스를 생성해야하는데 프로세스를 생성하는 것은 쓰레드를 생성하는 것에 비해 더 많은 시간과 메모리 공간이 필요하기 때문에 많은 수의 사용자 요청을 서비스하기 어렵다.

> [참고] 쓰레드를 가변운 프로세스, 즉 경량 프로세스(LWP, light-weight process)라고 부르기도 한다.

그러나 멀티쓰레딩에 장점만 있는 것은 아니어서 멀티쓰레드 프로세스는 여러 쓰레드가 같은 프로세스 내에서 자원을 공유하면서 작업을 하기 때문에 발생할 수 있는 동기화(synchronization), 교착상태(deadlock)와 같은 문제들을 고려해서 신중히 프로그래밍해야 한다.

> [참고] 교착상태란 두 쓰레드가 자원을 점유한 상태에서 서로 상대편이 점유한 자원을 사용하려고 기다리느라 진행이 멈춰 있는 상태를 말한다.

#### 정리

1. 쓰레드 
    
    - 프로세스가 할당받은 자원을 이용하는 실행의 단위
    - 한 프로세스 내에서 동작되는 여러 실행 흐름으로 프로세스 내의 Heap, Data, Code 영역을 공유(멀티 스레드의 경우, 이 프로세스의 영역들을 공유한다)
    - 하나의 프로세스 안에서 다양한 작업을 담당하는 최소 실행 단위를 스레드라고 한다.
        
        예시) 크롬 브라우저(=프로세스)에서 티스토리 작성(스레드 1)하면서 지니 음악 감상(스레드 2)
   
    - 멀티 스레딩의 경우, 스레드 간의 자원은 공유하고 자원의 생성과 관리의 중복성을 최소화하여 수행 능력을 향상시킨다.
    - 각각의 스레드는 독립적인 자원을 수행해야 하기 때문에 고융한 스레드 ID, 프로그램 카운터(PC), 레지스터 집합, 스택을 가지고 있다.
    
    <br />
    

2. 멀티 쓰레드
    
    멀티 스레드는 CPU의 최대 활용을 위해 프로그램의 둘 이상을 동시에 실행하는 기술이다. 이러한 작업은 컨텍스트 스위칭(Context Switching)을 통해서 이뤙진다. 하나의 스레드에서 다음 스레드로 이동을 하면서 컨텍스트 스위칭이 일어난다. 그리고 스위칭이 일어나면서 부분적으로 조금씩 조금씩 각각의 스레드에 대한 작업을 끝내게 된다.

    다시 말해 컨텍스트 위치싱이 엄청 빠르게 일어나면서, 프로그램들이 동시에 수행되는 것처럼 보인다.


3. 멀티 스레드의 장점
    
    1. 응답성

       프로그램의 일부분(스레드 중 하나)이 중단되거나 긴 작업을 수행하더라도 프로그램의 수행이 계속 되어 사용자에 대한 응답성이 증가한다. 다시 말해서, 멀티 스레드 모델은 에러 발생 시 새로운 스레드를 생성하여 극복한다. 다만, 새로운 스레드 생성이나 놀고 있는 스레드 처리에 비용이 발생한다. ( 싱글 스레드는 프로그램 일부분이 중단되거나, 에러가 발생하면 프로그램이 멈춘다 )
   
   2. 경제성

      프로세스 내 자원들과 메모리를 공유하기 때문에 메모리 공간과 시스템 자원 소모가 줄어든다. 스레드 간 통신이 필요한 경우에도 쉽게 데이터를 주고 받을 수 있으며, 프로세스의 context switching과 달리 스레드 간의 context switching은 캐시 메모리를 비울 필요가 없기 때문에 더 빠르다.
    
   3. 멀티프로세스의 활용

      다중 CPU 구조에서는 각각의 스레드가 다른 프로세서에서 병렬로 수행될 수 있으므로 병렬성이 증가한다.

4. 멀티 스레드의 단점

   1. 성능

      context switching, 동기화 등의 이유 때문에 싱글 코어 멀티 스레딩은 스레드 생성 시간이 오히려 오버헤드로 작용해 단일 스레드보다 느리다.

   2. 동기화

      공유하는 자원에 동시에 접근하는 경우, 프로세스와는 달리 스레드는 데이터와 힙 영역을 공유하기 때문에 어떤 스레드가 다른 스레드에서 사용 중인 변수나 자료구조에 접근하여 엉뚱한 값을 읽어오거나 수정할 수 있다. 따라서 동기화가 필요

   3. 환경

      멀티 스레딩을 위해서는 운영체제의 지원이 필요하다.

   4. 비용

      멀티 스레드 모델은 프로그래밍 난이도가 높다. 또한, 스레드 수만큼 자원을 많이 사용한다. 초보자의 경우, 동일한 프로그램을 만든다면, 싱글 스레드로는 완성이라도 한다. 그러나, 멀티 스레드의 경우는 완성도 못하는 경우도 많다.
   
5. 싱글 스레드의 장점
    
   1. context switching이 요구되지 않는다.

      context switching은 여러 개의 프로세스가 하나의 프로세서를 공유할 때 발생하는 작업으로 많은 비용을 필요로 한다.

   2. 동기화에 대한 고려 필요 없음

      여러 개의 스레드가 프로세스의 자원을 공유할 경우, 각 스레드가 원하는 결과를 얻게 하려면 공용 자원에 대한 접근을 제어해야 한다. 쉽게 말해서, 모든 스레드가 일정 자원에 동시에 접근하거나, 똑같은 작업을 실행하려는 경우, 에러가 발생하거나 원하는 값이 나오지 않는다. 그래서, 스레드들이 동시에 같은 자원에 접근하지 못하도록 제어해줘야만 한다. 이 작업은 프로그래머에게 많은 노력을 요구하고 비용을 발생시킨다.
   
   3. 성능 우위

      단순히 CPU만을 사용하는 계산작업이라면, 오히려 멀티스레드보다 싱글스레드로 프로그래밍하는 것이 더 효율적이다.
        
   4. 비용

      프로그래밍 난이도가 쉽고, CPU, 메모리를 적게 사용한다.

6. 싱글 스레드의 단점
    
   1. 여러 개의 CPU를 활용하지 못한다.
   2. 연산량이 많은 작업을 하는 경우, 그 작업이 완료되어야 다른 작업을 수행할 수 있다.
   3. 싱글 스레드 모델은 에러 처리를 못하는 경우 멈춘다.


## 2. 쓰레드의 구현과 실행

쓰레드를 구현하는 방법은 Thread클래스를 상속받는 방법과 Runnable 인터페이스를 구현하는 방법, 모두 두 가지가 있다. 어느 쪽을 선택해도 별 차이는 없지만 Thread 클래스를 상속받으면 다른 클래스를 상속받을 수 없기 때문에, Runnable 인터페이스를 구현하는 방법이 일반적이다.

Runnable 인터페이스를 구현하는 방법은 재사용성(reusability)이 높고 코드의 일관성(consistency)을 유지할 수 있기 때문에 보다 객체지향적인 방법이라고 할 수 있다.

```JAVA
// 1. Thread 클래스를 상속
public class MyThread extends Thread {
    @Override
    public void run() { /* 작업 내용 */ }
}
```

```java
// 2. Runnable 인터페이스 구현
public class MyThread implements Runnable {
    @Override
    public void run() { /* 작업 내용 */ }
}
```

Runnable 인터페이스는 오로지 run()만 정의도어 있는 간단한 인터페이스이다. Runnable 인터페이스를 구현하기 위해서 해야 할 일은 추상메서드 run()의 몸통{}을 만들ㄹ어 주는 것 뿐이다.

쓰레드를 구현한다는 것은, 위의 두 방법 중 어떤 것을 선택하든지, 그저 쓰레드를 통해 작업하고자 하는 내용으로 run()의 몸통을 채우는 것일 뿐이다.

Thread 클래스를 상속받은 경우와 Runnable 인터페이스를 구현한 경우의 인스턴스 생성 방법이 다르다.

```java
ThreadEx1_1 t1 = new ThreadEx1_1();     // Thread의 자손 클래스의 인스턴스 생성

Runnable r = new ThreadEx1_2();         // Runnable을 구현한 클래스의 인스턴스 생성
Thread t2 = new Thread(r);			    // 생성자 Thread(Runnable target)
```

Runnable 인터페이스를 구현한 경우, Runnable인터페이스를 구현한 클래스의 인스턴스를 생성한 다음, 이 인스턴스를 Thread 클래스의 생성자의 매개변수로 제공해야 한다.

아래의 코드는 실제 Thread클래스의 소스코드(Thread.java)를 이해하기 쉽게 수정한 것인데, 인스턴스변수로 Runnable타입의 변수 r을 선언해 놓고 생성자를 통해서 Runnable인터페이스를 구현한 인스턴스를 참조하도록 되어 있는 것을 확인할 수 있다.

그리고 run()을 호출하면 참조변수 r을 통해서 Runnable인터페이스를 구현한 인스턴스의 run()이 호출된다. 이렇게 함으로써 상속을 통해 run()을 오버라이딩하지 않고도 외부로부터 run()을 제공받을 수 있게 된다.

```java
public class Thread {
    
    private Runnable r;

    public Thread(Runnable runnable) {
        this.r = runnable;
    }
    
    public void run() {
        if(this.r != null) {
            r.run();
        }
    }
    
    ...
}
```

Thread클래스를 상속받으면, 자손 클래스에서 조상인 Thread클래스의 메서드를 직접 호출할 수 있지만, Runnable을 구현하면 Thread클래스의 static 메서드인 currentThread()를 호출하여 쓰레드에 대한 참조를 얻어 와야만 호출이 가능하다.

> static Thread currentThread()     현재 실행중인 쓰레드와 참조를 반환한다.
> String getName()                  쓰레드의 이름을 반환한다.

그래서 Thread를 상속받은 ThreadEx1_1에서는 간단히 getName()을 호출하면 되지만, Runnable을 구현한 ThreadEx1_2에는 멤버라고는 run()밖에 없기 때문에 Thread클래스의 getName()을 호출하려면, "Thread.currentThread().getName()"와 같이 해야 한다.

```java
class ThreadEx1_1 extends Thread {
    public void run() {
        for (int i=0; i<5; i++) {
            // 조상인 Thread의 getName()을 호출
            System.out.println(getName());	
        }
    }
}

class ThreadEx1_2 implements Runnable {
    public void run() {
        for (int i=0; i<5; i++) {
            // Thread.currentThread(); - 현재 실행 중인 Thread를 반환
            System.out.println(Thread.currentThread().getName());
        }
    }
}
```


참고로 쓰레드의 이름은 다음과 같은 생성자나 메서드를 통해서 지정 또는 변경할 수 있다.

```java
Thread(Runnable taget, String name)
Thread(String name)
void setName(String name)
```

예제 13-1의 결과에서도 알 수 있듯이 쓰레드의 이름을 지정하지 않으면 'Thread-번호'의 형식으로 이름이 정해진다.

그리고, "System.out.println(Thread.currentThread().getName());"는 아래의 코드를 한 줄러 줄여 쓴것이라고 이해하면 된다.

```java
Thread t = Thread.currentThread();
String name = t.getName();
System.out.println(name);
```

#### 쓰레드의 실행 - start()

쓰레드를 생성했다고 해서 자동으로 실행되는 것은 아니다. start()을 호출해야만 쓰레드가 실행된다.

```java
t1.start();     // 쓰레드 t1을 실행
t2.start();     // 쓰레드 t2을 실행
```

사실은 start()가 호출되었다고 해서 바로 실행되는 것이 아니라, 일단 실행대기 상태에 있다가 자신의 차례가 되어야 실행된다. 물론 실행대기중인 쓰레드가 하나도 없으면 곧바로 실행상태가 된다.

> [참고] 쓰레드의 실행순서는 OS의 스케쥴러가 작성한 스케쥴에 의해 결정된다. 쓰레드의 상태에 대한 내용은 후에 자세히 다룰 것이다.

한 가지 더 알아 두어야 하는 것은 한 번 실행이 종료된 쓰레드는 다시 실행할 수 없다는 것이다. 즉, 하나의 쓰레드에 대해 start()가 한 번만 호출될 수 있다는 뜻이다.

그래서 만일 쓰레드의 작업을 한 번 더 수행해야 한다면 아래의 오른쪽 코드와 같이 새로운 쓰레드를 생성한 다음에 start()를 호출해야 한다. 만일 아래 왼쪽의 코드처럼 하나의 쓰레드에 대해 start()를 두 번 이상 호출하면 실행시에 IllegalThreadStateException이 발생한다.

```java
// AS-IS
ThreadEx1_1 t1 = new ThreadEx1_1();
t1.start();
t1.start();     // 예외 발생

// TO-BE
ThreadEx1_1 t1 = new ThreadEx1_1();
t1.start();
t1 = new ThreadEx1_1();
t1.start();
```

## 3. start()와 run()

쓰레드를 실행시킬 때 run()이 아닌 start()를 호출한다는 것에 대해서 다소 의문이 들었을 것이다. 이제 start()와 run()의 차이와 쓰레드가 실행되는 과정에 대해서 자세히 살펴보자.

메인 메서드에서 run()을 호출하는 것은 생성된 쓰레드를 실행시키는 것이 아니라 단순히 클래스에 선언된 메서드를 호출하는 것일 뿐이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/2167aa14-8ba9-4531-8dab-a79723424df2)

반면에 start()는 새로운 쓰레드가 작업을 실행하는데 필요한 호출스택(call stack)을 생성한 다음에 run()을 호출해서, 생성된 호출스택에 run()이 첫 번째로 올라가게 한다.

모든 쓰레드는 독립적인 작업을 수행하기 위해 자신만의 호출스택을 필요로 하기 때문에, 새로운 쓰레드를 생성하고 실행시킬 때마다 새로운 호출스택이 생성되고 쓰레드가 종료되면 작업에 사용된 호출스택은 소멸된다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/9ac1ee31-ebc0-4284-8678-c1361a7382dd)

1. main 메서드에서 쓰레드의 start() 호출
2. start()는 새로운 쓰레드를 생성하고, 쓰레드가 작업하는데 사용될 호출스택 생성
3. 새로 생성된 호출스택에 run()이 호출되어, 쓰레드가 독립된 공간에서 작업 수행
4. 호출스택이 2개이므로 스케쥴러가 정한 순서에 의해서 번갈아 가면서 실행

호출스택에서는 가장 위에 있는 메서드가 현재 실행중인 메서드이고 나머지 메서드들은 대기상태에 있다는 것을 기억하고 있을 것이다. 그러나 위의 그림에서와 같이 쓰레드가 둘 이상일 때는 호출스택의 최상위에 있는 메서드일지라도 대기상태에 있을 수 있다.

스케줄러는 실행대기중인 쓰레드들의 우선순위를 고려하여 실행순서와 실행시간을 결정하고, 각 쓰레드들은 작성된 스케줄러에 따라 자신의 순서가 되면 지정된 시간동안 작업을 수행한다.

이 때 주어진 시간동안 작업을 마치지 못한 쓰레드는 다시 자신의 차례가 돌아올 때까지 대기상태로 있게 되며, 작업을 마친 쓰레드, 즉 run()의 수행이 종료된 쓰레드는 호출스택이 모두 비워지면서 이 쓰레드가 사용하던 호출스택은 사라진다.

이는 마치 자바프로그램을 실행하면 호출스택이 생성되고 main메서드가 처음으로 호출되고, main메서드가 종료되면서 호출스택이 비워지면서 프로그램도 종료되는 것과 같다.


#### main 쓰레드

main메서드의 작업을 수행하는 것도 쓰레이이며, 이를 main 쓰레드라고 한다. 프로그램을 실행하면 기본적으로 하나의 쓰레드를 생성하고, 그 쓰레드가 main메서드를 호출해서 작업이 수행되도록 하는 것이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/257fc670-fa3e-4d13-b175-e0433b859b40)

지금까지는 main 메서드가 수행을 마치면 프로그램이 종료되었으나, 위의 그림에서와 같이 main메서드가 수행을 마쳤다하더라도 다른 쓰레드가 아직 작업을 마치지 않은 상태라면 프로그램이 종료되지 않는다.

> 실행 중인 사용자 쓰레드가 하나도 없을 떄 프로그램은 종료된다.

쓰레드는 '사용자 쓰레드(user thread)'와 '데몬 쓰레드(daemon thread)'가 있다.

> [참고] 사용자 쓰레드(user thread)는 'non-daemon thread'라고도 한다.

```java
public class ThreadEx2 {

    public static void main(String[] args) {
        ThreadEx2_1 t1 = new ThreadEx2_1();
        t1.start();
    }
}

class ThreadEx2_1 extends Thread {
    @Override
    public void run() {
        throwException();
    }

    public void throwException() {
        try {
            throw new Exception();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

```shell
java.lang.Exception
	at chapter13.ThreadEx2_1.throwException(ThreadEx2.java:19)
	at chapter13.ThreadEx2_1.run(ThreadEx2.java:14)
```

새로 실성한 쓰레드에서 고의로 예외를 발생시키고 printStackTrace()를 이용해서 예외가 발생한 당시의 호출스택을 출력하는 예제이다. 호출스택의 첫 번째 메서드가 main메서드가 아니라 run메서드인 것을 확인하자.

한 쓰레드가 예외가 발생해서 종료되어도 다른 쓰레드의 실행에는 영향을 미치지 않느다. 아래의 그림에 main 쓰레드의 호출스택이 없는 이유는 main쓰레드가 종료되었기 때문이다.

![image](https://github.com/bangseongmin/Module-Project/assets/22147400/d3abd63c-a054-488d-8277-92af12fa4ebb)


```java
public class ThreadEx3 {

    public static void main(String[] args) {
        ThreadEx3_1 t1 = new ThreadEx3_1();
        t1.run();
    }
}

class ThreadEx3_1 extends Thread {
    @Override
    public void run() {
        throwException();
    }

    public void throwException() {
        try {
            throw new Exception();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

```shell
java.lang.Exception
	at chapter13.ThreadEx3_1.throwException(ThreadEx03.java:19)
	at chapter13.ThreadEx3_1.run(ThreadEx03.java:14)
	at chapter13.ThreadEx03.main(ThreadEx03.java:7)
```

이전 예제와 달리 쓰레드가 새로 생성되지 않았다. 그저 ThreadEx3_1클래스의 run()이 호출되었을 뿐이다. 아래 그림은 main쓰레드의 호출스택이며, main메서드가 포함되어 있음을 확인하자.

![image](https://github.com/bangseongmin/Module-Project/assets/22147400/3c2dc177-ecac-4a23-a621-0b5d0ecfe76e)


### 4. 싱글쓰레드와 멀티쓰레드

두 개의 작업을 하나의 쓰레드로 처리하는 경우와 두 개의 쓰레드로 처리하는 경우를 가정해보자. 하나의 쓰레드로 두 작업을 처리하는 경우는 한 작업을 마친 후에 다른 작업을 시작하지만, 두 개의 쓰레드로 작업 하는 경우에는 짧은 시간동안 2개의 쓰레드가 번갈아 가면서 작업을 수행해서 동시에 두 작업이 처리되는 것과 같이 느끼게 한다.

![싱글스레드 프로세스와 멀티스레드 프로세스의 비교(싱글 코어)](https://github.com/bangseongmin/Module-Project/assets/22147400/39e5f9cf-1dc3-4106-b179-bb4dfd51029c)

위의 그래프에서 알 수 있듯이 하나의 쓰레드로 두개의 작업을 수행한 시간과 두 개의 쓰레드로 두 개의 작업을 수행한 시간(t2=t2`)은 거의 같다. 오히려 두 개의 쓰레드로 작업한 시간이 싱글쓰레드로 작업한 시간보다 더 걸리게 되는데 그 이유는 쓰레드간의 작업전환(context switching)에 시간이 걸리기 때문이다.

작업 전환을 할 때는 현재 진행 중인 작업의 상태, 예를 들면 다음에 실행햏야할 위치(PC, 프로그램 카운터) 등의 정보를 저장하고 읽어 오는 시간이 소요된다. 참고로 스레드의 스위칭에 비해 프로세스의 스위칭이 더 많은 정보를 저장해야하므로 더 많은 시간이 소요된다.

> [참고] 프로세스 또는 쓰레드 간의 작업 전환을 `컨텍스트 스위칭(context switching)`이라고 한다.

그래서 싱글 코어에서는 단순히 CPU만을 사용하는 계산작업이라면 오히려 멀티쓰레드보다 싱글쓰레드로 프로그래밍하는 것이 더 효율적이다.

```java
public class ThreadEx4 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000_000; i++) {
            System.out.printf("%s", new String("-"));
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("소요시간1: " + (endTime1 - startTime));

        for(int i = 0; i < 1000_000; i++) {
            System.out.printf("%s", new String("|"));
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("소요시간2: " + (endTime2 - endTime1));
    }
}
```

```shell
실행결과1: 1232
실행결과2: 1228
```

새로운 쓰레드를 하나 생성해서 두 개의 쓰레드가 작업을 하나씩 나누어서 수행한 후 실행결과를 비교해보도록 하자.

```java
public class ThreadEx5 {

    static long startTime = 0;

    public static void main(String[] args) {
        ThreadEx5_1 th1 = new ThreadEx5_1();
        th1.start();
        startTime = System.currentTimeMillis();

        for(int i = 0; i< 1_000_000; i++) {
            System.out.printf("%s", new String("-"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간2: " + (endTime - startTime));
    }
}

class ThreadEx5_1 extends Thread {
    @Override
    public void run() {
        for(int i = 0; i< 1_000_000; i++) {
            System.out.printf("%s", new String("|"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간2: " + (endTime - ThreadEx5.startTime));
    }
}
```

```shell
실행결과1: 3555
실행결과2: 2995
```

이전 예제와는 달리 두 작업이 아주 짧은 시간동안 번갈아가면서 실행되었으며 거의 동시에 작업이 완료되었음을 알수 있다.

두 개의 쓰레드로 작업하는데도 더 많은 시간이 걸린 이유는 두 가지이다.

1. 두 쓰레드가 번갈아가면서 작업을 처리하기 때문에 쓰레드간의 작업전환시간이 소요되기 때문
2. 한 쓰레드가 화면에 출력하고 있는 동안 다른 쓰레드는 출력이 끝나기를 기다려야하는데, 이때 발생하는 대기시간 때문이다.

![싱글코어와 멀티코어의 비교](https://github.com/bangseongmin/Module-Project/assets/22147400/fc1c4473-bee3-4bce-aa98-6cc83d7b60d5)

실행 결과를 싱글 코어일 때와 멀티 코어일 때를 비교해 놓았는데, 싱글 코어인 경우에는 멀티쓰레드라도 하나의 코어가 번갈아가면서 작업을 수행하는 것이므로 두 작업이 절대 겹치지 않는다. 그러나, 멀티 코어에서는 멀티 쓰레드로 두 작업을 수행하면, 동시에 두 쓰레드가 수행될 수 있으므로 실행결과 2와 위 그림(b)처럼 두 작업, A와 B가 겹치는 부분이 발생한다. 그래서 화면(console)이라는 자원을 놓고 두 쓰레드가 경쟁하게 되는 것이다.

> [참고] 여러 쓰레드가 여러 작업을 동시에 진행하는 것을 병행(concurrent)라고 하고, 하나의 작업을 여러 쓰레드가 나눠서 처리하는 것을 병렬(parallel)이라고 한다.

위의 결과는 실행할 때마다 다른 겨로가를 얻을 수 잇는데 그 이유는 실행 중인 예제프로그램(프로세스)이 OS의 프로세스 스케쥴러의 영향을 받기 때문이다. JVM의 쓰레드 스케줄러에 의해서 어떤 쓰레드가 얼마동안 실행될 것인지 결정되는 것과 같이 프로세스도 프로세스 스케줄러에 의해서 실행순서와 실행시간이 결정되기 떄문에 매 순간 상황에 따라 프로세스에게 할당되는 실행시간이 일정하지 않고 쓰레드에게 할당되는 시간 역시 일정하지 않게 된다. 그래서 쓰레드가 이러한 불확실성을 가지고 있다는 것을 염두에 두어야 한다.

자바가 OS(플랫폼) 독립적이라고 하지만 실제로는 OS종속적인 부분이 몇 가지 있는데 쓰레드도 그 중의 하나이다.

> [참고] JVM의 종류에 따라 쓰레드 스케줄러의 구현방법이 다를 수 있기 때문에 멀티쓰레드로 작성된 프로그램을 다른 종류의 OS에서도 충분히 테스트해 볼 필요가 있다.

두 쓰레드가 서로 다른 자원을 사용하는 작업의 경우에는 싱글쓰레드 프로세스보다 멀티 쓰레드 프로세스가 더 효율적이다. 예를 들어 사용자로부터 데이터를 입력받는 작업, 네트워크로 파일을 주고받는 작업, 프린터로 파일을 출력하는 작업과 같이 외부기기와의 입출력을 필요로 하는 경우가 이에 해당한다.

![싱글쓰레드 프로세스(위)와 멀티쓰레드 프로세스(아래)의 비교](https://github.com/bangseongmin/Module-Project/assets/22147400/2a818725-73f4-43b1-be7e-256c7a7938b5)

만일 사용자로부터 입력받는 작업(A)과 화면에 출력하는 작업(B)을 하나의 쓰레드로 처리한다면 그림 13-8의 첫번째 그래프처럼 사용자가 입력을 마칠 때까지 아무 일도 하지 못하고 기다리기만 해야한다.

그러나 두 개의 쓰레드도 처리한다면 사용자의 입력을 기다리는 동안 다른 쓰레드가 작업을 처리할 수 있기 때문에 보다 효율적인 CPU의 사용이 가능하다.

작업 A와 작업 B가 모두 종료되는 시간 t2와 t2`를 비교하면 t2 > t2`로 멀티쓰레드 프로세스의 경우가 작업을 더 빨리 마치는 것을 알 수 있다.



## 5. 쓰레드의 우선순위 

쓰레드는 우선순위(priority)라는 속성(멤버변수)을 가지고 있는데, 이 우선순위의 값에 따라 스레드가 얻는 실행시간이 달라진다. 쓰레드가 수행하는 작업의 중요도에 따라 쓰레드의 우선순위를 서로 다르게 지정하여 특정 쓰레드가 더 많은 작업시간을 갖도록 할 수 있다.

예를 들어 파일전송기능이 있는 메신저의 경우, 파일다운로드를 처리하는 쓰레드보다 채팅내용을 전송하는 쓰레드의 우선순위가 높아야 사용자가 채팅하는데 불편함이 없을 것이다. 대신 파일다운로드 작업에 걸리는 시간은 더 길어질 것이다.

이처럼 시각적인 부분이나 사용자에게 빠르게 반응해야하는 작업을 하는 쓰레드의 우선 순위는 다른 작업을 수행하는 쓰레드에 비해 높아야 한다.

#### 쓰레드의 수선순위 지정하기

```java
void setPriority(int newPriority)       // 쓰레드의 우선순위를 지정한 값으로 변경
void getPriority()                      // 쓰레드의 우선순위를 반환

public static final int MAX_PRIORITY = 10;      // 최대 우선순위
public static final int MIN_PRIORITY = 1;       // 최소 우선순위
public static final int NORM_PRIORITY = 5;      // 보통 우선순위
```

쓰레드가 가질 수 있는 우선순위 범위는 1~10이며 숫자가 높을수록 우선순위가 높다. 

한 가지 더 알아두어야 할 것은 쓰레드의 우선순위는 쓰레드를 생성한 쓰레드로부터 상속받는다는 것이다. main메서드를 수행하는 쓰레드는 우선순위가 5이므로 main메서드내에서 생성하는 쓰레드의 우선순위는 자동적으로 5가 된다.

```java
public class ThreadEx8 {

    public static void main(String[] args) {
        ThreadEx8_1 th1 = new ThreadEx8_1();
        ThreadEx8_2 th2 = new ThreadEx8_2();

        th2.setPriority(7);

        System.out.println("Priority of th1(-) : " + th1.getPriority());
        System.out.println("Priority of th2(-) : " + th2.getPriority());

        th1.start();
        th2.start();
    }
}

class ThreadEx8_1 extends Thread {

    @Override
    public void run() {
        for(int i = 0; i < 300; i++) {
            System.out.print("-");
            for(int x = 0; x < 10_000_000; x++) {

            }
        }
    }
}

class ThreadEx8_2 extends Thread {

    @Override
    public void run() {
        for(int i = 0; i < 300; i++) {
            System.out.print("|");
            for(int x = 0; x < 10_000_000; x++) {

            }
        }
    }
}
```

th1과 th2 모두 main 메서드에서 생성하였기 때문에 main메서드를 실행하는 쓰레드의 우선순위인 5를 상속받았다. 그 다으메 th2.setPriority(7)로 th2의 우선순위를 7로 변경한 다음에 start()를 호출해서 쓰레드를 실행시켰다. 이처럼 쓰레드를 실행하기 전에만 우선순위를 변경할 수 있다는 것을 기억하자.

우선순위가 높아지면 한 번에 작업이 끝나버릴 수 있기 때문에 아무 일도 하지 않는 반복문을 추가하여 작업을 지연시켰다.


## 6. 쓰레드 그룹(thread group)

쓰레드 그룹은 서로 관련된 쓰레드를 그룹으로 다루기 우한 것으로, 폴더를 생성해서 관련된 파일들을 함께 넣어서 관리하는 것처럼 쓰레드 그룹을 생성해서 쓰레드를 그룹으로 묶어서 관리할 수 있다.

또한 폴더 안에 폴더를 생성할 수 있듯이 쓰레드 그룹에 다른 쓰레드 그룹을 포함시킬 수 있다. 사실 쓰레드 그룹은 보안상의 이유로 도입된 개념으로, 자신이 속한 쓰레드 그룹이나 하위 쓰레드 그룹은 변경할 수 있지만 다른 쓰레드 그룹의 쓰레드를 변경할 수는 없다. ThreadGroup을 사용해서 생성할 수 있으며, 주요 생성자와 메서드는 다음과 같다.

| 생성자/메서드                                      | 설명                             |
|----------------------------------------------|--------------------------------|
| ThreadGroup(String name)                     | 지정된 이름의 새로운 쓰레드 그룹 생성          |
| ThreadGroup(ThreadGroup parent, String name) | 지정된 쓰레드 그룹에 포함되는 새로운 쓰레드 그룹 생성 |
| int activeCount() | 쓰레드 그룹에 포함된 활성상태에 있는 쓰레드의 수 반환 |
| int activeGroupCount() | 쓰레드 그룹에 포함된 활성상태에 있느 쓰레드 그룹의 수를 반환 |
| void checkAccess() | 현재 실행중인 쓰레드가 쓰레드 그룹을 변경할 권한이 있는지 체크. 만일 권한이 없다면 SecurityException 발생 |
| void destroy() | 쓰레드 그룹과 하위 쓰레드 그룹까지 모두 삭제한다. 단, 쓰레드 그룹이나 하위 쓰레드 그룹이 비어있어야 한다. |
| int enumerate(Thread[] list), int enumerate(Thread[] list, boolean recurse), int enumerate(ThreadGroup[] list), int enumerate(ThreadGroup[] list, boolean recurse) | 쓰레드 그룹에 속한 쓰레드 또는 하위 쓰레드 그룹의 목록을 지정된 배열에 담고 그 개수를 반환. 두 번째 매겨변수인 recurse의 값을 true로 하면 쓰레드 그룹에 속한 하위 쓰레드 그룹에 쓰레드 또는 쓰레드 그룹까지 배열에 담는다. |
| int getMaxPriority() | 쓰레드 그룹의 최대우선순위 반환 | 
| String getName() | 쓰레드 그룹의 이름 반환 |
| ThreadGroup getParent() | 쓰레드 그룹의 상위 쓰레드 그룹을 반환 |
| void interrupt() | 쓰레드 그룹에 속한 모든 쓰레드를 interrupt |
| boolean isDaemon() | 쓰레드 그룹이 데몬 쓰레드 그룹인지 확인 |
| boolean isDestoryed() | 쓰레드 그룹이 삭제되었는지 확인 |
| void list() | 쓰레드 그룹에 속한 쓰레드와 하위 쓰레드그룹에 대한 정보를 출력 |
| boolean parentOf(ThreadGroup g) | 쓰레드 그룹에 속한 쓰레드와 하위 쓰레드 그룹에 대한 정보 출력 |
| void setDaemon(boolean daemon) | 쓰레드 글부을 데몬 쓰레드그룹으로 설정/해제 |
| void setMaxPriority(int priority) | 쓰레드 그룹의 최대우선순위 설정 |

쓰레드를 쓰레드 그룹에 포함시키려면 Thread의 생성자를 이용해야 한다.

```java
Thread(ThreadGroup group, String name);
Thread(ThreadGroup group, Runnable target);
Thread(ThreadGroup group, Runnable target, String name);
Thread(ThreadGroup group, Runnable target, String name, long stackSize);
```

모든 쓰레드는 반드시 쓰레드 그룹에 포함되어 있어야 하기 때문에, 위와 같이 쓰레드 그룹을 지정하는 생성자를 사용하지 않은 쓰레드는 기본적으로 자신을 생성한 쓰레드와 같은 쓰레드 그룹에 속하게 된다.

자바 어플리케이션이 실행되면, JVM은 main과 system이라는 쓰레드 그룹을 만들고 JVM운영에 필요한 쓰레드들을 생성해서 이 쓰레드 그룹에 포함시킨다. 예를 들어 main 메서드를 수행하는 main이라는 이름의 쓰레드는 main쓰레드 그룹에 속하고, 가비지 컬렉션을 수행하는 Finalizer쓰레드는 system쓰레드 그룹에 속한다.

우리가 생성하는 모든 쓰레드 그룹은 main쓰레드 그룹의 하위 쓰레드 그룹이 되며, 쓰레드 그룹을 지정하지 않고 생성한 쓰레드는 자동적으로 main쓰레드 그룹에 속하게 된다.

그 외에 Thread의 쓰레드 그룹과 관련된 메서드는 다음과 같다.

```java
ThreadGroup getThreadGroup();       // 쓰레드 자신이 속한 쓰레드 그룹 반환
void uncaughtException(Thread t, Throwable e) // 쓰레드 그룹의 쓰레드가 처리되지 않은 예외에 의해 실행이 종료되었을 때, JVM에 의해 이 메서드가 자동적으로 호출된다.
```

## 7. 데몬 쓰레드(Daemon Thread)

데몬 쓰레드는 다른 일반 쓰레드(데몬 쓰레드가 아닌 쓰레드)의 작업을 돕는 보조적인 역할을 수행하는 쓰레드이다. 일반 쓰레드가 모두 종료되면 데몬 쓰레드는 강제적으로 자동 종료되는데, 그 이유는 데몬 쓰레드는 일반 쓰레드의 보조역할을 수행하므로 일반 쓰레드가 모두 종료되고 나면 데몬 쓰레드의 존재의 의미가 없기 때문이다.

이 점을 제외하고는 데몬 쓰레드와 일반 쓰레드는 다르지 않다. 데몬 쓰레드의 예로는 가비지 컬렉터, 워드프로세서의 자동저장, 화면자동갱신 등이 있다.

데몬 쓰레드는 무한루프와 조건문을 이용해서 실행 후 대기하고 있다가 특정 조건이 만족되면 작업을 수행하고 다시 대기하도록 작성한다.

데몬 쓰레드는 일반 쓰레드의 작성방법과 실행방법이 같으며 다만 쓰레드를 생성한 다음 실행하기 전에 setDaemon(true)를 호출하기만 하면 된다. 그리고 데몬 쓰레드가 생성한 쓰레드는 자동적으로 데몬 쓰레드가 된다는 점도 알아두자.

```java
boolean isDaemon()      // 스레드가 데몬 쓰레드인지 확인한다. 데몬쓰레드이면 true를 반환
void setDaemon(boolean on)  // 쓰레드를 데몬 쓰레드로 또는 사용자 쓰레드로 변경한다. 매개변수 on의 값을 true로 지정하면 데몬 쓰레드가 된다.
```


## 8. 쓰레드의 실행제어

쓰레드 프로그래밍이 어려운 이유는 동기화(synchronization)와 스케줄링(scheduling) 때문이다. 앞서 우선순위를 통해 쓰레드간의 스케줄링을 하는 방법을 배우기는 했지만, 이것만으로는 한참 부족하다. 효율적인 멀티쓰레드 프로그램을 만들기 위해서는 보다 정교한 스케줄링을 통해 프로세스에게 주어진 자원과 시간을 여러 쓰레드가 낭비없이 잘 사용하도록 해야 한다.

쓰레드의 스케줄링을 잘하기 위해서는 쓰레드의 상태와 관련 메서드를 잘 알아야 하는데, 먼저 쓰레드의 스케줄링과 관련된 메서드는 다음과 같다.

| 메서드 | 설명 |
| --- | --- |
| static void sleep(long millis), static void sleep(long millis, int nanos) | 지정된 시간(천분의 일초 단위)동안 쓰레드를 일시정지시킨다. 지정한 시간이 지나고 나면, 자동적으로 다시 실행대기상태가 된다. |
| void join(), void join(long millis), void join(long millis, int nanos) | 지정된 시간동안 쓰레드가 실행되도록 한다. 지정된 시간이 지나거나 작업이 종료되면 join()을 호출한 쓰레드로 다시 돌아와 실행을 계속한다. |
| void interrupt() | sleep()이나 join()에 의해 일시정지상태인 쓰레드를 깨워서 실행대기상태로 만든다. 해당 쓰레드에서는 interruptedException이 발생함으로써 일시정지상태를 벗어나게 한다. |
| void stop() | 쓰레드를 즉시 종료시킨다. |
| void suspend() | 쓰레드를 일시정지시킨다. resume()을 호출함녀 다시 실행대기상태가 된다. |
| void resume() | suspend()에 의해 일시정지상태에 있는 쓰레드를 실행대기상태로 만든다. |
| static void yield() | 실행 중에 자신에게 주어진 실행시간을 다른 쓰레드에게 양보(yield)하고 자신은 실행대기상태가 된다. |

> [참고] resume(), stop(), suspend()는 쓰레드를 교착상태(dead-lock)로 만들기 쉽기 때문에 deprecated되었다.

| 상태 | 설명 |
| --- | --- |
| NEW | 쓰레드가 생성되고 아직 start()가 호출되지 않은 상태 |
| RUNNABLE | 실행 중 또는 실행 가능한 상태 |
| BLOCKED | 동기화블럭에 의해서 일시정지된 상태(lock이 풀릴 때까지 기다리는 상태) |
| WAITING, TIMED_WAITING | 쓰레드의 작업이 종료되지는 않았지만 실행가능하지 않은(unrunnable) 일시정지 상태, TIMED_WAITING은 일시정지시간이 지정된 경우를 의미한다. |
| TERMINATED | 쓰레드의 작업이 종료된 상태 |


다음 그림은 쓰레드의 생성부터 소멸까지의 모든 과정을 그린 것인데, 앞서 소개한 메서드들에 의해서 쓰레드의 상태가 어떻게 변화되는지를 보여준다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/ea3586c1-6522-47b6-9078-5dbbeabc287f)

1. 쓰레드를 생성하고 start()를 호출하면 바로 실행되는 것이 아니라 실행대기열에 저장되어 자신의 차례가 될 때까지 기다려야 한다. 실행대기열은 큐와 같은 구조로 먼저 실행대기열에 들어온 쓰레드가 먼저 실행된다. 
2. 실행대기상태에 있다가 자신의 차례가 되면 실행상태가 된다. 
3. 주어진 실행시간이 다 되거나 yield()를 만나면 다시 실행대기상태가 되고 다음 차례의 쓰레드가 실행 상태가 된다. 
4. 실행 중에 suspend(), sleep(), wait(), join(), I/O block에 의해 일시정지 상태가 될 수 잇다. I/O block은 입출력작업에서 발생하는 지연상태를 말한다. 사용자의 입력을 기다리는 경우를 예로 들 수 잇는데, 이런 경우 일시정지 상태에 있다가 사용자가 입력을 마치면 다시 실행대기 상태가 된다. 
5. 지정된 일시정지 시간이 다 되거나(time-out), notify(), resume(), interrupt()가 호출되면 일시정지상태를 벗어나 다시 실행대기열에 저장되어 자신의 차례를 기다리게 된다. 
6. 실행을 모두 마치거나 stop()이 호추로디면 쓰레드는 소멸한다.

> [참고] 설명을 위해 1부터 6까지 번호를 붙이기는 했지만 번호의 순서대로 쓰레드가 수행되는 것은 아니다.


#### sleep(long millis) - 일정시간동안 쓰레드를 멈추게 한다.

sleep()은 지정된 시간동안 쓰레드를 멈추게 한다.

```java
static void sleep(long millis);
static void sleep(long millis, int nanos);
```

밀리세컨드(millis, 1000분의 일초)와 나노세컨드(nanos, 10억분의 일초)의 시간단위로 세밀하게 값을 지정할 수 있지만 어느 정도의 오차가 발생할 수있다는 것은 염두에 둬야한다. 예를 들어, 쓰레드가 0.0015초 동안 멈추게 하려면 다음과 같이 한다.

> [참고] 나노세컨드(nanos)로 지정할 수 있는 값의 범위는 0 ~ 999999이며, 999999나노세컨드는 약 1밀리세컨드이다.

```java
try {
    Thread.sleep(1, 500000);
} catch(InterruptedException e) {
    
}
```

sllep()에 의해 일시정지 상태가 된 쓰레드는 지정된 시간이 다 되거나 interrupt()가 호출되면, InterruptedException이 발생되어 잠에서 깨어나 실행되기 상태가 된다. 그래서 sleep()을 호출할 때는 항상 try-catch문으로 예외를 처리해줘야 한다. 매번 예외처리를 해주는 것이 번거롭기 때문에, 아래와 같이 try-catch문까지 포함하는 새로운 메서드를 만들어서 사용하기도 한다.

```java
void delay(long millis) {
    try {
        Thread.sleep(millis);    
    } catch(InterruptedException e) {}
}
```

#### interrupt()와 interrupted() - 쓰레드의 작업을 취소한다.

진행 중인 쓰레드의 작업이 끝나기 전에 취소시켜야할 때가 있다. 예를 들어 큰 파일을 다운로드받을 때 시간이 너무 오래 걸리면 중간에 다운로드를 포기하고 취소할 수 있어야 한다. interrupt()는 쓰레드에게 작업을 멈추라고 요청한다. 단지 멈추라고 요청만 하는 것일뿐 쓰레드를 강제로 종료시키지는 못한다. interrupt()는 그저 쓰레드의 interrupted상태(인스턴스 변수)를 바꾸는 것일 뿐이다.

그리고 interrupted()는 쓰레드에 대해 interrupt()가 호출되었는지 알려준다. interrupt()가 호출되지 않았다면 false를, interrupt()가 호출되었다면 true를 반환한다.

```java
Thread th = new Thread();
th.start();
...
th.interrupt();     // 쓰레드 th에 interrupt();

class MyThread extends Thread {
    public void run() {
        while(!interrupted()) {     // interrupted()의 결과가 false인 동안 반복
           ...
        }
    }
}
```

interrupt()가 호출되면, interrupted()의 결과가 false에서 true로 바뀌어 while문을 벗어나게 된다. while문의 조건식에 '!'가 포함되어 있는 것에 주의하자.

isInterrupted()도 쓰레드의 interrupt()가 호출되었는지 확인하는데 사용할 수 있지만, interrupted()와 달리 isInterrupted()는 쓰레드의 interrupted상태를 false로 초기화하지 않는다.