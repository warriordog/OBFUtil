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
    public String deobfPackage(String obfName);

    /**
     * Gets the deobfuscated name of a class.
     *
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String deobfClass(String obfName);

    /**
     * Gets the deobfuscated name of a field.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String deobfField(String obfName);

    /**
     * Gets the deobfuscated name of a method.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String deobfMethod(String obfName);

    /**
     * Gets the deobfuscated name of a TargetType.
     * @param type The type of obfuscation to get.
     * @param obfName The obfuscated name.
     * @return Return the deobfuscated name, or null if the mapping is not defined.
     */
    public String deobfType(TargetType type, String obfName);

    /**
     * Gets the obfuscated name of a package.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    public String obfPackage(String deobfName);

    /**
     * Gets the obfuscated name of a class.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    public String obfClass(String deobfName);

    /**
     * Gets the obfuscated name of a field.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    public String obfField(String deobfName);

    /**
     * Gets the obfuscated name of a method.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    public String obfMethod(String deobfName);

    /**
     * Gets the obfuscated name of a TargetType.
     *
     * @param type      The type of obfuscation to get.
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    public String obfType(TargetType type, String deobfName);

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
    public boolean hasPackageObf(String obfName);

    /**
     * Checks if a class mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasClassObf(String obfName);

    /**
     * Checks if a field mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasFieldObf(String obfName);

    /**
     * Checks if a method mapping is defined.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasMethodObf(String obfName);

    /**
     * Checks if a TargetType mapping is defined.
     * @param type The type to check.
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasTypeObf(TargetType type, String obfName);

    /**
     * Checks if a package mapping is defined.
     *
     * @param deobfName The deobfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasPackageDeobf(String deobfName);

    /**
     * Checks if a class mapping is defined.
     *
     * @param deobfName The deobfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasClassDeobf(String deobfName);

    /**
     * Checks if a field mapping is defined.
     *
     * @param deobfName The deobfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasFieldDeobf(String deobfName);

    /**
     * Checks if a method mapping is defined.
     *
     * @param deobfName The deobfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasMethodDeobf(String deobfName);

    /**
     * Checks if a TargetType mapping is defined.
     *
     * @param type      The type to check.
     * @param deobfName The deobfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    public boolean hasTypeDeobf(TargetType type, String deobfName);

    /**
     * Get an array of all obfuscated package names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllPackagesObf();

    /**
     * Get an array of all obfuscated class names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllClassesObf();

    /**
     * Get an array of all obfuscated field names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllFieldsObf();

    /**
     * Get an array of all obfuscated method names.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllMethodsObf();

    /**
     * Get an array of all obfuscated TargetType names.
     *
     * @param type The type to get.
     * @return Return an array of Strings representing all obfuscated names.
     */
    public String[] getAllTypeObf(TargetType type);

    /**
     * Get an array of all deobfuscated package names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    public String[] getAllPackagesDeobf();

    /**
     * Get an array of all deobfuscated class names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    public String[] getAllClassesDeobf();

    /**
     * Get an array of all deobfuscated field names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    public String[] getAllFieldsDeobf();

    /**
     * Get an array of all deobfuscated method names.
     * @return Return an array of Strings representing all deobfuscated names.
     */
    public String[] getAllMethodsDeobf();

    /**
     * Get an array of all deobfuscated TargetType names.
     * @param type The type to get.
     * @return Return an array of Strings representing all deobfuscated names.
     */
    public String[] getAllTypeDeobf(TargetType type);

    /**
     * Write the contents of this table to another table.
     * @param table The table to write to.
     * @param overwrite If true, overwrite existing mappings.
     */
    public void writeToTable(OBFTable table, boolean overwrite);
}
