package example.java.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConditionsAndLoopsTest {

    private final ConditionsAndLoops conditionsAndLoops = new ConditionsAndLoops();

    @Test
    public void getGradeTest() {
        // A 등급 테스트
        String gradeA1 = conditionsAndLoops.getGrade(90);
        assertEquals("A", gradeA1);
        String gradeA2 = conditionsAndLoops.getGrade(100);
        assertEquals("A", gradeA2);

        // B 등급 테스트
        String gradeB1 = conditionsAndLoops.getGrade(80);
        assertEquals("B", gradeB1);
        String gradeB2 = conditionsAndLoops.getGrade(89);
        assertEquals("B", gradeB2);

        // C 등급 테스트
        String gradeC1 = conditionsAndLoops.getGrade(70);
        assertEquals("C", gradeC1);
        String gradeC2 = conditionsAndLoops.getGrade(79);
        assertEquals("C", gradeC2);

        // F 등급 테스트
        String gradeF1 = conditionsAndLoops.getGrade(50);
        assertEquals("F", gradeF1);
        String gradeF2 = conditionsAndLoops.getGrade(0);
        assertEquals("F", gradeF2);
    }

    @Test
    public void getDayTypeTest() {
        // 평일 테스트
        assertEquals("WeekDay", conditionsAndLoops.getDayType("MONDAY"));
        assertEquals("WeekDay", conditionsAndLoops.getDayType("TUESDAY"));
        assertEquals("WeekDay", conditionsAndLoops.getDayType("WEDNESDAY"));
        assertEquals("WeekDay", conditionsAndLoops.getDayType("THURSDAY"));
        assertEquals("WeekDay", conditionsAndLoops.getDayType("FRIDAY"));

        // 주말 테스트
        assertEquals("WeekEnd", conditionsAndLoops.getDayType("SATURDAY"));
        assertEquals("WeekEnd", conditionsAndLoops.getDayType("SUNDAY"));

        // 잘못된 입력 테스트
        assertEquals("unknown", conditionsAndLoops.getDayType("INVALID"));
        assertEquals("unknown", conditionsAndLoops.getDayType(""));
    }

    @Test
    public void getStatusTest() {
        // true 입력 테스트
        String activeStatus = conditionsAndLoops.getStatus(true);
        assertEquals("Active", activeStatus);

        // false 입력 테스트
        String inactiveStatus = conditionsAndLoops.getStatus(false);
        assertEquals("Inactive", inactiveStatus);
    }
}
