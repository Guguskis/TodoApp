package main.java.todoapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainApplication extends Application {

    private static MainApplication instance;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private Stage primaryWindow;
    private ComponentLoader componentLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
        componentLoader = new ComponentLoader();
    }

    @Override
    public void start(Stage window) throws FileNotFoundException {
        this.primaryWindow = window;
        window.setTitle("ToDo App");
        changeScene("LoginScreen");
//        Todo change back
//        changeScene("mainscreen/task/TaskContainer");
        window.show();
    }

    public void changeScene(String filePathRelativeToView) throws FileNotFoundException {

        Parent root = componentLoader.getComponent(filePathRelativeToView);
        Scene sceneComponent = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryWindow.setScene(sceneComponent);
    }

    public Stage createWindow(Parent root, String title) {
        HBox background = new HBox();
        background.setAlignment(Pos.CENTER);
        background.setStyle("-fx-background-color: #333;");
        background.getChildren().add(root);

        Scene scene = new Scene(background, primaryWindow.getWidth(), primaryWindow.getHeight());

        Stage window = new Stage();
        window.setTitle(title);
        window.setScene(scene);

        window.setX(primaryWindow.getX());
        window.setY(primaryWindow.getY());

        window.show();
        return window;
    }


    @Override
    public void stop() {

    }
}
