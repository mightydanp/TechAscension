package mightydanp.industrialtech.api.common.material.tool;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public enum EnumToolFlags {
    NORMAL("", "_tool"),
    ELECTRICAL("electrical_", "_tool"),
    NULL("", "");

    private final String prefix;
    private final String suffix;

    public String getPrefixString() {
        return this.prefix;
    }

    public String getSuffixString() {
        return this.suffix;
    }

    private EnumToolFlags(String prefix, String Suffix) {
        this.prefix = prefix;
        this.suffix = Suffix;
    }
}