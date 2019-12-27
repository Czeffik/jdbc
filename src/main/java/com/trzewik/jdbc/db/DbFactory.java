package com.trzewik.jdbc.db;

import java.sql.SQLException;

public class DbFactory {

    public static Dao<Account> accountDao() throws SQLException {
        return new AccountDao(db());
    }

    static Db db() throws SQLException {
        return new Db(properties());
    }

    static DbProperties properties() {
        return new DbProperties();
    }
}
