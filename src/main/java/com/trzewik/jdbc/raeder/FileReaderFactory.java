package com.trzewik.jdbc.raeder;

import com.trzewik.jdbc.domain.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileReaderFactory {

    public static FileReader<Account> createAccountCsvReader() {
        return new AccountCsvReader();
    }
}
