package mightydanp.industrialtech.client;

import mightydanp.industrialtech.api.common.ISidedReference;

import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.tileentities.ModTileEntities;
import mightydanp.industrialtech.common.tools.ModTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class ModClientEvent implements ISidedReference {
    private static Minecraft mc;

    public static void init(FMLClientSetupEvent event) {
        ModBlocks.setRenderType();
        ModMaterials.clientInit();
        ModTools.clientInit();
        ModTileEntities.clientInit();
    }

    @Override
    public void setup(IEventBus mod, IEventBus forge) {

    }

}
