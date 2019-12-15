package main.java.todoapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class ComponentLoader implements Serializable {
    private static final String FXML_Extension = ".fxml";

    public Parent getComponent(String relativeFilePath) throws FileNotFoundException {
        String filePath = "view/" + relativeFilePath + FXML_Extension;

        return handleLoading(filePath);
    }

    private Parent handleLoading(String filePath) throws FileNotFoundException {
        try {
            return new FXMLLoader().load(getClass().getResource(filePath));
        } catch (IOException e) {
            System.out.println("Component \"" + filePath + "\" path is wrong.");
            throw new FileNotFoundException();
        }
    }
}
