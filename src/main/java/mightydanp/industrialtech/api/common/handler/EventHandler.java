package mightydanp.industrialtech.api.common.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import mightydanp.industrialtech.api.client.settings.keybindings.KeyBindings;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.lang.reflect.Field;
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

    public static boolean hasBeenPressed = false;

    @SubscribeEvent
    public static void setModelProperties(FMLClientSetupEvent event) {

    }


        @SubscribeEvent
    public static void blockPlacementPreviewEvent(DrawHighlightEvent.HighlightBlock event) {
        Minecraft instance = Minecraft.getInstance();

        //fix by tick https://www.google.com/search?q=forge+get+tick+for+cooldown&oq=forge+get+tick+for+cooldown&aqs=chrome..69i57j33i160.20679j0j7&sourceid=chrome&ie=UTF-8
        if(KeyBindings.activateBlockPreViewer.keyBinding.isDown()){
            if(!hasBeenPressed){
                hasBeenPressed = true;
            }else{
                hasBeenPressed = false;
            }
        }

        if(hasBeenPressed && instance.player != null && instance.player.level != null && !instance.player.getMainHandItem().isEmpty() && instance.player.getMainHandItem().getItem() instanceof BlockItem) {
            ClientPlayerEntity player = instance.player;
            World world = player.level;

            int rayLength = 100;
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

                //player.sendMessage(ITextComponent.nullToEmpty(itemUseContext.getClickedFace().getName() + ", "+ x + ", " + y + ", "+ z), player.getUUID());
            }

            copyBlockPos = blockPos;
        }
    }

}