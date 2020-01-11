package com.trzewik.jdbc.raeder;

import com.trzewik.jdbc.db.Account;
import com.trzewik.jdbc.db.AccountFactory;
import com.trzewik.jdbc.util.CsvParser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

class AccountCsvReader implements FileReader<Account> {
    @Override
    public List<Account> read(String path) throws FileNotFoundException {
        return CsvParser.parseFile(path).stream()
            .map(AccountFactory::create)
            .collect(Collectors.toList());
    }
}
