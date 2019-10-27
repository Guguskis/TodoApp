package ToDoApp.controller;

import ToDoApp.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainContoller {
    @FXML
    private BorderPane mainPane;

    public void logout() throws IOException {
        var viewManager = ViewManager.getInstance();
        // Use appManager singleton?
        viewManager.appManager.logout();

        viewManager.changeScene("LoginScreen");
    }

    public void openProjects() throws IOException {
        var viewManager = ViewManager.getInstance();
        var displayProjectsComponent = viewManager.getComponent("MainScreen/ProjectsView");

        mainPane.setCenter(displayProjectsComponent);
    }

    public void openAccount() throws IOException {
        var viewManager = ViewManager.getInstance();
        var displayAccountComponent = viewManager.getComponent("MainScreen/AccountSettingsComponent");

        mainPane.setCenter(displayAccountComponent);
    }
}
