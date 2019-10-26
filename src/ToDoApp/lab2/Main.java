package ToDoApp.lab2;

import ToDoApp.model.AppManager;
import ToDoApp.model.DataStorageManager;
import ToDoApp.model.UserInput;
import ToDoApp.lab2.CommandLineInterface.CommandLineInterface;
import ToDoApp.model.Person;
import ToDoApp.model.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static String dataFileName = "data.txt";

    public static void main(String[] args) {
        var CLI = new CommandLineInterface(new AppManager(), new UserInput());
        var dataStorageManager = new DataStorageManager();
        var userInput = new UserInput();
        AppManager appManager = importData(dataStorageManager);

        var inAuthenticationMenu = true;
        var authenticationMenu = new ArrayList<String>(Arrays.asList("Login", "Register", "Exit"));

        while (inAuthenticationMenu) {
            var authenticationSelection = userInput.getSelectable(authenticationMenu);

            switch (authenticationSelection) {
                case "Login":
                    var success = CLI.login();
                    if (success) {
                        mainMenu(appManager, userInput, CLI, dataStorageManager);
                    } else {
                        inAuthenticationMenu = false;
                    }
                    break;
                case "Register":
                    CLI.register();
                    exportData(appManager, dataStorageManager);
                    break;
                case "Exit":
                    inAuthenticationMenu = false;
                    break;
            }
        }
    }

    public static void mainMenu(AppManager appManager, UserInput userInput, CommandLineInterface CLI, DataStorageManager dataStorageManager) {
        var inMainMenu = true;

        while (inMainMenu) {
            var user = appManager.getCurrentUser();

            var mainMenu = new ArrayList<String>();
            mainMenu.add("Create project");
            mainMenu.add("Manage account");
            if (!user.getProjects().isEmpty()) {
                mainMenu.add("Manage projects");
            }
            mainMenu.add("Logout");
            var mainSelection = userInput.getSelectable(mainMenu);

            switch (mainSelection) {
                case "Create project":
                    CLI.createProject();
                    break;
                case "Manage account":
                    accountMenu(appManager, CLI, userInput);
                    break;
                case "Manage projects":
                    Project selectedProject = CLI.selectProject(appManager.getProjects());
                    if (selectedProject != null) {
                        CLI.manageProject(selectedProject);
                    }
                    break;
                case "Logout":
                    CLI.logout();
                    exportData(appManager, dataStorageManager);
                    inMainMenu = false;
                    break;
            }
        }
    }

    public static void accountMenu(AppManager appManager, CommandLineInterface CLI, UserInput userInput) {
        var user = appManager.getCurrentUser();

        var inAccountMenu = true;
        var accountMenu = new ArrayList<String>(Arrays.asList("Account info", "Change username", "Change password"));
        if (user instanceof Person) {
            accountMenu.add("Change email");
            accountMenu.add("Change phone");
        } else {
            accountMenu.add("Change contact person");
        }
        accountMenu.add("Return");

        while (inAccountMenu) {
            System.out.println("Welcome, " + appManager.getCurrentUser().getUsername());

            var accountSelection = userInput.getSelectable(accountMenu);
            switch (accountSelection) {
                case "Account info":
                    CLI.displayText(appManager.getCurrentUser().toString());
                    break;
                case "Change username":
                    CLI.changeUsername();
                    break;
                case "Change password":
                    CLI.changePassword();
                    break;
                case "Change email":
                    CLI.changeEmail();
                    break;
                case "Change phone":
                    CLI.changePhone();
                    break;
                case "Change contact person":
                    CLI.changeContactPerson();
                    break;
                case "Return":
                    inAccountMenu = false;
                    break;
            }
        }
    }

    public static AppManager importData(DataStorageManager dataStorageManager) {
        try {
            return dataStorageManager.importData(dataFileName);
        } catch (Exception e) {
            System.out.println("Thank you for downloading this app. ");
            return new AppManager();
        }
    }

    public static void exportData(AppManager appManager, DataStorageManager dataStorageManager) {
        try {
            dataStorageManager.exportData(dataFileName, appManager);
        } catch (IOException e) {
            System.out.println("Failed to export data");
        }
    }

}
