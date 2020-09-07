package mightydanp.industrialtech.common.items;

import mightydanp.industrialtech.api.common.handler.IRegistry;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.lib.BlockReferences;
import mightydanp.industrialtech.common.lib.References;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created by MightyDanp on 9/5/2020.
 */
public class ModItems implements IRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, References.ID);

    public static final RegistryObject<Item> basic_machine_frame_item = ITEMS.register(BlockReferences.MACHINE_FRAME_NAME, () -> new BlockItem(ModBlocks.basic_machine_frame.get(), new Item.Properties().maxStackSize(1).group(ItemGroup.DECORATIONS)));

    public static void commonInit() {

    }

    public static void clientInit() {

    }
}
