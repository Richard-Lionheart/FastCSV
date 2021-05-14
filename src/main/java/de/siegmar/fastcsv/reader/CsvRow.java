package de.siegmar.fastcsv.reader;

import java.util.List;

/**
 * Index based CSV-row.
 *
 * @param originalLineNumber the original line number (starting with 1). On multi-line rows this is the starting
 *                           line number. Empty lines could be skipped via
 *                           {@link CsvReader.CsvReaderBuilder#skipEmptyRows(boolean)}.
 * @param fields             all fields of this row as an unmodifiable list, never {@code null}.
 * @param comment            {@code true}, if the row is a commented row, see
 *                           {@link CsvReader.CsvReaderBuilder#commentStrategy(CommentStrategy)}
 */
public final record CsvRow(long originalLineNumber,
                           List<String> fields,
                           boolean comment) {

    private static final List<String> EMPTY = List.of("");

    CsvRow(final long originalLineNumber, final boolean comment) {
        this(originalLineNumber, EMPTY, comment);
    }

    /**
     * Gets a field value by its index (starting with 0).
     *
     * @param index index of the field to return
     * @return field value, never {@code null}
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public String field(final int index) {
        return fields.get(index);
    }

    /**
     * Gets the number of fields of this row.
     *
     * @return the number of fields of this row
     * @see CsvReader.CsvReaderBuilder#errorOnDifferentFieldCount(boolean)
     */
    public int fieldCount() {
        return fields.size();
    }

    /**
     * Provides the information if the row is an empty row.
     *
     * @return {@code true} if the row is an empty row
     * @see CsvReader.CsvReaderBuilder#skipEmptyRows(boolean)
     */
    public boolean empty() {
        return fields == EMPTY;
    }

}
