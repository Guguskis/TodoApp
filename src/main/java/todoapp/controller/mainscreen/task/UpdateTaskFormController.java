package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.java.todoapp.model.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTaskFormController implements Initializable {
    private Task selected;
    private Task parent;

    private Runnable fillData;
    private Stage window;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void set(Runnable fillData, Stage window, Task selected, Task parent) {
        this.fillData = fillData;
        this.window = window;
        this.selected = selected;
        this.parent = parent;

    }

    public void closeForm() {
        fillData.run();
        window.close();
    }
}
