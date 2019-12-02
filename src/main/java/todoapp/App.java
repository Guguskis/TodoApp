package main.java.todoapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.todoapp.model.AppManager;
import main.java.todoapp.model.DataStorageManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App extends Application {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final String DATA_FILE_NAME = "data.dat";

    private static App instance;
    public AppManager appManager;
    private Stage primaryStage;
    private ComponentLoader componentLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
        componentLoader = new ComponentLoader();
        DataStorageManager dataStorage = new DataStorageManager();
        this.appManager = dataStorage.importData(DATA_FILE_NAME);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ToDo App");
        changeScene("LoginScreen");

        primaryStage.show();
    }

    public void changeScene(String filePathRelativeToView) throws FileNotFoundException {

        var root = componentLoader.getComponent(filePathRelativeToView);
        var sceneComponent = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryStage.setScene(sceneComponent);
    }


    @Override
    public void stop() throws IOException {
        var dataStorage = new DataStorageManager();
        dataStorage.exportData("data.dat", appManager);
    }

}
