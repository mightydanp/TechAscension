package mightydanp.industrialtech.api.client;

import mightydanp.industrialtech.api.client.inventory.container.ITToolItemContainerScreen;
import mightydanp.industrialtech.api.client.models.tools.ToolModelLoader;
import mightydanp.industrialtech.api.client.rendering.models.HoleBakedModel;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.handler.KeyBindingHandler;
import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.inventory.container.Containers;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by MightyDanp on 4/7/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void init(FMLClientSetupEvent event){
        ScreenManager.register(Containers.itToolItemContainer, ITToolItemContainerScreen::new);
        IndustrialTech.materialRegistryInstance.initiateClientMaterials();
        KeyBindingHandler.clientInit();
        TileEntities.clientInit();
        ITBlocks.colorBlock();
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
        //ModelLoaderRegistry.registerLoader(new ResourceLocation(Ref.mod_id, "tool"), ToolModelLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        // Find the existing mappings for CamouflageBakedModel - they will have been added automatically because
        //  of our blockstates file for the BlockCamouflage.
        // Replace the mapping with our CamouflageBakedModel.
        // we only have one BlockState variant but I've shown code that loops through all of them, in case you have more than one.

        for (BlockState blockState : ITBlocks.hole_block.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.stateToModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                LOGGER.warn("Did not find the expected vanilla baked model(s) for blockCamouflage in registry");
            } else if (existingModel instanceof HoleBakedModel) {
                LOGGER.warn("Tried to replace CamouflagedBakedModel twice");
            } else {
                HoleBakedModel customModel = new HoleBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }
    }
}
