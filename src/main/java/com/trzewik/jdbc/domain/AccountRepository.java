package com.trzewik.jdbc.domain;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> getAll();

    Optional<Account> findById(long userId);

    default Account getById(long userId) {
        return findById(userId).orElseThrow(() -> new AccountNotFoundException(userId));
    }

    void save(Account account);

    void saveMany(List<Account> accounts);

    void delete(Account account);

    void update(Account account);

    class AccountNotFoundException extends RuntimeException {
        public AccountNotFoundException(long userId) {
            super(String.format("Account with userId: [%s] not found!", userId));
        }
    }

    class Exception extends RuntimeException {
        public Exception(SQLException e) {
            super(e);
        }
    }
}
