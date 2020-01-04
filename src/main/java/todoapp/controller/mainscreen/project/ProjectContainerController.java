package main.java.todoapp.controller.mainscreen.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.todoapp.ComponentLoader;
import main.java.todoapp.JavaFxApplication;
import main.java.todoapp.communication.Session;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectContainerController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    private final Session session = Session.getInstance();
    private final JavaFxApplication javaFxApplication = JavaFxApplication.getInstance();

    @FXML
    public TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addColumns();
        fillData();
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

    private void fillData() {
        try {
            List<SimplifiedProjectDto> projects = session.getProjects();
            ObservableList<SimplifiedProjectDto> observableProjects = FXCollections.observableArrayList(projects);
            table.setItems(observableProjects);
        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
        } catch (HttpRequestFailedException e) {
            e.printStackTrace();
        }
    }

    public void openProjectFormCreate() throws IOException {
        FXMLLoader projectFormLoader = getFormLoader();
        Parent projectForm = projectFormLoader.load();
        ProjectFormController controller = projectFormLoader.getController();
        Stage window = javaFxApplication.createWindow(projectForm, "Add project");
        controller.setWindow(window);
        controller.setUpdateParent(this::fillData);
    }

    private FXMLLoader getFormLoader() throws IOException {
        return loader.getLoaderForComponent("mainscreen/project/ProjectForm");
    }

    public void openProjectFormUpdate() throws Throwable {
        SimplifiedProjectDto project = getSelectedItem();
        FXMLLoader projectFormLoader = getFormLoader();

        Parent projectForm = projectFormLoader.load();
        ProjectFormController controller = projectFormLoader.getController();
        controller.setUpdate(project, this::fillData);

        Stage window = javaFxApplication.createWindow(projectForm, "Add project");
        controller.setWindow(window);
    }

    private SimplifiedProjectDto getSelectedItem() {
        return (SimplifiedProjectDto) table.getSelectionModel().getSelectedItem();
    }

}
