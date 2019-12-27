package com.trzewik.jdbc.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class DbProperties {
    private final String username;
    private final String password;
    private final String url;

    public DbProperties() {
        username = System.getProperty("db.username","postgres");
        password = System.getProperty("db.password","example");
        url = System.getProperty("db.url","jdbc:postgresql://127.0.0.1:54320/");
    }
}
