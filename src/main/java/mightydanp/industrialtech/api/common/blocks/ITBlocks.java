package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.ITBlockRef;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class ITBlocks {
    public static RegistryObject<Block> hole_block;


    public static void init() {
        hole_block = RegistryHandler.BLOCKS.register(ITBlockRef.hole_name, HoleBlock::new);
    }

    public static void setRenderType(){
    }

    public static void colorBlock(){
        setupAHoleBlockColor(hole_block);
    }

    public static void setupAHoleBlockColor(RegistryObject<Block> block){
        //RenderTypeLookup.setRenderLayer(block.get(), RenderType.cutout());
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
            if (tintIndex == 0) {
                if(world != null && pos!= null && block.get() instanceof HoleBlock){
                    HoleTileEntity tileEntity = (HoleTileEntity)world.getBlockEntity(pos);
                    if(tileEntity != null){
                        return tileEntity.holeColor;
                    }
                }
                return 0xFFFFFFFF;
            }
            if (tintIndex == 1) {
                if(world != null && pos!= null && block.get() instanceof HoleBlock){
                    HoleTileEntity tileEntity = (HoleTileEntity)world.getBlockEntity(pos);
                    if(tileEntity != null){
                        return tileEntity.resinColor;
                    }
                }
                return 0xFFFFFFFF;
            }
            return 0xFFFFFFFF;
        }, block.get());
    }
}