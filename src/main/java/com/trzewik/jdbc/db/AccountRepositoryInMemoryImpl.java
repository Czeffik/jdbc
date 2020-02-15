package com.trzewik.jdbc.db;

import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class AccountRepositoryInMemoryImpl implements AccountRepository {
    private final Map<Long, Account> repository = new HashMap<>();

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Account> findById(long userId) {
        Account account = repository.get(userId);
        return Optional.ofNullable(account);
    }

    @Override
    public void save(Account account) {
        repository.put(account.getUserId(), account);
    }

    @Override
    public void saveMany(List<Account> accounts) {
        accounts.forEach(a -> repository.put(a.getUserId(), a));
    }

    @Override
    public void delete(Account account) {
        repository.remove(account.getUserId());
    }

    @Override
    public void update(Account account) {
        repository.put(account.getUserId(), account);
    }
}
