package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public enum DefaultToolType implements IToolType {
    NORMAL("", "_tool"),
    ELECTRICAL("electrical_", "_tool");

    private final String prefix;
    private final String suffix;

    public String getPrefixString() {
        return this.prefix;
    }

    public String getSuffixString() {
        return this.suffix;
    }

    private DefaultToolType(String prefix, String Suffix) {
        this.prefix = prefix;
        this.suffix = Suffix;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public Pair<String, String> getFixes() {
        return new Pair<>(prefix, suffix);
    }
}