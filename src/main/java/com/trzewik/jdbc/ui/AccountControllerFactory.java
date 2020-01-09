package com.trzewik.jdbc.ui;

import com.trzewik.jdbc.db.AccountServiceFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountControllerFactory {
    public static AccountController create(InputProvider provider, Printer printer) throws SQLException {
        return new AccountController(AccountServiceFactory.create(), provider, printer);
    }
}
