package com.trzewik.jdbc;

import com.trzewik.jdbc.db.AccountRepositoryFactory;
import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountRepository;
import com.trzewik.jdbc.domain.AccountService;
import com.trzewik.jdbc.domain.AccountServiceFactory;
import com.trzewik.jdbc.raeder.FileReader;
import com.trzewik.jdbc.raeder.FileReaderFactory;
import com.trzewik.jdbc.ui.AccountController;
import com.trzewik.jdbc.ui.AccountControllerFactory;
import com.trzewik.jdbc.ui.InputProvider;
import com.trzewik.jdbc.ui.InputProviderFactory;
import com.trzewik.jdbc.ui.Printer;
import com.trzewik.jdbc.ui.PrinterFactory;

import java.sql.SQLException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws SQLException {
        Printer printer = PrinterFactory.create();
        printer.printMessage("Possible commands:");
        Arrays.stream(AccountController.Action.values()).forEach(System.out::println);

        InputProvider provider = InputProviderFactory.create(printer);
        FileReader<Account> reader = FileReaderFactory.createAccountCsvReader();

        //Controller with real database - if want switch to in memory comment three lines bellow
        AccountRepository repository = AccountRepositoryFactory.create();
        AccountService service = AccountServiceFactory.create(repository, reader);
        AccountController controller = AccountControllerFactory.create(service, provider, printer);

        //Controller with in memory repository - if want switch to in memory uncomment three lines bellow
//        AccountRepository inMemoryRepository = AccountRepositoryFactory.createInMemory();
//        AccountService service = AccountServiceFactory.create(inMemoryRepository, reader);
//        AccountController controller = AccountControllerFactory.create(service, provider, printer);

        while (true) {
            try {
                controller.doAction(AccountController.Action.from(provider.collectString("Please type command: ")));
            } catch (Exception ex) {
                printer.printErrorMessage(ex.getMessage());
            }
        }
    }
}
