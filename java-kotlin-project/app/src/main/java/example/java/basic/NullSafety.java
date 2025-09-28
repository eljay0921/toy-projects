package example.java.basic;

import example.java.dataclass.Person;
import example.java.dataclass.User;

import java.util.Optional;

public class NullSafety {

    // 전통적인 null 처리
    public String getUpperCaseName(Person person) {
        if (person != null && person.getName() != null) {
            return person.getName().toUpperCase();
        }

        return "UNKNOWN";
    }

    // Optional 사용 (java 8 이상)
    public Optional<User> findUser(String userId) {
        User user = new User();  // userRepository.findById(userId); // ex) null일 수 있음
        if (user != null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    // Optional 체이닝
    public String getUserEmailDomain(String userId) {
        return findUser(userId)
                .map(user -> user.getEmail().split("@")[1])
                .orElse("unknown");
    }
}
