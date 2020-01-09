package com.trzewik.jdbc.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputProviderFactory {
    public static InputProvider create(Printer printer) {
        return new UserInputProvider(printer);
    }
}
