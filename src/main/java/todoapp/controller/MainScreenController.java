package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.JavaFxApplication;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final JavaFxApplication javaFxApplication = JavaFxApplication.getInstance();

    @FXML
    public Pane middlePane;

    public MainScreenController() {

    }

    @FXML
    public void logout() throws FileNotFoundException {
        //Todo: Logout logic?
        javaFxApplication.changeScene("LoginScreen");
    }


    public void openProjects() throws FileNotFoundException {
        Parent companyForm = loader.getComponent("registration/CompanyForm");
        middlePane.getChildren().add(companyForm);
    }

    public void openAccountSettings() throws FileNotFoundException {
//			var displayAccountComponent = viewManager.getComponent("MainScreen/AccountSettingsContainer");
//			mainBorderPane.setCenter(displayAccountComponent);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
