package mightydanp.industrialtech.common.tileentity;

import mightydanp.industrialtech.api.common.handler.IRegistry;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.lib.References;
import mightydanp.industrialtech.common.lib.TileEntityTypeReferences;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created by MightyDanp on 9/5/2020.
 */
public class ModTileEntity implements IRegistry {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITYS = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, References.ID);

    public static RegistryObject<TileEntityType<TileEntityBasicMachineFrame>> basicMachineFrameTileEntityType = TILE_ENTITYS.register(TileEntityTypeReferences.BASIC_MACHINE_FRAME_TILE_ENTITY_TYPE_NAME, () -> TileEntityType.Builder.create(TileEntityBasicMachineFrame::new, ModBlocks.basic_machine_frame.get()).build(null));

    public static void commonInit() {
    }

    public static void clientInit() {

    }
}
