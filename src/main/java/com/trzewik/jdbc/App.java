package com.trzewik.jdbc;

import com.trzewik.jdbc.db.Account;
import com.trzewik.jdbc.db.Dao;
import com.trzewik.jdbc.db.DbFactory;

public class App {
    public static void main(String[] args) {
        Dao<Account> dao = DbFactory.accountDao();
    }
}
