package ToDoApp.lab2.CommandLineInterface;

import ToDoApp.model.Project;
import ToDoApp.model.Task;
import ToDoApp.model.User;

import java.util.List;

public interface UserInterface {

    void register();

    boolean login();

    void createProject();

    void addMember(Project project);

    void createTask(User who, Task destination);

    void createTask(User who, Project destination);

    void changeUsername();

    void changePassword();

    void changeEmail();

    void changePhone();

    void changeContactPerson();

    void manageProject(Project p); // Refactor this

    void logout();

    Project selectProject(List<Project> projects);

}
