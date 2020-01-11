package com.trzewik.jdbc.ui;

import com.trzewik.jdbc.db.Account;
import com.trzewik.jdbc.db.AccountService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountController {
    private static final String TYPE_USER_ID = "Type userId: ";
    private static final String TYPE_USERNAME = "Type username: ";
    private static final String TYPE_EMAIL = "Type email: ";

    private final AccountService service;
    private final InputProvider provider;
    private final Printer printer;

    public void doAction(Action action) throws SQLException, FileNotFoundException {
        switch (action) {
            case GET:
                Account account = get();
                printer.printMessage(String.format("Account: [%s]", account.toString()));
                break;
            case GET_ALL:
                printer.printMessage("Accounts:");
                getAll().forEach(System.out::println);
                break;
            case CREATE:
                create();
                printer.printMessage("Account created");
                break;
            case CREATE_FROM_CSV:
                createFromCsv();
                printer.printMessage("Accounts created");
                break;
            case DELETE:
                delete();
                printer.printMessage("Account deleted");
                break;
            case UPDATE:
                update();
                printer.printMessage("Account updated");
                break;
            case EXIT:
                printer.printMessage("Stop controller");
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Unsupported Action!");
        }
    }

    private List<Account> getAll() throws SQLException {
        return service.getAllAccounts();
    }

    private Account get() throws SQLException {
        long userId = provider.collectLong(TYPE_USER_ID);
        return service.getAccountById(userId);
    }

    private void create() throws SQLException {
        String username = provider.collectString(TYPE_USERNAME);
        String email = provider.collectString(TYPE_EMAIL);
        service.createAccount(username, email);
    }

    private void delete() throws SQLException {
        long userId = provider.collectLong(TYPE_USER_ID);
        service.deleteAccount(userId);
    }

    private void update() throws SQLException {
        long userId = provider.collectLong(TYPE_USER_ID);
        String username = provider.collectString(TYPE_USERNAME);
        String email = provider.collectString(TYPE_EMAIL);
        service.updateAccount(userId, username, email);
    }

    private void createFromCsv() throws SQLException, FileNotFoundException {
        String path = provider.collectString("Type path to csv file with accounts to save: ");
        service.createAccountsFromCsv(path);
    }

    @Getter
    @AllArgsConstructor
    public enum Action {
        GET_ALL("--get-all", "-ga", "print all accounts from database"),
        GET("--get", "-g", "return account from database with selected userId"),
        CREATE("--create", "-c", "create new account"),
        CREATE_FROM_CSV("--create_csv", "-cc", "create accounts from csv file"),
        DELETE("--delete", "-d", "remove existing account from database"),
        UPDATE("--update", "-u", "update existing account in database"),
        EXIT("exit", "q!", "stop controller");

        private final String longCommand;
        private final String shortCommand;
        private final String definition;

        public static Action from(String command) {
            return Arrays.stream(values())
                .filter(action -> action.isCommand(command))
                .findFirst()
                .orElseThrow(() -> new NotActionCommandException(command, values()));
        }

        @Override
        public String toString() {
            return String.format("%s  -  %s", commands(), definition);
        }

        public String commands() {
            return String.format("%s,  %s", longCommand, shortCommand);
        }

        private boolean isCommand(String str) {
            return longCommand.equalsIgnoreCase(str) || shortCommand.equalsIgnoreCase(str);
        }

        static class NotActionCommandException extends RuntimeException {
            NotActionCommandException(String str, Action[] actions) {
                super(String.format(
                    "[%s] is not action command. Please provide one of: [%s]",
                    str,
                    Arrays.stream(actions).map(Action::commands).collect(Collectors.toList()))
                );
            }
        }
    }
}
