package de.bcxp.challenge.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-Implementation of TableReader.
 * Expects a header row, which is ignored.
 */
public class CsvTableReader implements TableReader {

    private final char separator;

    public CsvTableReader(char separator) {
        this.separator = separator;
    }

    @Override
    public List<String[]> readAll(Reader reader) throws IOException {
        try (BufferedReader br = new BufferedReader(reader)) {
            List<String[]> rows = new ArrayList<>();

            // Header überspringen
            String header = br.readLine();
            if (header == null) {
                return rows;
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    rows.add(line.split(Character.toString(separator)));
                }
            }

            return rows;
        }
    }
}
