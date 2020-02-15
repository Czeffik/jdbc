package com.trzewik.jdbc.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceFactory {
    public static AccountService create(AccountRepository repository, AccountsProvider<Account> reader) {
        return new AccountServiceImpl(repository, reader);
    }
}
