package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.todoapp.model.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskFormController implements Initializable {
    private Task selected;
    private Task parent;

    private Runnable fillData;
    private Stage window;

    @FXML
    public Button cancelButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button createButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.setVisible(false);
    }

    public void setUpdate(Runnable fillData, Stage window, Task selected, Task parent) {
        this.fillData = fillData;
        this.window = window;
        this.selected = selected;
        this.parent = parent;

        createButton.setVisible(false);
        updateButton.setVisible(true);
    }

    public void closeForm() {
        fillData.run();
        window.close();
    }
}
