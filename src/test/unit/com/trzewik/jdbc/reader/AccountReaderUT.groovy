package com.trzewik.jdbc.reader

import com.trzewik.jdbc.db.AccountEntity
import com.trzewik.jdbc.domain.Account
import com.trzewik.jdbc.raeder.FileReader
import com.trzewik.jdbc.raeder.FileReaderFactory
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AccountReaderUT extends Specification implements FileReading {

    @Subject
    FileReader<AccountEntity> reader = FileReaderFactory.createAccountCsvReader()

    @Unroll
    def 'should throw exception because: #REASON'() {
        when:
        reader.read(getAbsolutePath(FILE_NAME))

        then:
        thrown(Account.CreationException)

        where:
        FILE_NAME                   || REASON
        'account/moreArguments.csv' || 'more than 2 arguments for account'
        'account/lessArguments.csv' || 'less than 2 arguments for account'
    }

    def 'should return two parsed accounts'() {
        when:
        def accounts = reader.read(getAbsolutePath('account/correct.csv'))

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
