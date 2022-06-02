package mightydanp.industrialcore.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(Block blockIn) {
        super(blockIn, new Properties().tab(ModItemGroups.item_tab));
    }
}
