package mightydanp.techcore.client;

import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.client.settings.keybindings.KeyBindings;
import mightydanp.techcore.common.blocks.ITBlocks;
import mightydanp.techcore.common.handler.KeyBindingHandler;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.tileentities.TileEntities;
import mightydanp.techcore.server.client.rendering.models.CampFireBakedModel;
import mightydanp.techascension.common.blocks.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
                TechAscension.LOGGER.warn("Did not find the expected vanilla baked model(s) for HoleModel in registry");
            } else if (existingModel instanceof CampFireBakedModel) {
                TechAscension.LOGGER.warn("Tried to replace HoleModel twice");
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
