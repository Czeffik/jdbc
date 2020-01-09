package com.trzewik.jdbc.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceFactory {
    public static AccountService create() throws SQLException {
        return new AccountServiceImpl(DbFactory.accountDao());
    }
}
