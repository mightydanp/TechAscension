package mightydanp.techascension.client;

import mightydanp.techascension.client.settings.KeyBindings.ModKeyBindings;
import mightydanp.techascension.common.items.ModItems;
import mightydanp.techcore.client.rendering.models.HoleBakedModel;
import mightydanp.techcore.common.ISidedReference;

import mightydanp.techcore.common.blocks.TCBlocks;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techascension.common.blocks.ModBlocks;
import mightydanp.techascension.common.materials.ModMaterials;
import mightydanp.techascension.common.tileentities.ModBlockEntity;
import mightydanp.techascension.common.trees.ModTrees;
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
        ModItems.initClient();
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
        for (BlockState blockState : TCBlocks.hole_block.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
            BakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                TechAscension.LOGGER.warn("Did not find the expected vanilla baked model(s) for HoleModel in registry");
            } else if (existingModel instanceof HoleBakedModel) {
                TechAscension.LOGGER.warn("Tried to replace HoleModel twice");
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
