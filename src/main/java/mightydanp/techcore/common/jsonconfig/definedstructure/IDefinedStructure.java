package mightydanp.techcore.common.jsonconfig.definedstructure;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public interface IDefinedStructure {
    String getName();

    Map<BlockPos, BlockState> getBlockMap();
}
