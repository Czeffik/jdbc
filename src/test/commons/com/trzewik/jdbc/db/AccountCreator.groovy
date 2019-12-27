package com.trzewik.jdbc.db

trait AccountCreator {
    Account createAccount(AccountBuilder builder = new AccountBuilder()){
        new Account(
            builder.userId,
            builder.username,
            builder.email
        )
    }

    Account createAccount(long userId, Account account = createAccount()){
        new Account(
            userId,
            account.username,
            account.email
        )
    }

    static class AccountBuilder{
        long userId = 1
        String username = 'ADAM'
        String email = 'adam@o2.pl'
    }

}
