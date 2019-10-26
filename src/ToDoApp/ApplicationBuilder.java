package ToDoApp;

import ToDoApp.model.AppManager;
import ToDoApp.model.DataStorageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationBuilder extends Application {
    private static ApplicationBuilder instance;

    public static Stage primaryStage;
    public static AppManager appManager;

    private static int screenWidth = 800;
    private static int screenHeight = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){
        var dataStorage = new DataStorageManager();
        appManager = dataStorage.importData("data.dat");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hello World");
        changeScene("LoginScreen");
        primaryStage.show();

    }

    @Override
    public void stop() throws IOException {
        var dataStorage = new DataStorageManager();
        dataStorage.exportData("data.dat", appManager);
    }

    public static void changeScene(String fxmlFileName) throws IOException {
        Parent root = FXMLLoader.load(instance.getClass().getResource(fxmlFileName + ".fxml"));
        primaryStage.setScene(new Scene(root, screenWidth, screenHeight));
    }
}
