package main.java.todoapp.controller.mainscreen;

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
        //Todo: Logout logic?
        javaFxApplication.changeScene("LoginScreen");
    }


    public void openProjects() throws FileNotFoundException {
        Parent projectContainer = loader.getComponent("mainscreen/ProjectContainer");
        middlePane.getChildren().clear();
        middlePane.getChildren().add(projectContainer);
    }

    public void openAccountSettings() throws FileNotFoundException {
        Parent accountSettingsContainer = loader.getComponent("mainscreen/AccountSettingsContainer");
        middlePane.getChildren().clear();
        middlePane.getChildren().add(accountSettingsContainer);
    }

}
