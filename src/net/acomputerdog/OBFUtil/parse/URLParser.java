package net.acomputerdog.OBFUtil.parse;

import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.IOException;
import java.net.URL;

/**
 * Reads and write obfuscation mappings to a URL.
 */
public interface URLParser {
    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param url       The URL to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    public void loadEntries(URL url, OBFTable table, boolean overwrite) throws IOException;

    /**
     * Saves all entries located in an OBFTable into a file.
     *
     * @param url   The URL to write to.  Must exist.
     * @param table The table to read from
     * @throws IOException
     */
    public void storeEntries(URL url, OBFTable table) throws IOException;
}
