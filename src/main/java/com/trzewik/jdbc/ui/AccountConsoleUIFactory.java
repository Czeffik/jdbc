package com.trzewik.jdbc.ui;

import com.trzewik.jdbc.domain.AccountService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountConsoleUIFactory {
    public static AccountConsoleUI create(AccountService accountService, InputProvider provider, Printer printer) {
        return new AccountConsoleUI(accountService, provider, printer);
    }
}
