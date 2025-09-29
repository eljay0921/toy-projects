package example.java.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionsAndLoops {

    // 조건문 ==============================================================================

    // if-else
    public String getGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else {
            return "F";
        }
    }

    // switch (Java 14+ 향상된 switch)
    public String getDayType(String day) {
//        switch (day) {
//            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY":
//                return "WeekDay";
//            case "SATURDAY", "SUNDAY":
//                return "WeekEnd";
//            default:
//                return "unknown";
//        }

        return switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "WeekDay";
            case "SATURDAY", "SUNDAY" -> "WeekEnd";
            default -> "unknown";
        };
    }

    // 삼항 연산자
    public String getStatus(boolean isActive) {
        return isActive ? "Active" : "Inactive";
    }

    // 반복문 ==============================================================================

    private void normalForLoop() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

    private void enhancedForLoop() {
        List<String> friends = Arrays.asList("jin", "jake", "jane");
        for (String name : friends) {
            System.out.println(name);
        }
    }

    private void streamApi() {
        List<String> friends = Arrays.asList("jin", "jake", "jane");
        friends.stream()
                .filter(name -> name.length() > 3)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }


}
