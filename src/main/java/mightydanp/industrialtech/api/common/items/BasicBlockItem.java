package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(Block blockIn) {
        super(blockIn, new Properties().tab(ModItemGroups.item_tab));
    }
}
