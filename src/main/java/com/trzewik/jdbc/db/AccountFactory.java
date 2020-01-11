package com.trzewik.jdbc.db;

import java.util.List;

public class AccountFactory {

    public static Account create(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new Account.AccountCreationException();
        }
        return new Account(arguments.get(0), arguments.get(1));
    }
}
