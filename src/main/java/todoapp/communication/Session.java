package main.java.todoapp.communication;

import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;

import java.io.IOException;

public class Session {
    private static Session instance;
    private final UserConnection userConnection = new UserConnection();

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
}
