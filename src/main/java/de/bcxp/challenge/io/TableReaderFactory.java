package de.bcxp.challenge.io;

/**
 * Simple factory for choosing the appropriate TableReader implementation
 * based on the resource name or file extension.
 */
public final class TableReaderFactory {

    private TableReaderFactory() {
        // prevent instantiation
    }

    public static TableReader forResource(String resourcePath) {
        if (resourcePath.endsWith(".csv")) {
            char separator = resourcePath.contains("countries") ? ';' : ',';
            return new CsvTableReader(separator);
        }

        // could be extended to support JSON, XML, etc. in the future
        throw new IllegalArgumentException("Unsupported resource type: " + resourcePath);
    }
}
