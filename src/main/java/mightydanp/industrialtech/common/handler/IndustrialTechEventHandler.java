package mightydanp.industrialtech.common.handler;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.lib.References;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Set;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;

/**
 * Created by MightyDanp on 8/6/2020.
 */
@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IndustrialTechEventHandler {

    //circuitSlot = circuitInputWrapper.;
    //conveyorSlotItemStack = conveyorInputWrapper.getStackInSlot(conveyorInputWrapper.getSlots());
    //emitterSlotItemStack = emitterInputWrapper.getStackInSlot(emitterInputWrapper.getSlots());
    //fieldGeneratorSlotItemStack = fieldGeneratorInputWrapper.getStackInSlot(fieldGeneratorInputWrapper.getSlots());
    //motorSlotItemStack = motorInputWrapper.getStackInSlot(motorInputWrapper.getSlots());
    //pistonSlotItemStack = pistonInputWrapper.getStackInSlot(pistonInputWrapper.getSlots());
    //pumpSlotItemStack = pumpInputWrapper.getStackInSlot(pumpInputWrapper.getSlots());
    //robotArmSlotItemStack = robotArmInputWrapper.getStackInSlot(robotArmInputWrapper.getSlots());
    //sensorSlotItemStack = sensorInputWrapper.getStackInSlot(sensorInputWrapper.getSlots());

    public void inti(){

    }

    @SubscribeEvent
    public static void drawLast(PlayerInteractEvent.RightClickBlock event) {
        TileEntityMachineFrame tileEntity = (TileEntityMachineFrame)event.getWorld().getTileEntity(event.getPos());
        if(tileEntity != null){
            setSlotItem(event, SlotCircuit.getValidItems(), tileEntity.circuitInputWrapper);
            setSlotItem(event, SlotConveyor.getValidItems(), tileEntity.conveyorInputWrapper);
            setSlotItem(event, SlotEmitter.getValidItems(), tileEntity.emitterInputWrapper);
            setSlotItem(event, SlotFieldGenerator.getValidItems(), tileEntity.fieldGeneratorInputWrapper);
            setSlotItem(event, SlotMotor.getValidItems(), tileEntity.motorInputWrapper);
            setSlotItem(event, SlotPiston.getValidItems(), tileEntity.pistonInputWrapper);
            setSlotItem(event, SlotPump.getValidItems(), tileEntity.pumpInputWrapper);
            setSlotItem(event, SlotRobotArm.getValidItems(), tileEntity.robotArmInputWrapper);
            setSlotItem(event, SlotSensor.getValidItems(), tileEntity.sensorInputWrapper);
        }
    }

    public static void setSlotItem(PlayerInteractEvent.RightClickBlock event, Set<Item> itemArray, ItemStackWrapper itemStackWrapper) {
            ItemStack playersHand = event.getPlayer().getHeldItem(event.getHand());
            if (playersHand.isEmpty()) {
                event.setCanceled(false);
            } else {
                if (itemArray.contains(playersHand.getItem())){
                    ItemStack slotItem = itemStackWrapper.getStackInSlot(itemStackWrapper.getFirstValidSlot(playersHand));
                    if(slotItem.isEmpty()) {
                        itemStackWrapper.setStackInSlot(itemStackWrapper.getFirstValidSlot(playersHand), new ItemStack(playersHand.getItem()));
                        playersHand.shrink(1);
                    }else{
                        event.setCanceled(true);
                        /*
                        if(event.getPlayer().inventory.hasItemStack(ItemStack.EMPTY)){
                            event.getPlayer().inventory.addItemStackToInventory(slotItem);
                            slotItem.shrink(1);
                            itemStackWrapper.setStackInSlot(itemStackWrapper.getFirstValidSlot(playersHand), playersHand);
                            playersHand.shrink(1);
                        }else{
                            event.setCanceled(true);
                        }
                        */
                    }
                }
            }
    }
}