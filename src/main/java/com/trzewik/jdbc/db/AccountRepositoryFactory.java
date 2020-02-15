package com.trzewik.jdbc.db;

import com.trzewik.jdbc.domain.AccountRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepositoryFactory {
    public static AccountRepository create() {
        try {
            return new AccountRepositoryImpl(DbFactory.accountDao());
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }

    public static AccountRepository createInMemory() {
        return new AccountRepositoryInMemoryImpl();
    }

    private static class DbFactory {

        static Dao<AccountEntity> accountDao() throws SQLException {
            return new AccountDao(db());
        }

        private static Db db() throws SQLException {
            return new Db(properties());
        }

        private static DbProperties properties() {
            return new DbProperties();
        }
    }
}
