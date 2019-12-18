package main.java.todoapp.controller.registration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.JavaFxApplication;
import main.java.todoapp.exceptions.EmptyFieldException;
import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.repository.UserConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationScreenController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final UserConnection connection = new UserConnection();
    private final JavaFxApplication javaFxApplication = JavaFxApplication.getInstance();

    private PersonFormController personController;
    private CompanyFormController companyController;

    @FXML
    public Pane form;
    @FXML
    private ChoiceBox typeChoice;

    public void register() throws IOException, RegistrationFailedException, InterruptedException {
        switch (getChoice()) {
            case "Person":
                registerPerson();
                break;
            case "Company":
                registerCompany();
                break;
        }
    }

    private void registerPerson() throws IOException, RegistrationFailedException, InterruptedException {
        try {
            connection.register(personController.getPerson());
            goToLoginScreen();
        } catch (EmptyFieldException e) {
            triggerAlert("All fields are required.");
        }
    }

    private void registerCompany() throws IOException, InterruptedException, RegistrationFailedException {
        try {
            connection.register(companyController.getCompany());
            goToLoginScreen();
        } catch (EmptyFieldException e) {
            triggerAlert("All fields are required.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> types = Arrays.asList("Person", "Company");
        typeChoice.getItems().addAll(types);
        typeChoice.setValue(types.get(0));

        try {
            setPersonForm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPersonForm() throws IOException {
        form.getChildren().clear();
        form.getChildren().add(getPersonForm());
    }

    private Parent getPersonForm() throws IOException {
        FXMLLoader personFormLoader = loader.getLoaderForComponent("registration/PersonForm");
        Parent personForm = personFormLoader.load();
        personController = personFormLoader.getController();
        return personForm;
    }

    private void setCompanyForm() throws IOException {
        form.getChildren().clear();
        form.getChildren().add(getCompanyForm());
    }

    private Parent getCompanyForm() throws IOException {
        FXMLLoader personFormLoader = loader.getLoaderForComponent("registration/CompanyForm");
        Parent companyForm = personFormLoader.load();
        companyController = personFormLoader.getController();
        return companyForm;
    }

    public void changeForm() throws IOException {
        switch (getChoice()) {
            case "Person":
                setPersonForm();
                break;
            case "Company":
                setCompanyForm();
                break;
        }
    }

    private String getChoice() {
        return (String) typeChoice.getValue();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancel() throws FileNotFoundException {
        goToLoginScreen();
    }

    private void goToLoginScreen() throws FileNotFoundException {
        javaFxApplication.changeScene("LoginScreen");
    }
}
