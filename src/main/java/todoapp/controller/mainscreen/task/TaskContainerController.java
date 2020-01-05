package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.MainApplication;
import main.java.todoapp.communication.Session;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.model.Project;
import main.java.todoapp.model.Task;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TaskContainerController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final MainApplication mainApplication = MainApplication.getInstance();
    private final Session session = Session.getInstance();

    @FXML
    public TreeView<Task> table;
    private Project project = new Project();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setEditable(true);
        table.setShowRoot(false);
    }

    public void refreshData() {
        table.setRoot(getTasksTree(project));
    }

    private TreeItem<Task> getTasksTree(Project project) {
        TreeItem<Task> item = new TreeItem<>();
        for (Task task : project.getTasks()) {
            item.getChildren().add(getTasksTree(task));
        }
        return item;
    }

    private TreeItem<Task> getTasksTree(Task task) {
        TreeItem<Task> item = new TreeItem<>(task);

        if (task.getTasks().isEmpty()) {
            return new TreeItem<>(task);
        } else {
            for (Task child : task.getTasks()) {
                item.getChildren().add(getTasksTree(child));
            }
        }

        return item;
    }

    public void editStart() throws IOException {
        TreeItem<Task> treeItem = table.getEditingItem();

        Task selected = treeItem.getValue();

        openUpdateForm(selected);
    }

    private void openUpdateForm(Task selected) throws IOException {
        FXMLLoader taskFormLoader = getTaskFormLoader();
        Parent form = taskFormLoader.load();
        UpdateTaskFormController controller = taskFormLoader.getController();

        Stage window = mainApplication.createWindow(form, "Task form");
        controller.set(this::refreshData, window, selected);
    }

    private FXMLLoader getTaskFormLoader() throws IOException {
        return loader.getLoaderForComponent("mainscreen/task/UpdateTaskForm");
    }

    public void setProject(SimplifiedProjectDto project) throws InterruptedException, HttpRequestFailedException, JSONException, IOException {
        List<Task> tasks = session.getTasks(project.getId());
        this.project.setId(project.getId());
        this.project.setName(project.getName());
        this.project.setTasks(tasks);

        refreshData();
    }

    public void openCreateTaskForm() throws IOException {
        FXMLLoader taskFormLoader = loader.getLoaderForComponent("mainscreen/task/CreateTaskForm");

        Parent form = taskFormLoader.load();

        CreateTaskFormController controller = taskFormLoader.getController();
        Stage window = mainApplication.createWindow(form, "Create task form");

        // Fixme fillData is null inside of setProject
        controller.setProject(this::refreshData, window, project.getId());
    }
}
