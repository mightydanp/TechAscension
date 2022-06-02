package mightydanp.industrialcore.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public enum DefaultToolType implements IToolType {
    PICKAXE("", "_pickaxe"),
    AXE("", "_axe"),
    SHOVEL("", "_shovel"),
    HOE("", "_hoe"),
    SWORD("", "_sword"),
    ELECTRICAL("electrical_", "_tool");


    private final String prefix;
    private final String suffix;

    public String getPrefixString() {
        return this.prefix;
    }

    public String getSuffixString() {
        return this.suffix;
    }

    DefaultToolType(String prefix, String Suffix) {
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
    public String getName() {
        return fixesToName(new Pair<>(getPrefix(), getSuffix()));
    }

    @Override
    public Pair<String, String> getFixes() {
        return new Pair<>(prefix, suffix);
    }

    @Override
    public TagKey<Block> getToolTypeTag() {
        return BlockTags.create(new ResourceLocation("tool/" + fixesToName(getFixes())));
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