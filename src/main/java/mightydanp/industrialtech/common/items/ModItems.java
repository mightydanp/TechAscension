package mightydanp.industrialtech.common.items;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.items.BasicItem;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.items.RockBlockItem;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.libs.ItemRef;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class ModItems {
    public static RegistryObject<Item> cattail_bottom_item;
    public static RegistryObject<Item> cattail_top_item;
    public static RegistryObject<Item> cattail_stalk;
    public static RegistryObject<Item> cattail_root;
    public static RegistryObject<Item> cattail_shoot;
    public static RegistryObject<Item> cattail_leaf;
    public static RegistryObject<Item> plant_fiber;
    public static RegistryObject<Item> twine;
    public static RegistryObject<Item> campfire_block;
    //public static RegistryObject<Item> unfired_clay_pot;
    //public static RegistryObject<Item> unfired_clay_pan;
    //public static RegistryObject<Item> fired_clay_pot;
    //public static RegistryObject<Item> fired_clay_pan;

    public static void init(){
        //cattail_root = RegistryHandler.ITEMS.register(ItemRef.cattail_root_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_shoot = RegistryHandler.ITEMS.register(ItemRef.cattail_shoot_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_stalk = RegistryHandler.ITEMS.register(ItemRef.cattail_stalk_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_leaf = RegistryHandler.ITEMS.register(ItemRef.cattail_leaf_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));

        plant_fiber = RegistryHandler.ITEMS.register(ItemRef.plant_fiber_name, () -> new BasicItem(new Item.Properties().stacksTo(64)));
        twine = RegistryHandler.ITEMS.register(ItemRef.twine_name, () -> new BasicItem(new Item.Properties().stacksTo(64)));

        //unfired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //unfired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //fired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //fired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
    }

    public static void initBlockItems(){
        cattail_bottom_item = RegistryHandler.BLOCK_ITEMS.register(ItemRef.cattail_bottom_name, () -> new BlockItem(ModBlocks.cattail_plant_bottom_block.get(), new Item.Properties().tab(ModItemGroups.item_tab).stacksTo(64)));
        cattail_top_item = RegistryHandler.BLOCK_ITEMS.register(ItemRef.cattail_top_name, () -> new BlockItem(ModBlocks.cattail_plant_top_block.get(), new Item.Properties().tab(ModItemGroups.item_tab).stacksTo(64)));
        campfire_block = RegistryHandler.BLOCK_ITEMS.register(ItemRef.campfire_block_name, () -> new BlockItem(ModBlocks.campfire_override.get(), new Item.Properties().tab(ModItemGroups.item_tab).stacksTo(64)));


    }
}
