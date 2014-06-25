package net.acomputerdog.OBFUtil.util;

/**
 * Represents the part of a class to target.
 */
public enum TargetType {
    /**
     * A package name/structure
     */
    PACKAGE("PACKAGE", "PK"),
    /**
     * A class
     */
    CLASS("CLASS", "CL"),
    /**
     * A method or constructor
     */
    METHOD("METHOD", "MD"),
    /**
     * A field
     */
    FIELD("FIELD", "FD");
    //TODO: Add comment and param?

    private final String[] aliases;

    TargetType(String... aliases) {
        this.aliases = aliases;
    }

    public static TargetType getType(String type) {
        if (type == null) {
            return null;
        }
        for (TargetType tt : TargetType.values()) {
            for (String str : tt.aliases) {
                if (type.equalsIgnoreCase(str)) {
                    return tt;
                }
            }
        }
        return null;
    }
}
