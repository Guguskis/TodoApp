package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.todoapp.exceptions.LoginFailedException;

import java.io.FileNotFoundException;

public class LoginScreenController extends Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    protected void start() {
        System.out.println("LoginScreenController initialised");
    }

    public void login() {
        tryLogin(usernameField.getText(), passwordField.getText());
    }

    private void tryLogin(String username, String password) {
        try {
            appManager.login(username, password);
            viewManager.changeScene("MainScreen/MainScreen");
        } catch (LoginFailedException e) {
            triggerAlert(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void startRegistration() throws FileNotFoundException {
        viewManager.changeScene("RegistrationScreen");

    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
