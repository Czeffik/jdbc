package com.trzewik.jdbc.db;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);

    List<T> getAll();

    void save(T toSave);

    void update(T updated);

    void delete(T toDelete);
}
