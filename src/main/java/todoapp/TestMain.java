package main.java.todoapp;

import main.java.todoapp.repository.ConnectionManager;

public class TestMain {
    public static void main(String[] args) {
        ConnectionManager connection = new ConnectionManager();

        connection.updateTaskName(6, "Oh papaaaaa");
    }
}
