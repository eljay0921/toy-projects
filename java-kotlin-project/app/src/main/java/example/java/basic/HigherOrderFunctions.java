package example.java.basic;

/// 함수를 매개변수로 받는 메서드
public class HigherOrderFunctions {

    /// 고차함수 정의
    public int operation(int a, int b, Calculator calc) {
        return calc.calculate(a, b);
    }

    /// 함수를 반환하는 메서드
    public Calculator getOperation(String type) {
        switch (type) {
            case "add": return (a, b) -> a + b;
            case "multiply": return (a, b) -> a * b;
            default: return (a, b) -> 0;
        }
    }

    public void sample() {
        // 람다를 인자로 전달
        int result1 = operation(2, 4, (a, b) -> a + b);
        int result2 = operation(2, 4, (a, b) -> a * b);

        System.out.println(result1); // 6
        System.out.println(result2); // 8

        // 함수를 반환 받아 사용
        Calculator cal = getOperation("add");
        int result = cal.calculate(2, 4);
        System.out.println(result); // 6
    }
}
