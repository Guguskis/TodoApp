package ToDoApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectContainerController extends ScrollPane implements Initializable {
	public VBox projectsVBox;
	@FXML
	private ScrollPane mainScrollPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
}
