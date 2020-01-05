package main.java.todoapp.controller.mainscreen.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
    private SimplifiedProjectDto project = new SimplifiedProjectDto();
    private Runnable updateProjectContainerData;

    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button submitButton;
    @FXML
    public Button addMemberButton;

    @FXML
    public VBox membersContainer;
    @FXML
    public TextField name;
    @FXML
    public TextField owner;
    @FXML
    public HBox ownerRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersController = new ArrayList<>();
        setButtonsForSubmit();
        ownerRow.setVisible(false);
    }

    private void setButtonsForSubmit() {
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
    }

    private void setButtonsForReadOnly() {
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
        submitButton.setVisible(false);
        addMemberButton.setVisible(false);
    }

    public void addEmptyMemberField() throws IOException {
        addMemberFieldWithUsername("", true);
    }

    public void addMemberFieldWithUsername(String username, boolean editable) throws IOException {
        FXMLLoader memberLoader = loader.getLoaderForComponent("mainscreen/project/Member");
        Parent member = memberLoader.load();

        MemberController controller = memberLoader.getController();
        controller.setRemove(remove());
        controller.setUsername(username);

        if (!editable) {
            controller.makeUneditable();
        }

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
            List<String> usernames = getMembers();
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

    public void sendUpdate() throws Throwable {
        try {
            List<String> members = getMembersWithOwner();
            project.setMembers(members);
            project.setName(getName());
            session.updateProject(project);
            closeWindow();
        } catch (EmptyFieldException | DuplicateException | HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }
    }

    private List<String> getMembersWithOwner() throws Throwable {
        List<String> members = getMembers();
        members.add(session.getUser().getUsername());
        return members;
    }

    public void setUpdate(SimplifiedProjectDto project, Runnable updateParent) throws Throwable {
        if (notOwner(project)) {
            setButtonsForReadOnly();
            makeFieldsUneditable();
            setAndShowOwner(project.getOwner());
            setProject(project, false);
        } else {
            setButtonsForUpdate();
            setProject(project, true);
        }

        setUpdateParent(updateParent);
    }

    private void makeFieldsUneditable() {
        name.setEditable(false);
        owner.setEditable(false);
    }

    private void setAndShowOwner(String username) {
        owner.setText(username);
        ownerRow.setVisible(true);
    }

    private boolean notOwner(SimplifiedProjectDto project) throws Throwable {
        return !project.getOwner().trim().equals(session.getUser().getUsername());
    }

    private void setButtonsForUpdate() {
        deleteButton.setVisible(true);
        updateButton.setVisible(true);
        submitButton.setVisible(false);
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

    private List<String> getMembers() throws EmptyFieldException, DuplicateException {
        List<String> members = new ArrayList<>();
        for (MemberController member : membersController) {
            members.add(member.getUsername());
        }

        if (hasDuplicates(members)) {
            throw new DuplicateException("There cannot be duplicates.");
        }

        return members;
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

    private void setProject(SimplifiedProjectDto project, boolean editable) throws IOException {
        this.project = project;
        setFields(editable);
    }

    private void setFields(boolean editable) throws IOException {
        name.setText(project.getName());

        for (String member : project.getMembers()) {
            addMemberFieldWithUsername(member, editable);
        }
    }

    public void setUpdateParent(Runnable updateProjectContainerData) {
        this.updateProjectContainerData = updateProjectContainerData;
    }
}
