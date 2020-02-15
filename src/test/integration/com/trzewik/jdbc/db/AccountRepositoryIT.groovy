package com.trzewik.jdbc.db

import com.trzewik.jdbc.domain.Account
import com.trzewik.jdbc.domain.AccountCreation
import com.trzewik.jdbc.domain.AccountRepository
import spock.lang.Subject

class AccountRepositoryIT extends DbSpec implements AccountCreation {
    @Subject
    AccountRepository repository

    def setup() {
        repository = AccountRepositoryFactory.create()
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
        List<Account> accounts = repository.getAll()
        accounts.size() == 1

        and:
        with(accounts.first()) {
            getUserId() == getInsertedRowId(row)
            getUsername() == account.username
            getEmail() == account.email
        }
    }

    def 'should get account by user_id'() {
        given:
        def account = createAccount()
        def insertedRows = dbHelper.save(account)
        def userId = getInsertedRowId(insertedRows)

        expect:
        def found = repository.findById(userId)
        found.isPresent()

        and:
        with(found.get()) {
            getUsername() == account.username
            getEmail() == account.email
        }
    }

    def 'should return empty optional when can not find account with user_id'() {
        when:
        def found = repository.findById(4)

        then:
        !found.present
    }

    def 'should save new account in db'() {
        given:
        def account = createAccount()

        when:
        repository.save(account)

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
        repository.update(updatedAccount)

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
        repository.delete(account)

        then:
        dbHelper.allAccounts.isEmpty()
    }

    def 'should rollback when transaction fail - unique constraint violate when two users have same email address'() {
        given:
        def first = createAccount()
        def second = createAccount()

        when:
        repository.saveMany([first, second])

        then:
        thrown(AccountRepository.Exception)

        and:
        dbHelper.allAccounts.isEmpty()

        when:
        repository.save(first)

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
        repository.saveMany([first, second])

        then:
        dbHelper.allAccounts.size() == 2
    }

    def 'should do nothing when accounts are empty list'() {
        when:
        repository.saveMany([])

        then:
        dbHelper.allAccounts.isEmpty()
    }

    def 'should throw exception when accounts are null'() {
        when:
        repository.saveMany(null)

        then:
        thrown(NullPointerException)

        and:
        dbHelper.allAccounts.isEmpty()
    }

    long getInsertedRowId(List<List<Object>> rows, int rowNumber = 0) {
        return rows.get(rowNumber).first() as long
    }
}
