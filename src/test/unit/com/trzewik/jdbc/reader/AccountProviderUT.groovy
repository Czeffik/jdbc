package com.trzewik.jdbc.reader

import com.trzewik.jdbc.domain.Account
import com.trzewik.jdbc.domain.AccountsProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AccountProviderUT extends Specification implements FileReading {

    @Subject
    AccountsProvider<Account> provider = AccountsProviderFactory.createAccountCsvReader()

    @Unroll
    def 'should throw exception because: #REASON'() {
        when:
        provider.provide(getAbsolutePath(FILE_NAME))

        then:
        thrown(Account.CreationException)

        where:
        FILE_NAME                   || REASON
        'account/moreArguments.csv' || 'more than 2 arguments for account'
        'account/lessArguments.csv' || 'less than 2 arguments for account'
    }

    def 'should return two parsed accounts'() {
        when:
        def accounts = provider.provide(getAbsolutePath('account/correct.csv'))

        then:
        accounts.size() == 2

        and:
        with(accounts.first()) {
            username == 'adam'
            email == 'asdasd@oa.as'
        }

        and:
        with(accounts.get(1)) {
            username == 'polo'
            email == 'kokta@ka.sa'
        }
    }
}
