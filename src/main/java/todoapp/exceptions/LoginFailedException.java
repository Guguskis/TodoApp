package main.java.todoapp.exceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
