package creational_pattern.builder.user;

public class Main {
    public static void main(String[] args) {
        User user1 = new User.Builder()
                .name("Rahul")
                .email("dummy@gmail.com")
                .phone("1234567900")
                .address("Banglore,IN")
                .age(27)
                .build();
        System.out.println(user1);
    }
}
