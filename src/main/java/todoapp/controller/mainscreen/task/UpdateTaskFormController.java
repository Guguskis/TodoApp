package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.JavaFxApplication;
import main.java.todoapp.model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTaskFormController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final JavaFxApplication javaFxApplication = JavaFxApplication.getInstance();

    private Task task;
    private Task parentTask;

    private Runnable fillData;
    private Stage window;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void set(Runnable fillData, Stage window, Task selected, Task parent) {
        this.fillData = fillData;
        this.window = window;
        this.task = selected;
        this.parentTask = parent;
    }

    public void closeForm() {
        fillData.run();
        window.close();
    }

    public void openCreateTaskForm() throws IOException {
        FXMLLoader taskFormLoader = loader.getLoaderForComponent("mainscreen/task/CreateTaskForm");

        Parent form = taskFormLoader.load();

        CreateTaskFormController controller = taskFormLoader.getController();
        Stage window = javaFxApplication.createWindow(form, "Create task form");
        controller.setTask(fillData, window, task.getId());
    }
}
