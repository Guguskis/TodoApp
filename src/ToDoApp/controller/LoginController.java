package ToDoApp.controller;

import ToDoApp.exceptions.UnauthorisedException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class LoginController extends Controller {

    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;

    public void login() throws IOException {
        var username = usernameLogin.getText();
        var password = passwordLogin.getText();

        try {
            appManager.login(username, password);
            viewManager.changeScene("MainScreen/MainScreen");
        } catch (UnauthorisedException e) {
            //Todo add warning
            System.out.println("Username and password does not exist");
        }
    }

    public void startRegistration() throws Exception {
        viewManager.changeScene("RegisterScreen");
    }

    public void onKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
