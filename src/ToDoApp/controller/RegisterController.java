package ToDoApp.controller;

import ToDoApp.ApplicationBuilder;
import ToDoApp.exceptions.DuplicateException;
import ToDoApp.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameRegister;
    @FXML
    private TextField passwordRegister;
    @FXML
    private TextField firstNameRegister;
    @FXML
    private TextField lastNameRegister;
    @FXML
    private TextField phoneRegister;
    @FXML
    private TextField emailRegister;

    public void register() throws IOException {
        var username = usernameRegister.getText();
        var password = passwordRegister.getText();
        var firstName = firstNameRegister.getText();
        var lastName = lastNameRegister.getText();
        var phone = phoneRegister.getText();
        var email = emailRegister.getText();

        try {
            ApplicationBuilder.appManager.register(
                    new Person(firstName, lastName, phone, email, username, password));
            ApplicationBuilder.changeScene("LoginScreen");
        } catch (DuplicateException e) {
            //Todo add warning window
            System.out.println("Username taken");
        }

    }
}
