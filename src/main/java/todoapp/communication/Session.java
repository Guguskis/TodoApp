package main.java.todoapp.communication;

import main.java.todoapp.controller.mainscreen.task.UpdateTaskDto;
import main.java.todoapp.dto.CreateTaskDto;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.helper.JSONParser;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.Task;
import main.java.todoapp.model.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class Session {
    private static Session instance;
    private final UserConnection userConnection = new UserConnection(new JSONParser());
    private final ProjectConnection projectConnection = new ProjectConnection(new JSONParser());
    private final TaskConnection taskConnection = new TaskConnection(new JSONParser());

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

    public User getCurrentUser() throws Throwable {
        return userConnection.getUserInfo(currentUser.getUsername());
    }

    public void updateUserInformation(User user) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        user.setId(currentUser.getId());

        userConnection.updateUserInformation(user);
        currentUser = user;
    }

    public List<SimplifiedProjectDto> getProjects() throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        return projectConnection.getProjects(currentUser.getUsername());
    }

    public void createProject(List<String> usernames, String name) throws InterruptedException, IOException, JSONException, HttpRequestFailedException {
        projectConnection.create(currentUser.getUsername(), usernames, name);
    }

    public void deleteProject(long id) throws IOException, InterruptedException {
        projectConnection.delete(id);
    }

    public void updateProject(SimplifiedProjectDto project) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        projectConnection.update(project);
    }

    public void logout() {
        currentUser = null;
    }

    public List<Task> getTasks(long projectId) throws IOException, InterruptedException, JSONException, HttpRequestFailedException {
        return taskConnection.getTasks(projectId);
    }

    public void createTaskForProject(long projectId, String title) throws Throwable {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setCreatedBy(getCurrentUser().getUsername());
        dto.setProjectId(projectId);
        dto.setTitle(title);
        taskConnection.createForProject(dto);
    }

    public void createTask(long taskId, String title) throws Throwable {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setCreatedBy(getCurrentUser().getUsername());
        dto.setTaskId(taskId);
        dto.setTitle(title);
        taskConnection.createForTask(dto);
    }

    public void deleteTask(long id) throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        taskConnection.delete(id);
    }

    public void updateTask(UpdateTaskDto dto) throws Throwable {
        dto.setUpdatorUsername(getCurrentUser().getUsername());
        taskConnection.update(dto);
    }
}
