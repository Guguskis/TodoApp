package main.java.todoapp.controller.registration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.todoapp.exceptions.EmptyFieldException;
import main.java.todoapp.model.Company;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyFormController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField name;
    @FXML
    private TextField contactPersonPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Company getCompany() throws EmptyFieldException {
        if (anyOfFieldsEmpty()) {
            throw new EmptyFieldException();
        } else {
            return createCompany();
        }
    }

    private Company createCompany() {
        Company company = new Company();
        company.setUsername(username.getText());
        company.setPassword(password.getText());
        company.setName(name.getText());
        company.setContactPersonPhone(contactPersonPhone.getText());
        return company;
    }

    private boolean anyOfFieldsEmpty() {
        return username.getText().isEmpty() || password.getText().isEmpty() || name.getText().isEmpty() || contactPersonPhone.getText().isEmpty();
    }
}
