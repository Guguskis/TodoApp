package main.java.todoapp.controller;

import javafx.fxml.Initializable;
import main.java.todoapp.JavaFxApplication;
import main.java.todoapp.service.AppManager;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {
	protected JavaFxApplication javaFxApplication;
	protected AppManager appManager;

	protected abstract void start();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.javaFxApplication = JavaFxApplication.getInstance();
		this.appManager = AppManager.getInstance();
		start();
	}
}
