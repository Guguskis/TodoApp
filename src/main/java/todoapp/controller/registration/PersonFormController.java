package main.java.todoapp.controller.registration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.todoapp.exceptions.EmptyFieldException;
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

    public Person getPerson() throws EmptyFieldException {
        if (anyOfFieldsEmpty()) {
            throw new EmptyFieldException();
        } else {
            return createPerson();
        }
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
}
