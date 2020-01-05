package main.java.todoapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ComponentLoader {
    private static final String FXML_EXTENSION = ".fxml";

    public Parent getComponent(String relativeFilePath) throws FileNotFoundException {
        String filePath = getFilePath(relativeFilePath);
        return handleLoading(filePath);
    }

    private String getFilePath(String relativeFilePath) {
        return "view/" + relativeFilePath + FXML_EXTENSION;
    }

    private Parent handleLoading(String filePath) throws FileNotFoundException {
        try {
            return new FXMLLoader().load(getClass().getResource(filePath));
        } catch (IOException e) {
            throw new FileNotFoundException("Component \"" + filePath + "\" path is wrong.");
        }
    }

    public FXMLLoader getLoaderForComponent(String relativeFilePath) {
        String filePath = getFilePath(relativeFilePath);
        return new FXMLLoader(getClass().getResource(filePath));
    }
}
