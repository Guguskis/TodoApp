package main.java.todoapp.exceptions;

public class HttpRequestFailedException extends Throwable {
    public HttpRequestFailedException(String message) {
        super(message);
    }
}
