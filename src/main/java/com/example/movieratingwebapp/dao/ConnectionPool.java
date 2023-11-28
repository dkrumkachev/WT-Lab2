package com.example.movieratingwebapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool  {
    private static final ConnectionPool instance = new ConnectionPool();
    private final int POOL_CAPACITY = 10;
    private final BlockingQueue<Connection> connectionPool = new ArrayBlockingQueue<>(POOL_CAPACITY);
    private final BlockingQueue<Connection> givenAwayConnections = new ArrayBlockingQueue<>(POOL_CAPACITY);
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public synchronized static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        URL = bundle.getString("db.url") + bundle.getString("db.name");
        USER = bundle.getString("db.user");
        PASSWORD = bundle.getString("db.password");
        try {
            for (int i = 0; i < POOL_CAPACITY; i++) {
                connectionPool.add(createConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Unable to create a connection pool.");
        }
    }

    public synchronized Connection getConnection() {
        try {
            Connection connection = connectionPool.take();
            givenAwayConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            return null;
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        try {
            givenAwayConnections.remove(connection);
            connectionPool.put(connection);
        } catch (InterruptedException ignored) { }
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
