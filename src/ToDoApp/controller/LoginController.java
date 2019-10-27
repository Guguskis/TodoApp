package ToDoApp.controller;

import ToDoApp.ViewManager;
import ToDoApp.exceptions.UnauthorisedException;
import ToDoApp.model.AppManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;

    private AppManager appManager;

    public void login() throws IOException {
        var username = usernameLogin.getText();
        var password = passwordLogin.getText();

        try {
            var viewManager = ViewManager.getInstance();
            viewManager.appManager.login(username, password);
            viewManager.changeScene("MainScreen/MainScreen");
        } catch (UnauthorisedException e) {
            //Todo add warning
            System.out.println("Username and password does not exist");
        }
    }

    public void startRegistration() throws Exception {
        var viewManger = ViewManager.getInstance();

        viewManger.changeScene("RegisterScreen");
    }

    public void onKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
