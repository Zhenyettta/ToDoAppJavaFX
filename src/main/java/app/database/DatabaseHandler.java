package app.database;

import app.controller.LoginController;
import app.model.Task;
import app.model.User;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectString, dbUser, dbPass);

        return dbConnection;
    }


    public void deleteTask(int userId, int taskId) {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?" + " AND " + Const.TASKS_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signUpUser(User user) {
        String insert = "INSERT into " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + ","
                + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + ","
                + Const.USERS_PASSWORD + "," + Const.USERS_LOCATION + ","
                + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getTasksByUser(int userId) {
        ResultSet resultTasks = null;
        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE "
                + Const.USERS_ID + "=?" + " ORDER BY " + Const.TASKS_DATE + " DESC";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultTasks = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return resultTasks;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                + Const.USERS_USERNAME + "=?" + " AND "
                + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public int getAllTasks(int userid) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userid);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return resultSet.getInt(1);
    }

    public void insertTask(Task task) {
        String insert = "INSERT into " + Const.TASKS_TABLE + "(" + Const.USERS_ID + "," + Const.TASKS_DATE + ","
                + Const.TASKS_DESCRIPTION + "," + Const.TASKS_TASK + ")" + "VALUES(?,?,?,?)";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert)) {
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDateCreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAllTasksForToday() throws SQLException {
        int counter = 0;
        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, LoginController.getUserId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getTimestamp(4).toLocalDateTime().toLocalDate().equals(LocalDate.now())) {
                counter++;
            }
        }
        return counter;
    }

    public int getUsersCountByUsername(String username) {
        String query = "SELECT COUNT(*) FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet count = preparedStatement.executeQuery();

            while (count.next()) {
                return count.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
