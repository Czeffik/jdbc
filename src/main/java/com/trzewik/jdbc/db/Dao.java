package com.trzewik.jdbc.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    String DEFAULT_SCHEMA = System.getProperty("db.schema", "test_db");

    Optional<T> findById(long id) throws SQLException;

    List<T> findAll() throws SQLException;

    void save(T toSave) throws SQLException;

    void update(T updated) throws SQLException;

    void delete(T toDelete) throws SQLException;

    void saveMany(List<AccountEntity> accounts) throws SQLException;
}
