package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.NonNull;

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
    public void save(@NonNull Account account) {
        db.save(account);
    }

    @Override
    public void update(@NonNull Account updated) {
        db.update(updated);
    }

    @Override
    public void delete(@NonNull Account account) {
        db.delete(account);
    }

    void saveMany(@NonNull List<Account> accounts) {
        db.saveMany(accounts);
    }
}
