package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;

import java.util.Set;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 4/7/2021.
 */

public class WrenchToolItem extends ITToolItem{
    public WrenchToolItem(Set<Block> effectiveBlocksIn, Properties propertiesIn) {
        super(effectiveBlocksIn, 1, propertiesIn);
    }
}
