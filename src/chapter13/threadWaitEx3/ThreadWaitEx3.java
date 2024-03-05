package chapter13.threadWaitEx3;

import java.util.ArrayList;

/**
 * 테이블 객체의 waiting pool에 요리사 쓰레드와 손님 쓰레드가 같이 기다리는 문제 존재함.
 * notify()가 호출되었을 때, 요리사 쓰레드와 손님 쓰레드 중에서 누가 통지를 받을지 알 수 없다.
 * 운 좋게 요리사 쓰레드가 통지를 받으면 다행이지만, 손님 쓰레드가 통지를 받으면 lock을 얻어도
 * 여전히 자신이 원하는 음식이 없어서 다시 waiting pool에 들어가게 된다.
 */
class ThreadWaitEx3 {
    public static void main(String[] args) throws Exception {
        Table table = new Table();

        new Thread(new Cook(table), "COOK1").start();
        new Thread(new Customer(table, "donut"), "CUSTOMER1").start();
        new Thread(new Customer(table, "burger"), "CUSTOMER2").start();

        Thread.sleep(5000);
        System.exit(0);
    }
}

class Customer implements Runnable {
    private Table table;
    private String food;

    Customer(Table table, String food) {
        this.table = table;
        this.food = food;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            String name = Thread.currentThread().getName();

            table.remove(food);
            System.out.println(name + " ate a " + food);
        }
    }
}

class Cook implements Runnable {
    private Table table;

    Cook(Table table) {
        this.table = table;
    }

    public void run() {
        while (true) {
            int idx = (int)(Math.random() * table.dishNum());
            table.add(table.dishNames[idx]);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }
}

class Table {
    String[] dishNames = { "donut", "donut", "burger" };
    final int MAX_FOOD = 6;

    private ArrayList<String> dishes = new ArrayList<>();

    public synchronized void add(String dish) {
        while (dishes.size() >= MAX_FOOD) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting.");
            try {
                wait();	// COOK 스레드를 기다리게 한다.
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
        dishes.add(dish);
        notify();	// 기다리고 있는 CUST 스레드를 깨운다.
        System.out.println("Dishes : " + dishes.toString());
    }

    public void remove(String dishName) {
        synchronized(this) {
            String name = Thread.currentThread().getName();

            while (dishes.size() == 0) {
                System.out.println(name + " is waiting.");
                try {
                    wait();	// CUST 스레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
            while(true) {
                for (int i=0; i<dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        notify();	// 기다리고 있는 COOK 스레드를 꺠운다.
                        return;
                    }
                }

                try {
                    System.out.println(name + " is waiting.");
                    wait();	// 원하는 음식이 없는 CUST 스레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        }
    }

    public int dishNum() {
        return dishNames.length;
    }

    public int getBalance() {
        long stamp = lock.tryOptimisticRead();      //  낙관적 읽기 lock을 건다.

        int curBalance = this.balance;              // 공유 데이터인 balance를 읽어온다.

        if(lock.validate(stamp)) {                  // 쓰기 lock에 의해 낙관적 읽기 lock이 풀렸는지 확인
            stamp = lock.readLock();                // lock이 풀렸으면, 읽기 lock을 얻으려고 대기

            try {
                curBalance = this.balance;
            }finally {
                lock.unlockRead(stamp);             // 읽기 lock을 푼다.
            }
        }

        return curBalance;                          // 낙관적 읽기 lock이 풀리지 않았으면 곧바로 읽어온 값 반환
    }
}
