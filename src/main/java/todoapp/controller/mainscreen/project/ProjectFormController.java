package main.java.todoapp.controller.mainscreen.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.communication.Session;
import main.java.todoapp.exceptions.EmptyFieldException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectFormController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final Session session = Session.getInstance();

    private Stage window;
    private List<MemberController> membersController;

    @FXML
    public ScrollPane scrollingContainer;
    @FXML
    public VBox membersContainer;
    @FXML
    public TextField name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersController = new ArrayList<>();
    }

    public void addMember() throws IOException {
        FXMLLoader memberLoader = loader.getLoaderForComponent("mainscreen/project/Member");
        Parent member = memberLoader.load();

        MemberController controller = memberLoader.getController();
        controller.setRemove(index -> {
            for (int i = 0; i < membersController.size(); i++) {
                if (membersController.get(i).getIndex() == index) {
                    membersController.remove(i);
                    membersContainer.getChildren().remove(i);
                    break;
                }
            }
        });
        membersContainer.getChildren().add(member);
        membersController.add(controller);
    }

    public void submit() {
        try {
            List<String> usernames = getUsernames();
            session.createProject(usernames, getName());
            closeWindow();
        } catch (EmptyFieldException e) {
            triggerAlert(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void closeWindow() {
        window.close();
    }

    private String getName() throws EmptyFieldException {
        if (name.getText().isEmpty()) {
            throw new EmptyFieldException("Project name cannot be empty.");
        }
        return name.getText();
    }

    private List<String> getUsernames() throws EmptyFieldException {
        List<String> usernames = new ArrayList<>();
        for (MemberController member : membersController) {
            usernames.add(member.getUsername());
        }
        return usernames;
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void close() {
        closeWindow();
    }
}
