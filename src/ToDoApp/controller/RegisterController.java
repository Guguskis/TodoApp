package ToDoApp.controller;

import ToDoApp.exceptions.DuplicateException;
import ToDoApp.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController extends Controller {

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

        var personToRegister = new Person(
                firstNameRegister.getText(), lastNameRegister.getText(),
                phoneRegister.getText(), emailRegister.getText(),
                usernameRegister.getText(), passwordRegister.getText()
        );

        try {
            appManager.register(personToRegister);
            viewManager.changeScene("LoginScreen");
        } catch (DuplicateException e) {
            //Todo add warning window
            System.out.println("Username taken");
        }

    }
}
