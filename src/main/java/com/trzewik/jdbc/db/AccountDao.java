package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class AccountDao implements Dao<Account> {
    private final Db<Account> db;

    @Override
    public Optional<Account> get(long id) {
        return Optional.ofNullable(db.get(id, Account.class));
    }

    @Override
    public List<Account> getAll() {
        return db.getAll("SELECT a FROM Account a", Account.class);
    }

    @Override
    public void save(Account account) {
        db.save(account);
    }

    @Override
    public void update(Account updated) {
        db.update(updated);
    }

    @Override
    public void delete(Account account) {
        db.delete(account);
    }
}
