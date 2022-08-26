package mightydanp.techcore.common.tree.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class TCPlankBlock extends BlockItem {
    public TCPlankBlock(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }
}
