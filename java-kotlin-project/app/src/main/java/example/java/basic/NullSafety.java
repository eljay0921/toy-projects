package example.java.basic;

import example.java.dataclass.Person;
import example.java.dataclass.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NullSafety {

    private final List<User> userRepository;

    public NullSafety() {
        // userRepository 초기화
        userRepository = new ArrayList<>();
        userRepository.add(new User("john", "john@gmail.com", 25, true));
        userRepository.add(new User("jane", "jane@example.com", 30, false));
        userRepository.add(new User("bob", "bob@test.com", 28, true));
    }

    // 전통적인 null 처리
    public String getUpperCaseName(Person person) {
        if (person != null && person.getName() != null) {
            return person.getName().toUpperCase();
        }

        return "UNKNOWN";
    }

    // Optional 사용 (java 8 이상)
    public Optional<User> findUser(String userId) {
        if (userId == null) {
            return Optional.empty();
        }

        return userRepository.stream()
                .filter(user -> userId.equals(user.getUsername()))
                .findFirst();
    }

    // Optional 체이닝
    public String getUserEmailDomain(String userId) {
        return findUser(userId)
                .map(user -> user.getEmail().split("@")[1])
                .orElse("unknown");
    }
}
