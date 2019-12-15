package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.todoapp.exceptions.LoginFailedException;

import java.io.FileNotFoundException;

public class LoginScreenController extends Controller {

    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;

    @Override
    protected void start() {
        System.out.println("LoginScreenController initialised");
    }

    public void login() {
        var username = usernameLogin.getText();
        var password = passwordLogin.getText();

        tryLogin(username, password);
    }

    private void tryLogin(String username, String password) {
        try {
            appManager.login(username, password);
            viewManager.changeScene("MainScreen/MainScreen");
        } catch (LoginFailedException e) {
            // Todo implement warn dialog
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startRegistration() throws FileNotFoundException {
        viewManager.changeScene("RegistrationScreen");

    }

    public void onKeyPressed(KeyEvent keyEvent) throws FileNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
