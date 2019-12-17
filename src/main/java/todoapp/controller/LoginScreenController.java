package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.todoapp.repository.UserConnection;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginScreenController extends Controller {
    private final UserConnection userConnection = new UserConnection();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    protected void start() {
    }

    public void login() throws IOException, InterruptedException {
        if (usernameOrPasswordEmpty()) {
            triggerAlert("Username and password cannot be empty.");
        } else if (successfullyVerified()) {
            javaFxApplication.changeScene("MainScreen/MainScreen");
        } else {
            triggerAlert("Username and password are not correct.");
        }
    }

    private boolean usernameOrPasswordEmpty() {
        return getUsername().isEmpty() || getPassword().isEmpty();
    }

    private boolean successfullyVerified() throws IOException, InterruptedException {
        return userConnection.verify(getUsername(), getPassword());
    }

    private String getPassword() {
        return passwordField.getText();
    }

    private String getUsername() {
        return usernameField.getText();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void startRegistration() throws FileNotFoundException {
        javaFxApplication.changeScene("RegistrationScreen");

    }

    public void onKeyPressed(KeyEvent keyEvent) throws IOException, InterruptedException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
