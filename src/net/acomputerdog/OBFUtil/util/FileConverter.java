package net.acomputerdog.OBFUtil.util;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.DirectOBFTable;
import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.File;
import java.io.IOException;

/**
 * Utility class that allows converting a file from one type to another.
 */
public class FileConverter {

    /**
     * Converts an obfuscation table in one format to another.
     *
     * @param in      The input FileParser
     * @param out     The output FileParser
     * @param inFile  The input file
     * @param outFile The output file
     * @throws IOException If an IO error occurs
     */
    public static void convert(FileParser in, FileParser out, File inFile, File outFile) throws IOException {
        OBFTable table = new DirectOBFTable();
        in.loadEntries(inFile, table, true);
        out.storeEntries(outFile, table);
    }
}
