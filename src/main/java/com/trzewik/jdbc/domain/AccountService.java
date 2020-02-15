package com.trzewik.jdbc.domain;

import java.io.FileNotFoundException;
import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account getById(long userId);

    void create(String username, String email);

    void delete(long userId);

    void update(long userId, String username, String email);

    void createFromCsv(String pathToFile) throws FileNotFoundException;
}
