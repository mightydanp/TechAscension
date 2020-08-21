package mightydanp.industrialtech.common.handler;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.data.IndustrialTechData;
import mightydanp.industrialtech.common.data.Machines;
import mightydanp.industrialtech.common.data.Materials;
import mightydanp.industrialtech.common.lib.References;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.material.Material;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static muramasa.antimatter.Data.FRAME;

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
    public static void init(PlayerInteractEvent.RightClickBlock event) {
        TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
        if(tileEntity instanceof TileEntityMachineFrame){
            setSlotItem(event, SlotCircuit.getValidItems(), ((TileEntityMachineFrame) tileEntity).circuitInputWrapper);
            setSlotItem(event, SlotConveyor.getValidItems(), ((TileEntityMachineFrame) tileEntity).conveyorInputWrapper);
            setSlotItem(event, SlotEmitter.getValidItems(), ((TileEntityMachineFrame) tileEntity).emitterInputWrapper);
            setSlotItem(event, SlotFieldGenerator.getValidItems(), ((TileEntityMachineFrame) tileEntity).fieldGeneratorInputWrapper);
            setSlotItem(event, SlotMotor.getValidItems(), ((TileEntityMachineFrame) tileEntity).motorInputWrapper);
            setSlotItem(event, SlotPiston.getValidItems(), ((TileEntityMachineFrame) tileEntity).pistonInputWrapper);
            setSlotItem(event, SlotPump.getValidItems(), ((TileEntityMachineFrame) tileEntity).pumpInputWrapper);
            setSlotItem(event, SlotRobotArm.getValidItems(), ((TileEntityMachineFrame) tileEntity).robotArmInputWrapper);
            setSlotItem(event, SlotSensor.getValidItems(), ((TileEntityMachineFrame) tileEntity).sensorInputWrapper);
        }
        rightClickFrame(event, IndustrialTechData.cable_tin_tiny, Materials.Steel, Machines.machineFrameLV);
        rightClickFrame(event, IndustrialTechData.cable_copper_tiny, Materials.Aluminium, Machines.machineFrameMV);
    }

    public static void rightClickFrame(PlayerInteractEvent event, Block cableIn, Material materialIn, Block blockIn) {
        Block rightClickedBlock = event.getWorld().getBlockState(event.getPos()).getBlock();
        ItemStack playersHand = event.getPlayer().getHeldItem(event.getHand());
        if (playersHand.isEmpty()) {
            event.setCanceled(false);
        }else{
            Block frame = FRAME.get().get(materialIn).asBlock();

            if(rightClickedBlock == frame && cableIn != null && playersHand.getItem() == new ItemStack(cableIn).getItem() && blockIn != null){
                event.getWorld().setBlockState(event.getPos(), blockIn.getDefaultState());
                event.setCanceled(true);
                playersHand.shrink(1);
            }
        }
    }

    public static void setSlotItem(PlayerInteractEvent.RightClickBlock event, Set<Item> itemArray, ItemStackWrapper itemStackWrapper) {
            ItemStack playersHand = event.getPlayer().getHeldItem(event.getHand());
            if (playersHand.isEmpty()) {
                event.setCanceled(false);
            } else {
                System.out.println(event.getWorld().getBlockState(event.getPos()));
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