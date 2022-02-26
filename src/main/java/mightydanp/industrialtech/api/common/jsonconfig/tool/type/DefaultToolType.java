package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;
import net.minecraftforge.common.ToolType;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public enum DefaultToolType implements IToolType {
    NORMAL("", "_tool"),
    ELECTRICAL("electrical_", "_tool");


    private final String prefix;
    private final String suffix;
    private final ToolType toolType;

    public String getPrefixString() {
        return this.prefix;
    }

    public String getSuffixString() {
        return this.suffix;
    }

    private DefaultToolType(String prefix, String Suffix) {
        this.prefix = prefix;
        this.suffix = Suffix;

        toolType = ToolType.get(fixesToName(getFixes()));
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
    public ToolType getToolType() {
        return toolType;
    }

    @Override
    public Pair<String, String> getFixes() {
        return new Pair<>(prefix, suffix);
    }

    public static String fixesToName(Pair<String, String> fixes){
        String prefix = fixes.getFirst().replace("_", "");
        String suffix = fixes.getSecond().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }
}