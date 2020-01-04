package main.java.todoapp.controller.mainscreen.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.communication.Session;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.DuplicateException;
import main.java.todoapp.exceptions.EmptyFieldException;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.IntConsumer;

public class ProjectFormController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final Session session = Session.getInstance();

    private Stage window;
    private List<MemberController> membersController;

    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button submitButton;
    @FXML
    public VBox membersContainer;
    @FXML
    public TextField name;
    private SimplifiedProjectDto project = new SimplifiedProjectDto();
    private Runnable updateProjectContainerData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersController = new ArrayList<>();
        setButtonsForSubmit();
    }

    private void setButtonsForSubmit() {
        setDeleteButtonVisibility(false);
        setUpdateButtonVisibility(false);
        setSubmitButtonVisibility(true);
    }

    public void addEmptyMemberField() throws IOException {
        addMemberFieldWithUsername("");
    }

    public void addMemberFieldWithUsername(String username) throws IOException {
        FXMLLoader memberLoader = loader.getLoaderForComponent("mainscreen/project/Member");
        Parent member = memberLoader.load();

        MemberController controller = memberLoader.getController();
        controller.setRemove(remove());
        controller.setUsername(username);

        membersContainer.getChildren().add(member);
        membersController.add(controller);
    }

    private IntConsumer remove() {
        return index -> {
            for (int i = 0; i < membersController.size(); i++) {
                if (membersController.get(i).getIndex() == index) {
                    membersController.remove(i);
                    membersContainer.getChildren().remove(i);
                    break;
                }
            }
        };
    }

    public void sendCreate() throws InterruptedException, JSONException, IOException {
        try {
            List<String> usernames = getUsernames();
            session.createProject(usernames, getName());
            closeWindow();
        } catch (EmptyFieldException | HttpRequestFailedException | DuplicateException e) {
            triggerAlert(e.getMessage());
        }

    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void close() {
        closeWindow();
    }

    public void delete() throws IOException, InterruptedException {
        session.deleteProject(project.getId());
        closeWindow();
    }

    public void sendUpdate() throws IOException, InterruptedException, JSONException {
        try {
            project.setMembers(getUsernames());
            project.setName(getName());
            session.updateProject(project);
            closeWindow();
        } catch (EmptyFieldException | DuplicateException | HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }
    }

    public void setUpdate(SimplifiedProjectDto project) throws IOException {
        //Todo do not allow to edit fields if not owner (and show owner username field)
        setProject(project);
        setButtonsForUpdate();
    }

    private void setButtonsForUpdate() {
        setDeleteButtonVisibility(true);
        setUpdateButtonVisibility(true);
        setSubmitButtonVisibility(false);
    }

    private void closeWindow() {
        window.close();
        updateProjectContainerData.run();
    }

    private String getName() throws EmptyFieldException {
        if (name.getText().isEmpty()) {
            throw new EmptyFieldException("Project name cannot be empty.");
        }
        return name.getText();
    }

    private List<String> getUsernames() throws EmptyFieldException, DuplicateException {
        List<String> usernames = new ArrayList<>();
        for (MemberController member : membersController) {
            usernames.add(member.getUsername());
        }

        if (hasDuplicates(usernames)) {
            throw new DuplicateException("There cannot be duplicates.");
        }

        return usernames;
    }

    private boolean hasDuplicates(List<String> usernames) {
        Set<String> filteredUsernames = new HashSet<>(usernames);
        return usernames.size() != filteredUsernames.size();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setProject(SimplifiedProjectDto project) throws IOException {
        this.project = project;
        setFields();
    }

    private void setFields() throws IOException {
        name.setText(project.getName());

        for (String member : project.getMembers()) {
            addMemberFieldWithUsername(member);
        }
    }

    private void setDeleteButtonVisibility(boolean visible) {
        deleteButton.setVisible(visible);
    }

    private void setUpdateButtonVisibility(boolean visible) {
        updateButton.setVisible(visible);
    }

    private void setSubmitButtonVisibility(boolean visible) {
        submitButton.setVisible(visible);
    }

    public void setUpdateProjectContainer(Runnable updateProjectContainerData) {
        this.updateProjectContainerData = updateProjectContainerData;
    }
}
