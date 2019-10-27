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
    public static ApplicationBuilder instance;
    // Finished by making the login screen work
    // Got stuck on moving Screen classes to model package because of classLoader
    // Will do main menu
    private static final String FXML_Extension = ".fxml";
    public AppManager appManager;

    private static Stage primaryStage;
    private static int screenWidth = 800;
    private static int screenHeight = 800;

    public static void main(String[] args) {
        launch(args);
    }

    public void changeScene(String fileName) throws IOException {
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource( "view/" + fileName + FXML_Extension));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, screenWidth, screenHeight));
    }

    @Override
    public void init() {
        var dataStorage = new DataStorageManager();
        appManager = dataStorage.importData("data.dat");
        instance=this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
}
