package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.todoapp.MainApplication;
import main.java.todoapp.communication.Session;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    private final Session session = Session.getInstance();
    private final MainApplication mainApplication = MainApplication.getInstance();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setText("admin");
        passwordField.setText("admin");
    }


    public void login() throws Throwable {
        if (usernameOrPasswordEmpty()) {
            triggerAlert("Username and password cannot be empty.");
        } else if (successfullyVerified()) {
            goToMainScreen();
        } else {
            triggerAlert("Username and password are not correct.");
        }
    }

    private void goToMainScreen() throws FileNotFoundException {
        mainApplication.changeScene("MainScreen/MainScreen");
    }

    private boolean usernameOrPasswordEmpty() {
        return getUsername().isEmpty() || getPassword().isEmpty();
    }

    private boolean successfullyVerified() throws Throwable {
        return session.verify(getUsername(), getPassword());
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
        mainApplication.changeScene("registration/RegistrationScreen");

    }

    public void loginOnEnterPressed(KeyEvent keyEvent) throws Throwable {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
