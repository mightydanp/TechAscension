package mightydanp.industrialtech.api.common.libs;

/**
 * Created by MightyDanp on 7/23/2021.
 */
public enum EnumTreeFlags {
    SAPLING("", "_sapling"),
    LEAVE("", "_leave"),
    NORMAL_LOG("", "_log"),
    LOG_SIDE_HOLE("", "_log_side_hole"),
    PLANK("", "_plank"),
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
