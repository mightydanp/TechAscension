package mightydanp.techcore.common.tree.blocks.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class TCLogBlockItem extends BlockItem {
    public TCLogBlockItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }
}
