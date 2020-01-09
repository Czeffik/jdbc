package com.trzewik.jdbc.ui;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
class UserInputProvider implements InputProvider {
    private final Printer printer;

    @Override
    public String collectString(String message) {
        while (true) {
            try {
                return getUserInput(message).nextLine();
            } catch (Exception ex) {
                printer.printErrorMessage("Input must be a string! Try again: ");
            }
        }
    }

    @Override
    public long collectLong(String message) {
        while (true) {
            try {
                return getUserInput(message).nextLong();
            } catch (Exception ex) {
                printer.printErrorMessage("Input must be a long! Try again: ");
            }
        }
    }

    private Scanner getUserInput(String message) {
        Scanner userInput = new Scanner(System.in);
        printer.printMessage(message);
        return userInput;
    }
}
