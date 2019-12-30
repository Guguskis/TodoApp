package main.java.todoapp.controller.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectContainerController implements Initializable {

    @FXML
    public TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ProjectContainerController initialised");
    }
}
