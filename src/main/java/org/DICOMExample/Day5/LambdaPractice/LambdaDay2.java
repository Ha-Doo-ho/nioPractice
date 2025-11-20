package org.DICOMExample.Day5.LambdaPractice;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
interface GCD {
    int compute(int a, int b);
}

@Data
class Person{
     String name;
     int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" + this.name + " age:" + this.age;
    }
}

@Slf4j
public class LambdaDay2 {
    public static void main(String[] args) {

        /*람다식은 익명 객체!*/

        /// public class LambdaExample4 {
        ///     public static void main(String[] args) {
        ///         List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        ///         int sum = numbers.stream()
        ///                          .reduce(0, new BinaryOperator<Integer>() {
        ///                              @Override
        ///                              public Integer apply(Integer a, Integer b) {
        ///                                  return a + b;
        ///                              }
        ///                          });
        ///         System.out.println(sum); // 출력: 15
        ///     }
        /// } 람다식으로 바꾸면?
        /// .reduce란? java 8 Stream API에서 스트림의 요소들을 하나의 값으로 결합(축소)하는 연산을 수행하는 메서드 호출의 한 형태
        /// 앞의 0은 0번 인덱스부터 하나의 값으로 모으겠다는 뜻이다. 쉽게 생각하면 list요소의 총합(sum)을 구하는 기능.

        //매개변수: a, b ()로 묶어주어야 함. 0은 같이 붙여주기만 하면 됨. 실행식: a + b

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = numbers.stream()
                .reduce(0, (a, b) -> a + b); //0번 인덱스부터 전부 다 더함. 그것을 합으로 줄이는 것이므로 .reduce()
        log.info(".reduce()는 sum과 같은 기능이다. 총합은:{}",sum);


        /// public class LambdaExample6 {
        ///     public static void main(String[] args) {
        ///         List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        ///         List<Integer> squaredNumbers = numbers.stream()
        ///                                               .map(new Function<Integer, Integer>() {
        ///                                                   @Override
        ///                                                   public Integer apply(Integer n) {
        ///                                                       return n * n;
        ///                                                   }
        ///                                               })
        ///                                               .collect(Collectors.toList());
        ///         System.out.println(squaredNumbers); // 출력: [1, 4, 9, 16, 25]
        ///     }
        /// }
        /// Function<> 인터페이스는 제네릭 타입 2개를 받도록 되어 있다.
        ///<T,R> T는 함수에 들어가는 입력 파라이터의 타입. R은 함수가 작업을 마치고 내보내는 반환(결과)값의 타입.

        /// 실행시켜야 할 동작 n*n 매개변수 Integer의 n, 제네릭 Integer
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squaredNumbers = numbers1.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());//모은다. 모으는 기준은? -> toList 리스트로 모은다.
            //collect() 메서드는 Collector 인터페이스를 인자로 받아 스트림 요소에 대한 리듀싱 연산(크기를 줄여주는 연산)을 수행하며,
            // Collectors 클래스는 이러한 Collector를 편리하게 구현한 유틸리티 메서드들을 제공합니다.
        log.info("squaredNumbers:{}",squaredNumbers);


        /// public class LambdaExample7 {
        ///     public static void main(String[] args) {
        ///         GCD gcd = new GCD() {
        ///             @Override
        ///             public int compute(int a, int b) {
        ///                 while (b != 0) {
        ///                     int temp = b;
        ///                     b = a % b;
        ///                     a = temp;
        ///                 }
        ///                 return a;
        ///             }
        ///         };
        ///         System.out.println(gcd.compute(54, 24)); // 출력: 6
        ///     }
        /// }

        //매개변수: a,b이므로 ()로 묶은 뒤 계산.
        GCD gcd = (a,b) -> { //단일 실행문: 실행할 코드가 한 줄이라면 {}와 return을 생략할 수 있습니다. {}는 거의 생략해야 함. 에러가 왜 나지..?
            while (b != 0) {  //다중 실행문: 실행할 코드가 두 줄 이상이거나, 복잡한 로직(if, while 등) 이 들어가면 반드시 {}로 묶어야 하며 값을 반환해야 하는 경우 반드시 return 명시.
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        };
        log.info("gcd.compute의 값:{}",gcd.compute(54,24));


        /// public class LambdaExample8 {
        ///     public static void main(String[] args) {
        ///         List<String> strings = Arrays.asList("hello", "world", "java");
        ///         int totalLength = strings.stream()
        ///                                  .mapToInt(new ToIntFunction<String>() {
        ///                                      @Override
        ///                                      public int applyAsInt(String s) {
        ///                                          return s.length();
        ///                                      }
        ///                                  })
        ///                                  .sum();
        ///         System.out.println(totalLength); // 출력: 14
        ///     }
        /// }

        //매개변수: s , 실행식: s.length();, .sum();   .mapToInt(new ToIntFunction<String>() map은 stream의 데이터를 변형하는 용도로 사용하는데, Int면 글자 수를 숫자로 바꿈.

        List<String> strings = Arrays.asList("hello", "world", "java");
        int totalLength = strings.stream()
                .mapToInt(s -> s.length()).sum();
        log.info("List에 수록된 모든 단어의 총 길이:{}",totalLength);


        /// class Person {
        ///     String name;
        ///     int age;
        ///
        ///     Person(String name, int age) {
        ///         this.name = name;
        ///         this.age = age;
        ///     }
        ///
        ///     @Override
        ///     public String toString() {
        ///         return name + " (" + age + ")";
        ///     }
        /// }
        ///
        /// public class LambdaExample9 {
        ///     public static void main(String[] args) {
        ///         List<Person> people = Arrays.asList(
        ///                 new Person("Alice", 30),
        ///                 new Person("Bob", 25),
        ///                 new Person("Charlie", 35)
        ///         );
        ///
        ///         people.sort(new Comparator<Person>() {
        ///             @Override
        ///             public int compare(Person p1, Person p2) {
        ///                 return Integer.compare(p1.age, p2.age);
        ///             }
        ///         });
        ///         System.out.println(people); // 출력: [Bob(25), Alice (30), Charlie (35)]
        ///     }
        /// }

        //매개변수; p1, p2  실행문: Integer.compare
        List<Person> people = Arrays.asList(
                new Person("Allice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        );
        people.sort((a,b)->Integer.compare(a.age,b.age));
        log.info("나이순 정렬결과:{}",people);


        /// public class LambdaExample10 {
        ///     public static void main(String[] args) {
        ///         List<String> strings = Arrays.asList("hello", "world", "java");
        ///         List<Character> firstChars = strings.stream()
        ///                                             .map(new Function<String, Character>() {
        ///                                                 @Override
        ///                                                 public Character apply(String s) {
        ///                                                     return s.charAt(0);
        ///                                                 }
        ///                                             })
        ///                                             .collect(Collectors.toList());
        ///         System.out.println(firstChars); // 출력: [h, w, j]
        ///     }
        /// }

        List<String> strings1 = Arrays.asList("hello", "world", "java");
        List<Character> characterList= strings1.stream()
                .map(s -> s.charAt(0))
                .collect(Collectors.toList());//모은다. 모으는데? 어떻게 모을까? ->List로 모은다.
        log.info("리스트 내 단어 중 가장 앞글자만 출력함. 그 결과는->{}",characterList);

    }
}
