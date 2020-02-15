package com.trzewik.jdbc;

import com.trzewik.jdbc.db.AccountRepositoryFactory;
import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountRepository;
import com.trzewik.jdbc.domain.AccountService;
import com.trzewik.jdbc.domain.AccountServiceFactory;
import com.trzewik.jdbc.reader.FileReader;
import com.trzewik.jdbc.reader.FileReaderFactory;
import com.trzewik.jdbc.ui.AccountConsoleUI;
import com.trzewik.jdbc.ui.AccountConsoleUIFactory;
import com.trzewik.jdbc.ui.InputProvider;
import com.trzewik.jdbc.ui.InputProviderFactory;
import com.trzewik.jdbc.ui.Printer;
import com.trzewik.jdbc.ui.PrinterFactory;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Printer printer = PrinterFactory.create();
        printer.printMessage("Possible commands:");
        Arrays.stream(AccountConsoleUI.Action.values()).forEach(System.out::println);

        InputProvider provider = InputProviderFactory.create(printer);
        FileReader<Account> reader = FileReaderFactory.createAccountCsvReader();

        //AccountConsoleUI with real database - if want switch to in memory comment three lines bellow
        AccountRepository repository = AccountRepositoryFactory.create();
        AccountService service = AccountServiceFactory.create(repository, reader);
        AccountConsoleUI consoleUI = AccountConsoleUIFactory.create(service, provider, printer);

        //AccountConsoleUI with in memory repository - if want switch to in memory uncomment three lines bellow
        //AccountRepository inMemoryRepository = AccountRepositoryFactory.createInMemory();
        //AccountService service = AccountServiceFactory.create(inMemoryRepository, reader);
        //AccountConsoleUI consoleUI = AccountControllerFactory.create(service, provider, printer);

        while (true) {
            try {
                consoleUI.doAction(AccountConsoleUI.Action.from(provider.collectString("Please type command: ")));
            } catch (Exception ex) {
                printer.printErrorMessage(ex.getMessage());
            }
        }
    }
}
