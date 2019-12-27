package com.trzewik.jdbc.db


import spock.lang.Shared
import spock.lang.Subject

class AccountDaoIT extends DbSpec implements AccountCreator {
    @Shared
    DbHelper dbHelper
    @Subject
    Dao<Account> dao

    def setupSpec() {
        dbHelper = new DbHelper()
    }

    def setup() {
        dao = DbFactory.accountDao()
    }


    def 'should get all accounts from db'() {
        expect:
        dao.getAll().size() == 0

        when:
        def row = dbHelper.insert(createAccount()).first()

        then:
        dao.getAll().size() == 1

        cleanup:
        dbHelper.deleteAccountsByIds([row.first()])
    }

    def 'should get account by user_id'() {
        given:
        def account = createAccount()
        def userId = dbHelper.insert(account).first().first()
        account = createAccount(userId, account)

        expect:
        dao.get(account.userId).get() == account

        cleanup:
        dbHelper.deleteAccountsByIds([account.userId])
    }

    def 'should return null when can not find account with user_id'() {
        given:
        dbHelper.deleteAccountsByIds([4])

        expect:
//        dao.get(4).empty      //TODO have no idea why doesn't work when running gradle build
        !dao.get(4).present
    }

    def 'should save new account in db'() {
        given:
        def account = createAccount(new AccountBuilder(
            username: 'New user',
            email: 'new.email@o2.pl'
        ))

        when:
        dao.save(account)

        then:
        def accountsAfter = dbHelper.allAccounts
        accountsAfter.size() == 1

        and:
        with(accountsAfter.first()) {
            username == account.username
            email == account.email
        }

        cleanup:
        dbHelper.deleteAccounts([account])
    }

    def 'should update existing account'() {
        given:
        def account = createAccount()
        def userId = dbHelper.insert(account).first().first()
        account = createAccount(userId, account)

        and:
        def updatedAccount = createAccount(new AccountBuilder(
            username: 'NEW USERNAME',
            email: 'NEW EMAIL'
        ))

        when:
        dao.update(account, updatedAccount)

        then:
        def afterUpdateAccount = dbHelper.getAccountByUserId(userId)
        with(afterUpdateAccount) {
            username == updatedAccount.username
            email == updatedAccount.email
        }

        cleanup:
        dbHelper.deleteAccountsByIds([userId])
    }

    def 'should delete account'() {
        given:
        def account = createAccount()
        def userId = dbHelper.insert(account).first().first()
        account = createAccount(userId, account)

        when:
        dao.delete(account)

        then:
        dbHelper.allAccounts.size() == 0

        cleanup:
        dbHelper.deleteAccountsByIds([userId])
    }
}