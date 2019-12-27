package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class AccountDao implements Dao<Account> {
    private final Db db;

    @Override
    public Optional<Account> get(long id) throws SQLException {
        String select = String.format("SELECT * FROM %s.account WHERE user_id = %s", DEFAULT_SCHEMA, id);
        ResultSet rs = db.executeQuery(select);

        return Optional.ofNullable(rs.next() ? new Account(rs) : null);
    }

    @Override
    public List<Account> getAll() throws SQLException {
        String selectAll = String.format("SELECT * FROM %s.account", DEFAULT_SCHEMA);
        ResultSet rs = db.executeQuery(selectAll);
        List<Account> accounts = new ArrayList<>();

        while (rs.next()) {
            accounts.add(new Account(rs));
        }

        return accounts;
    }

    @Override
    public void save(Account account) throws SQLException {
        String insert = String.format("INSERT INTO %s.account (username, email) VALUES ('%s', '%s')",
            DEFAULT_SCHEMA, account.getUsername(), account.getEmail());
        db.executeUpdate(insert);
    }

    @Override
    public void update(Account account, Account updated) throws SQLException {
        String update = String.format("UPDATE %s.account SET username = '%s', email = '%s' WHERE user_id = %s",
            DEFAULT_SCHEMA, updated.getUsername(), updated.getEmail(), account.getUserId());
        db.executeUpdate(update);
    }

    @Override
    public void delete(Account account) throws SQLException {
        String delete = String.format("DELETE FROM %s.account where user_id = %s", DEFAULT_SCHEMA, account.getUserId());
        db.executeUpdate(delete);
    }
}
