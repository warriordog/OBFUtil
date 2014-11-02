package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.parse.FormatException;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.OBFUtil.util.TargetType;
import net.acomputerdog.core.file.TextFileReader;
import net.acomputerdog.core.java.Patterns;

import java.io.*;

/**
 * Reads and write obfuscation data to an SOBF (Sided OBFuscation) file.  This format is an adaption of the OBF format to support sides defined in MCP files.
 * General format is:
 * # Comment type 1
 * // Comment type 2
 * <TYPE>.<SIDE>:<OBF>=<DEOBF>
 */
public class SOBFFileParser implements FileParser {

    private final int side;

    /**
     * Creates a new SOBFFileParser.
     *
     * @param side The side to use.
     */
    public SOBFFileParser(int side) {
        this.side = side;
    }

    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param file      The file to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    @Override
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }
        TextFileReader reader = null;
        try {
            reader = new TextFileReader(file);
            int line = 0;
            for (String str : reader.readAllLines()) {
                line++;
                if (isCommentLine(str)) {
                    continue;
                }
                String[] typeParts = str.split(Patterns.COLON);
                if (typeParts.length < 2) {
                    throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                String[] sideParts = typeParts[0].split(Patterns.PERIOD);
                if (sideParts.length < 2) {
                    throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                TargetType type = TargetType.valueOf(sideParts[0]);
                int side = Integer.parseInt(sideParts[1]);
                if (type == null) {
                    throw new FormatException("Illegal target type on line " + line + ": \"" + typeParts[0] + "\"");
                }
                String[] obfParts = typeParts[1].split(Patterns.EQUALS);
                if (obfParts.length < 2) {
                    throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                if ((overwrite || !table.hasTypeDeobf(obfParts[0], type)) && (side == this.side)) {
                    table.addType(obfParts[0], obfParts[1], type);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Saves all entries located in an OBFTable into a file.
     *
     * @param file  The file to write to.  Must exist.
     * @param table The table to read from
     * @throws java.io.IOException
     */
    @Override
    public void storeEntries(File file, OBFTable table) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null!");
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            for (TargetType type : TargetType.values()) {
                for (String obf : table.getAllTypeObf(type)) {
                    String deobf = table.deobfType(obf, type);
                    out.write(type.name());
                    out.write(".");
                    out.write(this.side);
                    out.write(":");
                    out.write(obf);
                    out.write("=");
                    out.write(deobf);
                    out.write("\n");
                }
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private boolean isCommentLine(String str) {
        String trimmed = str.trim();
        return (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("//"));
    }
}
