package main.java.todoapp.communication;

import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class Session {
    private static Session instance;
    private final UserConnection userConnection = new UserConnection();
    private final ProjectConnection projectConnection = new ProjectConnection();

    private User currentUser;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public boolean verify(String username, String password) throws Throwable {
        boolean verified = userConnection.verify(username, password);
        if (verified) {
            currentUser = userConnection.getUserInfo(username);
        }
        return verified;
    }

    public void register(Person person) throws InterruptedException, IOException, RegistrationFailedException {
        userConnection.register(person);
    }

    public void register(Company company) throws InterruptedException, RegistrationFailedException, IOException {
        userConnection.register(company);
    }

    public User getUserInformation() throws Throwable {
        return userConnection.getUserInfo(currentUser.getUsername());
    }

    public void updateUserInformation(User user) throws IOException, InterruptedException, HttpRequestFailedException {
        user.setId(currentUser.getId());

        userConnection.updateUserInformation(user);
        currentUser = user;
    }

    public List<SimplifiedProjectDto> getProjects() throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        return projectConnection.getProjects(currentUser.getUsername());
    }

    public void createProject(List<String> usernames, String name) throws InterruptedException, IOException, JSONException {
        projectConnection.create(currentUser.getUsername(), usernames, name);
    }

    public void deleteProject(long id) throws IOException, InterruptedException {
        projectConnection.delete(id);
    }

    public void updateProject(SimplifiedProjectDto project) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        projectConnection.update(project);
    }
}
