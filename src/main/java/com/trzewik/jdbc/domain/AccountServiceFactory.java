package com.trzewik.jdbc.domain;

import com.trzewik.jdbc.reader.FileReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceFactory {
    public static AccountService create(AccountRepository repository, FileReader<Account> reader) {
        return new AccountServiceImpl(repository, reader);
    }
}
