package main.java.todoapp.repository;

import main.java.todoapp.model.User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager implements Serializable {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Todo create table
        connection = DriverManager.getConnection("jdbc:mysql://localhost/myscheme", "root", "");
    }

    private void disconnect() throws SQLException {
        connection.close();
    }

    public List<User> findAll() throws SQLException, ClassNotFoundException {
        connect();

        String findAllQuery = "select * from 'user'";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(findAllQuery);

        ArrayList<User> users = mapResultToUsers(result);

        result.close();
        statement.close();
        disconnect();

        return users;
    }

    public List<User> findByUsername(String username) throws ClassNotFoundException {
        List<User> users = new ArrayList<>();
        try {
            connect();
            String query = "select * from user where username=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();

            users = mapResultToUsers(result);

            result.close();
            preparedStatement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private ArrayList<User> mapResultToUsers(ResultSet result) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
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
