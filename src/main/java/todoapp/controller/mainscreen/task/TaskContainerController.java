package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.JavaFxApplication;
import main.java.todoapp.model.Project;
import main.java.todoapp.model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskContainerController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final JavaFxApplication javaFxApplication = JavaFxApplication.getInstance();

    @FXML
    public TreeView<Task> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillData();
        table.setEditable(true);
    }

    private void fillData() {
        Project project = new Project();

        Task taskA = new Task("Blaze", "Guguskis");
        Task taskB = new Task("Shingel", "Matas");
        Task taskAA = new Task("Ruck and Snorty", "Guguskis");
        Task taskAB = new Task("Gibberish", "admin");
        taskAB.setCompleted("admin");

        taskA.getTasks().add(taskAA);
        taskA.getTasks().add(taskAB);

        project.getTasks().add(taskA);
        project.getTasks().add(taskB);

        // Todo be careful because root might not be visible
        table.setShowRoot(false);
        table.setRoot(getTreeItem(project));
    }

    private TreeItem<Task> getTreeItem(Project project) {
        TreeItem<Task> item = new TreeItem<>();

        for (Task task : project.getTasks()) {
            item.getChildren().add(getTreeItem(task));
        }

        return item;
    }

    private TreeItem<Task> getTreeItem(Task task) {
        TreeItem<Task> item = new TreeItem<>(task);

        if (task.getTasks().isEmpty()) {
            return new TreeItem<>(task);
        } else {
            for (Task child : task.getTasks()) {
                item.getChildren().add(getTreeItem(child));
            }
        }

        return item;
    }

    public void editStart() throws IOException {
        TreeItem<Task> treeItem = table.getEditingItem();

        Task selected = treeItem.getValue();
        Task parent = getParentTask(treeItem);

        openUpdateForm(selected, parent);
    }

    private Task getParentTask(TreeItem<Task> treeItem) {
        Task parent = null;
        if (treeItem.getParent() != null) {
            parent = treeItem.getParent().getValue();
            System.out.println("parent = " + parent);
        }
        return parent;
    }

    private void openUpdateForm(Task selected, Task parent) throws IOException {
        FXMLLoader taskFormLoader = loader.getLoaderForComponent("mainscreen/task/TaskForm");

        Parent form = taskFormLoader.load();

        TaskFormController controller = taskFormLoader.getController();
        Stage window = javaFxApplication.createWindow(form, "Task form");
        controller.setUpdate(this::fillData, window, selected, parent);
    }

}
