package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.todoapp.communication.Session;
import main.java.todoapp.exceptions.HttpRequestFailedException;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTaskFormController implements Initializable {
    private final Session session = Session.getInstance();

    @FXML
    public TextField title;

    private Runnable fillData;
    private Stage window;
    private long parentTaskId;
    private long projectId;
    private long taskId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void closeForm() {
        window.close();
    }

    public void setProject(Runnable fillData, Stage window, long projectId) {
        this.fillData = fillData;
        this.window = window;
        this.projectId = projectId;
    }

    public void setTask(Runnable fillData, Stage window, long parentTaskId) {
        this.fillData = fillData;
        this.window = window;
        this.parentTaskId = parentTaskId;
    }

    public void createTask() throws Throwable {
        try {
            if (projectId != 0) {
                session.createProjectTask(projectId, title.getText());
            } else {
                session.createTask(parentTaskId, title.getText());
            }
        } catch (HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }

        refreshAndClose();
    }

    private void refreshAndClose() {
        window.close();
        fillData.run();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
