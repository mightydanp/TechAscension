package mightydanp.industrialtech.common.handler;

import mightydanp.industrialtech.common.lib.References;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 8/6/2020.
 */
@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IndustrialTechEventHandler {

    private static final List<Block> blocks = new ArrayList<>();
    private static final List<Item> items = new ArrayList<>();
    private static final List<TileEntityType> tileEntityTypes = new ArrayList<>();
    public static final List<ContainerType> containerTypes = new ArrayList<>();

    //circuitSlot = circuitWrapper.;
    //conveyorSlotItemStack = conveyorWrapper.getStackInSlot(conveyorWrapper.getSlots());
    //emitterSlotItemStack = emitterWrapper.getStackInSlot(emitterWrapper.getSlots());
    //fieldGeneratorSlotItemStack = fieldGeneratorWrapper.getStackInSlot(fieldGeneratorWrapper.getSlots());
    //motorSlotItemStack = motorWrapper.getStackInSlot(motorWrapper.getSlots());
    //pistonSlotItemStack = pistonWrapper.getStackInSlot(pistonWrapper.getSlots());
    //pumpSlotItemStack = pumpWrapper.getStackInSlot(pumpWrapper.getSlots());
    //robotArmSlotItemStack = robotArmWrapper.getStackInSlot(robotArmWrapper.getSlots());
    //sensorSlotItemStack = sensorWrapper.getStackInSlot(sensorWrapper.getSlots());

    public void inti(){

    }
/*
    @SubscribeEvent
    public static void init(PlayerInteractEvent.RightClickBlock event) {
        TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
        if(tileEntity instanceof TileEntityMachineFrame){
            setSlotItem(event, SlotCircuit.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().circuitWrapper);
            setSlotItem(event, SlotConveyor.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().conveyorWrapper);
            setSlotItem(event, SlotEmitter.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().emitterWrapper);
            setSlotItem(event, SlotFieldGenerator.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().fieldGeneratorWrapper);
            setSlotItem(event, SlotMotor.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().motorWrapper);
            setSlotItem(event, SlotPiston.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().pistonWrapper);
            setSlotItem(event, SlotPump.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().pumpWrapper);
            setSlotItem(event, SlotRobotArm.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().robotArmWrapper);
            setSlotItem(event, SlotSensor.getValidItems(), ((TileEntityMachineFrame) tileEntity).machineFrameItemHandler.get().sensorWrapper);
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

    public static void setSlotItem(PlayerInteractEvent.RightClickBlock event, List<Item> itemArray, ItemStackWrapper itemStackWrapper) {
        ItemStack playersHand = event.getPlayer().getHeldItem(event.getHand());
        if (playersHand.isEmpty()) {
            event.setCanceled(false);
        } else {
            ItemStack slotItem = itemStackWrapper.getStackInSlot(itemStackWrapper.getFirstValidSlot(playersHand));
            if (itemArray.contains(playersHand.getItem()) && slotItem.isEmpty()) {
                itemStackWrapper.setStackInSlot(itemStackWrapper.getFirstValidSlot(playersHand), new ItemStack(playersHand.getItem()));
                playersHand.shrink(1);
            } else if (itemArray.contains(playersHand.getItem()) && !slotItem.isEmpty()) {
                event.setCanceled(true);
            }
        }
    }
    */

    @SubscribeEvent
    public void registerBlockEvent(final RegistryEvent.Register<Block> event) {
        for (int i = 0; i < blocks.size(); i++) {
            event.getRegistry().registerAll(blocks.toArray(new Block[i]));
        }
    }

    @SubscribeEvent
    public void registerItemEvent(final RegistryEvent.Register<Item> event) {
        for (int i = 0; i < items.size(); i++) {
            event.getRegistry().registerAll(items.toArray(new Item[i]));
        }
    }


    @SubscribeEvent
    public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
        for (int i = 0; i < tileEntityTypes.size(); i++) {
            event.getRegistry().registerAll(tileEntityTypes.toArray(new TileEntityType[i]));
        }
    }

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
        for (int i = 0; i < containerTypes.size(); i++) {
            event.getRegistry().registerAll(containerTypes.toArray(new ContainerType[i]));
        }
    }

    public static void registerItem(Item itemIn){
        if(!items.contains(itemIn)) {
            items.add(itemIn);
        }
    }

    public static void registerBlock(Block blockIn){
        if(!blocks.contains(blockIn)) {
            blocks.add(blockIn);
        }
    }

    public static void registerTileEntityType(TileEntityType tileEntityTypeIn) {
        if(!tileEntityTypes.contains(tileEntityTypeIn)){
            tileEntityTypes.add(tileEntityTypeIn);
        }
    }
}