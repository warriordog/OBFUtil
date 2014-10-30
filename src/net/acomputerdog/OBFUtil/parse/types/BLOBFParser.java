package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.parse.FormatException;
import net.acomputerdog.OBFUtil.parse.StreamParser;
import net.acomputerdog.OBFUtil.table.DirectOBFTableSRG;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.OBFUtil.util.TargetType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * BlazeLoader OBFuscation file.
 * Formatted "<TYPE>:<OBF>:<SEARGE>:<MCP>"  for packages, classes, and fields.
 * Formatted "METHOD:<OBF_NAME>:<OBF_DESC>:<SEARGE_NAME>:<SEARGE_DESC>:<MCP_NAME>:<MCP_DESC>"  for methods
 */
public class BLOBFParser implements FileParser, StreamParser {
    private static final String PATTERN_COLON = Pattern.quote(":");
    private static final String PATTERN_SPACE = Pattern.quote(" ");

    private final boolean stripDescs;

    public BLOBFParser() {
        this(false);
    }

    public BLOBFParser(boolean stripMethodDescriptors) {
        this.stripDescs = stripMethodDescriptors;
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
        loadEntries(new FileInputStream(file), table, overwrite);
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
        storeEntries(new FileOutputStream(file), table);
    }

    /**
     * Loads all entries located in a stream into an OBFTable.
     *
     * @param stream    The stream to load from.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    @Override
    public void loadEntries(InputStream stream, OBFTable table, boolean overwrite) throws IOException {
        if (stream == null) {
            throw new NullPointerException("Stream cannot be null!");
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(stream));
            List<String> data = new ArrayList<String>();
            String line;
            while ((line = in.readLine()) != null) {
                data.add(line);
            }
            parseStringArray(data.toArray(new String[data.size()]), table, overwrite);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Saves all entries located in an OBFTable into a stream.
     *
     * @param stream The stream to write to.
     * @param table  The table to read from
     * @throws java.io.IOException
     */
    @Override
    public void storeEntries(OutputStream stream, OBFTable table) throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("Stream must not be null!");
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(stream));
            writeTable(out, table);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void parseStringArray(String[] lines, OBFTable table, boolean overwrite) throws FormatException {
        if (table instanceof DirectOBFTableSRG) {
            parseStringArraySRG(lines, (DirectOBFTableSRG) table, overwrite);
        } else {
            parseStringArrayNormal(lines, table, overwrite);
        }
    }

    private void parseStringArraySRG(String[] lines, DirectOBFTableSRG table, boolean overwrite) throws FormatException {
        int line = 0;
        for (String str : lines) {
            line++;
            if (isCommentLine(str)) {
                continue;
            }
            String[] parts = str.split(PATTERN_COLON);
            if (parts.length < 4) {
                throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
            }
            TargetType type = TargetType.valueOf(parts[0]);
            if (type == null) {
                throw new FormatException("Illegal target type on line " + line + ": \"" + parts[0] + "\"");
            }
            if (type == TargetType.METHOD) {
                if (parts.length < 7) {
                    throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                if (overwrite || !table.hasTypeObf(parts[1], type)) {
                    if (stripDescs) {
                        table.addTypeSRG(type, parts[1], parts[3], parts[5]);
                    } else {
                        table.addTypeSRG(type, parts[1] + " " + parts[2], parts[3] + " " + parts[4], parts[5] + " " + parts[6]);
                    }
                }
            } else {
                if (overwrite || !table.hasTypeObf(parts[1], type)) {
                    table.addTypeSRG(type, parts[1], parts[2], parts[3]);
                }
            }
        }
    }

    private void parseStringArrayNormal(String[] lines, OBFTable table, boolean overwrite) throws FormatException {
        int line = 0;
        for (String str : lines) {
            line++;
            if (isCommentLine(str)) {
                continue;
            }
            String[] parts = str.split(PATTERN_COLON);
            if (parts.length < 4) {
                throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
            }
            TargetType type = TargetType.valueOf(parts[0]);
            if (type == null) {
                throw new FormatException("Illegal target type on line " + line + ": \"" + parts[0] + "\"");
            }
            if (type == TargetType.METHOD) {
                if (parts.length < 7) {
                    throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                if (overwrite || !table.hasTypeObf(parts[1], type)) {
                    table.addType(parts[1] + " " + parts[2], parts[5] + " " + parts[6], type);
                }
            } else {
                if (overwrite || !table.hasTypeObf(parts[1], type)) {
                    table.addType(parts[1], parts[3], type);
                }
            }
        }
    }

    private void writeTable(Writer out, OBFTable table) throws IOException {
        if (table instanceof DirectOBFTableSRG) {
            writeTableSRG(out, (DirectOBFTableSRG) table);
        } else {
            writeTableNormal(out, table);
        }
    }

    private void writeTableNormal(Writer out, OBFTable table) throws IOException {

        for (TargetType type : TargetType.values()) {
            for (String obf : table.getAllTypeObf(type)) {
                out.write(type.name());
                out.write(":");
                String deobf = table.deobfType(obf, type);
                if (type == TargetType.METHOD) {
                    String[] obfParts = obf.split(PATTERN_SPACE);
                    String obfName = obfParts[0];
                    String obfDesc = packageToPath(obfParts[1]);
                    String[] mcpParts = deobf.split(PATTERN_SPACE);
                    String mcpName = mcpParts[0];
                    String mcpDesc = packageToPath(mcpParts[1]);
                    out.write(obfName);
                    out.write(":");
                    out.write(obfDesc);
                    out.write(": : :");
                    out.write(mcpName);
                    out.write(":");
                    out.write(mcpDesc);
                } else {
                    out.write(obf);
                    out.write(":");
                    out.write(" ");
                    out.write(":");
                    out.write(deobf);
                }
                out.write("\n");
            }
        }
    }

    private void writeTableSRG(Writer out, DirectOBFTableSRG table) throws IOException {
        for (TargetType type : TargetType.values()) {
            for (String obf : table.getAllTypeObf(type)) {
                out.write(type.name());
                out.write(":");
                String srg = table.getSRGFromObfType(obf, type);
                String deobf = table.deobfType(obf, type);
                if (type == TargetType.METHOD) {
                    String[] obfParts = obf.split(PATTERN_SPACE);
                    String obfName = obfParts[0];
                    String obfDesc = obfParts.length >= 2 ? packageToPath(obfParts[1]) : " ";
                    String[] srgParts = srg.split(PATTERN_SPACE);
                    String srgName = srgParts[0];
                    String srgDesc = srgParts.length >= 2 ? packageToPath(srgParts[1]) : " ";
                    String[] mcpParts = deobf.split(PATTERN_SPACE);
                    String mcpName = mcpParts[0];
                    String mcpDesc = mcpParts.length >= 2 ? packageToPath(mcpParts[1]) : " ";
                    out.write(String.valueOf(obfName));
                    out.write(":");
                    out.write(String.valueOf(obfDesc));
                    out.write(":");
                    out.write(String.valueOf(srgName));
                    out.write(":");
                    out.write(String.valueOf(srgDesc));
                    out.write(":");
                    out.write(String.valueOf(mcpName));
                    out.write(":");
                    out.write(String.valueOf(mcpDesc));
                } else {
                    out.write(String.valueOf(obf));
                    out.write(":");
                    out.write(String.valueOf(srg));
                    out.write(":");
                    out.write(String.valueOf(deobf));
                }
                out.write("\n");
            }
        }
    }

    private String packageToPath(String pkg) {
        if (pkg == null) {
            return null;
        }
        return pkg.replace('.', '/');
    }

    private boolean isCommentLine(String str) {
        String trimmed = str.trim();
        return (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("//"));
    }
}
