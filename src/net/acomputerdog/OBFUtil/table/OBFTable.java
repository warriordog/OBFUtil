package net.acomputerdog.OBFUtil.table;

import net.acomputerdog.OBFUtil.deobfuscator.TargetType;

public interface OBFTable {
    public String getPackage(String obfName);

    public String getClass(String obfName);

    public String getField(String obfName);

    public String getMethod(String obfName);

    public String getType(TargetType type, String obfName);

    public void addPackage(String obfName, String deObfName);

    public void addClass(String obfName, String deObfName);

    public void addField(String obfName, String deObfName);

    public void addMethod(String obfName, String deObfName);

    public void addType(TargetType type, String obfName, String deObfName);

    public boolean hasPackage(String obfName);

    public boolean hasClass(String obfName);

    public boolean hasField(String obfName);

    public boolean hasMethod(String obfName);

    public boolean hasType(TargetType type, String obfName);

    public String[] getAllPackages();

    public String[] getAllClasses();

    public String[] getAllFields();

    public String[] getAllMethods();

    public String[] getAllType(TargetType type);

    public void writeToTable(OBFTable table, boolean overwrite);

    public void readFromTable(OBFTable table, boolean overwrite);
}
