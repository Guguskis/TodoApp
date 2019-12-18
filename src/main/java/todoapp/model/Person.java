package main.java.todoapp.model;

public class Person extends User {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public Person(String firstName, String lastName, String phone, String email, String username, String password) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person " + firstName + " " + lastName + ", phone: " + phone + ", email: " + email + ".";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
