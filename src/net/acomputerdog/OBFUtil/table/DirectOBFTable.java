package net.acomputerdog.OBFUtil.table;

import net.acomputerdog.OBFUtil.util.TargetType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple, direct implementation of OBFTable.  Uses HashMaps and ArrayLists to store data.
 */
public class DirectOBFTable implements OBFTable {
    private final Map<String, String> packageMapObf = new HashMap<String, String>();
    private final List<String> packageListObf = new ArrayList<String>();
    private final Map<String, String> classMapObf = new HashMap<String, String>();
    private final List<String> classListObf = new ArrayList<String>();
    private final Map<String, String> fieldMapObf = new HashMap<String, String>();
    private final List<String> fieldListObf = new ArrayList<String>();
    private final Map<String, String> methodMapObf = new HashMap<String, String>();
    private final List<String> methodListObf = new ArrayList<String>();
    private final Map<String, String> packageMapDeobf = new HashMap<String, String>();
    private final List<String> packageListDeobf = new ArrayList<String>();
    private final Map<String, String> classMapDeobf = new HashMap<String, String>();
    private final List<String> classListDeobf = new ArrayList<String>();
    private final Map<String, String> fieldMapDeobf = new HashMap<String, String>();
    private final List<String> fieldListDeobf = new ArrayList<String>();
    private final Map<String, String> methodMapDeobf = new HashMap<String, String>();
    private final List<String> methodListDeobf = new ArrayList<String>();


    public String deobfPackage(String obfName) {
        return packageMapObf.get(obfName);
    }

    public String deobfClass(String obfName) {
        return classMapObf.get(obfName);
    }

    public String deobfField(String obfName) {
        return fieldMapObf.get(obfName);
    }

    public String deobfMethod(String obfName) {
        return methodMapObf.get(obfName);
    }

    @Override
    public String deobfType(String obfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return deobfPackage(obfName);
            }
            case CLASS: {
                return deobfClass(obfName);
            }
            case FIELD: {
                return deobfField(obfName);
            }
            case METHOD: {
                return deobfMethod(obfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    /**
     * Gets the obfuscated name of a package.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    @Override
    public String obfPackage(String deobfName) {
        return packageMapDeobf.get(deobfName);
    }

    /**
     * Gets the obfuscated name of a class.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    @Override
    public String obfClass(String deobfName) {
        return classMapDeobf.get(deobfName);
    }

    /**
     * Gets the obfuscated name of a field.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    @Override
    public String obfField(String deobfName) {
        return fieldMapDeobf.get(deobfName);
    }

    /**
     * Gets the obfuscated name of a method.
     *
     * @param deobfName The deobfuscated name.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    @Override
    public String obfMethod(String deobfName) {
        return methodMapDeobf.get(deobfName);
    }

    /**
     * Gets the obfuscated name of a TargetType.
     *
     * @param deobfName The deobfuscated name.
     * @param type      The type of obfuscation to get.
     * @return Return the obfuscated name, or null if the mapping is not defined.
     */
    @Override
    public String obfType(String deobfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return obfPackage(deobfName);
            }
            case CLASS: {
                return obfClass(deobfName);
            }
            case FIELD: {
                return obfField(deobfName);
            }
            case METHOD: {
                return obfMethod(deobfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    public void addPackage(String obfName, String deObfName) {
        packageMapObf.put(obfName, deObfName);
        packageListObf.add(obfName);
        packageMapDeobf.put(deObfName, obfName);
        packageListDeobf.add(deObfName);
    }

    public void addClass(String obfName, String deObfName) {
        classMapObf.put(obfName, deObfName);
        classListObf.add(obfName);
        classMapDeobf.put(deObfName, obfName);
        classListDeobf.add(deObfName);
    }

    public void addField(String obfName, String deObfName) {
        fieldMapObf.put(obfName, deObfName);
        fieldListObf.add(obfName);
        fieldMapDeobf.put(deObfName, obfName);
        fieldListDeobf.add(deObfName);
    }

    public void addMethod(String obfName, String deObfName) {
        methodMapObf.put(obfName, deObfName);
        methodListObf.add(obfName);
        methodMapDeobf.put(deObfName, obfName);
        methodListDeobf.add(deObfName);
    }

    @Override
    public void addType(String obfName, String deObfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                addPackage(obfName, deObfName);
                break;
            }
            case CLASS: {
                addClass(obfName, deObfName);
                break;
            }
            case FIELD: {
                addField(obfName, deObfName);
                break;
            }
            case METHOD: {
                addMethod(obfName, deObfName);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    /**
     * Checks if a package mapping is defined.
     *
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    @Override
    public boolean hasPackageObf(String obfName) {
        return packageListObf.contains(obfName);
    }

    /**
     * Checks if a class mapping is defined.
     *
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    @Override
    public boolean hasClassObf(String obfName) {
        return classListObf.contains(obfName);
    }

    /**
     * Checks if a field mapping is defined.
     *
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    @Override
    public boolean hasFieldObf(String obfName) {
        return fieldListObf.contains(obfName);
    }

    /**
     * Checks if a method mapping is defined.
     *
     * @param obfName The obfuscated name.
     * @return Return true if the mapping is defined, false otherwise.
     */
    @Override
    public boolean hasMethodObf(String obfName) {
        return methodListObf.contains(obfName);
    }

    /**
     * Checks if a TargetType mapping is defined.
     *
     * @param obfName The obfuscated name.
     * @param type    The type to check.
     * @return Return true if the mapping is defined, false otherwise.
     */
    @Override
    public boolean hasTypeObf(String obfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return hasPackageObf(obfName);
            }
            case CLASS: {
                return hasClassObf(obfName);
            }
            case FIELD: {
                return hasFieldObf(obfName);
            }
            case METHOD: {
                return hasMethodObf(obfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    @Override
    public boolean hasPackageDeobf(String deobfName) {
        return packageListDeobf.contains(deobfName);
    }

    @Override
    public boolean hasClassDeobf(String deobfName) {
        return classListDeobf.contains(deobfName);
    }

    @Override
    public boolean hasFieldDeobf(String deobfName) {
        return fieldListDeobf.contains(deobfName);
    }

    @Override
    public boolean hasMethodDeobf(String deobfName) {
        return methodListDeobf.contains(deobfName);
    }

    @Override
    public boolean hasTypeDeobf(String deobfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return hasPackageDeobf(deobfName);
            }
            case CLASS: {
                return hasClassDeobf(deobfName);
            }
            case FIELD: {
                return hasFieldDeobf(deobfName);
            }
            case METHOD: {
                return hasMethodDeobf(deobfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    @Override
    public String[] getAllPackagesObf() {
        return packageListObf.toArray(new String[packageListObf.size()]);
    }

    @Override
    public String[] getAllClassesObf() {
        return classListObf.toArray(new String[classListObf.size()]);
    }

    @Override
    public String[] getAllFieldsObf() {
        return fieldListObf.toArray(new String[fieldListObf.size()]);
    }

    @Override
    public String[] getAllMethodsObf() {
        return methodListObf.toArray(new String[methodListObf.size()]);
    }

    @Override
    public String[] getAllTypeObf(TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getAllPackagesObf();
            }
            case CLASS: {
                return getAllClassesObf();
            }
            case FIELD: {
                return getAllFieldsObf();
            }
            case METHOD: {
                return getAllMethodsObf();
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    /**
     * Get an array of all deobfuscated package names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    @Override
    public String[] getAllPackagesDeobf() {
        return packageListDeobf.toArray(new String[packageListDeobf.size()]);
    }

    /**
     * Get an array of all deobfuscated class names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    @Override
    public String[] getAllClassesDeobf() {
        return classListDeobf.toArray(new String[classListDeobf.size()]);
    }

    /**
     * Get an array of all deobfuscated field names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    @Override
    public String[] getAllFieldsDeobf() {
        return fieldListDeobf.toArray(new String[fieldListDeobf.size()]);
    }

    /**
     * Get an array of all deobfuscated method names.
     *
     * @return Return an array of Strings representing all deobfuscated names.
     */
    @Override
    public String[] getAllMethodsDeobf() {
        return methodListDeobf.toArray(new String[methodListDeobf.size()]);
    }

    /**
     * Get an array of all deobfuscated TargetType names.
     *
     * @param type The type to get.
     * @return Return an array of Strings representing all deobfuscated names.
     */
    @Override
    public String[] getAllTypeDeobf(TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getAllPackagesDeobf();
            }
            case CLASS: {
                return getAllClassesDeobf();
            }
            case FIELD: {
                return getAllFieldsDeobf();
            }
            case METHOD: {
                return getAllMethodsDeobf();
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    @Override
    public void writeToTable(OBFTable table, boolean overwrite) {
        for (String obfName : packageListObf) {
            if (overwrite || !table.hasPackageObf(obfName)) {
                table.addPackage(obfName, packageMapObf.get(obfName));
            }
        }
        for (String obfName : classListObf) {
            if (overwrite || !table.hasClassObf(obfName)) {
                table.addClass(obfName, classMapObf.get(obfName));
            }
        }
        for (String obfName : fieldListObf) {
            if (overwrite || !table.hasFieldObf(obfName)) {
                table.addField(obfName, fieldMapObf.get(obfName));
            }
        }
        for (String obfName : methodListObf) {
            if (overwrite || !table.hasMethodObf(obfName)) {
                table.addMethod(obfName, methodMapObf.get(obfName));
            }
        }
    }

}
