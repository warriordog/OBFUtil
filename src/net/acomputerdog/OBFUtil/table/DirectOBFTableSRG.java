package net.acomputerdog.OBFUtil.table;

import net.acomputerdog.OBFUtil.util.TargetType;
import net.acomputerdog.core.storage.TripleStringMap;

/**
 * OBFTable that adds support for a third "searge" obfuscation name.  Based on DirectOBFTable.
 */
public class DirectOBFTableSRG extends DirectOBFTable implements OBFTableSRG {
    private final TripleStringMap packages = new TripleStringMap();
    private final TripleStringMap classes = new TripleStringMap();
    private final TripleStringMap fields = new TripleStringMap();
    private final TripleStringMap methods = new TripleStringMap();

    @Override
    public void addPackage(String obfName, String deObfName) {
        addPackageSRG(obfName, deObfName, deObfName);
    }

    @Override
    public void addClass(String obfName, String deObfName) {
        addClassSRG(obfName, deObfName, deObfName);
    }

    @Override
    public void addField(String obfName, String deObfName) {
        addFieldSRG(obfName, deObfName, deObfName);
    }

    @Override
    public void addMethod(String obfName, String deObfName) {
        addMethodSRG(obfName, deObfName, deObfName);
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
                super.addType(obfName, deObfName, type);
                break;
            }
        }
    }

    public void addPackageSRG(String obfName, String seargeName, String deObfName) {
        super.addPackage(obfName, deObfName);
        packages.addItems(obfName, seargeName, deObfName);
    }

    public void addClassSRG(String obfName, String seargeName, String deObfName) {
        super.addClass(obfName, deObfName);
        classes.addItems(obfName, seargeName, deObfName);
    }

    public void addFieldSRG(String obfName, String seargeName, String deObfName) {
        super.addField(obfName, deObfName);
        fields.addItems(obfName, seargeName, deObfName);
    }

    public void addMethodSRG(String obfName, String seargeName, String deObfName) {
        super.addMethod(obfName, deObfName);
        methods.addItems(obfName, seargeName, deObfName);
    }

    @Override
    public void addTypeSRG(String obfName, String seargeName, String deObfName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                addPackageSRG(obfName, seargeName, deObfName);
                break;
            }
            case CLASS: {
                addClassSRG(obfName, seargeName, deObfName);
                break;
            }
            case FIELD: {
                addFieldSRG(obfName, seargeName, deObfName);
                break;
            }
            case METHOD: {
                addMethodSRG(obfName, seargeName, deObfName);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown TargetType!");
            }
        }
    }


    public String getObfFromSRGPackage(String searge) {
        return packages.getFrom2(searge).getItem1();
    }

    public String getObfFromSRGClass(String searge) {
        return classes.getFrom2(searge).getItem1();
    }

    public String getObfFromSRGField(String searge) {
        return fields.getFrom2(searge).getItem1();
    }

    public String getObfFromSRGMethod(String searge) {
        return methods.getFrom2(searge).getItem1();
    }

    public String getObfFromSRGType(String searge, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getObfFromSRGPackage(searge);
            }
            case CLASS: {
                return getObfFromSRGClass(searge);
            }
            case FIELD: {
                return getObfFromSRGField(searge);
            }
            case METHOD: {
                return getObfFromSRGMethod(searge);
            }
            default: {
                throw new IllegalArgumentException("Unknown TargetType!");
            }
        }
    }

    public String getDeObfFromSRGPackage(String searge) {
        return packages.getFrom2(searge).getItem3();
    }

    public String getDeObfFromSRGClass(String searge) {
        return classes.getFrom2(searge).getItem3();
    }

    public String getDeObfFromSRGField(String searge) {
        return fields.getFrom2(searge).getItem3();
    }

    public String getDeObfFromSRGMethod(String searge) {
        return methods.getFrom2(searge).getItem3();
    }

    public String getDeObfFromSRGType(String searge, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getDeObfFromSRGPackage(searge);
            }
            case CLASS: {
                return getDeObfFromSRGClass(searge);
            }
            case FIELD: {
                return getDeObfFromSRGField(searge);
            }
            case METHOD: {
                return getDeObfFromSRGMethod(searge);
            }
            default: {
                throw new IllegalArgumentException("Unknown TargetType!");
            }
        }
    }

    public String getSRGFromObfPackage(String obf) {
        return packages.getFrom1(obf).getItem2();
    }

    public String getSRGFromObfClass(String obf) {
        return classes.getFrom1(obf).getItem2();
    }

    public String getSRGFromObfField(String obf) {
        return fields.getFrom1(obf).getItem2();
    }

    public String getSRGFromObfMethod(String obf) {
        return methods.getFrom1(obf).getItem2();
    }

    public String getSRGFromObfType(String obf, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getSRGFromObfPackage(obf);
            }
            case CLASS: {
                return getSRGFromObfClass(obf);
            }
            case FIELD: {
                return getSRGFromObfField(obf);
            }
            case METHOD: {
                return getSRGFromObfMethod(obf);
            }
            default: {
                throw new IllegalArgumentException("Unknown TargetType!");
            }
        }
    }

    public String getSRGFromDeObfPackage(String deobf) {
        return packages.getFrom3(deobf).getItem2();
    }

    public String getSRGFromDeObfClass(String deobf) {
        return classes.getFrom3(deobf).getItem2();
    }

    public String getSRGFromDeObfField(String deobf) {
        return fields.getFrom3(deobf).getItem2();
    }

    public String getSRGFromDeObfMethod(String deobf) {
        return methods.getFrom3(deobf).getItem2();
    }

    public String getSRGFromDeObfType(String deobf, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return getSRGFromDeObfPackage(deobf);
            }
            case CLASS: {
                return getSRGFromDeObfClass(deobf);
            }
            case FIELD: {
                return getSRGFromDeObfField(deobf);
            }
            case METHOD: {
                return getSRGFromDeObfMethod(deobf);
            }
            default: {
                throw new IllegalArgumentException("Unknown TargetType!");
            }
        }
    }

    @Override
    public boolean hasPackageSRG(String srgName) {
        return packages.hasItem2(srgName);
    }

    @Override
    public boolean hasClassSRG(String srgName) {
        return classes.hasItem2(srgName);
    }

    @Override
    public boolean hasMethodSRG(String srgName) {
        return methods.hasItem2(srgName);
    }

    @Override
    public boolean hasFieldSRG(String srgName) {
        return fields.hasItem2(srgName);
    }

    @Override
    public boolean hasTypeSRG(String srgName, TargetType type) {
        switch (type) {
            case PACKAGE: {
                return hasPackageSRG(srgName);
            }
            case CLASS: {
                return hasClassSRG(srgName);
            }
            case FIELD: {
                return hasFieldSRG(srgName);
            }
            case METHOD: {
                return hasMethodSRG(srgName);
            }
            default: {
                throw new IllegalArgumentException("Invalid TargetType!");
            }
        }
    }

    public void addTypeSRG(TargetType type, String obfName, String seargeName, String deObfName) {
        switch (type) {
            case PACKAGE: {
                addPackageSRG(obfName, seargeName, deObfName);
                break;
            }
            case CLASS: {
                addClassSRG(obfName, seargeName, deObfName);
                break;
            }
            case FIELD: {
                addFieldSRG(obfName, seargeName, deObfName);
                break;
            }
            case METHOD: {
                addMethodSRG(obfName, seargeName, deObfName);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid TargetType!");
            }
        }
    }
}
