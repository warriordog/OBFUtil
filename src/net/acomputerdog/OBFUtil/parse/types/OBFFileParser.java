package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.deobfuscator.TargetType;
import net.acomputerdog.OBFUtil.parse.FileFormatException;
import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.core.file.TextFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class OBFFileParser implements FileParser {
    @Override
    public void loadEntries(File file, OBFTable table, boolean overwrite) throws IOException {
        TextFileReader reader = null;
        try {
            reader = new TextFileReader(file);
            int line = 0;
            for (String str : reader.readAllLines()) {
                line++;
                if (isCommentLine(str)) {
                    continue;
                }
                String[] typeParts = str.split(Pattern.quote(":"));
                if (typeParts.length < 2) {
                    throw new FileFormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                TargetType type = TargetType.valueOf(typeParts[0]);
                if (type == null) {
                    throw new FileFormatException("Illegal target type on line " + line + ": \"" + typeParts[0] + "\"");
                }
                String[] obfParts = typeParts[1].split(Pattern.quote("="));
                if (obfParts.length < 2) {
                    throw new FileFormatException("Format error on line " + line + ": \"" + str + "\"");
                }
                if (overwrite || !table.hasType(type, obfParts[0])) {
                    table.addType(type, obfParts[0], obfParts[1]);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private boolean isCommentLine(String str) {
        String trimmed = str.trim();
        return (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("//"));
    }

    @Override
    public void storeEntries(File file, OBFTable table) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            for (String str : table.getAllPackages()) {
                String deobf = table.getPackage(str);
                out.write(TargetType.PACKAGE.name() + ":" + str + "=" + deobf + "\n");
            }
            for (String str : table.getAllClasses()) {
                String deobf = table.getClass(str);
                out.write(TargetType.CLASS.name() + ":" + str + "=" + deobf + "\n");
            }
            for (String str : table.getAllFields()) {
                String deobf = table.getField(str);
                out.write(TargetType.FIELD.name() + ":" + str + "=" + deobf + "\n");
            }
            for (String str : table.getAllMethods()) {
                String deobf = table.getMethod(str);
                out.write(TargetType.METHOD.name() + ":" + str + "=" + deobf + "\n");
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
