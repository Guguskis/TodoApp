package main.java.todoapp.controller.mainscreen.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.MainApplication;
import main.java.todoapp.communication.Session;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectTasksContainerController implements Initializable {
    private final Session session = Session.getInstance();
    private final ComponentLoader loader = new ComponentLoader();
    private final MainApplication mainApplication = MainApplication.getInstance();

    @FXML
    public TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addColumns();
        fillData();
    }

    public void openTasksContainer() throws IOException, InterruptedException, JSONException {
        try {
            FXMLLoader taskContainerLoader = loader.getLoaderForComponent("mainscreen/task/TaskContainer");
            Parent tasksContainer = taskContainerLoader.load();

            TaskContainerController controller = taskContainerLoader.getController();
            controller.setProject(getSelectedItem());
            mainApplication.createWindow(tasksContainer, getSelectedItem().getName());
        } catch (HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        }
    }

    private SimplifiedProjectDto getSelectedItem() {
        return (SimplifiedProjectDto) table.getSelectionModel().getSelectedItem();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fillData() {
        try {
            List<SimplifiedProjectDto> projects = session.getProjects();
            ObservableList<SimplifiedProjectDto> observableProjects = FXCollections.observableArrayList(projects);
            table.setItems(observableProjects);
        } catch (HttpRequestFailedException e) {
            triggerAlert(e.getMessage());
        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addColumns() {
        addColumn("Name", "name", 300);
        addColumn("Owner", "owner", 100);
        addColumn("Members", "memberCount", 50);
    }

    private void addColumn(String columnName, String propertyName, int minWidth) {
        TableColumn<String, SimplifiedProjectDto> column = new TableColumn<>(columnName);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        table.getColumns().add(column);
    }
}
