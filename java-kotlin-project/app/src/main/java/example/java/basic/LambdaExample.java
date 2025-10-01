package example.java.basic;


@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

@FunctionalInterface
interface Printer {
    void print(String msg);
}

public class LambdaExample {

    public void sample() {

        // 기본 람다 표현식
        Calculator add = (a, b) -> a + b;
        // Calculator add = Integer::sum;               // (메서드 참조형)
        Calculator multiply = (a, b) -> a * b;

        System.out.println(add.calculate(2, 4)); // 6
        System.out.println(multiply.calculate(2, 4)); // 8

        // N줄 람다 표현식
        Calculator complexCal = (a, b) -> {
            int result = a + b;
            result *= 2;
            return result;
        };

        System.out.println(complexCal.calculate(3, 4)); // 14

        // 매개변수가 없는 람다
        Runnable task = () -> System.out.println("Hello");

        // 매개변수가 하나인 람다
        Printer printer = msg -> System.out.println(msg);
        // Printer printer = System.out::println;       // (메서드 참조형)
    }

}
