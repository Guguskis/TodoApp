package main.java.todoapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.todoapp.service.AppManager;

import java.io.FileNotFoundException;

public class ViewManager extends Application {

    private static ViewManager instance;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private AppManager appManager;
    private Stage primaryStage;
    private ComponentLoader componentLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public static ViewManager getInstance() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
        appManager = new AppManager();
        componentLoader = new ComponentLoader();
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
    public void stop() {

    }

    public AppManager getAppManager() {
        return appManager;
    }
}
