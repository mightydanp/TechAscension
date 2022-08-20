package mightydanp.techcore.common.items;

import mightydanp.techcore.common.blocks.TCBlocks;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techascension.common.libs.ItemRef;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class TCItems {
    public static RegistryObject<Item> hole_block;

    public static void init() {
    }

    public static void initBlockItems() {
        hole_block = RegistryHandler.BLOCK_ITEMS.register(ItemRef.hole_block_name, () -> new BlockItem(TCBlocks.hole_block.get(), new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(1)));
    }
}