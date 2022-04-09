package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.client.settings.keybindings.KeyBindings;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

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

    public static BlockPos copyBlockPos = new BlockPos(0, 0, 0);

    public static boolean newPosition = false;

    public static boolean hasBeenPressed = false;

    @SubscribeEvent
    public static void setModelProperties(FMLClientSetupEvent event) {

    }


    /*
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
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
            LocalPlayer player = instance.player;
            Level world = player.level;

            int rayLength = 100;
            Vec3 playerRotation = player.getViewVector(0);
            Vec3 rayPath = playerRotation.scale(rayLength);

            //RAY START AND END POINTS
            Vec3 from = player.getEyePosition(0);
            Vec3 to = from.add(rayPath);

            //CREATE THE RAY
            ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
            //CAST THE RAY
            BlockHitResult rayHit = world.clip(rayCtx);

            //CHECK THE RESULTS
            if (event.getMatrix() == null || rayHit.getType() == HitResult.Type.MISS){
                return;
            }

            Vec3 hitLocation = rayHit.getLocation();

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

            UseOnContext itemUseContext = new UseOnContext(player, InteractionHand.MAIN_HAND, rayHit);

            BlockState blockStatePlacement = ((BlockItem)player.getMainHandItem().getItem()).getBlock().getStateForPlacement(new BlockPlaceContext(itemUseContext));

            copyBlockPos = copyBlockPos.equals(new BlockPos(0, 0, 0)) ? blockPos : copyBlockPos;

            if (newPosition && blockStatePlacement != null){
                double x = (blockPos.getX() - (player.getPosition(0).x) - (itemUseContext.getClickedFace() == Direction.WEST ? 1 : 0));
                double y = (-1.62 + (blockPos.getY() - (player.getPosition(0).y)) - (itemUseContext.getClickedFace() == Direction.DOWN ? 1 : 0));
                double z = (blockPos.getZ() - (player.getPosition(0).z) - (itemUseContext.getClickedFace() == Direction.NORTH ? 1 : 0));

                RenderType blockStateRenderType = ItemBlockRenderTypes.getRenderType(blockStatePlacement, false);
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

     */

}