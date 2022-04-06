package mightydanp.industrialtech.api.client;

import mightydanp.industrialtech.api.client.inventory.container.ITToolItemContainerScreen;
import mightydanp.industrialtech.api.client.rendering.models.HoleBakedModel;
import mightydanp.industrialtech.api.client.settings.keybindings.KeyBindings;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.handler.KeyBindingHandler;
import mightydanp.industrialtech.api.common.inventory.container.Containers;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.client.rendering.models.CampFireBakedModel;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void init(FMLClientSetupEvent event){
        KeyBindings.init();
        //ScreenManager.register(Containers.itToolItemContainer, ITToolItemContainerScreen::new);////////////////////////////////////
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
        for (BlockState blockState : ModBlocks.campfire_override.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
            BakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                IndustrialTech.LOGGER.warn("Did not find the expected vanilla baked model(s) for HoleModel in registry");
            } else if (existingModel instanceof CampFireBakedModel) {
                IndustrialTech.LOGGER.warn("Tried to replace HoleModel twice");
            } else {
                CampFireBakedModel customModel = new CampFireBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }
    }

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        // Tell the renderer to render the camouflage block and Altimeter as a solid texture
        //RenderTypeLookup.setRenderLayer(StartupCommon.blockCamouflage, RenderType.getSolid());
    }
}
