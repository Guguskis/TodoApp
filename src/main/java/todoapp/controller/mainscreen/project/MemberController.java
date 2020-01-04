package main.java.todoapp.controller.mainscreen.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.todoapp.exceptions.EmptyFieldException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;

public class MemberController implements Initializable {
    private static int counter = 0;
    @FXML
    public TextField username;
    @FXML
    public Button removeButton;

    private int index;
    private IntConsumer remove;

    public MemberController() {
        index = counter++;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void removeMember() {
        remove.accept(index);
    }

    public int getIndex() {
        return index;
    }

    public void setRemove(IntConsumer remove) {
        this.remove = remove;
    }

    public String getUsername() throws EmptyFieldException {
        if (username.getText().isEmpty()) {
            throw new EmptyFieldException("Member username cannot bet empty.");
        }
        return username.getText();
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void makeUneditable() {
        username.setEditable(false);
        removeButton.setVisible(false);
    }
}
