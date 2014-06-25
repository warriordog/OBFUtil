package net.acomputerdog.OBFUtil.parse.types;

import net.acomputerdog.OBFUtil.deobfuscator.TargetType;
import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.table.DirectOBFTable;
import net.acomputerdog.OBFUtil.table.OBFTable;

import java.io.File;
import java.io.IOException;

public class MCPCSVFileParser extends CSVFileParser {
    private static final int OBFNAME_INDEX = 0;
    private static final int DEOBFNAME_INDEX = 1;
    private static final int SIDE_INDEX = 2;
    private static final int DESC_INDEX = 3;

    private final TargetType type;
    private final int side;
    private final boolean ignoreSides;

    public MCPCSVFileParser(TargetType type) {
        this(type, true, -1); //-1 in case of bugs
    }

    public MCPCSVFileParser(TargetType type, int side) {
        this(type, false, side);
    }

    public MCPCSVFileParser(TargetType type, boolean ignoreSides, int side) {
        this.type = type;
        this.side = side;
        this.ignoreSides = ignoreSides;
    }

    @Override
    public void writeCSVToTable(File source, CSVFile csv, OBFTable table) {
        for (int rowNum = 0; rowNum < csv.size(); rowNum++) {
            String[] row = csv.getRow(rowNum);
            if (ignoreSides || Integer.parseInt(row[SIDE_INDEX]) == side) {
                table.addType(type, row[OBFNAME_INDEX], row[DEOBFNAME_INDEX]);
            }
        }
    }

    @Override
    public CSVFile readCSVFromTable(File source, OBFTable table) {
        CSVFile csv = new CSVFile();

        String[] obfs = table.getAllType(type);
        for (String obf : obfs) {
            csv.addItem("searge", obf);
            csv.addItem("name", table.getType(type, obf));
            csv.addItem("side", Integer.toString(ignoreSides ? 0 : side));
            csv.addItem("desc", "");
        }
        return csv;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No file specified!  Use \"test <path_to_csv>\"");
            System.exit(0);
        }
        File csvFile = new File(args[0]);
        if (!csvFile.exists() || !csvFile.isFile()) {
            System.out.println("Path does not represent a file!  Use \"test <path_to_csv>\"");
            System.exit(0);
        }
        FileParser parser = new MCPCSVFileParser(getTypeOfFile(csvFile), false, 0);
        OBFTable table = new DirectOBFTable();
        try {
            parser.loadEntries(csvFile, table, true);
        } catch (IOException e) {
            System.out.println("Exception parsing CSV!");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Packages:");
        for (String str : table.getAllPackages()) {
            System.out.println("   " + str + " = " + table.getPackage(str));
        }
        System.out.println("Classes:");
        for (String str : table.getAllClasses()) {
            System.out.println("   " + str + " = " + table.getClass(str));
        }
        System.out.println("Fields:");
        for (String str : table.getAllFields()) {
            System.out.println("   " + str + " = " + table.getField(str));
        }
        System.out.println("Methods:");
        for (String str : table.getAllMethods()) {
            System.out.println("   " + str + " = " + table.getMethod(str));
        }

    }

    private static TargetType getTypeOfFile(File f) {
        if (f.getName().startsWith("fields")) return TargetType.FIELD;
        else if (f.getName().startsWith("methods")) return TargetType.METHOD;
        else throw new IllegalArgumentException("File is not a valid MCP CSV file!");
    }
}
