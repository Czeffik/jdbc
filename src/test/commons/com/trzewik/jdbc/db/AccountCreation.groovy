package com.trzewik.jdbc.db

trait AccountCreation {
    Account createAccount(AccountCreator creator = new AccountCreator()) {
        new Account(
            creator.userId,
            creator.username,
            creator.email
        )
    }

    static class AccountCreator {
        long userId
        String username = 'ADAM'
        String email = 'adam@o2.pl'

        AccountCreator() {}

        AccountCreator(long userId, Account account) {
            this.userId = userId
            this.username = account.username
            this.email = account.email
        }
    }

}
