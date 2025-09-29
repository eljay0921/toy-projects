package example.java.basic;

public class ConditionsAndLoops {

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
}
