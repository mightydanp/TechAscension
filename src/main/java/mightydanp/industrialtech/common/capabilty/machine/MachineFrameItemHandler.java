package mightydanp.industrialtech.common.capabilty.machine;

import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.event.ContentEvent;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import tesseract.Tesseract;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class MachineFrameItemHandler extends MachineItemHandler {

    public ItemStackWrapper circuitWrapper, conveyorWrapper, emitterWrapper, fieldGeneratorWrapper, motorWrapper, pistonWrapper, pumpWrapper, robotArmWrapper, sensorWrapper;

    protected ContentEvent CIRCUIT_CHANGER, CONVEYOR_CHANGER, EMITTER_CHANGER, FIELD_GENERATOR_CHANGER, MOTOR_CHANGER, PISTON_CHANGER, PUMP_CHANGER, ROBOT_CHANGER, SENSOR_CHANGER;

    protected TileEntityMachineFrame tile;

    public MachineFrameItemHandler(TileEntityMachineFrame tile) {
        super(tile);
        this.tile = tile;/*
        circuitWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.CIRCUIT_IN, CIRCUIT_CHANGER);
        conveyorWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.CONVEYOR_IN, CONVEYOR_CHANGER);
        emitterWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.EMITTER_IN, EMITTER_CHANGER);
        fieldGeneratorWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.FIELD_GENERATOR_IN, FIELD_GENERATOR_CHANGER);
        motorWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.Motor_IN, MOTOR_CHANGER);
        pistonWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.PISTON_IN, PISTON_CHANGER);
        pumpWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.PUMP_IN, PUMP_CHANGER);
        robotArmWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.ROBOT_ARM_IN, ROBOT_CHANGER);
        sensorWrapper = createItemStackWrapper(tile, IndustrialTechSlotType.SENSOR_IN, SENSOR_CHANGER);
        */

        if (tile.isServerSide()) {
            Tesseract.ITEM.registerNode(tile.getDimension(), tile.getPos().toLong(), this);
        }
    }

    public IItemHandler getCircuitWrapper() {
        return circuitWrapper;
    }
    public IItemHandler getMotorWrapper() {
        return motorWrapper;
    }

    public static ItemStackWrapper createItemStackWrapper(TileEntityMachine tileEntityIn, SlotType slotTypeIn, ContentEvent contentEventIn){
        return new ItemStackWrapper(tileEntityIn, tileEntityIn.getMachineType().getGui().getSlots(slotTypeIn, tileEntityIn.getMachineTier()).size(), contentEventIn);
    }

    public static ItemStack getSlotItemstack(ItemStackWrapper itemStackWrapperIn, Object object){
        return itemStackWrapperIn.getStackInSlot(itemStackWrapperIn.getFirstValidSlot(object));
    }
}