package main.java.todoapp.controller.mainscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.todoapp.communication.Session;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectContainerController implements Initializable {
    private final Session session = Session.getInstance();

    @FXML
    public TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addColumns();
        fillTable();
    }

    private void addColumns() {
        addInvisibleColumn("Id", "id");
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

    private void addInvisibleColumn(String columnName, String propertyName) {
        TableColumn<String, SimplifiedProjectDto> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setVisible(false);
        table.getColumns().add(column);
    }

    private void fillTable() {
        try {
            List<SimplifiedProjectDto> projects = session.getProjects();
            ObservableList<SimplifiedProjectDto> runnerList = FXCollections.observableArrayList(projects);
            table.setItems(runnerList);
        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
        } catch (HttpRequestFailedException e) {
            e.printStackTrace();
        }
    }
}
