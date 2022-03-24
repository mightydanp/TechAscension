package mightydanp.industrialtech.api.common.tileentities;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.client.rendering.tileentities.HoleTileEntityRenderer;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.client.rendering.tileentities.CampfireTileEntityRenderer;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class TileEntities {
    public static Map<String, Pair<Supplier<TileEntity>, RegistryObject<? extends Block>>> tileEntities = new HashMap<>();


    public static void init(){
        tileEntities.forEach((s, pair) -> register(s, pair.getFirst(), pair.getSecond()));
    }

    public static void clientInit(){
        //ClientRegistry.bindTileEntityRenderer(hole_tile_entity.get(), HoleTileEntityRenderer::new);
    }

    public static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return RegistryHandler.TILES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }

}