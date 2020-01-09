package com.trzewik.jdbc.db

import spock.lang.Shared
import spock.lang.Subject

import java.sql.SQLException

class AccountDaoIT extends DbSpec implements AccountCreation {
    @Shared
    DbHelper dbHelper
    @Subject
    Dao<Account> dao

    def setupSpec() {
        dbHelper = new DbHelper()
    }

    def setup() {
        dao = DbFactory.accountDao()
        dbHelper.dbCleanup()
    }

    def cleanup() {
        dbHelper.dbCleanup()
    }

    def 'should get all accounts from db'() {
        given:
        Account account = createAccount()

        when:
        def row = dbHelper.save(account)

        then:
        List<Account> accounts = dao.findAll()
        accounts.size() == 1

        and:
        with(accounts.first()) {
            userId == getInsertedRowId(row)
            username == account.username
            email == account.email
        }
    }

    def 'should get account by user_id'() {
        given:
        def account = createAccount()
        def insertedRows = dbHelper.save(account)
        def userId = getInsertedRowId(insertedRows)

        expect:
        def found = dao.findById(userId)
        found.isPresent()

        and:
        with(found.get()) {
            username == account.username
            email == account.email
        }
    }

    def 'should return empty optional when can not find account with user_id'() {
        when:
        def found = dao.findById(4)

        then:
        !found.present
    }

    def 'should save new account in db'() {
        given:
        def account = createAccount()

        when:
        dao.save(account)

        then:
        def accounts = dbHelper.allAccounts
        accounts.size() == 1

        and:
        with(accounts.first()) {
            username == account.username
            email == account.email
        }
    }

    def 'should update existing account'() {
        given:
        def account = createAccount()
        def userId = getInsertedRowId(dbHelper.save(account))

        and:
        def updatedAccount = createAccount(new AccountCreator(
            userId: userId,
            username: 'NEW USERNAME',
            email: 'NEW EMAIL'
        ))

        when:
        dao.update(updatedAccount)

        then:
        def accounts = dbHelper.getAllAccounts()
        accounts.size() == 1

        and:
        with(accounts.first()) {
            username == updatedAccount.username
            email == updatedAccount.email
        }
    }

    def 'should delete account'() {
        given:
        def account = createAccount()
        def userId = getInsertedRowId(dbHelper.save(account))
        account = createAccount(new AccountCreator(userId, account))

        when:
        dao.delete(account)

        then:
        dbHelper.allAccounts.isEmpty()
    }

    def 'should rollback when transaction fail - unique constraint violate when two users have same email address'() {
        given:
        def first = createAccount()
        def second = createAccount()

        when:
        dao.saveMany([first, second])

        then:
        thrown(SQLException)

        and:
        dbHelper.allAccounts.isEmpty()

        when:
        dao.save(first)

        then:
        dbHelper.allAccounts.size() == 1
    }

    def 'should save accounts in transaction successfully'() {
        given:
        def first = createAccount()
        def second = createAccount(new AccountCreator(
            username: 'Other',
            email: 'some.email@o2.pl'
        ))

        when:
        dao.saveMany([first, second])

        then:
        dbHelper.allAccounts.size() == 2
    }

    def 'should do nothing when accounts are empty list'() {
        when:
        dao.saveMany([])

        then:
        dbHelper.allAccounts.isEmpty()
    }

    def 'should throw exception when accounts are null'() {
        when:
        dao.saveMany(null)

        then:
        thrown(NullPointerException)

        and:
        dbHelper.allAccounts.isEmpty()
    }

    long getInsertedRowId(List<List<Object>> rows, int rowNumber = 0) {
        return rows.get(rowNumber).first() as long
    }
}
