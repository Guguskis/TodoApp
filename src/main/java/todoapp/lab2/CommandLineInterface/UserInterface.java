package main.java.todoapp.lab2.CommandLineInterface;

import main.java.todoapp.model.Project;
import main.java.todoapp.model.Task;
import main.java.todoapp.model.User;

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
