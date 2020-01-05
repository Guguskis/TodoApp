package main.java.todoapp.controller.mainscreen.task;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTaskFormController implements Initializable {
    private Runnable fillData;
    private Stage window;
    private long projectId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void closeForm() {
        window.close();
    }

    public void set(Runnable fillData, Stage window, long projectId) {
        this.fillData = fillData;
        this.window = window;
        this.projectId = projectId;
    }
}
