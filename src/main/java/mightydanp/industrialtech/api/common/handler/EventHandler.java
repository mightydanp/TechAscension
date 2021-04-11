package mightydanp.industrialtech.api.common.handler;

import javafx.util.Pair;
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

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().getItem() == Items.STICK) {
            ItemStack itemStack = new ItemStack(ToolHandler.pickaxe);
            ((PickaxeToolItem)itemStack.getItem()).setHeadColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setBindingColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setHandleColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setAttackDamage(itemStack, 12F);
            ((PickaxeToolItem)itemStack.getItem()).setEfficiency(itemStack, 12F);
            ((PickaxeToolItem)itemStack.getItem()).setAttackSpeed(itemStack, 12F);
            List<Pair<ITToolType, Integer>> itToolTypesList = new ArrayList<Pair<ITToolType, Integer>>(){{
                add(new Pair<>(ITToolType.PICKAXE, 0));
            }};

            ((PickaxeToolItem)itemStack.getItem()).setHarvestLevel(itemStack, itToolTypesList);

            event.getWorld().addFreshEntity(new ItemEntity(event.getWorld(), event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), itemStack));
        }


        if(event.getItemStack().getItem() == Items.IRON_INGOT){
            ItemStack itemStack = new ItemStack(ToolHandler.pickaxe);
            ((PickaxeToolItem)itemStack.getItem()).setHeadColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setBindingColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setHandleColor(itemStack, 0);
            ((PickaxeToolItem)itemStack.getItem()).setAttackDamage(itemStack,6F);
            ((PickaxeToolItem)itemStack.getItem()).setEfficiency(itemStack,6F);
            ((PickaxeToolItem)itemStack.getItem()).setAttackSpeed(itemStack,6F);
            List<Pair<ITToolType, Integer>> itToolTypesList = new ArrayList<Pair<ITToolType, Integer>>(){{
                add(new Pair<>(ITToolType.PICKAXE, 0));
            }};
            ((PickaxeToolItem)itemStack.getItem()).setHarvestLevel(itemStack, itToolTypesList);

            event.getWorld().addFreshEntity(new ItemEntity(event.getWorld(), event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), itemStack));
        }
    }


}
