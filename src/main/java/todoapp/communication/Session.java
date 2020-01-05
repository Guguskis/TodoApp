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

    private final UserConnection userConnection;
    private final ProjectConnection projectConnection;
    private final TaskConnection taskConnection;

    private User user;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Session() {
        userConnection = new UserConnection(new JSONParser());
        projectConnection = new ProjectConnection(new JSONParser());
        taskConnection = new TaskConnection(new JSONParser());
    }

    public boolean verify(String username, String password) throws Throwable {
        boolean verified = userConnection.sendVerify(username, password);
        if (verified) {
            user = userConnection.sendGet(username);
        }
        return verified;
    }

    public void register(Person person) throws InterruptedException, IOException, RegistrationFailedException {
        userConnection.sendPost(person);
    }

    public void register(Company company) throws InterruptedException, RegistrationFailedException, IOException {
        userConnection.sendPost(company);
    }

    public User getUser() {
        return user;
    }

    public void updateUserInformation(User user) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        user.setId(this.user.getId());
        userConnection.sendPut(user);
        this.user = user;
    }

    public List<SimplifiedProjectDto> getProjects() throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        return projectConnection.sendGet(user.getUsername());
    }

    public void createProject(List<String> usernames, String name) throws InterruptedException, IOException, JSONException, HttpRequestFailedException {
        projectConnection.sendPost(user.getUsername(), usernames, name);
    }

    public void deleteProject(long id) throws IOException, InterruptedException {
        projectConnection.sendDelete(id);
    }

    public void updateProject(SimplifiedProjectDto project) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        projectConnection.sendPut(project);
    }

    public List<Task> getTasks(long projectId) throws IOException, InterruptedException, JSONException, HttpRequestFailedException {
        return taskConnection.sendGet(projectId);
    }

    public void createTaskForProject(long projectId, String title) throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setCreatedBy(getUser().getUsername());
        dto.setProjectId(projectId);
        dto.setTitle(title);
        taskConnection.sendPostForProject(dto);
    }

    public void createTaskForTask(long taskId, String title) throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setCreatedBy(getUser().getUsername());
        dto.setTaskId(taskId);
        dto.setTitle(title);
        taskConnection.sendPostForTask(dto);
    }

    public void deleteTask(long id) throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        taskConnection.sendDelete(id);
    }

    public void updateTask(UpdateTaskDto dto) throws InterruptedException, JSONException, HttpRequestFailedException, IOException {
        dto.setUpdatorUsername(getUser().getUsername());
        taskConnection.sendUpdate(dto);
    }

    public void logout() {
        user = null;
    }
}
