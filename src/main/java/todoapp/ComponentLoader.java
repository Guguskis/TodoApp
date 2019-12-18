package main.java.todoapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ComponentLoader {
    private static final String FXML_Extension = ".fxml";

    public Parent getComponent(String relativeFilePath) throws FileNotFoundException {
        String filePath = getFilePath(relativeFilePath);
        return handleLoading(filePath);
    }

    private String getFilePath(String relativeFilePath) {
        return "view/" + relativeFilePath + FXML_Extension;
    }

    public Object getController(String relativeFilePath) throws IOException {
        String filePath = getFilePath(relativeFilePath);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        loader.load();

        return loader.getController();
    }

    private Parent handleLoading(String filePath) throws FileNotFoundException {
        try {
            return new FXMLLoader().load(getClass().getResource(filePath));
        } catch (IOException e) {
            throw new FileNotFoundException("Component \"" + filePath + "\" path is wrong.");
        }
    }

    public FXMLLoader getLoaderForComponent(String relativeFilePath) throws IOException {
        String filePath = getFilePath(relativeFilePath);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        return loader;
    }
}
