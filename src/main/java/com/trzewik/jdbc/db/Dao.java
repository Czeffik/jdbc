package com.trzewik.jdbc.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    String DEFAULT_SCHEMA = System.getProperty("db.schema", "test_db");

    Optional<T> get(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T toSave) throws SQLException;

    void update(T updated) throws SQLException;

    void delete(T toDelete) throws SQLException;
}
