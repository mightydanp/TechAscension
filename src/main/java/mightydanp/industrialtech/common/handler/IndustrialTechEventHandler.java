package mightydanp.industrialtech.common.handler;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.data.Materials;
import mightydanp.industrialtech.common.lib.References;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static mightydanp.industrialtech.common.data.IndustrialTechData.CABLE_TIN;
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

    @SubscribeEvent
    public static void rightClickFrame(PlayerInteractEvent event) {
        Block rightClickedBlock = event.getWorld().getBlockState(event.getPos()).getBlock();
        ItemStack playersHand = event.getPlayer().getHeldItem(event.getHand());
        Machine<?>  type =  Machine.get("machine_frame");
        //BlockMachineFrame machine = Machines.MACHINE_FRAME;
        //DirectionProperty TYPE = DirectionProperty.create("direction", event.getFace());
       ///////// BlockState blockStateLV = new BlockMachine(type, Tier.LV).getDefaultState();
        if (playersHand.isEmpty()) {
            event.setCanceled(false);
        }else{
            Block frame = FRAME.get().get(Materials.Steel).asBlock();
            Set<Block> cableSet = CABLE_TIN.getBlocks();
            Block[] arr = cableSet.stream().toArray(Block[] ::new);
            if(rightClickedBlock == frame && playersHand.isItemEqual(new ItemStack(arr[0]))){
                //event.getWorld().setBlockState(event.getPos(), blockStateLV);
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