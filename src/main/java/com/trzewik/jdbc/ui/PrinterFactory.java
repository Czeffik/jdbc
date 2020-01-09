package com.trzewik.jdbc.ui;

public class PrinterFactory {
    public static Printer create() {
        return new MessagePrinter();
    }
}
