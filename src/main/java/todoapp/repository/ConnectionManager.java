package main.java.todoapp.repository;

import main.java.todoapp.model.TempTask;
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
    private final String connectionUsername = "root";
    private final String connectionPassword = "";

    private Connection connection;

    private void connect() {
        // Prisijungimas
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, connectionUsername, connectionPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    private void disconnect() throws SQLException {
        // Atsijungimas
        connection.close();
    }

    public List<User> findAllUsers() {
        // Pilnas gavimas
        List<User> users = new ArrayList<>();
        String query = "select * from user";

        try {
            connect();
            Statement statement = createStatement();

            ResultSet result = statement.executeQuery(query);
            users = mapResultToUsers(result);

            result.close();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println("findAll SQL query is wrong.");
        }

        return users;
    }

    public List<TempTask> getIncompleteProjectTasks(int id) {
        // Filtravimas
        List<TempTask> tasks = new ArrayList<>();
        String query = "select t.id, t.name\n" +
                "from project as p, task as t\n" +
                "where p.id=t.projectId and p.id=?\n" +
                "and t.completedById is null;";

        try {
            connect();
            PreparedStatement preparedStatement = getPreparedStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();
            tasks = getMappedTasks(result);

            result.close();
            preparedStatement.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println("Wrong getIncompleteProjectTasks query.");
        }

        return tasks;
    }

    public Optional<User> findByUsername(String username) {
        // Atrinktas
        List<User> users = new ArrayList<>();
        String query = "select * from user where username=?";

        try {
            connect();
            PreparedStatement preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();
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

    public void removeTask(int id) {
        // Salinimas
        String query = "delete from task where id=?";

        try {
            connect();
            PreparedStatement preparedStatement = getPreparedStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            preparedStatement.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println("Incorrect removeTask query.");
        }
    }

    public void updateTaskName(int id, String name) {
        // Redagavimas
        String query = "update task\n" +
                "set name = ?\n" +
                "where id = ?";

        try {
            connect();
            PreparedStatement preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIncompleteTasksCount(String username) {
        // Is keliu lenteliu
        int count = 0;
        String query = "select u.username, count(p.id) as count \n" +
                "from user as u, project as p, task as t \n" +
                "where u.id=p.ownerId AND p.id=t.projectId \n" +
                "AND u.username=? \n" +
                "AND t.completedById is null \n" +
                "group by u.username; ";
        try {
            connect();
            PreparedStatement statement = getPreparedStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            result.next();
            count = result.getInt("count");

            result.close();
            statement.close();
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    private List<TempTask> getMappedTasks(ResultSet result) throws SQLException {
        List<TempTask> tasks = new ArrayList<>();
        while (result.next()) {
            TempTask task = new TempTask();
            task.setId(result.getInt("id"));
            task.setName(result.getString("name"));
            tasks.add(task);
        }
        return tasks;
    }

    private Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
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
