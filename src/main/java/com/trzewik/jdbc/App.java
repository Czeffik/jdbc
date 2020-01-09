package com.trzewik.jdbc;

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
        //AccountController controller = AccountControllerFactory.create(provider, printer);
        AccountController controller = AccountControllerFactory.createWithInMemory(provider, printer);

        while (true) {
            try {
                controller.doAction(AccountController.Action.from(provider.collectString("Please type command: ")));
            } catch (Exception ex) {
                printer.printErrorMessage(ex.getMessage());
            }
        }
    }
}
