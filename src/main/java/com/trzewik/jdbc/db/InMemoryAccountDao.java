package com.trzewik.jdbc.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountDao implements Dao<Account> {
    private final Map<Long, Account> repository = new HashMap<>();

    @Override
    public Optional<Account> findById(long id) throws SQLException {
        Account account = repository.get(id);
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> findAll() throws SQLException {
        return new ArrayList<>(repository.values());
    }

    @Override
    public void save(Account toSave) throws SQLException {
        repository.put(toSave.getUserId(), toSave);
    }

    @Override
    public void update(Account updated) throws SQLException {
        repository.put(updated.getUserId(), updated);
    }

    @Override
    public void delete(Account toDelete) throws SQLException {
        repository.remove(toDelete.getUserId());
    }

    @Override
    public void saveMany(List<Account> accounts) throws SQLException {
        accounts.forEach(account -> repository.put(account.getUserId(), account));
    }
}
