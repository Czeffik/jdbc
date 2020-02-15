package com.trzewik.jdbc.reader;

import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountFactory;
import com.trzewik.jdbc.domain.AccountsProvider;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class AccountCsvReader implements AccountsProvider<Account> {
    private final CsvParser parser;

    @Override
    public List<Account> provide(String path) throws FileNotFoundException {
        return parser.parseFile(path).stream()
            .map(AccountFactory::create)
            .collect(Collectors.toList());
    }
}
