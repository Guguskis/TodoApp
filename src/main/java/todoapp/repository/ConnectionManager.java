package main.java.todoapp.repository;

import main.java.todoapp.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectionManager {
    private final String className = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost/myscheme";
    private final String username = "root";
    private final String password = "";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet result;
    private Statement statement;

    private void connect() {
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    private void disconnect() throws SQLException {
        connection.close();
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String findAllQuery = "select * from 'user'";

        try {
            connect();
            statement = connection.createStatement();
            result = statement.executeQuery(findAllQuery);

            users = mapResultToUsers(result);

            result.close();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println("findAll SQL query is wrong.");
        }

        return users;
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = new ArrayList<>();
        String query = "select * from user where username=?";

        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();

            users = mapResultToUsers(result);

            result.close();
            preparedStatement.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println("findByUsername SQL query is wrong.");
        }

        User user = users.size() == 1 ? users.get(0) : null;
        return Optional.ofNullable(user);
    }

    private List<User> mapResultToUsers(ResultSet result) throws SQLException {
        List<User> users = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String username = result.getString("username");
            String password = result.getString("password");
            Boolean active = result.getBoolean("active");

            users.add(new User(id, username, password, active));
        }
        return users;
    }


}
