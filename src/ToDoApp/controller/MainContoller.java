package ToDoApp.controller;

import ToDoApp.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainContoller extends Controller {
    @FXML
    private BorderPane mainPane;

    @FXML
    public void logout() throws IOException {
        var viewManager = ViewManager.getInstance();
        // Todo Use appManager singleton?
        viewManager.appManager.logout();

        viewManager.changeScene("LoginScreen");
    }


    public void openProjects() throws IOException {
        var displayProjectsComponent = viewManager.getComponent("MainScreen/ProjectsContainer");
        mainPane.setCenter(displayProjectsComponent);
    }

    public void openAccountSettings() throws IOException {
        var displayAccountComponent = viewManager.getComponent("MainScreen/AccountSettingsContainer");
        mainPane.setCenter(displayAccountComponent);
    }
}
