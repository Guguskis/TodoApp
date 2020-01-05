package main.java.todoapp.controller.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.communication.Session;
import main.java.todoapp.controller.registration.CompanyFormController;
import main.java.todoapp.controller.registration.PersonFormController;
import main.java.todoapp.exceptions.EmptyFieldException;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.exceptions.InvalidTypeException;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountSettingsContainerController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final Session session = Session.getInstance();

    private PersonFormController personController;
    private CompanyFormController companyController;
    private User currentUser;

    @FXML
    public HBox form;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadUserInformation();
        } catch (IOException e) {
            triggerErrorAlert("Unable to get person info");
        }
    }

    private void loadUserInformation() throws IOException {
        currentUser = session.getUser();

        if (currentUser instanceof Person) {
            loadAndFillPersonForm((Person) currentUser);
        } else {
            loadAndFillCompanyForm((Company) currentUser);
        }
    }

    public void updateUserInformation() throws IOException, InterruptedException, JSONException {
        try {
            session.updateUserInformation(getUser());
            triggerConfirmationAlert("Your information was successfully updated!");
        } catch (HttpRequestFailedException | EmptyFieldException | InvalidTypeException e) {
            triggerErrorAlert(e.getMessage());
        }
    }

    private User getUser() throws EmptyFieldException, InvalidTypeException {
        User updatedUser;
        if (currentUser instanceof Person) {
            updatedUser = personController.getPerson();
        } else {
            updatedUser = companyController.getCompany();
        }
        return updatedUser;
    }

    private void triggerErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void triggerConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadAndFillCompanyForm(Company user) throws IOException {
        setCompanyForm();
        companyController.setCompany(user);
        companyController.setUsernameEditable(false);
    }

    private void loadAndFillPersonForm(Person user) throws IOException {
        setPersonForm();
        personController.setPerson(user);
        personController.setUsernameEditable(false);
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
        FXMLLoader companyFormLoader = loader.getLoaderForComponent("registration/CompanyForm");
        Parent companyForm = companyFormLoader.load();
        companyController = companyFormLoader.getController();
        return companyForm;
    }
}
