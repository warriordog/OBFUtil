package net.acomputerdog.OBFUtil.table;

import net.acomputerdog.OBFUtil.util.TargetType;

public interface OBFTableSRG extends OBFTable {
    public void addPackageSRG(String obfName, String seargeName, String deObfName);

    public void addClassSRG(String obfName, String seargeName, String deObfName);

    public void addFieldSRG(String obfName, String seargeName, String deObfName);

    public void addMethodSRG(String obfName, String seargeName, String deObfName);

    public void addTypeSRG(String obfName, String seargeName, String deObfName, TargetType type);

    public String getObfFromSRGPackage(String searge);

    public String getObfFromSRGClass(String searge);

    public String getObfFromSRGField(String searge);

    public String getObfFromSRGMethod(String searge);

    public String getObfFromSRGType(String searge, TargetType type);

    public String getDeObfFromSRGPackage(String searge);

    public String getDeObfFromSRGClass(String searge);

    public String getDeObfFromSRGField(String searge);

    public String getDeObfFromSRGMethod(String searge);

    public String getDeObfFromSRGType(String searge, TargetType type);

    public String getSRGFromObfPackage(String obf);

    public String getSRGFromObfClass(String obf);

    public String getSRGFromObfField(String obf);

    public String getSRGFromObfMethod(String obf);

    public String getSRGFromObfType(String obf, TargetType type);

    public String getSRGFromDeObfPackage(String deobf);

    public String getSRGFromDeObfClass(String deobf);

    public String getSRGFromDeObfField(String deobf);

    public String getSRGFromDeObfMethod(String deobf);

    public String getSRGFromDeObfType(String deobf, TargetType type);

    public boolean hasPackageSRG(String srgName);

    public boolean hasClassSRG(String srgName);

    public boolean hasMethodSRG(String srgName);

    public boolean hasFieldSRG(String srgName);

    public boolean hasTypeSRG(String srgName, TargetType type);
}
