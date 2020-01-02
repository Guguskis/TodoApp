package main.java.todoapp.controller.mainscreen.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.java.todoapp.ComponentLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectFormController implements Initializable {
    private final ComponentLoader loader = new ComponentLoader();
    @FXML
    public ScrollPane scrollingContainer;

    @FXML
    public VBox membersContainer;
    private List<MemberController> membersController = new ArrayList<>();
    @FXML
    public TextField username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void addMember() throws IOException {
        FXMLLoader memberLoader = loader.getLoaderForComponent("mainscreen/project/Member");
        Parent member = memberLoader.load();

        MemberController controller = memberLoader.getController();
        controller.setRemove(index -> {
            for (int i = 0; i < membersController.size(); i++) {
                if (membersController.get(i).getIndex() == index) {
                    membersController.remove(i);
                    membersContainer.getChildren().remove(i);
                    break;
                }
            }
        });
        membersContainer.getChildren().add(member);
        membersController.add(controller);
    }
}
