package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class AccountDao implements Dao<AccountEntity> {
    private final Db db;

    @Override
    public Optional<AccountEntity> findById(long id) throws SQLException {
        String query = String.format("SELECT * FROM %s.account WHERE user_id = ?", DEFAULT_SCHEMA);

        PreparedStatement statement = db.preparedStatement(query);
        statement.setLong(1, id);
        ResultSet rs = db.executeQuery(statement);

        return Optional.ofNullable(rs.next() ? new AccountEntity(rs) : null);
    }

    @Override
    public List<AccountEntity> findAll() throws SQLException {
        String query = String.format("SELECT * FROM %s.account", DEFAULT_SCHEMA);

        PreparedStatement statement = db.preparedStatement(query);
        ResultSet rs = db.executeQuery(statement);

        List<AccountEntity> accounts = new ArrayList<>();
        while (rs.next()) {
            accounts.add(new AccountEntity(rs));
        }

        return accounts;
    }

    @Override
    public void save(@NonNull AccountEntity account) throws SQLException {
        String query = String.format("INSERT INTO %s.account (username, email) VALUES (?, ?)", DEFAULT_SCHEMA);

        PreparedStatement statement = db.preparedStatement(query);
        statement.setString(1, account.getUsername());
        statement.setString(2, account.getEmail());

        db.executeUpdate(statement);
    }

    @Override
    public void update(@NonNull AccountEntity updated) throws SQLException {
        String query = String.format("UPDATE %s.account SET username = ?, email = ? WHERE user_id = ?", DEFAULT_SCHEMA);

        PreparedStatement statement = db.preparedStatement(query);
        statement.setString(1, updated.getUsername());
        statement.setString(2, updated.getEmail());
        statement.setLong(3, updated.getUserId());

        db.executeUpdate(statement);
    }

    @Override
    public void delete(@NonNull AccountEntity account) throws SQLException {
        String query = String.format("DELETE FROM %s.account where user_id = ?", DEFAULT_SCHEMA);

        PreparedStatement statement = db.preparedStatement(query);
        statement.setLong(1, account.getUserId());

        db.executeUpdate(statement);
    }

    @Override
    public void saveMany(@NonNull List<AccountEntity> accounts) throws SQLException {
        if (!accounts.isEmpty()) {
            try {
                db.startTransaction();
                for (AccountEntity account : accounts) {
                    save(account);
                }
            } finally {
                db.endTransaction();
            }
        }
    }
}
