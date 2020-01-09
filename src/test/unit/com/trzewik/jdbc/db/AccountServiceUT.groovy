package com.trzewik.jdbc.db

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll


class AccountServiceUT extends Specification implements AccountCreation {

    Dao<Account> accountDao = Mock()

    @Subject
    AccountServiceImpl service = new AccountServiceImpl(accountDao)

    def 'should get all accounts'() {
        given:
        List<Account> accounts = [createAccount()]

        when:
        def results = service.getAllAccounts()

        then:
        1 * accountDao.findAll() >> accounts

        and:
        results.size() == accounts.size()
        results.first() == accounts.first()
    }

    def 'should get account by userId'() {
        given:
        Account account = createAccount()

        when:
        def result = service.getAccountById(account.userId)

        then:
        1 * accountDao.findById(account.userId) >> Optional.of(account)

        and:
        result == account
    }

    def 'should throw exception when can not find account with userId'() {
        given:
        long userId = 123123131231

        when:
        service.getAccountById(userId)

        then:
        1 * accountDao.findById(userId) >> Optional.empty()

        and:
        thrown(AccountServiceImpl.AccountNotFoundException)
    }

    def 'should create new account with given username and email'() {
        given:
        def username = 'example name'
        def email = 'example email address'

        when:
        service.createAccount(username, email)

        then:
        1 * accountDao.save({ Account account ->
            assert account.username == username
            assert account.email == email
            account
        })
    }

    @Unroll
    def 'should throw exception when trying create new account with username: [#USERNAME] and email: [#EMAIL]'() {
        when:
        service.createAccount(USERNAME, EMAIL)

        then:
        thrown(Account.AccountCreationException)

        where:
        USERNAME | EMAIL
        null     | null
        ''       | null
        ' '      | null
        null     | ''
        null     | ' '
        ''       | ' '
        ' '      | ''
        ' '      | ' '
        'Adam'   | null
        'Adam'   | ''
        'Adam'   | ' '
        ' '      | 'email'
        ''       | 'email'
        null     | 'email'
    }

    def 'should find account with userId and when found delete it'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))

        when:
        service.deleteAccount(userId)

        then:
        1 * accountDao.findById(userId) >> Optional.of(account)
        1 * accountDao.delete(account)
    }

    def 'should throw exception when trying delete account with userId which is not found in db'() {
        given:
        long userId = 1231312

        when:
        service.deleteAccount(userId)

        then:
        1 * accountDao.findById(userId) >> Optional.empty()
        0 * accountDao.delete(_)

        and:
        thrown(AccountServiceImpl.AccountNotFoundException)
    }

    def 'should throw exception when trying update account with userId which is not found in db'() {
        given:
        long userId = 1231312

        when:
        service.updateAccount(userId, 'new Username', '')

        then:
        1 * accountDao.findById(userId) >> Optional.empty()
        0 * accountDao.update(_)

        and:
        thrown(AccountServiceImpl.AccountNotFoundException)
    }

    @Unroll
    def 'should throw exception when trying trying update account with username: [#USERNAME] and email: [#EMAIL]'() {
        when:
        service.updateAccount(12132, USERNAME, EMAIL)

        then:
        thrown(IllegalArgumentException)

        where:
        USERNAME | EMAIL
        null     | null
        ''       | null
        ' '      | null
        null     | ''
        null     | ' '
        ''       | ' '
        ' '      | ''
        ' '      | ' '
    }

    def 'should update account username'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))
        def username = 'new Username'

        when:
        service.updateAccount(userId, username, null)

        then:
        1 * accountDao.findById(userId) >> Optional.of(account)
        1 * accountDao.update({ Account updated ->
            assert updated.getUsername() == username
            assert updated.getUserId() == userId
            assert updated.getEmail() == account.getEmail()
            updated
        })
    }

    def 'should update account email'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))
        def email = 'new email'

        when:
        service.updateAccount(userId, '', email)

        then:
        1 * accountDao.findById(userId) >> Optional.of(account)
        1 * accountDao.update({ Account updated ->
            assert updated.getUsername() == account.getUsername()
            assert updated.getUserId() == userId
            assert updated.getEmail() == email
            updated
        })
    }

    def 'should update account username and email'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))
        def email = 'new email'
        def username = 'new username'

        when:
        service.updateAccount(userId, username, email)

        then:
        1 * accountDao.findById(userId) >> Optional.of(account)
        1 * accountDao.update({ Account updated ->
            assert updated.getUsername() == username
            assert updated.getUserId() == userId
            assert updated.getEmail() == email
            updated
        })
    }
}
