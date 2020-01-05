package main.java.todoapp.exceptions;

public class HttpRequestFailedException extends Exception {
    public HttpRequestFailedException(String message) {
        super(message);
    }
}
