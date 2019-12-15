package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.FileNotFoundException;

public class MainScreenController extends Controller {
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private Parent middleContainer;
	
	
	public MainScreenController() {

	}
	
	@FXML
	public void logout() throws FileNotFoundException {
		// Todo Use appManager singleton?
		appManager.logout();
		viewManager.changeScene("LoginScreen");
		
	}
	
	
	public void openProjects() {
	}
	
	public void openAccountSettings() throws FileNotFoundException {
//			var displayAccountComponent = viewManager.getComponent("MainScreen/AccountSettingsContainer");
//			mainBorderPane.setCenter(displayAccountComponent);
	}
	
	@Override
	protected void start() {
	
	}
}
