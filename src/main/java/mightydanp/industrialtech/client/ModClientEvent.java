package mightydanp.industrialtech.client;

import mightydanp.industrialtech.api.client.rendering.models.HoleBakedModel;
import mightydanp.industrialtech.api.common.ISidedReference;

import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.client.settings.KeyBindings.ModKeyBindings;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.tileentities.ModTileEntities;
import mightydanp.industrialtech.common.tool.ModTools;
import mightydanp.industrialtech.common.trees.ModTrees;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.client.renderer.texture.AtlasTexture.LOCATION_BLOCKS;

/**
 * Created by MightyDanp on 9/26/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvent implements ISidedReference {
    private static Minecraft mc;

    public static void init(FMLClientSetupEvent event) {
        ModBlocks.setRenderType();
        ModMaterials.clientInit();
        ModTools.clientInit();
        ModTrees.clientInit();
        ModTileEntities.clientInit();
        ModKeyBindings.init();
    }

    @Override
    public void setup(IEventBus mod, IEventBus forge) {

    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        for (BlockState blockState : ITBlocks.hole_block.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.stateToModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                IndustrialTech.LOGGER.warn("Did not find the expected vanilla baked model(s) for HoleModel in registry");
            } else if (existingModel instanceof HoleBakedModel) {
                IndustrialTech.LOGGER.warn("Tried to replace HoleModel twice");
            } else {
                HoleBakedModel customModel = new HoleBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }
    }

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().location() == LOCATION_BLOCKS) {
            event.addSprite(new ResourceLocation("block/campfire_log"));
        }
    }

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.campfire_override.get(), RenderType.cutout());
    }

}
