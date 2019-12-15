package main.java.todoapp.model;

import java.io.Serializable;

public class Company extends User implements Serializable {

    private String name;
    private String contactPerson;

    public Company(String name, String contactPerson, String username, String password) {
        super(username, password);
        this.name = name;
        this.contactPerson = contactPerson;
    }

    @Override
    public String toString() {
        return "Company: \"" + name + "\", contact person: " + contactPerson + ".";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}