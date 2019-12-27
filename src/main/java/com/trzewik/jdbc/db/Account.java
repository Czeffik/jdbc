package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Account {
    private final long userId;
    private final String username;
    private final String email;

    Account(ResultSet rs) throws SQLException {
        this.userId = rs.getLong("user_id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
    }
}
