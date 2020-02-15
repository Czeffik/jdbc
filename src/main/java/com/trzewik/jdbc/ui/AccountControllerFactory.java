package com.trzewik.jdbc.ui;

import com.trzewik.jdbc.domain.AccountService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountControllerFactory {
    public static AccountController create(AccountService accountService, InputProvider provider, Printer printer) {
        return new AccountController(accountService, provider, printer);
    }
}
