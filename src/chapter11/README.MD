# Chatper 11. 컬렉션 프레임워크

## 1. 컬렉션 프레임워크(Collection Framework)

컬렉션 프레임워크란 '데이터 군을 저장하는 클래스들을 표준화한 설계'를 뜻한다. 컬렉션(Collection)은 다수의 데이터, 즉 데이터 그룹을, 프레임워크는 표준화된 프로그래밍 방식을 의미한다.

> [참고] Java API문서에서는 컬렉션 프레임우커는 '데이터 군(group)을 다루고 표현하기 위한 단일화된 구조(architecture)'라고 정의하고 있다.

JDK 1.2이전까지는 Vector, Hashtable, Properties와 같은 컬렉션 클래스, 다수의 데이터를 저장할 수 있는 클래스들을 서로 다른 각자의 방식으로 처리해야 했으나 JDK1.2부터 컬렉션 프레임워크가 등장하면서 다양한 종류의 컬렉션 클래스가 추가되고 모든 컬렉션 클래스를 표준화된 방식으로 다룰 수 있도록 체계화되었다.

> [참고] 앞으로 Vector와 같이 다수의 데이터를 저장할 수 있는 클래스를 `컬렉션 클래스`라고 하겠다.

컬렉션 프레임워크는 컬렉션, 다수의 데이터를 다루는데 필요한 다양하고 풍부한 클래스들을 제공하기 때문에 프로그래머의 짐을 상당히 덜어 주고 있으며, 또한 인터페이스와 다형성을 이용한 객체지향적 설게를 통해 표준화되어 있기 때문에 사용법을 익히기에도 편리하고 재사용성이 높은 코드를 작성할 수 있다는 장점이 있다.

### 1.1 컬렉션 프레임워크의 핵심 인터페이스

컬렉션 프레임워크에서는 컬렉션데이터 그룹을 크게 3가지 타입이 존재한다고 인식하고 컬렉션을 다루는데 필요한 기능을 가진 3개의 인터페이스를 정의하였다. 그리고 인터페이스 List와 Set의 공통된 부분을 다시 뽑아서 새로운 인터페이스인 Collection을 추가로 정의하였다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/ebdda4ff-db47-4659-9790-a3cfc8c82c4d)

인터페이스 List와 Set을 구현한 컬렉션 클래스들은 서로 많은 공통부분이 있어서, 공통된 부분을 다시 뽑아 Collection인터페이스를 정의할 수 있었지만 Map인터페이스는 이들과는 전혀 다른 형태로 컬렉션을 다루기 때문에 같은 상속계층도에 포함되지 못했다.

이러한 설계는 객체지향언어의 장점을 극명히 보여주는 것으로 객체지향개념을 학습하는 사람들에게 많은 것을 느끼게 한다. 후에 프로그래밍 실력을 어느 정도 갖추게 되었을 때 컬렉션 프레임워크의 실제 소스를 분석해보면 객체지향적인 설계능력을 향상시키는데 많은 도움이 될 것이다.

> [참고] JDK1.5부터 Iterable인터페이스가 이를 Collection 인터페이스가 상속받도록 변경되었으나 이것은 단지 인터페이스들의 공통적인  메서드인 iterator()를 뽑아서 중복을 제거하기 위한 것에 불과하므로 상속계층도에서 별 의미가 없다.

| 인터페이스 | 특징 | 구현 클래스 |
| --- | --- | --- |
| List | 순서가 있는 데이터 집합, 데이터의 중복을 허용한다. (예시: 대기자 명단) | ArrayList, LinkedList, Stack, Vector 등 |
| Set | 순서를 유지하지 않는 데이터의 집합, 데이터의 중복을 허용하지 않는다. (예: 양의 정수집합, 소수의 집합) | HashSet, TreeSet |
| Map | 키(key)와 값(value)의 쌍(pair)으로 이루어진 데이터의 집합으로 순서는 유지되지 않으며, 키는 중복을 허용하지 않고, 값은 중복을 허용한다. (예시. 유편번호, 지역번호(전화번호)) | HashMap, TreeMap, Hashtable, Properties 등 |

> [참고] 키(key)란, 데이터 집합 중에서 어떤 값(value)를 찾는데 열쇠(key)가 된다는 의미에서 붙여진 이름이다. 그래서 키(Key)는 중복을 허용하지 않는다.


실제 개발 시에는 다루고자 하는 컬렉션의 특징을 파악하고 어떤 인터페이스를 구현한 컬렉션 클래스를 사용해야하는지 결정해야하므로 각 인터페이스의 특징과 차이를 잘 이해하고 있어야 한다.

컬렉션 프레임워크의 모든 컬렉션 클래스들은 List, Set, Map 중의 하나를 구현하고 있으며, 구현한 인터페이스의 이름이 클래스의 이름에 포함되어있어서 이름만으로도 클래스의 특징을 쉽게 알 수 있도록 되어있다.

그러나 Vector, Stack, Hashtable, Properties와 같은 클래스들은 컬렉션 프레임워크가 만들어지기 이전부터 존재하던 것이기 때문에 컬렉션 프레임워크의 명명법을 따지 않는다.

Vector나 Hashtable과 같은 기존의 컬렉션 클래스들은 호환을 위해, 설계를 변경해서 남겨두었지만 가능하면 사용하지 않는 것이 좋다. 그 대신 새로 추가된 ArrayList와 HashMap을 사용하자.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/fb311ce9-6442-466f-b08e-059b86b40a3d)

#### Collection 인터페이스

List와 Set의 조상인 Collection 인터페이스에는 다음과 같은 메서드들이 정의되어 있다.

| 메서드 | 설명 |
| --- | --- | 
| boolean add(Object o), boolean addAll(Object o) | 지정된 객체(o)는 또는 Collection(c)의 객체들을 Collection에 추가한다. |
| void clear() | Collection의 모든 객체를 삭제한다. |
| boolean contains(Object o), boolean containsAll(Collection c) | 지정된 객체(o) 또는 Collection의 객체들이 Collection에 포함되어 있는지 확인한다. |
| boolean equals(Object o) | 동일한 Collection인지 비교한다. |
| int hashCode() | Collection의 hash code를 반환한다. |
| boolean isEmpty() | Collection이 비어있는지 확인한다. |
| Iterator iterator() | Collection의 Iterator를 얻어서 반환한다. |
| boolean remove(Object o) | 지정된 객체를 삭제한다. |
| boolean removeAll(Collection c) | 지정된 Collection에 포함된 객체들을 삭제한다. |
| boolean retainAll(Collection c) | 지정된 Collection에 포함된 객체만 남기도 다른 객체들은 Collection에서 삭제한다. 이 작업으로 인해 Collection에 변화가 있으면 true를 그렇지 않으면 false를 반환한다. |
| int size() | Collection에 저장된 객체의 개수를 반환한다. |
| Object[] toArray() | Collection에 저장된 객체를 객체배열(Object[])로 반환한다. |
| Object[] toArray(Object[] a) | 지정된 배열에 Collection의 객체를 저장해서 반환한다. |

> [참고] JDK1.8부터 추가된 parallelStream, removeIf, stream, forEach 등은 람다와 스트림에서 설명한다.
> [참고] Iterable인터페이스는 컬렉션에 포함된 객체들에 접근할 수 있는 방법을 제공한다. 

Collection 인터페이스는 컬렉션 클래스에 저장된 데이터를 읽고, 추가하고 삭제하는 등 컬렉션을 다루는데 필요한 기본적인 메서드들을 정의하고 있다.

반환 타입이 boolean인 메서드들은 작업을 성공하거나 사실이면 true를, 그렇지 않으면 false를 반환한다.

예를 들어 `boolean add(Object o)`를 사용해서 객체를 컬렉션에 추가할 때, 성공하면 true를, 실패하면 false를 반환한다.  `boolean isEmpty()`를 사용해서 컬렉션에 포함된 객체가 없으면, 즉 컬렉션이 비어있으면 true를, 그렇지 않으면 false를 반환한다.

이 외에도 JDK1.8부터 추가된 `람다(Lambda)와 스트림(Stream)`에 관련된 메서드들이 더 존재한다.

그리고 Java API를 보면, 위 표에 사용된 'Object'가 아닌 'E'로 표기되어있는데, E는 특정 타입을 의미하는 것으로 JDK 1.5부터 추가된 제네릭(Generics)에 의한 표기이다.('E'는 'Object'로 표기하고, 'E'외에는 'I', 'K', 'V'를 사용하는 경우도 있는데 모두 Object 타입이라고 이해하자.)


#### List 인터페이스

List 인터페이스는 **중복을 허용**하면서 **저장순서**가 유지되는 컬렉션을 구현하는데 사용된다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/21c3dd34-ea19-4b51-9c54-48b3c2dc964a)

List 인터페이스에 정의된 메서드는 다음과 같다. Collection 인터페이스로부터 상속받은 메서드는 제외하였다.

| 메서드 | 설명 |
| --- | --- |
| void add(int index, Object element), boolean addAll(int index, Collection c) | 지정된 위치(index)에 객체(element) 또는 컬렉션에 포함된 객체들을 추가한다. |
| Object get(int index) | 지정된 위치(index)에 있는 객체를 반환한다. |
| int indexOf(Object o) | 지정된 객체의 위치(index)를 반환한다. |
| int lastIndexOf(Object o) | 지정된 객체의 위치(index)를 반환한다. |
| ListIterator listIterator(), ListIterator listIterator(int index) | List의 객체에 접근할 수 있는 ListIterator를 반환한다. |
| Object remove(int index) | 지정된 위치(index)에 있는 객체를 삭제하고 삭제된 객체를 반환한다. |
| Object set(int index, Object element) | 지정된 위치(index)에 객체(element)를 저장한다. |
| void sort(Comparator c) | 지정된 비교자(comparotor)로 List를 정렬한다. |
| List subList(int fromIndex, toIndex) | 지정된 범위(fromIndex부터 toIndex)에 있는 객체를 반환한다. |


#### Set 인터페이스

Set 인터페이스는 중복을 허용하지 않고 저장순서가 유지되지 않는 컬렉션 클래스를 구현하는데 사용된다. Set 인터페이스를 구현한 클래스로는 HashSet, TreeSet, LinkedHashSet 등이 있다.

![set](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/4cb60818-0176-4b66-88b2-d30af75e4103)


#### Map 인터페이스

Map 인터페이스는 키(Key)와 값(Value)을 하나의 쌍으로 묶어서 저장하는 컬렉션 클래스를 구현하는 데 사용된다. 키는 중복될 수 없지만 값은 중복을 허용한다. 기존에 저장된 데이터와 중복된 키와 값을 저장하면 기존의 값은 없어지고 마지막에 저장된 값이 남게 된다. Map 인터페이스를 구현한 클래스로는 Hashtable, HashMap, LinkedHashMap, SortedMap, TreeMap 등이 있다.

> [참고] Map 이란 개념은 어떤 두 값을 연결한다는 의미에서 붙여진 이름이다.

![map](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/d4548325-682a-420a-ae86-ec7603d38a93)


| 메서드 | 설명 |
| --- | --- |
| void clear() | Map의 모든 객체를 삭제한다. |
| boolean containsKey(Object key) | 지정된 key객체와 일치하는 Map의 key 객체가 있는지 확인 |
| boolean containsValue(Object value) | 지정된 value의 객체와 일치하는 Map의 value객체가 있는지 확인 |
| Set entrySet() | Map에 저장되어 있는 key-value쌍을 Map.Entry타입의 객체로 저장한 Set으로 반환 |
| boolean equals(Object o) | 동일한 Map인지 비교 |
| Object get(Object key) | 지정한 key 객체에 대응하는 value 객체를 찾아서 반환 |
| int hashCode() | 해시코드 반환 |
| boolean isEmpty() | Map이 비어있는지 확인 |
| Set keySet() | Map에 저장된 모든 key 객체 반환 |
| Object put(Object key, Object value) | Map에 value객체를 key 객체에 연결(mapping)하여 저장 |
| void putAll(Map t) | 지정된 Map의 모든 key-value쌍을 추가한다.
| Object remove(Object key) | 지정한 key와 일치하는 key-value객체를 반환한다. |
| int size() | Map에 저장된 key-value쌍의 개수를 반환한다. |
| Collection values() | Map에 저장된 모든 value 객체를 반환 |


values()에서 반환타입이 Collection이고, keySet()에서는 반환타입이 Set인 것에 주목하자. Map 인터페이스에서 값(value)은 중복을 허용하기 때문에 Collection타입으로 반환하고, 키(key)는 중복을 허용하지 않기 때문에 Set 타입으로 반환한다.

#### Map.Entry 인터페이스

Map.Entry 인터페이스는 Map 인터페이스의 내부 인터페이스다. 내부 클래스와 같이 인터페이스도 인터페이스 안에 인터페이스를 정의하는 내부 인터페이스(inner interface)를 정의하는 것이 가능핟.

Map에 저장되는 key-value쌍을 다루기 위해 내부적으로 Entry 인터페이스를 정의해 놓았다. 이것은 보다 객체지향적으로 설계하도록 유도하기 위한 것으로 Map 인터페이스를 구현하는 클래스에서는 Map.Entry 인터페이스도 함께 구현해야한다.

다음은 Map 인터페이스의 소스코드의 일부이다.

```JAVA
public interface Map<K, V> {
   
    interface Entry<K, V> {
        K getKey();     
        V getValue();   
        V setValue(V value);
        boolean equals(Object o);
        int hashCode();

        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }
}

```

### 1.2 ArrayList

ArrayList는 컬렉션 프레임워크에서 가장 많이 사용된 컬렉션 클래스일 것이다. ArrayList는 List인터페이스를 구현하기 때문에 데이터의 저장순서가 유지되고 중복을 허용한다는 특징을 갖는다.

ArrayList는 기존의 Vector를 개선한 것으로 Vector의 구현원리와 기능적인 측면에서 동일하다고 할 수 있다. 앞에서 얘기했던 것과 같이 Vector는 기존에 작성된 소스와의 호환성을 위해서 계속 남겨 두고 있을 뿐이기 때문에 가능하면 Vector보다는 ArrayList를 사용하자.

ArrayList는 Object 배열을 이용해서 데이터를 순차적으로 저장한다. 예를 들면, 첫 번째 저장한 객체는 Object 배열의 0번째 위치에 저장되고 그 다음에 저장하는 객체는 1번째에 저장된다. 이런식으로 계속 배열에 순서대로 저장되며, 배열에 더 이상 저장할 공간이 없으면 보다 큰 새로운 배열을 생성해서 기존의 배열에 저장된 내용을 새로운 배열로 복사한 다음에 저장된다.

```JAVA
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    transient Object[] elementData; // non-private to simplify nested class access
}
```

> [참고] transient는 Serialize하는 과정에 제외하고 싶은 경우 선언하는 키워드

위의 코드는 ArrayList의 소스코드 일부인데 ArrayList는 elementData라는 이름의 Object배열을 멤버변수로 선언하고 있따는 것을 알 수 있다. 선언된 배열의 타입이 모든 객체의 최고조상인 Object이기 때문에 모든 종류의 객체를 담을 수 있다.

| 메서드 | 설명 |
| --- | --- |
| ArrayList() | 크기가 10인 ArrayList 생성 |
| ArrayList(Collection c) | 주어진 컬렉션이 저장된 ArrayList를 생성 |
| ArrayList(int initialCapacity) | 지정된 초기용량을 갖는 ArrayList를 생성 |
| boolean add(Object o) | ArrayList의 마지막에 객체를 추가, 성공하면 true |
| void add(int index, Object element) | 지정된 위치(index)에 객체를 저장 |
| boolean addAll(Collection c) | 주어진 컬렉션의 모든 객체를 저장 |
| boolean addAll(int index, Collection c) | 지정된 위치부터 주어진 컬렉션의 모든 객체를 저장한다. |
| void clear() | ArrayList를 완전히 비운다. |
| Object clone() | ArrayList를 복제한다. |
| boolean contains(Object o) | 지정된 객체(o)가 ArrayList에 포함되어 있는지 확인
| void ensureCapacity(int minCapacity) | ArrayList의 용량이 최소한 minCapacity가 되도록 한다. |
| Object get(int index) | 지정된 위치(index)에 저장된 객체를 반환 |
| int indexOf(Object o) | 지정된 객체가 저장된 위치 반환 |
| boolean isEmpty() | ArrayList가 비어있는지 확인 | 
| Iterator iterator() | ArrayList의 Iterator객체 반환 |
| int lastIndexOf(Object o) | 객체(o)가 저장된 위치를 끝부터 역방향으로 검색해서 반환 |
| ListIterator listIterator() | ArrayList의 ListIterator를 반환 |
| ListIterator listIterator() | ArrayList의 지정된 위치부터 시작하는 ListIterator를 반환 |
| Object remove(int index) | 지정된 위치(index)부터 시작하는 ListIterator를 반환
| boolean remove(Object o) | 지정한 객체를 제거한다(성공하면 true, 실패하면 false) |
| boolean removeAll(Collection c) | 지정한 컬렉션에 저장된 것과 동일한 객체들을 ArrayList에서 제거 |
| Object set(int index, Object element) | 주어진 객체(element)를 지정한 위치(index)에 저장한다.
| int size() | ArrayList에 저장된 객체의 개수를 반환 |
| void sort(Comparator c) | 지정된 정렬기준(c)으로 ArrayList를 정렬 |
| List subList(int fromIndex, int toIndex) | fromIndex부터 toIndex사이에 저장된 객체를 반환 |
| Object[] toArray() | ArrayList에 저장된 모든 객체들을 객체배열로 반환 |
| Object[] toArray(Object[] a) | ArrayList에 저장된 모든 객체들을 객체배열 a에 담아 반환한다 |
| void trimToSize() | 용량을 크기에 맞춰 줄인다.(빈 공간을 없앤다.) |

ArrayList를 생성할 때, 저장할 요소의 개수를 고려해서 실제 저장할 개수보다 약간 여유 있는 크기로 하는 것이 좋다. 생성할 때 지정한 크기보다 더 많은 객체를 저장하면 자동적으로 크기가 늘어나기는 하지만 이 과정에서 처리시간이 많이 소요되기 때문이다.

ArrayList나 Vector 같이 배열을 이용한 자료구조는 데이터를 읽어오고 저장하는 데는 효율이 좋지만, 용량을 변경해야할 떄는 새로운 배열을 생성한 후 기존의 배열로부터 새로 생성된 배열로 데이터를 복사해야하기 때문에 상당히 효율이 떨어진다는 단점을 가지고 있다. 그래서 처음에 인스턴스를 생성할 때, 저장할 데이터의 개수를 잘 고려하여 충분한 용량의 인스턴스를 생성하는 것이 좋다.


> [참고] 인터페이스를 구현할 떄 인터페이스에 정의된 모든 메서드를 구현해야 한다. 일부 메서드만 구현했다면 추상클래스로 선언해야한다. 그러나 JDK1.8부터 List 인터페이스에 3개의 디폴트 메서드가 추가되었으며, 이 들은 구현하지 않아도 된다.


### 1.3 LinkedList

배열은 가장 기본적인 형태의 자료구조로 구조가 간단하며 사용하기 쉽고 데이터를 읽어오는데 걸리는 시간(접근시간, access time)이 가장 빠르다는 장점을 가지고 있지만 다음과 같은 단점을 가지고 있다.

> 1. **크기를 변경할 수 없다.**
>
>       - 크기를 변경할 수 없으므로 새로운 배열을 생성해서 데이터를 복사해야한다.
>       - 실행속도를 향상시키기 위해서는 충분히 큰 크기의 배열을 생성해야 하므로 메모리가 낭비된다.
>
> 2. **비순차적인 데이터의 추가 또는 삭제에 시간이 많이 걸린다.**
>
>       - 차례대로 데이터를 추가하고 마지막에서부터 데이터를 삭제하는 것은 빠르지만 배열의 중간에 데이터를 추가하려면 빈자리를 만들기 위해 다른 데이터들을 복사해서 이동해야 한다.

이러한 배열의 단점을 보완하기 위해서 링크드 리스트(linked list)라는 자료구조가 고안되었다. 배열은 모든 데이터가 연속적으로 존재하지만 링크드 리스트는 불연속적으로 존재하는 데이터를 서로 연결(link)한 형태로 구성되어 있다.

링크드 리스트의 각 요소(node)들은 자신과 연결된 다음 요소에 대한 참조(주소값)와 데이터로 구성되어 있다.

```JAVA
class Node {
    Node next;
    Object obj;
}
```

링크드 리스트에서 데이터 삭제는 간단하다. 삭제하고 하는 요소의 이전요소가 삭제하고자 하는 요소의 다음 요소를 참조하도록 변경하기만 하면 된다. 단 하나의 참조만 변경하면 삭제가 이루어지는 것이다. 배열처럼 데이터를 이동하기 위해 복사하는 과정이 없기 때문에 처리속도가 매우 빠르다.

새로운 데이터를 추가할 때는 새로운 요소를 생성한 다음 추가하고자 하는 위치의 이전 요소의 참조를 새로운 요소에 대한 참조로 변경해주고, 새로운 요소가 그 다음 요소를 참조하도록 변경하기만 하면 되므로 처리속도가 빠르다.

링크드 리스트는 이동방향이 단방향이기 때문에 다음 요소에 대한 접근은 쉽지만 이전요소에 대한 접근은 어렵다. 이 점을 보완한 것이 더블 링크드 리스트(이중 연결리스트, doubly linked list)이다.

더블 링크드 리스트는 단순히 링크드 리스트에 참조변수를 하나 더 추가하여 다음 요소에 대한 참조뿐 아니라 이전 요소에 대한 참조가 가능하도록 했을 뿐, 그 외에는 링크드 리스트와 같다.

더블 링크들 리스트는 링크드 리스트보다 각 요소에 대한 접근과 이동이 쉽기 때문에 링크드 리스트보다 더 많아 사용된다.

```JAVA
class Node {
    Node next;      // 다음 요소의 주소를 저장
    Node previous;  // 이전 요소의 주소를 저장
    Object obj;     // 데이터를 저장
}
```

더블 링크드 리스트의 접근성을 보다 향상시큰 것이 '더블 써큘러 링크드 리스트(이중 원형 연결리스트, doubly circular linked link)'인데, 단순히 더블 링크드 리스트의 첫 번째 요소와 마지막 요소를 서로 연결시킨 것이다. 이렇게 하면, 마지막 요소의 다음요소가 첫 번째 요소가 되고, 첫 번째 요소의 이전 요소가 마지막 요소가 된다. 마치 TV의 마지막 채널에서 채널을 증가시키면 첫 번째 채널로 이동하고 첫 번째 채널에서 채널음 감소시키면 마지막 채널로 이동하는 것과 같다.

실제로 LinkedList클래스는 이름과 달리 '링크드 리스트'가 아닌 '더블 링크드 리스트'로 구현되어 있는데, 이는 링크드 리스트의 단점인 낮은 접근성(accessablity)을 높이기 위한 것이다.

| 생성자 또는 메서드 | 설명 |
| --- | --- |
| LinkedList() | LinkedList 객체 생성 |
| LnikedList(Collection c) | 주어진 컬렉션을 포함하는 LinkedList객체를 생성 |
| boolean add(Object o) | 주어진 객체(o)를 LinkedList의 끝에 추가. 저장에 성공한면 true, 실패하면 false |
void add(int index, Object element) | 지정된 위치(index)에 객체(element)를 추가|
|boolean addAll(Collection c) | 주어진 컬렉션에 포함된 모든 요소를 LinkedList의 끝에 추가한다. 성공하면 true, 실패하면 false |
|boolean addAll(int index, Collection c) | 지정된 위치(index)에 주어진 컬렉션에 포함된 모든 요소를 추가, 성공하면 true, 실패하면 false |
|void clear() | LinkedList의 모든 요소를 삭제 |
|boolean contains(Object o) | 지정된 객체가 LinkedList에 포함되었는지 알려줌 |
|boolean contains(Collection c) | 지정된 컬렉션의 모든 요소가 포함되었는지 알려줌 |
|Object get(index) | 지정된 위치(index)의 객체를 반환 |
|int indexOf(Object o) | 지정된 객체가 저장된 위치를 반환 |
|boolean isEmpty() | LinkedList가 비어있는지 알려준다 |
|Iterator iterator() | Iterator를 반환|
|int lastIndexOf(Object o) | 지정된 객체의 위치(index)를 반환(역순검색)|
|ListIterator listIterator() | ListIterator를 반환 |
|ListIterator listIteraotr(int index) | 지정된 위치에서부터 시작하는 ListIterator를 반환|
|Object remove(int index) | 지정된 위치(index)의 객체를 LinkedList에서 제거 |
|boolean remove(Object o) | 지정된 객체를 LinkedList에서 제거, 성공하면 true, 실패 false |
|boolean removeAll(Collection c) | 지정된 컬렉션의 요소와 일치하는 요소를 모두 삭제 |
|boolean retainAll(Collection c) | 지정된 컬렉션의 모든 요소가 포함되어 있는지 확인 |
|Object set(int index, Object element) | 지정된 위치(index)의 객체를 주어진 객체를 바꿈 |
|int size() | LinkedList에 저장된 객체의 수를 반환 |
|List subList(int fromIndex, int toIndex) | LinkedList에 일부를 List로 반환 |
|Object[] toArray() | LinkedList에 저장된 객체를 배열로 반환 |
|Object[] toArray(Object[] a) | LinkedList에 저장된 객체를 주어진 배열에 저장하여 반환 |
|Object element() | LinkedList의 첫 번째 요소를 반환 |
|boolean offer(Object o) | LinkedList의 첫 번째 요소를 반환 |
|Object peek() | LinkedList의 첫 번째 요소를 반환 |
|Object poll() | LinkedList의 첫 번째 요소를 반환, LinkedList에서는 제거된다. |
|Object remove() | LinkedList의 첫 번째 요소를 제거 |
|void addFirst(Object o) | LinkedList의 맨 앞에 객체(o)를 추가 |
|void addLast(Object o) | LinkedList의 맨 끝에 객체(o)를 추가 |
|Iterator descondingIterator() | 역순으로 조회하기 위한 DescendingIterator를 반환 | 
|Object getFirst() | LinkedList의 첫번째 요소를 반환 |
|Object getLast() | LinkedList의 마지막 요소를 반환 |
|boolean offerFirst(Object o) | LinkedList의 맨 앞에 객체(o)를 추가, 성공하면 true |
|boolean offerLast(Object o) | LinkedList의 맨 뒤에 객체(o)를 추가, 성공하면 true |
|Object peekFirst(Object o) | LinkedList의 첫번째 요소를 반환 |
|Object peekLast(Object o) | LinkedList의 마지막 요소를 반환 |
|Object pollFirst(Object o) | LinkedList의 첫번째 요소를 반환하면서 제거 |
|Object pollLast(Object o) | LinkedList의 마지막 요소를 반환하면서 제거 |
|Object pop() | removeFirst()와 동일 |
|void push(Object o) | addFirst()와 동일 |
|Object removeFirst() | LinkedList의 첫번째 요소를 제거 |
|Object removeLast() | LinkedList의 마지막 요소를 제거 |
|boolean removeFirstOccurence(Object o) | LinkedList에서 첫 번째로 일치하는 객체로 제거 |
|boolean removeLastOccurence(Object o) | LinkedList에서 마지막으로 일치하는 객체로 제거 |


1. **순차적으로 추가/삭제하는 경우에는 ArrayList가 LinkedList보다 빠르다.**

    단순히 저장하는 시간만을 비교할 수 있도록 하기 위해서는 ArrayList를 생성할 때는 저장할 데이터의 개수만큼 충분한 초기용량을 확보해서, 저장공간이 부족해서 새로운 ArrayList를 생성해야하는 상황이 일어나지 않도록 했다. 만일 ArrayList의 크기가 충분하지 않으면, 새로운 크기의 ArrayList를 생성하고 데이터를 복사하는 일이 발생하게 되므로 순차적으로 데이터를 추가해서 ArrayList를 생성하고 데이터를 복사하는 일이 발생하게 되므로 순차적으로 데이터를 추가해도 ArrayList보다 LinkedList가 더 빠를 수 있다.


    순차적으로 삭제한다는 것은 마지막 데이터부터 역순으로 삭제해나간다는 것을 의미하며, ArrayList는 마지막 데이터부터 삭제할 경우 각 요소들의 재배치가 필요하지 않기 때문에 상당히 빠르다. 단지 마지막 요소의 값을 null로만 바꾸면 되기 때문이다.

2. **중간 데이터를 추가/삭제하는 경우에는 LinkedList가 ArrayList보다 빠르다.** 
    
    중간 요소를 추가 또는 삭제하는 경우, LinkedList는 각 요소간의 연결만 변경해주면 되기 떄문에 처리속도가 상당히 빠르다. 반면에 ArrayList는 각 요소들을 재배치하여 추가할 공간을 확보하거나 빈 공간을 채워야하기 때문에 처리속도가 느리다.

    데이터의 개수가 그리 크지 않다면 어느 것을 사용해도 큰 차이가 나지는 않는다. 그래도 ArrayList와 LinkedList의 장단점을 잘 이해하고 상황에 따라 적랗ㅂ한 것을 선택해서 사용하는 것이 좋다.



배열의 경우 만일 인덱스가 n인 원소의 값을 얻어 오고자 한다면 단순히 아래와 같은 수식을 계산함으로써 해결된다. 

> 인덱스가 n인 데이터의 주소 = 배열의 주소 + n * 데이터 타입의 크기


배열은 각 요소들이 연속적으로 메모리상에 존재하기 때문에 간단한 계산만으로 원하는 요소의 주소를 얻어서 저장된 데이터를 곧바로 읽어올 수 있지만, LinkedList는 불연속적으로 위치한 각 요소들이 서로 연결된 것이라 처음부터 n번째 데이터까지 차례대로 따라가야만 원하는 값을 얻을 수 있다.

그래서 LinkedList는 저장해야하는 데이터의 개수가 많아질수록 데이터를 읽어오는 시간, 즉 접근시간(access time)이 길어진다는 단점이 있다.

다음과 같이 정리될 수 있다.

| 컬렉션 | 읽기(접근시간) | 추가 / 삭제 | 비고 |
| --- | --- | --- | --- |
| ArrayList | 빠르다 | 느리다 | 순차적인 추가삭제는 더 빠르지만, 비효율적인 메모리사용 |
| LinkedList | 느리다 | 빠르다 | 데이터가 많을수록 접근성이 떨어짐 |


### 1.4 Stack과 Queue

자바에서 제공하는 Stack과 Queue에 대해서 알아보기 이전에 스택(Stack)과 큐(Queue)의 기본 개념과 특징에 대해서 먼저 살펴보도록 하자.

스택은 마지막에 저장된 데이터를 가장 먼저 꺼내게 되는 LIFO(Last In First Out)구조로 되어 있고, 큐는 처음에 저장한 데이터를 가장 먼저 꺼내게 되는 FIFO(First In First Out)구조로 되어있다.

그렇다면 스택과 큐를 규현하기 위해서는 어떤 컬력션 클래스를 사용하는 것이 좋을까? 수찬적으로 데이터를 추가하고 삭제하는 스택에는 ArrayList와 같은 배열기반의 컬렉션 클래스가 적합하지만, 큐는 데이터를 꺼낼 때 항상 첫번째 저장된 데이터를 삭제하므로 ArrayList와 같은 배열기반의 컬렉션 클래스를 사용한다면 데이터를 꺼낼 때마다 빈 공간을 채우기 위해 데이터의 복사가 발생하므로 비효율적이다. 그래서 큐는 ArrayList보다 데이터의 추가/삭제가 쉬운 LinkedList로 구현하는 것이 더 적합하다.

| 메서드 | 설명 |
| --- | --- |
| boolean empty() | Stack이 비어있는지 알려준다. |
| Object peek() | Stack의 맨 위에 저장된 객체를 반환. pop()과 달리 Stack에서 객체를 꺼내지 않음 |
| Object pop() | Stack의 맨 위에 저장된 객체를 꺼낸다. |
| Object push(Object item) | Stack에 객체(Item)를 저장한다. |
| int search(Object o) | Stack에서 주어진 객체(o)를 찾아서 그 위치를 반환. 못찾으면 -1을 반환 |
| boolean add(Object o) | 지정된 객체 Queeue에 추가한다. |
| Object remove() | Queue에서 객체를 꺼내 반환 |
| Object element() | 삭제없이 요소를 읽어온다. (Stack의 peek()과 달리 비어있을 경우 NoSuchElementException 발생) |
| boolean offer(Object o) | Queue에 객체를 저장 . 성공하면 true, 실패하면 false |
| Object poll() | Queue에서 객체를 꺼내서 반환. 비어있으면 null을 반환 |
| Object peek() | 삭제없이 요소를 읽어온다. Queue가 비어있으면 null을 반환 |


자바에서는 스택을 Stack클래스로 구현하여 제공하고 있지만 큐는 Queue인터페이스로만 정의해 놓았을 뿐 별도의 클래스를 제공하고 있지 않다. 대신 Queue 인터페이스를 구현한 클래스들이 있어서 이 들 중의 하나를 선택해서 사용하면 된다.


#### Stack 직접 구현하기

Stack은 컬렉션 프레임워크 이전부터 존해던 것이기 때문에 ArrayList가 아닌 Vector부터 상속받아 구현하였다. Stack의 실제 코드를 이해하기 쉽게 약간 수정해서 MyStack을 만들어 보았다.

#### 스택과 큐의 활용

- 스택의 활용 예: 수식계산, 수식괄호검사, 워드프로세서의 undo/redo, 웹브라우저의 뒤로/앞으로
- 큐의 활용 예: 최근사용문서, 인쇄작업 대기목록, 버퍼(buffer)

스택과 큐는 실제 프로그래밍에서 빈번하게 사용되는 자료구조라고는 하지만 어디에 사용되었고 막상 어떻게 활용해야할지를 생각해보면 잘 떠오르지 않을 것이다.


#### PriorityQueue

Queue 인터페이스의 구현체 중 하나로, 저장한 순서에 관계없이 우선순위(priority)가 높은 것부터 꺼내게 된다는 특징이 있다. 그리고 null을 저장할 수 없다. null을 저장하면 NullPointException이 발생한다.

PriorityQueue는 저장공간으로 배열을 사용하며, 각 요소를 '힙(heap)'이라는 자료구조의 형태로 저장한다. 힙은 이진 트리의 한 종류로 가장 큰 값이나 가장 작은 값을 빠르게 찾을 수 있다는 특징이 있다.


#### Deque(Double-Ended Queue)

Quue의 변형으로, 한쪽 끝으로만 추가/삭제할 수 있는 Queue와 달리, Deque(덱, 또는 디큐)은 양쪽 끝에 추가/삭제가 가능하다. Deque의 조상은 Queue이며, 구현체로는 ArrayDeque와 LinkedList 등이 있다.

덱은 스택과 큐를 하나로 합쳐놓은 것과 같으며 스택으로 사용할 수도 있고, 큐로 사용할 수도 있다.

| Deque | Queue | Stack |
| --- | --- | --- |
| offerLast() | offer() | push() |
| pollLast() | - | pop() |
| pollFirst() | poll() | - |
| peekFirst() | peek() | - |
| peekLast() | - | peek() |


### 1.5 Iterator, ListIterator, Enumeration

Iterator, ListIterator, Enumeration은 모두 컬렉션에 저장된 요소를 접근하는데 사용되는 인터페이스이다. Enumeration은 Iterator의 구버전이며, ListIterator는 Iteraotr의 기능을 향상 시킨 것이다.


#### Iterator

컬렉션 프레임워크에서는 컬렉션에 저장된 요소들을 읽어오는 방법을 표준화하였다. 컬렉션에 저장된 각 요소에 접근하는 기능을 가진 Iterator인터페이스를 정의하고, Collection인터페이스에는 'Iterator(Iterator를 구현한 클래스의 인스턴스)'를 반환하는 iterator()를 정의하고 있다.

```JAVA
public interface Iterator {
    boolean hasNext();
    Object next();
    void remove();
}

public interface Collection {
    ...
    public Iterator iterator();
    ...
}
```

iterator()는 Collection인터페이스에 정의된 메서드이므로 Collection인터페이스의 자손인 List와 Set에도 포함되어 있다. 그래서 List나 Set 인터페이스를 구현하는 컬렉션은 iterator()가 각 컬렉션의 특징에 알맞게 작성되어 있다. 컬렉션 클래스에 대해 iterator()를 호출하여 Iterator를 얻은 다음 반복문, 주로 while문을 사용해서 컬렉션 클래스의 요소들을 읽어 올 수 있다.

| 메서드 | 설명 |
| --- | --- |
| boolean hasNext() | 읽어 올 요소가 남아있는지 확인한다. (있으면 true, 없으면 false) |
| Object next() | 다음 요소를 읽어온다. next()를 호출하기 전에 hasNext()를 호출해서 읽어올 요소가 있는지 확인하는 것이 안전하다. |
| void remove() | next()로 읽어 온 요소를 삭제한다. next()를 호출한 다음에 remove()를 호출해야한다(선택적 기능) |


ArrayList에 저장된 요소들을 출력하기 위한 코드는 다음과 같이 작성할 수 있따.

```JAVA
List list = new ArrayList<>();

Iterator iterator = list.iterator();

while (iterator.hasNext()) {
System.out.println(iterator.next());
}
```

ArrayList대신 List인터페이스를 구현한 다른 컬렉션 클래스에 대해서도 이와 동일한 코드를 사용할 수 있다. 첫 줄에서 ArrayList대신 List인터페이스를 구현한 다른 컬렉션 클래스의 객체를 생성하도록 변경하기만 하면 된다.

Iterator를 이용해서 컬렉션의 요소를 읽어오는 방법을 표준화했기 때문에 이처럼 코드의 재사용성을 높이는 것이 가능한 것이다. 이처럼 공통 인터페이스를 정의해서 표준을 정의하고 구현하여 표준을 따르도록 함으로써 코드의 일관성을 유지하여 재사용성을 극대화하는 것이 객체지향 프로그래밍의 중요한 목적 중의 하나이다.

> Q. **참조변수의 타입을 ArrayList타입이 아니라 List타입으로 한 이유는 무엇인가?**
>
> A. List에 없고 ArrayList에만 있는 메서드를 사용하는 게 아니라면, List타입의 참조변수로 선언하는 것이 좋습니다. 만일 List인터페이스를 구현한 다른 클래스, 예를 들어 LinkedList로 바꿔야 한다면 선언문 하나만 변경하면 나머지 코드는 검토하지 않아도 됩니다. 참조변수의 타입이 List이므로 list에 정의되지 않은 메서드는 사용되지 않았을 것이 확실하기 때문입니다. 그러나 참조변수의 타입을 ArrayList로 했다면, 선언문 이후의 문장들을 검토해야 합니다. List에 정의되지 않은 메서드를 호출했을 수도 있기 때문입니다.


Map 인터페이스를 구현한 컬렉션 클래스는 키(key)와 값(value)을 쌍(pair)으로 저장하고 있기 때문에 iterator()를 직접 호출할 수 없고, 그 대신 keySet()이나 entrySet()과 같은 메서드를 통해서 키와 값을 각각 따로 Set의 형태로 얻어 온 후에 다시 iterator()를 호출해야 Iterator를 얻을 수 있다.

```JAVA
Map map = new HashMap();

Iterator it = map.keySet().iterator();
```

Iterator list = map.entrySet().iterator(); 는 아래의 두 문장을 하나로 합친 것이라고 이해하면 된다.

```JAVA
Set eSet = map.entrySet();
Iterator list = eSet.iterator();
```

List 클래스들은 저장순서를 유지하기 때문에 Iterator를 이용해서 읽어 온 결과 역시 저장순서와 동일하지만 Set클래스들은 각 요소간의 순서가 유지 되지 않기 때문에 Iterator를 이용해서 저장된 요소들을 읽어 와도 처음에 저장된 순서와 같지 않다.


#### ListIterator와 Enumeration

Enumeration은 컬렉션 프레임워크가 만들어지기 이전에 사용하던 것으로 Iterator의 구버전이라고 생각하면 된다. 이전 버전으로 작성된 소스와의 호환을 위해서 남겨 두고 있을뿐이므로 가능하면 Enumeration대신 Iterator를 사용하자.

ListIterator는 Iterator를 상속받아서 기능을 추가한 것으로, 컬렉션의 요소에 접근할 때 Iterator는 단방향으로만 이동할 수 있는 데 반해 ListIterator는 양방향으로의 이동이 가능하다. 다만 ArrayList나 LinkedList와 같이 List인터페이스를 구현한 컬렉션에서만 사용할 수 있다.

> **Enumeration** - Iterator의 구버전
> **ListIterator** - Iterator에 양방향 조회기능추가(List를 구현한 경우만 사용가능)

다음은 Enumeration, Iterator, ListIterator의 메서드에 대한 설명이다. Enumeration과 Iterator는 메서드이름만 다를 뿐 기능은 같고, ListIterator는 Iterator에 이전방향으로의 접근기능을 추가한 것뿐이라는 것을 알 수 있다. 

#### Enumeration인터페이스의 메서드

| 메서드 | 설 명 |
| --- | --- |
| boolean hasMoreEelements() | 읽어 올 요소가 남아있는지 확인한다. 있으면 true, 없으면 false를 반환한다. Iterator의 hasNext()와 같다. |
| Object nextElement() | 다음 요소를 읽어온다. nextElement()를 호출하기 전에 hasMoreElements()를 호출해서 읽어올 요소가 남아있는지 확인하는 것이 안전하다. Iterator의 next()와 같다. |

#### ListIterator의 메서드

| 메서드 | 설명 |
| --- | --- |
| void add(Object o) | 컬렉션에 새로운 객체(o)를 추가한다. (선택적 기능) |
| boolean hasNext() | 읽어올 다음 요소가 남아있는지 확인 |
| boolean hasPrevious() | 읽어올 이전 요소가 남아있는지 확인 |
| int nextIndex() | 다음 요소의 index 반환 |
| int previousIndex() | 이전 요소의 index 반환 |
| void remove() | next() 또는 previous()을 읽어 온 요소를 삭제한다. 반드시 next()나 previous()를 먼저 호출한 다음에 이 메서드를 호출해야한다. |
| void set(Object o) | next() 또는 previous()를 읽어 온 요소를 지정된 객체(o)로 변경한다. |


### 1.6 Arrays

Arrays 클래스에는 배열을 다루는데 유용한 메서드가 정의도어 있다. 

##### 배열의 복사 - copyOf(), copyOfRange()

copyOf()는 배열 전체를, copyOfRange()는 배열의 일부를 복사해서 새로운 배열을 만들어 반환한다. copyOfRange()에 지정된 범위의 끝은 포함되지 않는다.

#### 배열 채우기 - fill(), setAll()

fill()은 배열의 모든 요소를 지정된 값으로 채운다. setAll()은 배열을 채우는데 사용할 함수형 인터페이스를 매개변수로 받는다. 이 메서드를 호출할 때는 함수형 인터페이스를 구현한 객체를 매개변수로 지정하던가 아니면 람다식으로 지정해야한다.

```JAVA
int[] arr = new int[5];
Arrays.fill(arr, 9);        // [9, 9, 9, 9, 9]
Arrays.setAll(arr, () -> (int)(Math.random() * 5) + 1);
```

#### 배열의 정렬과 검색 - sort(), binarySearch()

sort()는 배열을 정렬 할 때, 그리고 배열에 저장된 요소를 검색할 때는 binarySearch()를 사용한다. binarySearch()는 배열에서 지정된 값이 저장된 위치(index)를 찾아서 반환하는데, 반드시 배열이 정렬된 상태이어야 올바른 결과를 얻는다. 그리고 만일 검색한 값과 일치하는 요소들이 여러 개 있다면, 이중에서 어떤 것의 위치가 반환될지는 알 수 없다.

```JAVA
int[] arr = { 3, 2, 0, 1, 4 };
int idx = Arrays.binarySearch(arr, 2);      // idx = -5  잘못된 결과

Arrays.sort(arr);
System.out.println(Arrays.toString(arr));
int idx = Arrays.binarySearch(arr, 2);      // idx = 2  올바른 결과
```

배열의 첫 번째 요소부터 순서대로 하나씩 검색하는 것을 '순차 검색(linear search)'이라고 하는데, 이 검색 방법은 배열이 정렬되어 있을 필요는 없지만 배열의 요소를 하나씩 비교하기 때문에 시간이 많이 걸린다. 반면에 이진 검색(binary search)은 배열의 검색할 범위를 반복적으로 절반씩 줄여가면서 검색하기 때문에 검색속도가 상당히 빠르다. 배열의 길이가 10배가 늘어나도 검색 횟수는 3~4회 밖에 늘어나지 않으므로 큰 배열의 검색에 유리하다. 단, 배열의 정렬이 되어 있는 경우에만 사용할 수 있다는 단점이 있다.


#### 문자열의 비교와 출력 - equals(), toString()

toString()배열의 모든 요소를 문자열로 편하게 출력할 수 있다. toString()은 일차원 배열에만 사용할 수 있으므로, 다차원 배열에는 deepToString()을 사용해야한다. deepToString()은 배열의 모든 요소를 재귀적으로 접근해서 문자열을 구성하므로 2차원 배열뿐만 아니라 3차원 배열의 이상의 배열에도 동작한다.

```JAVA
int[] arr = { 0, 1, 2, 3, 4 };
int[][] arr2D = { { 1, 2, 3}, { 4, 5, 6} };

System.out.println(Arrays.toString(arr));
System.out.println(Arrays.deepToString(arr2D));
```

equals()는 두 배열에 저장된 모든 요소를 비교해서 같으면 true, 다르면 false를 반환한다. equals()도 일차원 배열에만 사용가능하므로, 다차원 배열의 비교에는 deepEquals()를 사용해야한다.

```JAVA
String[][] arr2D = { { "aaa", "bbb", "ccc"}, { "AAA", "BBB", "CCC"} };
String[][] arr2D2 = { { "aaa", "bbb", "ccc"}, { "AAA", "BBB", "CCC"} };

System.out.println(Arrays.equals(arr2D, arr2D2));       // false
System.out.println(Arrays.deepEquals(arr2D, arr2D2));   // true
```

위와 같이 2차원 String배열을 equals()로 비교하면 배열에 저장된 내용이 같은데도 false를 결과로 얻는다. 다차원 배열은 '배열의 배열'의 형태로 구성하기 때문에 equals()로 비교하면, 문자열을 비교하는 것이 아니라 '배열에 저장된 배열의 주소'를 비교하게 된다. 서로 다른 배열은 항상 주소가 다르므로 false를 결과로 얻는다.


#### 배열을 List로 변환 - asList(Object... a)

asList()는 배열을 List에 담아서 반환한다. 매개변수의 타입이 가변인수라서 배열 생성없이 저장할 요소들만 나열하는 것도 가능하다.

```JAVA
List list = Arrays.asList(new Integer[]{ 1, 2, 3, 4, 5 });
List list = Arrays.asList(1, 2, 3, 4, 5);
list.add(6);            // UnsupportedOperationException 발생
```

한 가지 주의할 점은 asList()가 반환한 List의 크기를 변경할 수 없다는 것이다. 즉, 추가 또는 삭제가 불가능하다. 저장된 내용은 변경가능하다. 만일 크기를 변경할 수 있는 list가 필요하다면 다음과 같이 하면 된다.

```JAVA
List list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));
```

#### parallelXXX(), spliterator(), stream()

'parallel'로 시작하는 이름의 메서드들이 있는데, 이 메서드들은 보다 빠른 결과를 얻기 위해 여러 쓰레드가 작업을 나누어 처리하도록 한다. spliterator()는 여러 쓰레드가 처리할 수 있게 하나의 작업을 여러 작업으로 나누는 Spliterator를 반환하며, stream()은 컬렉션을 스트림으로 변환한다.


### 1.7 Comparator와 Comparable

Arrays.sort()를 호출만 하면 컴퓨터가 알아서 배열을 정렬하는 것처럼 보이지만, 사실은 Character클래스의 Comparable의 구현에 의해 정렬되었던 것이다. 

Comparator와 Comparable은 모두 인터페이스로 컬렉션을 정렬하는데 필요한 메서드를 정의하고 있으며, Comparable을 구현하고 있는 클래스들은 같은 타입의 인스턴스끼리 서로 비교할 수 있는 클래스들이다. 주로 Integer와 wrapper클래스와 String, Date, File과 같은 클래스를 기본적으로 오름차순, 즉 작은 값에서부터 큰 값의 순으로 정렬되도록 구현되어 있다. 그래서 Comparable을 구현한 클래스는 정렬이 가능하다는 것을 의미한다.

```JAVA
public interface Comparator<T> {
    int compare(Object o1, Object o2);
    boolean equals(Object obj);
}

public interface Comparable {
    public int compareTo(Object o);
}
```

> [참고] Comparable은 java.lang패키지에 있고, Comparator는 java.util패키지에 있다.

compare()와 compareTo()는 선언형태와 이름이 약간 다를 뿐 두 객체를 비교한다는 같은 기능을 목적을 고안된 것이다. compareTo()의 반환값은 int이지만 실제로는 비교하는 두 객체가 같으면 0, 비교하는 값보다 작으면 음수, 크면 양수를 반환하도록 구현해야 한다. 이와 마찬가지로 compare()도 객체를 비교해서 음수, 0, 양수 중의 하나를 반환하도록 구현해야한다.

equals메서드는 모든 클래스가 가지고 있는 공통적인 메서드이지만, Comparator를 구현하는 클래스는 오버라이딩이 필요할 수도 있다는 것을 알리기 위해서 정의한 것일 뿐, compare(Object o1, Object o2)만 구현된다.

```JAVA
public final class Integer extends Number implements Comparable<Integer> {
    
    public int compareTo(Object o) {
        return compareTo((Integer) o);
    }

    public int compareTo(Integer anotherInteger) {
        return compare(this.value, anotherInteger.value);
    }

    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}
```

위의 코드는 Integer 클래스의 일부이다. Comparable의 compareTo(Object o)를 구현해 놓은 것을 볼 수 있다. 두 Intgeger객체에 저장된 int값(value)을 비교해서 같으면 0, 크면 -1, 작으면 1을 반환하는 것을 알 수있다.

Comparable을 구현한 클래스들이 기본적으로 오름차순으로 정렬되어 있지만, 내림차순으로 정렬하던가 아니면 다른 기준에 의해서 정렬되도록 하고 싶을 때 Comparator를 구현해서 정렬기준을 제공할 수 있다.

> **Comparable** : 기본 정렬기준을 구현하는데 사용
> **Comparator** : 기본 정렬기준 외에 다른 기준으로 정렬하고자 할 때 사용


Arrays.sort()는 배열을 정렬할 때, Comparator를 지정해주지 않으면 저장하는 객체(주로 Comparable을 구현한 클래스의 객체)에 구현된 내용에 따라 정렬된다.

```JAVA
static void sort(Object[] a)        // 객체 배열에 저장된 객체가 구현한 Comparable에 의한 정렬
static void sort(Object[] a, Comparator c)  // 지정한 Comparator에 의한 정렬
```

String의 Comparable구현은 문자열이 사전 순으로 정렬되도록 작성되어 있다. 문자열의 오름차순 정렬은 공백, 숫자, 대문자, 소문자의 순으로 정렬되는 것을 의미한다. 정확히 얘기하면 문자의 유니코드의 순서가 작은 값에서부터 큰 값으로 정렬되는 것이다.

그리고 아래와 같이 대소문자를 구분하지 않고 비교하는 Comparator를 상수의 형태로 제공한다.

```JAVA
public static final Comparator<String> CASE_INSENSITIVE_ORDER
```

String의 기본 정렬을 반대로 하는 것, 즉 문자열을 내림차순을 구현하는 것은 아주 간단하다. 단지 String에 구현된 compareTo()의 결과에 -1을 곱하기만 하면 된다. 또는 비교하는 객체의 위치를 바꿔서 c2.compareTo(c1)과 같이 해도 된다.

다만 compare()의 매개변수가 Object타입이기 때문에 compareTo()를 바로 호출할 수 없으므로 먼저 Comparable로 형변환해야 한다는 것만 확인하자.

```JAVA
class Descending implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Comparable && o2 instanceof Comparable) {
            Comparable c1 = (Comparable) o1;
            Comparable c2 = (Comparable) o2;
            return c1.compareTo(c2) * -1;   // -1을 곱해서 기본 정렬방식의 역으로 변경
        }

        return -1;
    }
}
```

### 1.8 HashSet

HashSet은 Set 인터페이스를 구현한 가장 대표적인 컬렉션이며, Set인터페이스의 특징으대로 HashSet은 중복된 요소를 저장하지 않는다. 

HashSet에 새로운 요소를 추가할 때는 add메서드나 addAll메서드를 사용하는데, 만일 HashSet에 이미 저장되어 있는 요소와 중복된 요소를 추가하고자 한다면 이 메서드들은 false를 반환함으로써 중복된 요소이기 때문에 추가에 실패했다는 것을 알린다.

이러한 HashSet의 특징을 이용하면, 컬렉션 내의 중복 요소들을 쉽게 제거할 수 있다.

ArrayList와 같이 List인터페이스를 구현한 컬렉션과 달리 HashSet은 저장순서를 유지하지 않으므로 저장순서를 유지하고 한다면 LinkedHashSet을 사용해야한다.

> [참고] HashSet은 내부적으로 HashMap을 이용해서 만들어졌으며, HashSet이란 이름은 해싱(hasing)을 이용해서 구현했기 때문에 붙여진 것이다.

| 생성자 또는 메서드 | 설명 |
| --- | --- |
| HashSet() | HashSet 객체 생성 |
| HashSet(Collection c) | 주어진 컬렉션을 포함하는 HashSet객체 생성 |
| HashSet(int initialCapacity) | 주어진 값을 초기용량으로하는 HashSet객체 생성 |
| HashSet(int initialCapacity, float loadFactor) | 초기용량과 load factor를 지정하는 생성자 |
| boolean add(Object o) | 새로운 객체 저장 |
| boolean addAll(Collection c) | 주어진 컬렉션에 저장된  모든 객체들을 추가 |
| void clear() | 저장된 모든 객체 삭제 |
| Object clone() | HashSet을 복제해서 반환(얕은 복사) |
| boolean contains(Object o) | 지정된 객체를 포함하고 있는지 알려준다. |
| boolean containsAll(Collection c) | 주어진 컬렉션에 저장된 모든 객체들을 포함하고 있는지 알려준다. |
| boolean isEmpty() | HashSet이 비어있는지 알려준다. |
| Iterator iterator() | iterator 반환 |
| boolean remove(Object o) | 지정된 객체를 HashSet에서 삭제 |
| boolean removeAll(Collection c) | 주어진 컬렉션에 저장된 모든 객체와 동일한 것들을 HashSet에서 모두 삭제 |
| boolean retainsAll(Collection c) | 주어진 컬렉션에 저장된 객체와 동일한 것만 남기고 삭제 |
| int size() | 저장된 객체의 개수 반환 |
| Object[] toArray() | 저장된 객체들을 객체배열의 형태로 반환 | 
| Object[] toArray(Object[] a) | 저장된 객체들을 주어진 객체배열(a)에 담는다. |

> [참고] load factor는 컬렉션 클래스에 저장공간이 가득 차기 전에 미리 용량을 확보하기 위한 것으로 이 값을 0.8로 지정하면, 저장공간의 80%가 채워졌을 떄 용량이 두 배로 늘어난다. 기본값은 0.75, 즉 75%이다.
> [참고] JDK1.8부터 추가된 스트림(Stream)과 관련된 메서드들이 추가되었으나 메서드 목록에 넣지 않았다.

HashSet의 add메서드는 새로운 요소를 추가하기 전에 기존에 저장된 요소와 같은 것인지 판별하기 위해 추가하려는 요소의 equals()와 hashcode()를 호출하기 때문에 equals()와 hashCode()를 목적에 맞게 오버라이딩하면 같은 데이터를 가진 인스턴스가 동일하는 것을 판단할 수 있다.

JDK 1.8부터 추가된 java.util.Objects 클래스의 hash()를 이용해서 작성하면 hashCode()를 쉽게 구할 수 있다.
```JAVA
// AS-IS
public int hashCode() {
    return (name + age).hashCode();
}

// TO-BE
public int hashCode() {
    return Objects.hash(name, age);
}
```

오버라이딩을 통해 작성된 hashCode()는 다음의 세 가지 조건을 만족 시켜야 한다.

1. 실행 중인 애플리케이션 내의 동일한 객체에 대해서 여러 번 hashCode()를 호출해도 동일한 int값을 반환해야 한다. 하지만, 실행시마다 int값을 반환할 필요는 없다.
   
   <br />

   > [참고] String 클래스는 문자열의 내용으로 해시코드를 만들어 내기 때문에 내용이 같은 문자열에 대한 hashCode()호출은 항상 동일한 해시코드를 반환한다. 반면에 Object 클래스는 객체의 주소로 해시코드를 만들어 내기 때문에 실행할 때마다 해시코드값이 달라질 수 있다.

2. equals 메서드를 이용한 비교에 의해서 true를 얻은 두 객체에 대해 각각 hashCode()를 ㅎ호출해서 얻은 결과는 반드시 같아야 한다.

    <br />

    인스턴스 p1과 p2에 대해서 equals메서드를 이용한 비교의 결과인 변수 b의 값이 true라면, hashCode1과 hashCode2의 값은 같아야한다는 뜻이다.

    ```JAVA
    Person2 p1 = new Person2("David", 10);
    Person2 p2 = new Person2("David", 10);

    boolean b = p1.equals(p2);

    int hashCode1 = p1.hashCode();
    int hashCode2 = p2.hashCode();
    ```


3. equals 메서드를 호출했을 때 false를 반환하는 두 객체는 hashCode() 호출에 대해 같은 값 int을 반환하는 경우가 있어도 괜찮지만, 해싱(hasing)을 사용하는 컬렉션의 성능을 향상시키기 위해서는 다른 int값을 반환하는 것이 좋다.
   
    <br />

    위의 코드에서 변수 b의 값이 false일지라도 hashCode1과 hashCode2의 값이 같은 경우가 발생하는 것을 허용한다. 하지만, 해시코드를 사용하는 Hashtable이나 HashMap과 같은 컬렉션의 성능을 높이기 위해서는 가능한 한 서로 다른 값을 반환하도록 hashCode()를 잘 작성해야 한다는 뜻이다.

    <br />

    서로 다른 객체에 대해서 해시코드값(hashCode()를 호출한 결과)이 중복되는 경우가 많아질수록 해싱을 사용하는 Hashtable, HashMap과 같은 컬렉션의 검색속도가 떨어진다.

    두 객체에 대해 equals 메서드를 호출한 결과가 true이면, 두 객체의 해시코드는 반드시 같아야하지만, 두 객체의 해시코드가 같다고 해서 equals메서드의 호출결과가 반드시 ture이어야 하는 것은 아니다.

    사용자 정의 클래스를 작성할 때 equals메서드를 오버라이딩해야 한다면 hashCode()도 클래스의 작성의도에 맞게 오버라이딩하는 것이 원칙이지만, 경우에 따라 위의 예제에서와 같이 간단히 구현하거나 생략해도 별 문제가 되지 않으므로 hashCode()를 구현하는데 너무 부담 갖지 않았으면 한다.



### 1.9 TreeSet

TreeSet은 이진 검색 트리(binary search tree)라는 자료구조의 형태로 데이터를 저장하는 컬렉션 클래스이다. 이진 검색 트리는 정렬, 검색, 범위검색(range search)에 높은 성능을 보이는 자료구조이며 TreeSet은 이진 검색 트리의 성능을 향상시킨 '레드 - 블랙 트리(Red-Black Tree)'로 구현되어 있다.

그리고 Set 인터페이스를 구현했으므로 중복된 데이터의 저장을 허용하지 않으며 정렬된 위치에 저장하므로 저장순서를 유지하지도 않는다.

이진 트리(binary tree)는 링크드 리스트처럼 여러 개의 노드(node)가 서로 연결된 구조로, 각 노드에 최대 2개의 노드를 연결할 수 있으며 '루트(root)'라고 불리는 하나의 노드에서 시작해서 계속 확장해 나갈 수 있다.

위 아래로 연결된 두 노드를 '부모-자식관계'에 있다고 하며 위의 노드를 부모 노드, 아래의 노드를 자식 노드라 한다. 부모-자식관계는 상대적인 것이며 하나의 무도 느드는 최대 두 개의 자식 노드와 연결될 수 있다.

아래의 그림에서 A는 B와 C의 부모 노드이고, B와 C는 A의 자식노드이다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/5ad7e11e-208d-4c1a-8add-c74b34537d5e)

이진 트리의 노드를 표현하면 다음과 같다.

```JAVA
class TreeNode {
    TreeNode left;      // 왼쪽 자식 노드
    Object element;     // 객체를 저장하기 위한 참조변수
    TreeNode right;     // 오른쪽 자식 노드
}
```

데이터를 저장하기 위한 Object 타입의 참조변수 하나와 두 개의 노드를 참조하기 위한 두 개의 참조변수를 선언했다.

이진 검색 트리(binary search tree)는 부모노드의 왼쪽에는 부모노드의 값보다 작은 값의 자식노드를 오른쪽에는 큰 값의 자식노드를 저장하는 이진 트리이다.

예를 들어 데이터를 5, 1, 6의 순서로 저장한 이진 트리의 구조는 아래와 같이 표현할 수 있다.


![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/b97b00ea-b7e6-4479-9c07-64ee77747c5d)

첫 번째로 저장되는 값은 루트가 되고, 두 번째 값은 트리의 루트부터 시작해서 값의 크기를 비교하면서 트리를 따라 내려간다. 작은 값은 왼쪽에 큰 값은 오른쪽에 저장한다. 이렇게 트리를 구성하면, 왼쪽 마지막 레벨이 제일 작은 값이 되고 오른쪽 마지막 값이 제일 큰 값이 된다.

TreeSet에 저장되는 객체가 Comparable을 구현하던가 아니면, Comparator를 제공해서 두 객체를 비교할 방법을 알려줘야 한다. 그렇지 않으면, TreeSet에 객체를 저장할 때 예외가 발생한다.

왼쪽 마짐가 값에서부터 오른쪽 값까지 값을 '왼쪽 노드 -> 부모 노드 -> 오른쪽 노드'순으로 읽어오면 오름차순으로 정렬된 순서를 얻을 수 있다. TreeSet은 이처럼 정렬된 상태를 유지하기 때문에 단일 값 검색과 범위 검색(range search), 예를 들면 3과 7사이의 범위에 있는 값을 검색이 매우 빠르다. 

저장된 값의 개수에 비례해서 검색시간이 증가하긴 하지만 값의 개수가 10배 증가해도 특정 값을 찾는데 필요한 비교횟수가 3~4번만 증가할 정도로 검색효율이 뛰어난 자료구조이다.

트리는 데이터를 순차적으로 저장하는 것이 아니라 저장위치를 찾아서 저장해야하고, 삭제하는 경우 트리의 일부를 재구성해야하므로 링크드 리스트보다 데이터의 추가/삭제 시간은 더 걸린다. 대신 배열이나 링크드 리스트에 비해 검색과 정렬기능이 더 뛰어나다.

> **이진 검색 트리의 특징**
>
> - 모든 노드는 최대 두 개의 자식노드를 가질 수 있다.
> - 왼쪽 자식노드의 값은 부모느드이 값보다 작고 오른쪽 자식노드의 값은 부모노드의 값보다 커야한다.
> - 노드의 추가 삭제에 시간이 걸린다.(순차적으로 저장하지 않으므로)
> - 검색(범위검색)과 정렬에 유리하다.
> - 중복된 값을 저장하지 못한다.


| 생성자 또는 메서드 | 설명 |
| --- | --- |
| TreeSet() | 기본 생성자 |
| TreeSet(Collection c) | 주어진 컬렉션을 저장하는 TreeSet을 생성 |
| TreeSet(Comparator c) | 주어진 정렬조건으로 정렬하는 TreeSet을 생성 |
| TreeSet(SortedSet s) | 주어진 SortedSet을 구현한 컬렉션을 저장하는 TreeSet을 생성 |
| boolean add(Object o), boolean addAll(Collection c) | 지정된 객체(o) 또는 Collection(c)의 객체들을 Collection에 추가 |
| Object ceiling(Object o) | 지정된 객체와 같은 객체를 반환. 없으면 큰 값을 가진 객체 중 제일 가까운 값의 객체를 반환. 없으면 null |
| void clear() | 저장된 모든 객체를 삭제 |
| Object clone() | TreeSet을 복제하여 반환 |
| Comparator comparator() | TreeSet의 정렬기준(COmparator)를 반환 |
| boolean contains(Object o), boolean containsAll(Collection c) | 지정된 객체(o) 또는 Collection의 객체들이 포함되어 있는지 확인 |
| NavigableSet descendingSet() | TreeSet에 저장된 요소들을 역순으로 정렬해서 반환 |
| Object first() | 정렬된 순서에서 첫 번째 객체를 반환 |
| Object floor(Object o) | 지정된 객체와 같은 객체를 반환. 없으면 작은 값을 가진 객체 중 제일 가까운 값의 객체를 반환. 없으면 null |
| SortedSet headSet(Object element) | 지정된 객체보다 작은 값의 객체들을 반환 |
| NavigableSet headSet(Object toLement, boolean inclusive) | 지정된 객체보다 작은 값의 객체들을 반환 inclusive가 true이면, 같은 값의 객체도 포함 |
| Object higher(Object o) | 지정된 객체보다 큰 값을 가진 객체 중 제일 가까운 값의 객체를 반환, 없으면 null |
| boolean isEmpty() | TreeSet이 비어있는지 확인 |
| Iterator iterator() | TreeSet의 iterator반환 |
| Object last() | 정려된 순서에서 마지막 객체를 반환 | 
| Object lower(Object o) | 지정된 객체보다 작은 값을 가진 객체 중 제일 가까운 값의 객체를 반환. 없으면 null |
| Object pollFirst() | TreeSet의 첫번째 요소(제일 작은 값의 객체)를 반환 |
| Object pollLast() | Treeset의 마지막 번째 요소(제일 큰 값의 객체)를 반환 |
| boolean remove(Object o) | 지정된 객체를 삭제 |
| boolean retainsAll(Collection c) | 주어진 컬렉션과 공통된 요소만을 남기고 삭제(교집합) | 
| int size() | 지정된 객체의 개수를 반환 |
| Spliterator spliterator() | TreeSet의 spliterator를 반환 |
| SortedSet subSet(Object fromElement, Object toElement) | 범위 검색(fromElement와 toElement사이)의 결과를 반환 |
| NaviagleSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) | 범위 검색(fromElement와 toElement사이)의 결과를 반환 |
| SortedSet tailSet(Object fromElement) | 지정된 객체보다 큰 값의 객체들을 반환 |
| Object[] toArray() | 저장된 객체를 객체배열로 반환 |
| Object[] toArray(Object[] a) | 저장된 객체를 주어진 객체배열에 저장하여 반환 |


### 1.10 HashMap과 HashTable

Hashtable과 HashMap의 관계는 Vector와 ArrayList의 관계와 같아서 Hashtable보다는 새로운 버전인 HashMap을 사용하는 것을 권한다.

HashMap은 Map을 구현했으므로 앞에서 살펴본 Map의 특징, 키(Key)와 값(Value)을 묶어서 하나의 데이터(Entry)로 저장한다는 특징을 갖는다. 그리고 해싱(Hashing)을 사용하기 때문에 많은 양의 데이터를 검색하는데 있어서 뛰어난 성능을 보인다.

HashMap이 데이터를 어떻게 저장하는지 확인하기 위해 실제소스의 일부를 발췌하였다.

```JAVA
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
     transient Entry[] table;
     static class Entry implements Map.Entry {
        final Object key;
        Object value;
     }
}
```

HashMap은 Entry라는 내부 클래스를 정의하고, 다시 Entry타입의 배열을 선언하고 있다. 키(Key)와 값(Value)은 별개의 값이 아니라 서로 관련된 값이기 때문에 각각의 배열로 선언하기 보다는 하나의 클래스로 정의해서 하나의 배열로 다루는 것이 데이터의 무결성(itegrity)적인 측면에서 더 바람직하기 때문이다.

```JAVA
// 비객체지향적인 코드
Object[] key;
Object[] value;

// 객체지향적인 코드
Entry[] table;

class Entry {
    Object key;
    Object value;
}
```

> [참고] Map.Entry는 Map인터페이스의 정의된 `static inner interface`이다.


HashMap은 키와 값을 각각 Object타입으로 저장한다. 즉 (Object, Object)의 형태로 저장하기 때문에 어떠한 객체도 저장할 수 있지만 키는 주로 String을 대문자 또는ㄴ 소문자로 통해서 사용하곤 한다.

> 키(key) - 컬렉션 내의 키(key) 중에서 유일해야 한다.
> 값(value) - 키(key)와 달리 데이터의 중복을 허용한다.

키는 저장된 값을 찾는데 사용되는 것이기 때문에 컬렉션 내에서 유일(unique)해야한다. 즉, HashMap에 저장된 데이터를 하나의 키로 검색했을 때 결과가 단 하나이어야 함을 의미한다. 만일 하나의 키에 대해 여러 검색결과 값을 얻는다면 원하는 값이 어떤 것인지 알 수 없기 때문이다.


#### 해싱과 해시함수

해싱이란 해시함수(hash function)를 이용해서 데이터를 해시테이블에 저장하는고 검색하는 기법을 말한다. 해시함수는 데이터가 저정되어 있는 곳을 알려 주기 때문에 다량의 데이터 중에서도 원하는 데이터를 빠르게 찾을 수 있다.

해싱을 구현한 컬렉션 클래스로는 HashSet, HashMap, Hashtable 등이 있다. Hashtable은  컬렉션 프레임워크가 도입되면서 HashMap으로 대체되었으나 이전 소스와의 호환성 문제로 남겨 두고 있다. 가능하면 Hashtable 대신 HashMap을 사용하도록 하자.

해싱에서 사용하는 자료구조는 다음과 같이 배열과 링크드 리스트의 조합으로 되어 있다.

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/69c20336-10cb-4522-ab55-eada0ff95149)

저장할 데이터의 키를 해시함수에 넣으면 배열의 한 요소를 얻게 되고, 다시 그 곳에 연결되어 있는 링크드 리스트에 저장하게 된다.

이해를 돕기 위해 실생황에 비유한 예를 하나 들어보겠다. 한 간호사가 많은 환자들의 데이터 중에서, 원하는 환자의 데이터를 쉽게 찾을 수 있는 방법이 없을까를 고민하다가 주민등록번호의 맨 앞자리인 생년을 기준으로 데이터를 분류해서 10개의 서랍(배열)에 나눠 담는 방법을 생각해냈다. 예를 들면 71년생, 72년생과 같은 70년대생 환자들의 데이터는 같은 서랍에 저장된다.

이렇게 분류해서 저장해두면 환자의 주민번호로 태어난 년대를 계산해서 어느 서랍에서 찾아야 할지를 쉽게 알 수 있다.

여기서 서랍은 해싱에 사용되는 자료구조 중 배열의 각 요소를 의미하며, 배열의 각 요소에는 링크드 리스트가 저장되어 있어서 실제 데이터는 링크드 리스트에 담겨지게 된다.

링크드 리스트는 검색에 불리한 자료구조이기 때문에 링크드 리스트의 크기가 커질수록 검색속도가 떨어지게 된다. 이는 하나의 서랍에 데이터의 수가 많을 수록 검색에 시간이 더 걸리는 것과 같다.

반면에 배열은 배열의 크기가 커져도, 원하는 요소가 몇 번째 있는 지만 알면 아래의 공식에 의해서 빠르게 원하는 값을 찾을 수 있다.

> 배열의 인덱스가 n인 요소의 주소 = 배열의 시작주소 + type의 size * n


그래서 실생활과는 맞지 않지만, 하나의 서랍에 많은 데이터가 저장되어 있는 형태보다는 많은 서랍에 하나의 데이터만 저장되어 있는 형태가 더 빠른 검색결과를 얻을 수 있다.

하나의 링크드 리스트(서랍)에 최소한의 데이터만 저장되려면, 저장될 데이터의 크기를 고려해서 HashMap의 크기를 적절하게 지정해주어야 하고, 해시함수가 서로 다른 키(주민번호)에 대해서 중복된 해시코드(서랍위치)의 반환을 최소화해야 한다. 그래야 HashMap에서 빠른 검색시간을 얻을 수 있다.

그래서 해싱을 구현하는 과정에서 제일 중요한 것은 해시함수의 알고리즘이며, 이 예에서 사용오딘 해시함수의 알고리즘은 주어진 키(주민번호)의 첫 번째 문자를 뽑아서 정수로 반환하기만 하면 되므로 아래와 같이 코드로 표현할 수 있다.

```JAVA
int hashFunction(String key) {
    return Integer.parseInt(ket.substring(0, 1));
}
```

알고리즘이 간단한 만큼 성능은 좋지 않아서 서로 다른 키에 대해서 중복된 해시코드를 반환하는 경우가 많다.

실제로는 HashMap과 같이 해싱을 구현한 컬렉션 클래스에서는 Object 클래스에 정의된 hashCode()를 해시함수로 사용한다. Object 클래스에 정의된 hashCode()는 객체의 주소를 이용하는 알고리즘으로 해시코드를 만들어 내기 때문에 모든 객체에 대해 hashCode()를 호출한 결과가 서로 유일한 휼륭한 방법이다.

String 클래스의 경우 Object로부터 상속받은 hashCode()를 오버라이딩해서 문자열의 내용으로 해시코드를 만들어 낸다. 그래서 서로 다른 String 인스턴스일지라도 같은 내용의 문자열을 가졌다면 hashCode()를 호출하면 같은 해시코드를 얻는다.

HashSet에서 이미 설명했던 것과 같이 서로 다른 두 객체에 대해 equals()를 비교한 결과가 true인 동시에 hashCode()의 반환값이 같아야 같은 객체로 인식한다. HashMap에서도 같은 방법으로 객체를 구별하며, 이미 존재하는 키에 대한 값을 저장하면 기존의 값을 새로운 값으로 덮어쓴다.

그래서 새로운 클래스를 정의할 때 equals()를 재정의오버라이딩해야 한다면 hashCode()도 같이 재정의해서 equals()의 결과가 true인 두 객체의 해시코드의 결과 값이 같도록 해주어야 한다.

그렇지 않으면 HashMap 과 같이 해싱을 구현한 컬렉션 클래스에서는 equals()의 호출결과가 true이지만 해시코드가 다른 두 객체를 서로 다른 것으로 인식하고 따로 저장할 것이다.

> [참고] equals()로 비교한 결과가 false이고 해시코드가 같은 경우는 같은 링크드 리스트에 저장된 서로 다른 두 데이터가 된다.


### 1.11 TreeMap

TreeMap은 이름에서 알 수 있듯이 이진검색트리의 형태로 키와 값의 쌍으로 이루어진 데이터를 저장한다. 그래서 검색과 정렬에 적합한 컬렉션 클래스이다.

검색에 관한한 대부분의 경우에서 HashMap이 TreeMap보다 더 뛰어나므로 HashMap을 사용하는 것이 좋다. 다만 범위검색이나 정렬이 필요한 경우에는 TreeMap을 사용하는 것이 좋다.


### 1.12 Properties

Properties는 HashMap의 구버전인 HashTable을 상속받아 구현한 것으로, Hashtable은 키와 값(Object, Object)의 형태로 저장하는데 비해 Properties는 (String, String)의 형태로 저장하는 보다 단순화된 컬렉션클래스이다.

주로 애플리케이션의 환경설정과 관련된 속성(properties)을 저장하는데 사용되며 데이터를 파일로부터 읽고 쓰는 편릭한 기능을 제공한다. 그래서 간단한 입출력은 Properties를 활용하면 몇 줄의 코드로 해결될 수 있다.


### 1.13 Collections

Arrays가 배열과 관련된 메서드를 제공하는 것처럼, Collections는 컬렉션과 관련된 메서드를 제공한다. fil(), copy(), sort(), binarySearch() 등의 메서드는 두 클래스에 모두 포함되어 있으며 같은 기능을 한다.


#### 컬렉션의 동기화

멀티 쓰레드(multi-trhead) 프로그래밍에서는 하나의 객체를 여러 쓰레드가 동시에 접근할 수 있기 때문에 데이터의 일관성(consistency)을 유지하기 위해서는 공유되는 객체에 동기화(synchronization)이 필요하다.

Vector와 Hashtable과 같은 구버전(JDK1.2 이전)의 클래스들은 자체적으로 동기화 처리가 되어 있는데, 멀티쓰레드 프로그래밍이 아닌 경우에는 불필요한 기능이 되어 성능을 떨어뜨리는 요인이 된다.

그래서 새로 추가된 ArrayList와 HashMap과 같은 컬렉션은 동기화를 자체적으로 처리하지 않고 필요한 경우에만 Collections클래스의 동기화 메서드를 이용해서 동기화처리가 가능하도록 변경하였다.

Collections 클래스에는 다음과 같은 동기화 메서드를 제공하고 있으므로, 동기화가 필요할 때 해당하는 것을 사용하면 된다.

```JAVA
static Collection synchronizedCollection(Collection c)
static List synchronizedList(List list)
static Set synchronizedSet(Set s)
static Map synchronizedMap(Map m)
static SortedSet synchronizedSortedSet(SortedSet s)
static SortedMap synchronizedSortedMap(SortedMap m)
```

이 들을 사용하는 방법은 다음과 같다.

```JAVA
List syncList = Collections.synchronizedList(new ArrayList());
```

#### 변경불가 컬렉션 만들기

컬렉션에 저장된 데이터를 보호하기 위해서 컬렉션을 변경할 수 없게, 즉 읽기전용으로 만들어야할 때가 있다. 주로 멀티 쓰레드 프로그래밍에서 여러 쓰레드가 하나의  컬렉션을 공유하다보면 데이터가 손상될 수 있는데, 이를 방지하려면 아래의 메서드들을 이용하자.

```JAVA
static Collection unmodifiableCollection(Collection c)
static List unmodifiableList(List list)
static Set unmodifiableSet(Set s)
static Map unmodifiableMap(Map m)
static SortedSet unmodifiableSortedSet(SortedSet s)
static NaviagableSet unmodifiableNaviagableSet(NaviagableSet s)
static SortedMap unmodifiableSortedMap(SortedMap m)
static NaviagableMap unmodifiableNaviagableMap(NaviagableMap m)
```


#### 싱글톤 컬렉션 만들기

인스턴스를 new연산자가 아닌 메서드를 통해서만 생성할 수 있게 함으로써 생성할 수 있는 인스턴스의 개수를 제한하는 방법에 대해서 6장에서 배웠다. 이러한 기능을 제공하는 것이 바로 'singleton'으로 시작흐는 메서드이다.

```JAVA
static List singletonList(Object o)
static Set singletonSet(Object o)
static Map singletonMap(Obejct key, Object map)
```

매개변수로 저장할 요소를 지정하면, 해당 요소를 저장하는 컬렉션을 반환한다. 그리고 반환된 컬렉션은 변경할 수 없다.

#### 한 종류의 객체만 저장하는 컬렉션 만들기

컬렉션에 모든 종류의 객체를 저장할 수 있다는 것은 장점이기도 하고 단점이기도 하다.

대부분의 경우 한 종류의 객체를 저장하며, 컬렉션에 지정된 종류의 객체만 저장할 수 있도록 제한하고 싶을 때 아래의 메서드를 사용한다.

```JAVA
static Collection checkedCollection(Collection c, Class type)
static List checkedCollection(List c, Class type)
static Set checkedCollection(Set c, Class type)
static Map checkedCollection(Map c, Class keyType, Class valueType)
static Queue checkedCollection(Queue c, Class type)
static NavigableSet checkedCollection(Collection c, Class type)
static static SortedSet unmodifiableSortedSet(SortedSet s)
static NaviagableMap checkedCollection(NaviagableMap c,  Class keyType, Class valueType)
```

사용방법은 다음과 같이 두 번째 매개변수에 저장할 객체의 클래스를 지정하면 된다.

```JAVA
List list = new ArrayList();
List checkedList = checkedList(list, String.class);     // String만 저장가능
```

컬렉션에 저장할 요소의 타입을 제한하는 것은 제네릭스로 간단히 처리할 수 있는데도 이런 메서드들을 제공하는 이유는 호환성 때문이다. 제네릭은 JDK1.5부터 도입된 기능이므로 JDK 1.5이전에 작성된 코드를 사용할 때는 이 메서드들이 필요할 수 있다.


### 1.14 컬렉션 클래스 정리 및 요약

![image](https://github.com/bangseongmin/Standard-Of-Java/assets/22147400/a974efe5-6fa4-4423-948e-4d0fb8aabba4)


 - ArrayList
  
   - 배열기반
   - 데이터의 추가와 삭제에 불리
   - 순차적인 추가삭제는 제일 빠르다.
   - 임의의 요소에 대한 접근성(accessibility)이 뛰어남
  
 - LinkedList

   - 연결 기반 
   - 데이터의 추가와 삭제에 유리
   - 임의의 요소에 대한 접근성이 좋지 않음

- HashMap
  
    - 배열과 연결이 결합된 형태
    - 추가, 삭제, 검색, 접근성이 모두 뛰어남
    - 검색에는 최고성능을 보임
  
- TreeMap

    - 연결기반
    - 정렬과 검색(특히 범위검색)에 적합
    - 검색성능은 HashMap보다 떨어짐
  
- Stack

    - Vector를 상속받아 구현
  
- Queue

    - LinkedList가 Queue인터페이스를 구현
  
- Properties

    - Hashtable을 상속받아 구현
  
- HashSet

    - HashMap을 이용해서 구현
  
- TreeSet

    - TreeMap을 이용해서 구현
  
- LinkedHashMap, LinkedHashSet

    - HashMap과 HashSet에 저장순서유지기능 추가