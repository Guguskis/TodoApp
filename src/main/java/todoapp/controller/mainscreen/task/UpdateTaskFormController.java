package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.MainApplication;
import main.java.todoapp.communication.Session;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.model.Task;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTaskFormController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final MainApplication mainApplication = MainApplication.getInstance();
    private final Session session = Session.getInstance();

    @FXML
    public TextField title;
    @FXML
    public CheckBox isCompleted;

    private Task task;

    private Runnable fillData;
    private Stage window;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void set(Runnable fillData, Stage window, Task selected) {
        this.fillData = fillData;
        this.window = window;
        this.task = selected;

        title.setText(task.getTitle());
        isCompleted.setSelected(task.isCompleted());
    }

    public void closeForm() {
        fillData.run();
        window.close();
    }

    public void openCreateTaskForm() throws IOException {
        FXMLLoader taskFormLoader = loader.getLoaderForComponent("mainscreen/task/CreateTaskForm");

        Parent form = taskFormLoader.load();

        CreateTaskFormController controller = taskFormLoader.getController();
        Stage window = mainApplication.createWindow(form, "Create task form");
        controller.setTask(fillData, window, task.getId());
    }

    public void deleteTask() throws InterruptedException, IOException, JSONException {
        try {
            session.deleteTask(task.getId());
            closeForm();
        } catch (HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }
    }

    public void updateTask() throws Throwable {
        try {
            session.updateTask(getUpdateTaskDto());
            closeForm();
        } catch (HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }
    }

    private UpdateTaskDto getUpdateTaskDto() {
        UpdateTaskDto dto = new UpdateTaskDto();
        dto.setId(task.getId());
        dto.setTitle(title.getText());
        dto.setCompleted(isCompleted.isSelected());
        return dto;
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
