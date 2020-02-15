package com.trzewik.jdbc.domain

import com.trzewik.jdbc.raeder.FileReader
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AccountServiceUT extends Specification implements AccountCreation {

    AccountRepository repository = new AccountRepositoryInMemory()
    FileReader<Account> reader = Mock()

    @Subject
    AccountServiceImpl service = new AccountServiceImpl(repository, reader)

    def 'should get all accounts'() {
        given:
        List<Account> accounts = [createAccount()]

        and:
        repository.saveMany(accounts)

        when:
        def results = service.getAll()

        then:
        results.size() == accounts.size()
        results.first() == accounts.first()
    }

    def 'should get account by userId'() {
        given:
        Account account = createAccount()

        and:
        repository.save(account)

        when:
        def result = service.getById(account.userId)

        then:
        result == account
    }

    def 'should throw exception when can not find account with userId'() {
        given:
        long userId = 123123131231

        when:
        service.getById(userId)

        then:
        thrown(AccountRepository.AccountNotFoundException)
    }

    def 'should create new account with given username and email'() {
        given:
        def username = 'example name'
        def email = 'example email address'

        when:
        service.create(username, email)

        then:
        repository.repository.size() == 1
        with(repository.repository.values().first()) {
            it.username == username
            it.email == email
        }
    }

    @Unroll
    def 'should throw exception when trying create new account with username: [#USERNAME] and email: [#EMAIL]'() {
        when:
        service.create(USERNAME, EMAIL)

        then:
        thrown(Account.CreationException)

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

        and:
        repository.save(account)

        when:
        service.delete(userId)

        then:
        repository.repository.size() == 0
    }

    def 'should throw exception when trying delete account with userId which is not found in db'() {
        given:
        long userId = 1231312

        when:
        service.delete(userId)

        then:
        thrown(AccountRepository.AccountNotFoundException)
    }

    def 'should throw exception when trying update account with userId which is not found in db'() {
        given:
        long userId = 1231312

        when:
        service.update(userId, 'new Username', '')

        then:
        thrown(AccountRepository.AccountNotFoundException)
    }

    @Unroll
    def 'should throw exception when trying trying update account with username: [#USERNAME] and email: [#EMAIL]'() {
        when:
        service.update(12132, USERNAME, EMAIL)

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

        and:
        repository.save(account)

        when:
        service.update(userId, username, null)

        then:
        repository.repository.size() == 1
        with(repository.repository.values().first()) {
            it.userId == userId
            it.username == username
            it.email == account.email
        }
    }

    def 'should update account email'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))
        def email = 'new email'

        and:
        repository.save(account)

        when:
        service.update(userId, '', email)

        then:
        repository.repository.size() == 1
        with(repository.repository.values().first()) {
            it.userId == userId
            it.username == account.username
            it.email == email
        }
    }

    def 'should update account username and email'() {
        given:
        long userId = 1231312
        def account = createAccount(new AccountCreator(userId: userId))
        def email = 'new email'
        def username = 'new username'

        and:
        repository.save(account)

        when:
        service.update(userId, username, email)

        then:
        repository.repository.size() == 1
        with(repository.repository.values().first()) {
            it.userId == userId
            it.username == username
            it.email == email
        }
    }

    def 'should create accounts from csv file'() {
        given:
        def accounts = [createAccount(), createAccount(new AccountCreator(email: 'other emaill'))]

        and:
        def pathToFile = 'some/path'

        when:
        service.createFromCsv(pathToFile)

        then:
        1 * reader.read(pathToFile) >> accounts

        and:
        repository.repository.size() == accounts.size()
    }
}
