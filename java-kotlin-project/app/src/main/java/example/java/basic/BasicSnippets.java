package example.java.basic;

import java.util.ArrayList;
import java.util.List;

public class BasicSnippets {

    // basic 01 ============================================================
    public void basicSnippets01() {

        // 기본 타입
        int number = 100;
        String name = "jin";
        boolean isValid = true;
        double price = 99.99;

        // 상수
        final String CONSTANT = "HELLO_WORLD";
        final List<String> list = new ArrayList<>();

        // 타입 추론
        var userName = "jiiiin"; // String으로 추론
        var count = 20;         // int로 추론

        // Null 허용
        String nullableString = null; // 컴파일 시점에 null 체크 없음
    }

    // basic 02 ============================================================

    // 기본 메서드
    public int add(int a, int b) {
        return a + b;
    }

    // void 메서드
    public void printMessage(String message) {
        System.out.println(message);
    }

    // 오버로딩
    public String greet(String name) {
        return "Hello, " + name;
    }

    public String greet(String name, String title) {
        return "Hello, " + title + " " + name;
    }

    // 정적 메서드
    public static int multiply(int a, int b) {
        return a * b;
    }

    // 기본값 처리 (메서드 오버로딩으로)
    public void createUser(String name) {
        createUser(name, 18, true);  // 기본값 하드코딩
    }

    public void createUser(String name, int age, boolean isActive) {
        // 실제 구현
    }
}
