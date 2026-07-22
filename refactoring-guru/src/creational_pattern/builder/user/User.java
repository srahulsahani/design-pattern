package creational_pattern.builder.user;

public class User {

    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final int age;
    private final String country;


    /**
     * Private constructor.
     * Only Builder can create User objects.
     */
    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.age = builder.age;
        this.country = builder.country;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                '}';
    }

    public static class Builder{
        private String name;
        private String email;
        private String phone;
        private String address;
        private int age;
        private String country;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        //Final Object Creation
        public User build(){
            return new User(this);
        }
    }
}
