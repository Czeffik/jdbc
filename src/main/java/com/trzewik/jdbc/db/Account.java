package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@ToString
public class Account {
    private long userId;
    private final @NonNull String username;
    private final @NonNull String email;

    Account(ResultSet rs) throws SQLException {
        this.userId = rs.getLong("user_id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
    }

    Account(String username, String email) {
        if (StringUtils.isAnyBlank(username, email)) {
            throw new AccountCreationException(username, email);
        }
        this.username = username;
        this.email = email;
    }

    public static class AccountCreationException extends RuntimeException {
        AccountCreationException(String username, String email) {
            super(String.format("Can not create new account with username: [%s] and email: [%s]!", username, email));
        }
    }
}
