package de.siegmar.fastcsv.reader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Name (header) based CSV-row.
 *
 * @param originalLineNumber the original line number (starting with 1). On multi-line rows this is the starting
 *                           line number. Empty lines (and maybe commented lines) have been skipped.
 * @param fields             an unmodifiable map of header names and field values of this row.
 *                           The map will always contain all header names - even if their value is {@code null}.
 */
public final record NamedCsvRow(long originalLineNumber,
                                Map<String, String> fields) {

    NamedCsvRow(final Set<String> header, final CsvRow row) {
        this(row.originalLineNumber(), build(header, row));
    }

    private static Map<String, String> build(final Set<String> header, final CsvRow row) {
        final Map<String, String> fieldMap = new LinkedHashMap<>(header.size());
        int i = 0;
        final List<String> fields = row.fields();
        for (final String h : header) {
            final int index = i++;
            fieldMap.put(h, fields.get(index));
        }
        return Collections.unmodifiableMap(fieldMap);
    }

    /**
     * Gets a field value by its name.
     *
     * @param name field name
     * @return field value, never {@code null}
     * @throws NoSuchElementException if this row has no such field
     */
    public String field(final String name) {
        final String val = fields.get(name);
        if (val == null) {
            throw new NoSuchElementException("No element with name '" + name + "' found. "
                + "Valid names are: " + fields.keySet());
        }
        return val;
    }

}
