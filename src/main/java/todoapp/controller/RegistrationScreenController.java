package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.todoapp.exceptions.DuplicateException;
import main.java.todoapp.model.Person;

import java.io.FileNotFoundException;

public class RegistrationScreenController extends Controller {

	@FXML
	private TextField usernameRegister;
	@FXML
	private TextField passwordRegister;
	@FXML
	private TextField firstNameRegister;
	@FXML
	private TextField lastNameRegister;
	@FXML
	private TextField phoneRegister;
	@FXML
	private TextField emailRegister;
	
	public void register() throws FileNotFoundException {
		handleRegistration(mapFieldToPerson());
	}
	
	private void handleRegistration(Person personToRegister) throws FileNotFoundException {
		try {
			appManager.register(personToRegister);
			javaFxApplication.changeScene("LoginScreen");
		} catch (DuplicateException e) {
			//Todo add warning window
			System.out.println("Username taken");
		}
	}
	
	private Person mapFieldToPerson() {
		return new Person(
				firstNameRegister.getText(), lastNameRegister.getText(),
				phoneRegister.getText(), emailRegister.getText(),
				usernameRegister.getText(), passwordRegister.getText()
		);
	}
	
	@Override
	protected void start() {
	
	}
}
