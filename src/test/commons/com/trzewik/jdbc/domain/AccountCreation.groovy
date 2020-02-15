package com.trzewik.jdbc.domain

trait AccountCreation {
    Account createAccount(AccountCreator creator = new AccountCreator()) {
        new AccountImpl(
            creator.userId,
            creator.username,
            creator.email
        )
    }

    static class AccountCreator {
        long userId =  Math.abs(new Random().nextLong())
        String username = 'ADAM'
        String email = 'adam@o2.pl'

        AccountCreator() {}

        AccountCreator(long userId, Account account) {
            this.userId = userId
            this.username = account.username
            this.email = account.email
        }

        AccountCreator(Account account) {
            this.userId = account.userId
            this.username = account.username
            this.email = account.email
        }
    }

}
