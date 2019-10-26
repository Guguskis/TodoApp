package ToDoApp.controller;

import ToDoApp.ApplicationBuilder;
import ToDoApp.exceptions.UnauthorisedException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;

    public void login() throws IOException {
        var username = usernameLogin.getText();
        var password = passwordLogin.getText();

        try {
            ApplicationBuilder.appManager.login(username, password);
            ApplicationBuilder.changeScene("MainScreen");
        } catch (UnauthorisedException e) {
            //Todo add warning
            System.out.println("Username and password does not exist");
        }
    }

    public void startRegistration() throws Exception {
        ApplicationBuilder.changeScene("RegisterScreen");
    }
}
