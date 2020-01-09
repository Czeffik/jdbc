package com.trzewik.jdbc.ui

import com.trzewik.jdbc.db.AccountCreation
import com.trzewik.jdbc.db.AccountService
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
        1 * service.getAccountById(12) >> createAccount()
        1 * printer.printMessage(_)
    }

    def 'should print all accounts'() {
        given:
        Action action = Action.GET_ALL

        when:
        controller.doAction(action)

        then:
        1 * service.getAllAccounts() >> [createAccount()]
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
        1 * service.createAccount('username', 'email')
        1 * printer.printMessage(_)
    }

    def 'should delete account'() {
        given:
        Action action = Action.DELETE

        when:
        controller.doAction(action)

        then:
        1 * provider.collectLong('Type userId: ') >> 123123
        1 * service.deleteAccount(123123)
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
        1 * service.updateAccount(123123, 'username', 'email')
        1 * printer.printMessage(_)
    }
}
