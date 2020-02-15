package com.trzewik.jdbc.reader;

import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountsProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountsProviderFactory {

    public static AccountsProvider<Account> createAccountCsvReader() {
        return new AccountCsvReader(new CsvParser());
    }
}
