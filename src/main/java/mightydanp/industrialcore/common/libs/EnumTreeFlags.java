package mightydanp.industrialcore.common.libs;

/**
 * Created by MightyDanp on 7/23/2021.
 */
public enum EnumTreeFlags {
    SAPLING("", "_sapling"),
    LEAVE("", "_leave"),
    LOG("", "_log"),
    PLANK("", "_plank"),
    RESIN("", "_resin"),
    NULL("", "");

    private final String prefix;
    private final String suffix;

    public String getPrefixString() {
        return this.prefix;
    }

    public String getSuffixString() {
        return this.suffix;
    }

    private EnumTreeFlags(String prefix, String Suffix) {
        this.prefix = prefix;
        this.suffix = Suffix;
    }
}
