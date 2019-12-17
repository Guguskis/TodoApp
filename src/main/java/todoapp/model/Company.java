package main.java.todoapp.model;

public class Company extends User {

    private String name;
    private String contactPersonPhone;

    public Company(String name, String contactPersonPhone, String username, String password) {
        super(username, password);
        this.name = name;
        this.contactPersonPhone = contactPersonPhone;
    }

    @Override
    public String toString() {
        return "Company: \"" + name + "\", contact person: " + contactPersonPhone + ".";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }
}
