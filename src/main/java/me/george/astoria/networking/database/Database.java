package me.george.astoria.networking.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;
    private static Database instance;

    private String host, database, username, password;
    private int port;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static boolean isConnected() {
        return (connection == null ? false : true);
    }

    public static Connection getConnection() {
        return connection;
    }

    public void connect() {
        host = "localhost";
        port = 3306;
        database = "astoria";
        username = "root"; // change of course
        password = ""; // change of course

        try {
            openConnection();

            System.out.println("CONNECTED to Database: " + database);
        } catch (SQLException exception) {
            exception.printStackTrace();

            System.err.println("UNABLE TO CONNECT to Database: " + database);
        }
    }

    private void openConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("SHUTTING DOWN Database: " + database);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
