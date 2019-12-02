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

    public List<User> findByUsername(String username) throws SQLException, ClassNotFoundException {
        connect();

        PreparedStatement ps = connection.prepareStatement("select * from user where username=?");
        ps.setString(1, username);
        ResultSet result = ps.executeQuery();

        ArrayList<User> users = mapResultToUsers(result);

        result.close();
        ps.close();
        disconnect();

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
