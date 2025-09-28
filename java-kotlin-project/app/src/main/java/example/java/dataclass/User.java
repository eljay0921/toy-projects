package example.java.dataclass;

public class User {
    private String username;
    private String email;
    private int age;
    private boolean isActive;


    // 기본 생성자
    public User() {
        this.isActive = true; // 기본값
    }

    // username만 받는 생성자
    public User(String username) {
        this();
        this.username = username;
    }

    // username, email 받는 생성자
    public User(String username, String email) {
        this(username);
        this.email = email;
    }

    // 전체 필드 생성자
    public User(String username, String email, int age, boolean isActive) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
