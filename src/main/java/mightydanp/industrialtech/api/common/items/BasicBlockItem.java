package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(Block blockIn) {
        super(blockIn, new Properties().group(ModItemGroups.item_tab));
    }
}
