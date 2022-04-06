package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public interface IToolType {
    String getPrefix();
    String getSuffix();
    String getName();
    Pair<String, String> getFixes();
    Tag.Named<Block> getToolTypeTag();
}
