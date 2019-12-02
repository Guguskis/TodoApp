package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import main.java.todoapp.exceptions.UnauthorisedException;

public class TestController extends Controller {
	@FXML
	private Text ProjectNameText;
	
	
	@Override
	protected void start() {
		try {
			appManager.login("admin", "admin");
		} catch (UnauthorisedException e) {
			e.printStackTrace();
		}
		var username = appManager.getCurrentUser().getUsername();
		ProjectNameText.setText(username);
		
	}
	
	public void changeText(KeyEvent keyEvent) {
	}
}
