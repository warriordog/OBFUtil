package net.acomputerdog.OBFUtil.tool;

import net.acomputerdog.OBFUtil.parse.FileParser;
import net.acomputerdog.OBFUtil.parse.types.BLOBFParser;
import net.acomputerdog.OBFUtil.parse.types.MCPCSVFileParser;
import net.acomputerdog.OBFUtil.parse.types.SRGFileParser;
import net.acomputerdog.OBFUtil.table.DirectOBFTable;
import net.acomputerdog.OBFUtil.table.DirectOBFTableSRG;
import net.acomputerdog.OBFUtil.table.OBFTable;
import net.acomputerdog.OBFUtil.util.TargetType;
import net.acomputerdog.core.java.Patterns;

import java.io.File;
import java.io.IOException;

/**
 * Creates BlazeLoader configuration files from MCP config files.
 */
public class BLConfigGen {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Use \"Convert <mcp_dir> <output_dir>\"");
            System.exit(0);
        }
        File mcp = new File(args[0]);
        File out = new File(args[1]);
        if (!mcp.isDirectory() || !out.isDirectory()) {
            System.out.println("Folders are missing!");
            System.exit(0);
        }
        System.out.println("Starting.");
        FileParser srgClient = new SRGFileParser("C", false);
        FileParser csvMethodClient = new MCPCSVFileParser(TargetType.METHOD, true, 1);
        FileParser csvFieldClient = new MCPCSVFileParser(TargetType.FIELD, true, 1);
        DirectOBFTableSRG client = new DirectOBFTableSRG();
        OBFTable srgTemp = new DirectOBFTable();
        OBFTable mcpTemp = new DirectOBFTable();
        srgClient.loadEntries(new File(mcp, "joined.srg"), srgTemp, false);
        csvFieldClient.loadEntries(new File(mcp, "fields.csv"), mcpTemp, true);
        csvMethodClient.loadEntries(new File(mcp, "methods.csv"), mcpTemp, true);
        addOthers(client, srgTemp);
        addSRGsMethod(client, srgTemp, mcpTemp);
        addSRGsField(client, srgTemp, mcpTemp);

        FileParser bl = new BLOBFParser();
        bl.storeEntries(new File(out, "minecraft_client.obf"), client);
        System.out.println("Done.");
    }

    private static void addSRGsMethod(DirectOBFTableSRG dest, OBFTable sourceSRG, OBFTable sourceMCP) {

        for (String str : sourceSRG.getAllMethodsDeobf()) {
            String[] parts1 = str.split(Patterns.SPACE);
            if (parts1.length >= 1) {
                String[] parts2 = parts1[0].split(Patterns.PERIOD);
                String obf = sourceSRG.obfMethod(str);
                String searge = parts2[parts2.length - 1];
                String mcp = sourceMCP.deobfMethod(searge);
                dest.addMethodSRG(obf, str, rebuildName(parts2, mcp) + " " + parts1[1]);
            } else {
                System.out.println("No parts: " + str);
            }
        }
    }

    private static void addSRGsField(DirectOBFTableSRG dest, OBFTable sourceSRG, OBFTable sourceMCP) {
        for (String str : sourceSRG.getAllFieldsDeobf()) {
            String[] parts = str.split(Patterns.PERIOD);
            if (parts.length >= 1) {
                String searge = parts[parts.length - 1];
                String mcp = sourceMCP.deobfField(searge);
                String obf = sourceSRG.obfField(str);
                dest.addFieldSRG(obf, str, rebuildName(parts, mcp));
            } else {
                System.out.println("No parts: " + str);
            }
        }
    }

    private static void addOthers(OBFTable dest, OBFTable source) {
        for (String str : source.getAllClassesObf()) {
            String cls = source.deobfClass(str);
            dest.addClass(str, cls);
        }
        for (String str : source.getAllPackagesObf()) {
            String pkg = source.deobfPackage(str);
            dest.addPackage(str, pkg);
        }
    }

    private static String rebuildName(String[] srgs, String mcp) {
        StringBuilder builder = new StringBuilder(srgs.length * 2);
        for (int index = 0; index < srgs.length - 1; index++) {
            builder.append(srgs[index]);
            builder.append(".");
        }
        builder.append(mcp == null ? srgs[srgs.length - 1] : mcp);
        return builder.toString();
    }
}
