package com.trzewik.jdbc.ui;

public interface InputProvider {
    String collectString(String message);

    long collectLong(String message);
}
