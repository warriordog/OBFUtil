package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.parse.FormatException;
import net.acomputerdog.OBFUtil.parse.URLParser;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.OBFUtil.util.TargetType;
import net.acomputerdog.core.file.TextFileReader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

/**
 * Reads and write obfuscation mappings to a .obf file.
 * General format is:
 *   # Comment type 1
 *   // Comment type 2
 *   <TYPE>:<OBF>=<DEOBF>
 */
public class OBFParser implements FileParser, URLParser {
    @Override
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException {
        TextFileReader reader = null;
        try {
            reader = new TextFileReader(file);
            parseStringArray(reader.readAllLines(), table, overwrite);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    @Override
    public void storeEntries(File file, OBFTable table) throws IOException {
        Writer out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            writeTable(out, table);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Loads all entries located in a File into an OBFTable.
     *
     * @param url       The URL to load from.  Must exist.
     * @param table     The table to write to.
     * @param overwrite If true overwrite existing mappings.
     */
    @Override
    public void loadEntries(URL url, OBFTable table, boolean overwrite) throws IOException {
        URLConnection con = url.openConnection();
        con.connect();
        if (!con.getDoInput()) {
            throw new IllegalArgumentException("URL does not allow reading!");
        }
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(con.getInputStream());
            String data = "";
            while (in.available() > 0) {
                data = data.concat(String.valueOf((char) in.read()));
            }
            parseStringArray(data.split(Pattern.quote("\n")), table, overwrite);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Saves all entries located in an OBFTable into a file.
     *
     * @param url   The URL to write to.  Must exist.
     * @param table The table to read from
     * @throws java.io.IOException
     */
    @Override
    public void storeEntries(URL url, OBFTable table) throws IOException {
        URLConnection con = url.openConnection();
        con.connect();
        if (!con.getDoOutput()) {
            throw new IllegalArgumentException("URL does not allow writing!");
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            writeTable(out, table);
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

    private int parseStringArray(String[] lines, OBFTable table, boolean overwrite) throws FormatException {
        int line = 0;
        for (String str : lines) {
            line++;
            if (isCommentLine(str)) {
                continue;
            }
            String[] typeParts = str.split(Pattern.quote(":"));
            if (typeParts.length < 2) {
                throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
            }
            TargetType type = TargetType.valueOf(typeParts[0]);
            if (type == null) {
                throw new FormatException("Illegal target type on line " + line + ": \"" + typeParts[0] + "\"");
            }
            String[] obfParts = typeParts[1].split(Pattern.quote("="));
            if (obfParts.length < 2) {
                throw new FormatException("Format error on line " + line + ": \"" + str + "\"");
            }
            if (overwrite || !table.hasTypeDeobf(type, obfParts[0])) {
                table.addType(type, obfParts[0], obfParts[1]);
            }
        }
        return line;
    }

    private void writeTable(Writer out, OBFTable table) throws IOException {
        for (TargetType type : TargetType.values()) {
            for (String obf : table.getAllTypeObf(type)) {
                String deobf = table.deobfType(type, obf);
                out.write(type.name());
                out.write(":");
                out.write(obf);
                out.write("=");
                out.write(deobf);
                out.write("\n");
            }
        }
    }
}
