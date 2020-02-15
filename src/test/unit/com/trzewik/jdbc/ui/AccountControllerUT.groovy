package com.trzewik.jdbc.ui

import com.trzewik.jdbc.domain.AccountCreation
import com.trzewik.jdbc.domain.AccountService
import spock.lang.Specification
import spock.lang.Subject

import static com.trzewik.jdbc.ui.AccountController.Action

class AccountControllerUT extends Specification implements AccountCreation {
    AccountService service = Mock()
    InputProvider provider = Mock()
    Printer printer = Mock()

    @Subject
    AccountController controller = new AccountController(service, provider, printer)

    def 'should print one account'() {
        given:
        Action action = Action.GET

        when:
        controller.doAction(action)

        then:
        1 * provider.collectLong(_) >> 12
        1 * service.getById(12) >> createAccount()
        1 * printer.printMessage(_)
    }

    def 'should print all accounts'() {
        given:
        Action action = Action.GET_ALL

        when:
        controller.doAction(action)

        then:
        1 * service.getAll() >> [createAccount()]
        1 * printer.printMessage(_)
    }

    def 'should create account'() {
        given:
        Action action = Action.CREATE

        when:
        controller.doAction(action)

        then:
        1 * provider.collectString('Type username: ') >> 'username'
        1 * provider.collectString('Type email: ') >> 'email'
        1 * service.create('username', 'email')
        1 * printer.printMessage(_)
    }

    def 'should delete account'() {
        given:
        Action action = Action.DELETE

        when:
        controller.doAction(action)

        then:
        1 * provider.collectLong('Type userId: ') >> 123123
        1 * service.delete(123123)
        1 * printer.printMessage(_)
    }

    def 'should update account'() {
        given:
        Action action = Action.UPDATE

        when:
        controller.doAction(action)

        then:
        1 * provider.collectLong('Type userId: ') >> 123123
        1 * provider.collectString('Type username: ') >> 'username'
        1 * provider.collectString('Type email: ') >> 'email'
        1 * service.update(123123, 'username', 'email')
        1 * printer.printMessage(_)
    }

    def 'should create accounts from csv file'() {
        given:
        Action action = Action.CREATE_FROM_CSV

        when:
        controller.doAction(action)

        then:
        1 * provider.collectString('Type path to csv file with accounts to save: ') >> 'some/path/to/file'
        1 * service.createFromCsv('some/path/to/file')
        1 * printer.printMessage(_)
    }
}
