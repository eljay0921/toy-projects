package example.java.basic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProcessing {

    public void sample() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter : 특정 대상만 필터링 -> 짝수만
        List<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
        // List<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0).toList();

        // map : 각 요소를 (탐색해) 변환 -> 제곱
        List<Integer> squared = numbers.stream().map(n -> n * n).toList();

        // filter + map
        List<Integer> evenSquared = numbers.stream().filter(n -> n % 2 == 0).map(n -> n * n).toList();

        // reduce : 각 요소를 하나의 값으로 축소(reduce) -> 합계 구하기
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        // int sum = numbers.stream().reduce(0, Integer::sum);

        // forEach : 각 요소 (단순) 탐색 (map의 경우는 최종(터미널)연산이 필요하다)
        // 참고로 대부분의 상황에서는 Collections.forEach()가 더 낫다고 한다. (차이점을 알고 상황에 맞게 적용)
        numbers.stream().forEach(n -> System.out.println(n));
        // numbers.forEach(System.out::println);

        // 복잡한 예제 : 짝수들의 제곱의 합
        int result = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);
        System.out.println(result);
    }
}
