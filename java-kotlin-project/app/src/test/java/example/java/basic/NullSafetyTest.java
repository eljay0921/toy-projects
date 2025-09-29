package example.java.basic;

import example.java.dataclass.Person;
import example.java.dataclass.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class NullSafetyTest {

    private final NullSafety nullSafety = new NullSafety();

    @Test
    public void getUpperCaseNameTest() {
        Person person = new Person("jake", 33, "jake@gmail.com");

        // 정상적인 Person 객체를 전달한 경우
        String result = nullSafety.getUpperCaseName(person);
        assertEquals("JAKE", result);

        // null을 전달한 경우
        String nullResult = nullSafety.getUpperCaseName(null);
        assertEquals("UNKNOWN", nullResult);
    }

    @Test
    public void findUserTest() {
        // 존재하는 사용자 검색
        Optional<User> result = nullSafety.findUser("john");
        assertNotNull(result);
        assertTrue(result.isPresent());

        // 존재하지 않는 사용자 검색
        Optional<User> notFoundResult = nullSafety.findUser("who?");
        assertNotNull(notFoundResult);
        assertFalse(notFoundResult.isPresent());

        // null userId 전달
        Optional<User> nullResult = nullSafety.findUser(null);
        assertNotNull(nullResult);
        assertFalse(nullResult.isPresent());
    }

    @Test
    public void getUserEmailDomainTest() {
        // 정상적인 userId 전달
        String result = nullSafety.getUserEmailDomain("jane");
        assertNotNull(result);

        // null userId 전달
        String nullResult = nullSafety.getUserEmailDomain(null);
        assertNotNull(nullResult);
    }
}
