package com.trzewik.jdbc;

import com.trzewik.jdbc.db.Account;
import com.trzewik.jdbc.db.Dao;
import com.trzewik.jdbc.db.DbFactory;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        Dao<Account> dao = DbFactory.accountDao();
    }
}
