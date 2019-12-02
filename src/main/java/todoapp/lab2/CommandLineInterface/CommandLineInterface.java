package main.java.todoapp.lab2.CommandLineInterface;

import main.java.todoapp.exceptions.DuplicateException;
import main.java.todoapp.exceptions.NotFoundException;
import main.java.todoapp.exceptions.UnauthorisedException;
import main.java.todoapp.model.AppManager;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.Project;
import main.java.todoapp.model.Task;
import main.java.todoapp.model.User;
import main.java.todoapp.model.UserInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CommandLineInterface implements UserInterface {
    private AppManager appManager;
    private UserInput userInput;

    public CommandLineInterface(AppManager appManager, UserInput userInput) {
        this.appManager = appManager;
        this.userInput = userInput;
    }

    @Override
    public void register() {
        var options = new ArrayList<String>(Arrays.asList("Person", "Company", "Return"));
        var selection = userInput.getSelectable(options);

        switch (selection) {
            case "Person":
                String personUsername = "";
                do {
                    personUsername = userInput.getString("Enter username");
                    if (!appManager.canRegister(personUsername)) {
                        System.out.println("Username already in use");
                    }
                } while (!appManager.canRegister(personUsername));

                var personPassword = userInput.getString("Enter password");
                var firstName = userInput.getString("Enter your first name");
                var lastName = userInput.getString("Enter your last name");
                var phone = userInput.getString("Enter your phone");
                var email = userInput.getString("Enter your email");

                try {
                    appManager.register(new Person(firstName, lastName, phone, email, personUsername, personPassword));
                } catch (DuplicateException e) {
                    e.printStackTrace();
                }
                break;

            case "Company":
                String companyUsername = "";
                do {
                    companyUsername = userInput.getString("Enter username");
                    if (!appManager.canRegister(companyUsername)) {
                        System.out.println("Username already in use");
                    }
                } while (!appManager.canRegister(companyUsername));

                var companyPassword = userInput.getString("Enter password");
                var companyName = userInput.getString("Enter company name");
                var contactPerson = userInput.getString("Enter contact person full name");
                appManager.register(new Company(companyName, contactPerson, companyUsername, companyPassword));
                break;
            case "Return":
                break;
        }
    }

    @Override
    public boolean login() {
        int maxAttempts = 3;
        int attempts = 0;

        while (true) {
            String username = userInput.getString("Enter your username");
            String password = userInput.getString("Enter your password");

            try {
                appManager.login(username, password);
                return true;
            } catch (UnauthorisedException e) {
                attempts++;
                if (attempts != maxAttempts) {
                    System.out.println("Wrong username or password, " + (maxAttempts - attempts) + " attempts remaining");
                }
            }

            if (attempts == maxAttempts) {
                return false;
            }
        }
    }

    @Override
    public void createProject() {
        var projectName = userInput.getString("Enter project name:");
        Project newProject = new Project(projectName);

        try {
            appManager.createProject(newProject);
            System.out.println("Project \"" + newProject.getName() + "\" has been created");

        } catch (Exception e) {
            System.out.println("Project \"" + projectName + "\" already exists");
        }

    }

    @Override
    public void addMember(Project project) {
        if (appManager.getCurrentUser() != project.getOwner()) {
            return;
        }

        var username = userInput.getString("Enter member username:");
        try {
            var whatToAdd = appManager.getUser(username);
            var whoIsAdding = appManager.getCurrentUser();
            project.addMember(whoIsAdding, whatToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createTask(User who, Task destination) {
        String taskName = userInput.getString("Enter task name:");
        Task newTask = new Task(taskName, who);
        try {
            destination.addTask(who, newTask);
        } catch (DuplicateException e) {
            System.out.println("Task with that name already exists");
        }
    }

    @Override
    public void createTask(User who, Project destination) {
        String taskName = userInput.getString("Enter task name:");
        Task newTask = new Task(taskName, who);
        try {
            destination.addTask(newTask);
        } catch (DuplicateException e) {
            System.out.println("Task already exists");
        }
    }

    @Override
    public void manageProject(Project project) {
        while (true) {
            var options = new ArrayList<String>(Arrays.asList("Create task"));
            var containsTasks = !project.getTasks().isEmpty();
            if (containsTasks) {
                options.add("Select task");
                options.add("Delete task");
            }
            options.add("More info");

            var isOwner = project.isOwner(appManager.getCurrentUser());
            if (isOwner) {
                options.add("Add member");
            }

            var hasMembersToRemove = project.getMembers().size() > 1;
            if (hasMembersToRemove && isOwner) {
                options.add("Remove member");
            }
            if (isOwner) {
                options.add("Delete this project");
            }
            options.add("Return");

            var selection = userInput.getSelectable(options);

            switch (selection) {
                case "Create task":
                    createTask(appManager.getCurrentUser(), project);
                    break;
                case "More info":
                    displayText(project.toString());
                    break;
                case "Select task":
                    var selectedTask = selectTask(project.getTasks());
                    if (selectedTask != null) {
                        manageTask(appManager.getCurrentUser(), selectedTask);
                    }
                    break;
                case "Delete task":
                    var taskToRemove = selectTask(project.getTasks());
                    if (taskToRemove != null) {
                        project.removeTask(taskToRemove);
                    }
                    break;
                case "Add member":
                    addMember(project);
                    break;
                case "Remove member":
                    removeMember(appManager.getCurrentUser(), project);
                    break;
                case "Delete this project":
                    appManager.removeProject(project);
                    return;
                case "Return":
                    return;
            }
        }

    }

    @Override
    public Project selectProject(List<Project> projects) {
        if (projects.isEmpty()) {
            return null;
        }

        var labels = projects.stream()
                .map(project -> project.getName())
                .collect(Collectors.toList());
        var selection = userInput.getSelectable(projects, labels);

        return selection;
    }

    @Override
    public void changeUsername() {
        var username = userInput.getString("Enter new username:");
        try {
            appManager.getUser(username);
        } catch (NotFoundException e) {
            appManager.getCurrentUser().setUsername(username);
        }
    }

    @Override
    public void changePassword() {
        var contactPerson = userInput.getString("Enter phone:");
        var currentUser = appManager.getCurrentUser();
        currentUser.setPassword(contactPerson);
    }

    @Override
    public void changeEmail() {
        var email = userInput.getString("Enter email:");
        var currentUser = (Person) appManager.getCurrentUser();
        currentUser.setEmail(email);
    }

    @Override
    public void changePhone() {
        var phone = userInput.getString("Enter phone:");
        var currentUser = (Person) appManager.getCurrentUser();
        currentUser.setPhone(phone);
    }

    @Override
    public void changeContactPerson() {
        var contactPerson = userInput.getString("Enter phone:");
        var currentUser = (Company) appManager.getCurrentUser();
        currentUser.setContactPerson(contactPerson);
    }

    @Override
    public void logout() {
        appManager.logout();
    }

    private void manageTask(User user, Task task) {
        while (true) {
            var options = new ArrayList<String>();
            if (!task.getTasks().isEmpty()) {
                options.add("Select subtask");
            }
            options.add("Add task");
            options.add("More info");
            options.add(task.getCompleted() ? "Set to incomplete" : "Set to completed");
            if (!task.getTasks().isEmpty()) {
                options.add("Remove subtask");
            }
            options.add("Return");
            var selection = userInput.getSelectable(options);

            switch (selection) {
                case "Select subtask":
                    var selectedTask = selectTask(task.getTasks());
                    if (selectedTask != null) {
                        manageTask(user, selectedTask);
                    }
                    break;
                case "More info":
                    displayText(task.toString());
                    break;
                case "Add task":
                    createTask(user, task);
                    break;
                case "Set to completed":
                    task.setCompleted(user);
                    break;
                case "Set to incomplete":
                    task.setIncompleted();
                    break;
                case "Remove subtask":
                    var subtaskToRemove = selectTask(task.getTasks());
                    if (subtaskToRemove != null) {
                        task.deleteTask(subtaskToRemove);
                    }
                    break;
                case "Return":
                    return;
            }
        }

    }

    private void removeMember(User whoIsRemoving, Project project) {
        if (whoIsRemoving != project.getOwner()) {
            return;
        }

        var members = new ArrayList<User>(project.getMembers());
        var labels = members.stream()
                .map(member -> member.getUsername())
                .collect(Collectors.toList());

        members.removeIf(member -> member.equals(project.getOwner()));
        labels.removeIf(label -> label.equals(whoIsRemoving.getUsername()));

        if (labels.isEmpty()) {
            System.out.println("There are no members to remove.");
            return;
        }

        var memberToRemove = userInput.getSelectable(members, labels);

        if (memberToRemove != null) {
            project.removeMember(memberToRemove);
        }
    }

    private Task selectTask(List<Task> tasks) {
        var labels = tasks.stream()
                .map(task -> task.getName())
                .collect(Collectors.toList());
        var selection = userInput.getSelectable(tasks, labels);

        return selection;
    }

    public void displayText(String text) {
        System.out.println(text);
        System.out.println("Press enter to continue");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}
