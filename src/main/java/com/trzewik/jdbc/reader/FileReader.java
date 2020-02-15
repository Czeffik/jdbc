package com.trzewik.jdbc.reader;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReader<T> {
    List<T> read(String path) throws FileNotFoundException;
}
