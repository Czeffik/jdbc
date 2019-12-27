package com.trzewik.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Db implements AutoCloseable {
    Connection connection;

    Db(DbProperties properties) throws SQLException {
        connection = DriverManager.getConnection(
            properties.getUrl(),
            properties.getUsername(),
            properties.getPassword());
    }

    ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    void executeUpdate(String updateQuery) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.executeUpdate();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
