package com.trzewik.jdbc.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountFactory {

    public static Account create(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new Account.CreationException();
        }
        return create(arguments.get(0), arguments.get(1));
    }

    public static Account create(String username, String email) {
        return create(GenerateId.get(), username, email);
    }

    public static Account create(long userId, String username, String email) {
        return new AccountImpl(userId, username, email);
    }

    static class GenerateId {
        private static final Random random = new Random();

        public static long get() {
            return Math.abs(random.nextLong());
        }
    }
}
