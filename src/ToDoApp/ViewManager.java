package ToDoApp;

import ToDoApp.model.AppManager;
import ToDoApp.model.DataStorageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager extends Application {
    private static final String DATA_FILE_NAME = "data.dat";
    private static final String FXML_Extension = ".fxml";

    private static ViewManager instance;

    public AppManager appManager;
    private Stage primaryStage;

    private int screenWidth = 800;
    private int screenHeight = 600;
    private DataStorageManager dataStorage;


    public static void main(String[] args) {
        launch(args);
    }

    public static ViewManager getInstance() {
        return instance;
    }

    @Override
    public void init() {
        this.instance = this;

        this.dataStorage = new DataStorageManager();
        this.appManager = dataStorage.importData(DATA_FILE_NAME);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Hello World");
        changeScene("LoginScreen");

        primaryStage.show();
    }

    public void changeScene(String filePathRelativeToView) throws IOException {
        Parent root = getComponent(filePathRelativeToView);
        primaryStage.setScene(new Scene(root, screenWidth, screenHeight));
    }

    public Parent getComponent(String filePathRelativeToView) throws IOException {
        var name = "view/" + filePathRelativeToView + FXML_Extension;
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(name));

        var component = (Parent) loader.load();
        return component;
    }

    @Override
    public void stop() throws IOException {
        var dataStorage = new DataStorageManager();
        dataStorage.exportData("data.dat", appManager);
    }
}
