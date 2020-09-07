package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.api.common.handler.IRegistry;
import mightydanp.industrialtech.common.lib.BlockReferences;
import mightydanp.industrialtech.common.lib.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created by MightyDanp on 9/5/2020.
 */
public class ModBlocks implements IRegistry {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, References.ID);

    public static RegistryObject<Block> basic_machine_frame = BLOCKS.register(BlockReferences.MACHINE_FRAME_NAME, () -> new BlockBasicMachineFrame(Block.Properties.create(Material.IRON)));
    public static BlockItem basic_machine_frame_item;

    public static void commonInit() {
    }

    public static void clientInit() {

    }
}
