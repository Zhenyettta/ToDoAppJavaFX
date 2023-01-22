package app.todoappjavafx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectString, dbUser, dbPass);
        return dbConnection;
    }
}
