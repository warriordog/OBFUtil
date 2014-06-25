package net.acomputerdog.OBFUtil.table;

import net.acomputerdog.OBFUtil.util.TargetType;

/**
 * Represents an object capable of managing obfuscation data.
 */
public interface OBFTable {
    /**
     * Gets the deobfuscated name of a package.
     *
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String getPackage(String obfName);

    /**
     * Gets the deobfuscated name of a class.
     *
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String getClass(String obfName);

    /**
     * Gets the deobfuscated name of a field.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String getField(String obfName);

    /**
     * Gets the deobfuscated name of a method.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String getMethod(String obfName);

    /**
     * Gets the deobfuscated name of a TargetType.
     * @param type The type of obfuscation to get.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String getType(TargetType type, String obfName);

    /**
     * Adds an obfuscation mapping for a package.
     * @param obfName The obfuscated name.
     * @param deObfName The deobfuscated name.
     */
    public void addPackage(String obfName, String deObfName);

    /**
     * Adds an obfuscation mapping for a class.
     * @param obfName The obfuscated name.
     * @param deObfName The deobfuscated name.
     */
    public void addClass(String obfName, String deObfName);

    /**
     * Adds an obfuscation mapping for a field.
     * @param obfName The obfuscated name.
     * @param deObfName The deobfuscated name.
     */
    public void addField(String obfName, String deObfName);

    /**
     * Adds an obfuscation mapping for a method.
     * @param obfName The obfuscated name.
     * @param deObfName The deobfuscated name.
     */
    public void addMethod(String obfName, String deObfName);

    /**
     * Adds an obfuscation mapping for a TargetType.
     * @param type The type to define.
     * @param obfName The obfuscated name.
     * @param deObfName The deobfuscated name.
     */
    public void addType(TargetType type, String obfName, String deObfName);

    /**
     * Checks if a package mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasPackage(String obfName);

    /**
     * Checks if a class mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasClass(String obfName);

    /**
     * Checks if a field mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasField(String obfName);

    /**
     * Checks if a method mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasMethod(String obfName);

    /**
     * Checks if a TargetType mapping is defined.
     * @param type The type to check.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasType(TargetType type, String obfName);

    /**
     * Get an array of all obfuscated package names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllPackages();

    /**
     * Get an array of all obfuscated class names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllClasses();

    /**
     * Get an array of all obfuscated field names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllFields();

    /**
     * Get an array of all obfuscated method names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllMethods();

    /**
     * Get an array of all obfuscated TargetType names.
     * @param type The type to get.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllType(TargetType type);

    /**
     * Write the contents of this table to another table.
     * @param table The table to write to.
     * @param overwrite If true, overwrite existing mappings.
     */
    public void writeToTable(OBFTable table, boolean overwrite);
}
