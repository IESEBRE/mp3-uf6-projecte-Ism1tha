package com.insebre.project.model;

import java.sql.Connection;

/**
 * The DatabaseConnection class represents a database connection configuration.
 */
public class DatabaseConnection {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    private Connection connection;

    /**
     * Constructs a new DatabaseConnection instance with the specified JDBC URL, username, and password.
     *
     * @param jdbcUrl  the JDBC URL of the database
     * @param username the username for accessing the database
     * @param password the password for accessing the database
     */
    public DatabaseConnection(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the JDBC URL of the database.
     *
     * @return the JDBC URL of the database
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * Gets the username for accessing the database.
     *
     * @return the username for accessing the database
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password for accessing the database.
     *
     * @return the password for accessing the database
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the connection to the database.
     *
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the connection to the database.
     *
     * @return the connection to the database
     */
    public Connection getConnection() {
        return connection;
    }
}
