package com.trzewik.jdbc.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrinterFactory {
    public static Printer create() {
        return new MessagePrinter();
    }
}
