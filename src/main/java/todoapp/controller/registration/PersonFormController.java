package main.java.todoapp.controller.registration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.todoapp.exceptions.EmptyFieldException;
import main.java.todoapp.exceptions.InvalidTypeException;
import main.java.todoapp.model.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonFormController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Person getPerson() throws EmptyFieldException, InvalidTypeException {
        if (anyOfFieldsEmpty()) {
            throw new EmptyFieldException("All fields are required.");
        } else if (phoneContainsOtherSymbols()) {
            throw new InvalidTypeException("Phone should be made only out of digits");
        } else {
            return createPerson();
        }
    }

    private boolean phoneContainsOtherSymbols() {
        try {
            Integer.parseInt(phone.getText());
        } catch (NumberFormatException e) {
            return true;
        }

        return false;
    }

    private Person createPerson() {
        Person person = new Person();
        person.setUsername(username.getText());
        person.setPassword(password.getText());
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        person.setPhone(phone.getText());
        person.setEmail(email.getText());
        return person;
    }

    private boolean anyOfFieldsEmpty() {
        return username.getText().isEmpty()
                || password.getText().isEmpty()
                || firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || phone.getText().isEmpty()
                || email.getText().isEmpty();
    }

    public void setPerson(Person person) {
        username.setText(person.getUsername());
        password.setText(person.getPassword());
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        phone.setText(person.getPhone());
        email.setText(person.getEmail());
    }

    public void setUsernameEditable(boolean editable) {
        username.setEditable(editable);
    }
}
