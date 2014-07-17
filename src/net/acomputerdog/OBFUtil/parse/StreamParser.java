package net.acomputerdog.OBFUtil.parse;

import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Reads and write obfuscation mappings to a stream.
 */
public interface StreamParser {

    /**
     * Loads all entries located in a stream into an OBFTable.
     *
     * @param stream    The stream to load from.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    public void loadEntries(InputStream stream, OBFTable table, boolean overwrite) throws IOException;

    /**
     * Saves all entries located in an OBFTable into a stream.
     *
     * @param stream The stream to write to.
     * @param table  The table to read from
     * @throws IOException
     */
    public void storeEntries(OutputStream stream, OBFTable table) throws IOException;
}
