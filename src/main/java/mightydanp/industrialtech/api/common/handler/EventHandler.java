package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.client.gui.screen.CustomPlayerInventoryScreen;
import mightydanp.industrialtech.api.client.gui.widget.IngameModListButton;
import mightydanp.industrialtech.api.common.inventory.container.CustomPlayerInventoryContainer;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by MightyDanp on 9/30/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void handleFiniteWaterSource(BlockEvent.CreateFluidSourceEvent event){
        BlockState state = event.getState();
        FluidState fluidState = state.getFluidState();
        if (fluidState.getFluid().isEquivalentTo(Fluids.WATER)){
            event.setResult(Event.Result.DENY);
        } else if (fluidState.getFluid().isEquivalentTo(Fluids.LAVA)){
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event) {
        final Screen gui = event.getGui();
        PlayerEntity player = Minecraft.getInstance().player;
        if (gui instanceof InventoryScreen) {
            int maxY = 0;
            for (final Widget button : event.getWidgetList()) {
                maxY = Math.max(button.y, maxY);
            }
            event.addWidget(new IngameModListButton(gui, maxY - 80, 88));
        }
        
    }

    @SubscribeEvent
    public static void overrideInventoryEvent(GuiOpenEvent event) {
        final Screen gui = event.getGui();
        //if (gui instanceof InventoryScreen) {
        //    ClientPlayerEntity player = Minecraft.getInstance().player;
        //    if(player != null) {
        //        event.setGui(new CustomPlayerInventoryScreen(new CustomPlayerInventoryContainer(0, player.inventory,player), player.inventory));
        //    }
        // }
    }


}
