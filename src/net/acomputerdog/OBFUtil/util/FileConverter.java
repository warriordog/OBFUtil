package net.acomputerdog.OBFUtil.util;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.DirectOBFTable;
import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.File;
import java.io.IOException;

public class FileConverter {
    public static void convert(FileParser in, FileParser out, File file) throws IOException {
        OBFTable table = new DirectOBFTable();
        in.loadEntries(file, table, true);
        out.storeEntries(file, table);
    }
}
