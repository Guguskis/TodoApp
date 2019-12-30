package main.java.todoapp.exceptions;

public class EmptyFieldException extends Throwable {
    public EmptyFieldException(String message) {
        super(message);
    }
}
