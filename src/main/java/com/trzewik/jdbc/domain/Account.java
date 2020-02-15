package com.trzewik.jdbc.domain;

public interface Account {
    long getUserId();

    String getUsername();

    String getEmail();

    class CreationException extends RuntimeException {
        CreationException(String username, String email) {
            super(String.format("Can not create new account with username: [%s] and email: [%s]!", username, email));
        }

        CreationException() {
            super("For creation account are required 2 arguments: username, email.");
        }
    }
}
