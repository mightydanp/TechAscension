package mightydanp.industrialcore.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public interface IToolType {
    String getPrefix();
    String getSuffix();
    String getName();
    Pair<String, String> getFixes();
    default TagKey<Block> getToolTypeTag(){
        return BlockTags.create(new ResourceLocation("forge", "tool/" + getName()));
    };
}
