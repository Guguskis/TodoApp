package main.java.todoapp.model;

import main.java.todoapp.exceptions.DuplicateException;
import main.java.todoapp.exceptions.NotFoundException;
import main.java.todoapp.exceptions.UnauthorisedException;
import main.java.todoapp.repository.ConnectionManager;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppManager implements Serializable {
    private User currentUser = null;
    private List<User> users = new ArrayList();
    private ConnectionManager connection = new ConnectionManager();

    public AppManager() {
    }

    public void register(Person person) throws DuplicateException {
        createUser(person);
    }

    public Company register(Company company) {
        try {
            createUser(company);
            return company;
        } catch (DuplicateException e) {
            return null;
        }
    }

    private void createUser(User user) throws DuplicateException {
        try {
            getUser(user.getUsername());
            throw new DuplicateException("Username " + user.getUsername() + " already exists");

        } catch (NotFoundException e) {
            users.add(user);
        }

    }

    public void login(String username, String password) throws UnauthorisedException, SQLException, ClassNotFoundException {
        var connectionResult = connection.findByUsername(username);

        for (var user : connectionResult) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase()) && user.getPassword().equals(password)) {
                currentUser = user;
                return;
            }
        }
        throw new UnauthorisedException();
    }

    public void logout() {
        currentUser = null;
    }

    public boolean canRegister(String username) {
        try {
            getUser(username);
            return false;
        } catch (NotFoundException e) {
            return true;
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getUser(int id) throws NotFoundException {
        for (var user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new NotFoundException("User with id=" + id + " not found.");
    }

    public User getUser(String username) throws NotFoundException {
        for (var user : users) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return user;
            }
        }
        throw new NotFoundException("User with username=" + username + " not found.");
    }

    public List<Project> getProjects() {
        if (currentUser != null) {
            return currentUser.getProjects();

        } else {
            return null;
        }
    }

    public Project getProject(String projectName) {
        if (currentUser == null) {
            return null;
        }
        try {
            return currentUser.getProject(projectName);

        } catch (NotFoundException e) {
            return null;
        }
    }

    public void createProject(Project project) throws Exception {
        if (currentUser == null) {
            return;
        }

        var existingProject = getProject(project.getName());
        if (existingProject != null) {
            throw new DuplicateException("Project \"" + project.getName() + "\" already exists.");
        }
        currentUser.createProject(project);
    }

    public void removeProject(Project projectToRemove) {
        if (currentUser == null) {
            return;
        }

        if (!projectToRemove.isOwner(currentUser)) {
            return;
        }

        var projects = currentUser.getProjects();
        var filteredList = projects.stream()
                .filter(project -> project.equals(projectToRemove))
                .findFirst();
        if (filteredList == null) {
            return;
        }

        var projectMembers = projectToRemove.getMembers();
        projectMembers.stream()
                .forEach(member -> member.removeProject(projectToRemove));

        projects.removeIf(project -> project.equals(projectToRemove));
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
