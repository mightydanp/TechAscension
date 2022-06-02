package mightydanp.industrialcore.common.tileentities;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class TileEntities {
    public static Map<String, Pair<BlockEntityType.BlockEntitySupplier<BlockEntity>, RegistryObject<? extends Block>>> tileEntities = new HashMap<>();


    public static void init(){
        tileEntities.forEach((s, pair) -> register(s, pair.getFirst(), pair.getSecond()));
    }

    public static void clientInit(){
        //ClientRegistry.bindTileEntityRenderer(hole_tile_entity.get(), HoleTileEntityRenderer::new);
    }

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, RegistryObject<? extends Block> block) {
        return RegistryHandler.Block_Entities.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
    }

}