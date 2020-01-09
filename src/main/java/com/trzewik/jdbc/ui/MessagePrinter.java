package com.trzewik.jdbc.ui;

public class MessagePrinter implements Printer {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
    }
}
