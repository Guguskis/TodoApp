package main.java.todoapp.controller.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.MainApplication;
import main.java.todoapp.communication.Session;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final MainApplication mainApplication = MainApplication.getInstance();
    private final Session session = Session.getInstance();

    @FXML
    public Pane middlePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            openProjects();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout() throws FileNotFoundException {
        session.logout();
        mainApplication.changeScene("LoginScreen");
    }

    public void openProjects() throws FileNotFoundException {
        Parent projectContainer = loader.getComponent("mainscreen/project/ProjectContainer");
        setMiddlePane(projectContainer);
    }

    public void openAccountSettings() throws FileNotFoundException {
        Parent accountSettingsContainer = loader.getComponent("mainscreen/AccountSettingsContainer");
        setMiddlePane(accountSettingsContainer);
    }

    public void openTasks() throws FileNotFoundException {
        Parent projectTasksContainer = loader.getComponent("mainscreen/task/ProjectTasksContainer");
        setMiddlePane(projectTasksContainer);
    }

    private void setMiddlePane(Parent component) {
        middlePane.getChildren().clear();
        middlePane.getChildren().add(component);
    }
}
