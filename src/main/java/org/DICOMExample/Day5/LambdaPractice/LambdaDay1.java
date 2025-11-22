package org.DICOMExample.Day5.LambdaPractice;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class LambdaDay1 {
    public static void main(String[] args) {

        /*람다식은 익명 객체!*/


        /// Function<String, String> toUpperCase = new Function<String, String>() {
        ///             @Override
        ///             public String apply(String s) {
        ///                 return s.toUpperCase();
        ///             }
        ///         };
        ///         System.out.println(toUpperCase.apply("hello")); // 출력: HELLO
        /// 이를 람다식을 활용하여 바꾸면?


        ///new 빠짐. 매개변수를 확인하기. string s 하나이므로 () 내부에 넣을 필요가 없음
        /// -> {} 내부에 어떤 실행식이 들어가는 지 확인. s.uppercase(); 가 사용된다.
        /// return 은 생략된다.

        Function<String, String> toUpperCase = s->s.toUpperCase();

        log.info(toUpperCase.apply("hello"));



        /// public class LambdaExample2 {
        ///     public static void main(String[] args) {
        ///         List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        ///         List<Integer> evenNumbers = numbers.stream()
        ///                                            .filter(new Predicate<Integer>() {
        ///                                                @Override
        ///                                                public boolean test(Integer n) {
        ///                                                    return n % 2 == 0;
        ///                                                }
        ///                                            })
        ///                                            .collect(Collectors.toList()); //collect()는 Collector를 매개변수로 하는 스트림의 최종연산이다.
        ///         System.out.println(evenNumbers); // 출력: [2, 4, 6]
        ///     }
        /// }
        /// 람다식을 사용하여 바꾸면?


        /// 매개변수: Integer n, 실행식 .stream,.filter, n % 2 == 0, .collect(Collectors.toList()); 모으는데, 어떻게 모으냐? -> 리스트로 모은다.
        /// 람다식은 익명 "객체" 이므로 new 가 생략됨.
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6); //Arrays.asList()는 원본 배열의 요소는 수정 가능하지만, add()나 remove()와 같은 크기를 변경하는 작업은 할 수 없음.
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        log.info("결과:{}",evenNumbers);



        /// public class LambdaExample3 {
        ///     public static void main(String[] args) {
        ///         List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");
        ///         strings.sort(new Comparator<String>() {
        ///             @Override
        ///             public int compare(String s1, String s2) {
        ///                 return Integer.compare(s1.length(), s2.length());
        ///             }
        ///         });
        ///         System.out.println(strings); // 출력: [date, apple, banana, cherry]
        ///     }
        /// }

        /// 매개변수: 2개 () 써야 함. 실행문: strings.sort, Integer.compare

        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");
        strings.sort((s1,s2)->s1.compareTo(s2));
        log.info("결과:{}",strings);


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


    }
}
