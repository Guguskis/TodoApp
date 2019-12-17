package main.java.todoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import main.java.todoapp.ComponentLoader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationScreenController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();

    @FXML
    public Pane form;
    @FXML
    private Button submitButton;
    @FXML
    private ChoiceBox typeChoice;

    public void register() {

        System.out.println("Submitted");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> types = Arrays.asList("Person", "Company");
        typeChoice.getItems().addAll(types);
        typeChoice.setValue(types.get(0));
        setPersonForm();
    }

    private void setPersonForm() {
        try {
            Parent personForm = loader.getComponent("registration/PersonForm");
            form.getChildren().clear();
            form.getChildren().add(personForm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCompanyForm() {
        try {
            Parent personForm = loader.getComponent("registration/CompanyForm");
            form.getChildren().clear();
            form.getChildren().add(personForm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeForm() {
        switch ((String) typeChoice.getValue()) {
            case "Person":
                setPersonForm();
                break;
            case "Company":
                setCompanyForm();
                break;
        }
    }
}
