package com.trzewik.jdbc.domain;

import com.trzewik.jdbc.reader.FileReader;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final FileReader<Account> fileReader;

    @Override
    public List<Account> getAll() {
        return repository.getAll();
    }

    @Override
    public Account getById(long userId) {
        return repository.getById(userId);
    }

    @Override
    public void create(String username, String email) {
        Account account = AccountFactory.create(username, email);
        repository.save(account);
    }

    @Override
    public void delete(long userId) {
        repository.delete(repository.getById(userId));
    }

    @Override
    public void update(long userId, String username, String email) {
        if (StringUtils.isAllBlank(username, email)) {
            throw new IllegalArgumentException("At least one field must not be blank when trying update account!");
        }
        Account account = repository.getById(userId);

        Account updated = createUpdated(account, username, email);

        repository.update(updated);
    }

    @Override
    public void createFromCsv(String pathToFile) throws FileNotFoundException {
        List<Account> accounts = fileReader.read(pathToFile);

        repository.saveMany(accounts);
    }

    private Account createUpdated(Account account, String username, String email) {
        Account updated;
        if (updateAll(username, email)) {
            updated = AccountFactory.create(account.getUserId(), username, email);
        } else if (updateUsername(username)) {
            updated = AccountFactory.create(account.getUserId(), username, account.getEmail());
        } else {
            updated = AccountFactory.create(account.getUserId(), account.getUsername(), email);
        }
        return updated;
    }

    private boolean updateAll(String username, String email) {
        return updateUsername(username) && updateEmail(email);
    }

    private boolean updateUsername(String username) {
        return StringUtils.isNotBlank(username);
    }

    private boolean updateEmail(String email) {
        return StringUtils.isNotBlank(email);
    }
}
