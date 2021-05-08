package mightydanp.industrialtech.api.common.handler;

import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialtech.api.client.models.tools.ToolModelLoader;
import mightydanp.industrialtech.api.common.items.PickaxeToolItem;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

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


}
