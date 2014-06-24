package net.acomputerdog.OBFUtil.parse;

import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.File;
import java.io.IOException;

public interface FileParser {

    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param file      The file to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException;

    /**
     * Saves all entries located in an OBFTable into a file.
     *
     * @param file  The file to write to.  Must exist.
     * @param table The table to read from
     * @throws IOException
     */
    public void storeEntries(File file, OBFTable table) throws IOException;
}
