package de.bcxp.challenge.io;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Interface to read table data.
 * Implementations could read CSV, JSON, Webservices etc.
 */
public interface TableReader {

    /**
     * Reads all rows of data source.
     *
     * @param reader input source
     * @return List of rows, each row is an array of strings representing the columns
     * @throws IOException for I/O-Problems
     */
    List<String[]> readAll(Reader reader) throws IOException;
}
