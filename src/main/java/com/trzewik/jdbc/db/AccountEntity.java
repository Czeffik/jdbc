package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
public class AccountEntity {
    private long userId;
    private String username;
    private String email;

    AccountEntity(ResultSet rs) throws SQLException {
        this.userId = rs.getLong("user_id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
    }

    AccountEntity(AccountDto dto) {
        this.userId = dto.getUserId();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
    }
}
