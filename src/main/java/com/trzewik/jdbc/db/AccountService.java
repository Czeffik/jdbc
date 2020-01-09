package com.trzewik.jdbc.db;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts() throws SQLException;

    Account getAccountById(long userId) throws SQLException;

    void createAccount(String username, String email) throws SQLException;

    void deleteAccount(long userId) throws SQLException;

    void updateAccount(long userId, String username, String email) throws SQLException;
}
