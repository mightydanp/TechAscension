package mightydanp.industrialtech.api.common.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialtech.api.client.models.tools.ToolModelLoader;
import mightydanp.industrialtech.api.common.items.PickaxeToolItem;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 9/30/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class EventHandler {

    @SubscribeEvent
    public static void handleFiniteWaterSource(BlockEvent.CreateFluidSourceEvent event){
        BlockState state = event.getState();
        FluidState fluidState = state.getFluidState();
        if (fluidState.getType().isSame(Fluids.WATER)){
            event.setResult(Event.Result.DENY);
        } else if (fluidState.getType().isSame(Fluids.LAVA)){
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event) {}

    @SubscribeEvent
    public static void overrideInventoryEvent(GuiOpenEvent event) {}

    public static BlockPos copyBlockPos = new BlockPos(0, 0, 0);

    public static boolean newPosition = false;

    @SubscribeEvent
    public static void blockPlacementPreviewEvent(DrawHighlightEvent.HighlightBlock event) {
        Minecraft instance = Minecraft.getInstance();

        if(instance.player != null && instance.player.level != null && !instance.player.getMainHandItem().isEmpty() && instance.player.getMainHandItem().getItem() instanceof BlockItem) {
            ClientPlayerEntity player = instance.player;
            World world = player.level;

            Double rayLength = new Double(100);
            Vector3d playerRotation = player.getViewVector(0);
            Vector3d rayPath = playerRotation.scale(rayLength);

            //RAY START AND END POINTS
            Vector3d from = player.getEyePosition(0);
            Vector3d to = from.add(rayPath);

            //CREATE THE RAY
            RayTraceContext rayCtx = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
            //CAST THE RAY
            BlockRayTraceResult rayHit = world.clip(rayCtx);

            //CHECK THE RESULTS
            if (event.getMatrix() == null || rayHit.getType() == RayTraceResult.Type.MISS){
                return;
            }

            Vector3d hitLocation = rayHit.getLocation();

            BlockPos blockPos = new BlockPos(hitLocation.x, hitLocation.y, hitLocation.z);

            if(copyBlockPos.getX() != blockPos.getX()){
                newPosition = true;
            }

            if(copyBlockPos.getY() != blockPos.getY()){
                newPosition = true;
            }

            if(copyBlockPos.getZ() != blockPos.getZ()){
                newPosition = true;
            }

            ItemUseContext itemUseContext = new ItemUseContext(player, Hand.MAIN_HAND, rayHit);

            BlockState blockStatePlacement = ((BlockItem)player.getMainHandItem().getItem()).getBlock().getStateForPlacement(new BlockItemUseContext(itemUseContext));

            copyBlockPos = copyBlockPos.equals(new BlockPos(0, 0, 0)) ? blockPos : copyBlockPos;

            if (newPosition && blockStatePlacement != null){
                double x = (blockPos.getX() - (player.getPosition(0).x) - (itemUseContext.getClickedFace() == Direction.WEST ? 1 : 0));
                double y = (-1.62 + (blockPos.getY() - (player.getPosition(0).y)) - (itemUseContext.getClickedFace() == Direction.DOWN ? 1 : 0));
                double z = (blockPos.getZ() - (player.getPosition(0).z) - (itemUseContext.getClickedFace() == Direction.NORTH ? 1 : 0));

                RenderType blockStateRenderType = RenderTypeLookup.getRenderType(blockStatePlacement, false);
                event.getMatrix().pushPose();
                event.getMatrix().scale(1, 1, 1);
                event.getMatrix().translate(x, y, z);

                instance.getBlockRenderer().renderModel(blockStatePlacement, blockPos.relative(itemUseContext.getClickedFace(), 0), world, event.getMatrix(), event.getBuffers().getBuffer(blockStateRenderType), false, new Random(), EmptyModelData.INSTANCE);

                event.getMatrix().popPose();

                player.sendMessage(ITextComponent.nullToEmpty(itemUseContext.getClickedFace().getName() + ", "+ x + ", " + y + ", "+ z), player.getUUID());

            }

            copyBlockPos = blockPos;
        }
    }
}