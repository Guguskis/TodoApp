package ToDoApp.controller;

import ToDoApp.ViewManager;
import ToDoApp.model.AppManager;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {
	AppManager appManager;
	ViewManager viewManager;
	
	protected abstract void start();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.viewManager = ViewManager.getInstance();
		this.appManager = viewManager.appManager;
		
		start();
	}
}
