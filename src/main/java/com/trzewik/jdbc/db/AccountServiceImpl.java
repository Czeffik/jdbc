package com.trzewik.jdbc.db;

import com.trzewik.jdbc.raeder.FileReader;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class AccountServiceImpl implements AccountService {
    private final Dao<Account> accountDao;
    private final FileReader<Account> fileReader;

    @Override
    public List<Account> getAllAccounts() throws SQLException {
        return accountDao.findAll();
    }

    @Override
    public Account getAccountById(long userId) throws SQLException {
        Optional<Account> optional = accountDao.findById(userId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new AccountNotFoundException(userId);
    }

    @Override
    public void createAccount(String username, String email) throws SQLException {
        Account account = new Account(username, email);
        accountDao.save(account);
    }

    @Override
    public void deleteAccount(long userId) throws SQLException {
        accountDao.delete(getAccountById(userId));
    }

    @Override
    public void updateAccount(long userId, String username, String email) throws SQLException {
        if (StringUtils.isAllBlank(username, email)) {
            throw new IllegalArgumentException("At least one field must not be blank when trying update account!");
        }
        Account account = getAccountById(userId);

        Account updated = createUpdated(account, username, email);

        accountDao.update(updated);
    }

    @Override
    public void createAccountsFromCsv(String pathToFile) throws SQLException, FileNotFoundException {
        List<Account> accounts = fileReader.read(pathToFile);

        accountDao.saveMany(accounts);
    }

    private Account createUpdated(Account account, String username, String email) {
        Account updated;
        if (updateAll(username, email)) {
            updated = new Account(account.getUserId(), username, email);
        } else if (updateUsername(username)) {
            updated = new Account(account.getUserId(), username, account.getEmail());
        } else {
            updated = new Account(account.getUserId(), account.getUsername(), email);
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

    public static class AccountNotFoundException extends RuntimeException {
        AccountNotFoundException(long userId) {
            super(String.format("Account with userId: [%s] not found!", userId));
        }
    }
}
