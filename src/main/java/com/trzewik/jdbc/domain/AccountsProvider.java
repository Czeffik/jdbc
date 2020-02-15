package com.trzewik.jdbc.domain;

import java.io.FileNotFoundException;
import java.util.List;

public interface AccountsProvider<T> {
    List<Account> provide(String path) throws FileNotFoundException;
}
