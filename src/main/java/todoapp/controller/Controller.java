package main.java.todoapp.controller;

import javafx.fxml.Initializable;
import main.java.todoapp.App;
import main.java.todoapp.model.AppManager;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {
	AppManager appManager;
	App viewManager;
	
	protected abstract void start();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.viewManager = App.getInstance();
		this.appManager = viewManager.appManager;
		
		start();
	}
}
