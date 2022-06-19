package mightydanp.industrialtech.client;

import mightydanp.industrialcore.client.rendering.models.HoleBakedModel;
import mightydanp.industrialcore.common.ISidedReference;

import mightydanp.industrialcore.common.blocks.ITBlocks;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.client.settings.KeyBindings.ModKeyBindings;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import mightydanp.industrialtech.common.tool.ModTools;
import mightydanp.industrialtech.common.trees.ModTrees;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import static net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;

@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvent implements ISidedReference {
    private static Minecraft mc;

    public static void init(FMLClientSetupEvent event) {
        ModBlocks.setRenderType();
        ModMaterials.clientInit();
        ModTrees.clientInit();
        ModBlockEntity.clientInit();
        ModKeyBindings.init();
    }

    @Override
    public void setup(IEventBus mod, IEventBus forge) {

    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        for (BlockState blockState : ITBlocks.hole_block.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
            BakedModel existingModel = event.getModelRegistry().get(variantMRL);
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
        if (event.getAtlas().location() == LOCATION_BLOCKS) {
            event.addSprite(new ResourceLocation("block/campfire_log"));
        }
    }

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.campfire_override.get(), RenderType.cutout());
    }

}
