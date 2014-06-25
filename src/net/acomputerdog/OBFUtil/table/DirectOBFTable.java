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
    private final Map<String, String> packageMap = new HashMap<String, String>();
    private final List<String> packageList = new ArrayList<String>();
    private final Map<String, String> classMap = new HashMap<String, String>();
    private final List<String> classList = new ArrayList<String>();
    private final Map<String, String> fieldMap = new HashMap<String, String>();
    private final List<String> fieldList = new ArrayList<String>();
    private final Map<String, String> methodMap = new HashMap<String, String>();
    private final List<String> methodList = new ArrayList<String>();

    public String getPackage(String obfName) {
        return packageMap.get(obfName);
    }

    public String getClass(String obfName) {
        return classMap.get(obfName);
    }

    public String getField(String obfName) {
        return fieldMap.get(obfName);
    }

    public String getMethod(String obfName) {
        return methodMap.get(obfName);
    }

    @Override
    public String getType(TargetType type, String obfName) {
        switch (type) {
            case PACKAGE: {
                return getPackage(obfName);
            }
            case CLASS: {
                return getClass(obfName);
            }
            case FIELD: {
                return getField(obfName);
            }
            case METHOD: {
                return getMethod(obfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    public void addPackage(String obfName, String deObfName) {
        packageMap.put(obfName, deObfName);
        packageList.add(obfName);
    }

    public void addClass(String obfName, String deObfName) {
        classMap.put(obfName, deObfName);
        classList.add(obfName);
    }

    public void addField(String obfName, String deObfName) {
        fieldMap.put(obfName, deObfName);
        fieldList.add(obfName);
    }

    public void addMethod(String obfName, String deObfName) {
        methodMap.put(obfName, deObfName);
        methodList.add(obfName);
    }

    @Override
    public void addType(TargetType type, String obfName, String deObfName) {
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

    @Override
    public boolean hasPackage(String obfName) {
        return packageList.contains(obfName);
    }

    @Override
    public boolean hasClass(String obfName) {
        return classList.contains(obfName);
    }

    @Override
    public boolean hasField(String obfName) {
        return fieldList.contains(obfName);
    }

    @Override
    public boolean hasMethod(String obfName) {
        return methodList.contains(obfName);
    }

    @Override
    public boolean hasType(TargetType type, String obfName) {
        switch (type) {
            case PACKAGE: {
                return hasPackage(obfName);
            }
            case CLASS: {
                return hasClass(obfName);
            }
            case FIELD: {
                return hasField(obfName);
            }
            case METHOD: {
                return hasMethod(obfName);
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    @Override
    public String[] getAllPackages() {
        return packageList.toArray(new String[packageList.size()]);
    }

    @Override
    public String[] getAllClasses() {
        return classList.toArray(new String[classList.size()]);
    }

    @Override
    public String[] getAllFields() {
        return fieldList.toArray(new String[fieldList.size()]);
    }

    @Override
    public String[] getAllMethods() {
        return methodList.toArray(new String[methodList.size()]);
    }

    @Override
    public String[] getAllType(TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getAllPackages();
            }
            case CLASS: {
                return getAllClasses();
            }
            case FIELD: {
                return getAllFields();
            }
            case METHOD: {
                return getAllMethods();
            }
            default: {
                throw new IllegalArgumentException("Unknown target type: " + type.name());
            }
        }
    }

    @Override
    public void writeToTable(OBFTable table, boolean overwrite) {
        for (String obfName : packageList) {
            if (overwrite || !table.hasPackage(obfName)) {
                table.addPackage(obfName, packageMap.get(obfName));
            }
        }
        for (String obfName : classList) {
            if (overwrite || !table.hasClass(obfName)) {
                table.addClass(obfName, classMap.get(obfName));
            }
        }
        for (String obfName : fieldList) {
            if (overwrite || !table.hasField(obfName)) {
                table.addField(obfName, fieldMap.get(obfName));
            }
        }
        for (String obfName : methodList) {
            if (overwrite || !table.hasMethod(obfName)) {
                table.addMethod(obfName, methodMap.get(obfName));
            }
        }
    }

}
