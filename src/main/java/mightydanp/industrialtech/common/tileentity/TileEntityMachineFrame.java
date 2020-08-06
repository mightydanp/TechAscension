package mightydanp.industrialtech.common.tileentity;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.machine.event.ContentEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class TileEntityMachineFrame extends TileEntityMachine {
    public ItemStackWrapper circuitInputWrapper;
    public ItemStackWrapper conveyorInputWrapper;
    public ItemStackWrapper emitterInputWrapper;
    public ItemStackWrapper fieldGeneratorInputWrapper;
    public ItemStackWrapper motorInputWrapper;
    public ItemStackWrapper pistonInputWrapper;
    public ItemStackWrapper pumpInputWrapper;
    public ItemStackWrapper robotArmInputWrapper;
    public ItemStackWrapper sensorInputWrapper;

    protected ContentEvent CIRCUIT_INPUT_CHANGER;
    protected ContentEvent CONVEYOR_INPUT_CHANGER;
    protected ContentEvent EMITTER_INPUT_CHANGER;
    protected ContentEvent FIELD_GENERATOR_INPUT_CHANGER;
    protected ContentEvent MOTOR_INPUT_CHANGER;
    protected ContentEvent PISTON_INPUT_CHANGER;
    protected ContentEvent PUMP_INPUT_CHANGER;
    protected ContentEvent ROBOT_INPUT_CHANGER;
    protected ContentEvent SENSOR_INPUT_CHANGER;

    public TileEntityMachineFrame(Machine<?> type){
        super(type);
    }
    public void onServerUpdate(){
        super.onServerUpdate();
    }

    public ItemStack circuitSlotItemStack;
    public ItemStack conveyorSlotItemStack;
    public ItemStack emitterSlotItemStack;
    public ItemStack fieldGeneratorSlotItemStack;
    public ItemStack motorSlotItemStack;
    public ItemStack pistonSlotItemStack;
    public ItemStack pumpSlotItemStack;
    public ItemStack robotArmSlotItemStack;
    public ItemStack sensorSlotItemStack;


    @Override
    public void onLoad(){
        super.onLoad();
        circuitInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.CIRCUIT_IN, this.getMachineTier()).size(), CIRCUIT_INPUT_CHANGER);
        conveyorInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.CONVEYOR_IN, this.getMachineTier()).size(), CONVEYOR_INPUT_CHANGER);
        emitterInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.EMITTER_IN, this.getMachineTier()).size(), EMITTER_INPUT_CHANGER);
        fieldGeneratorInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.FIELD_GENERATOR_IN, this.getMachineTier()).size(), FIELD_GENERATOR_INPUT_CHANGER);
        motorInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.Motor_IN, this.getMachineTier()).size(), MOTOR_INPUT_CHANGER);
        pistonInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.PISTON_IN, this.getMachineTier()).size(), PISTON_INPUT_CHANGER);
        pumpInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.PUMP_IN, this.getMachineTier()).size(), PUMP_INPUT_CHANGER);
        robotArmInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.ROBOT_ARM_IN, this.getMachineTier()).size(), ROBOT_INPUT_CHANGER);
        sensorInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.SENSOR_IN, this.getMachineTier()).size(), SENSOR_INPUT_CHANGER);

        circuitSlotItemStack = circuitInputWrapper.getStackInSlot(circuitInputWrapper.getSlots()-1);
        conveyorSlotItemStack = conveyorInputWrapper.getStackInSlot(conveyorInputWrapper.getSlots()-1);
        emitterSlotItemStack = emitterInputWrapper.getStackInSlot(emitterInputWrapper.getSlots()-1);
        fieldGeneratorSlotItemStack = fieldGeneratorInputWrapper.getStackInSlot(fieldGeneratorInputWrapper.getSlots()-1);
        motorSlotItemStack = motorInputWrapper.getStackInSlot(motorInputWrapper.getSlots()-1);
        pistonSlotItemStack = pistonInputWrapper.getStackInSlot(pistonInputWrapper.getSlots()-1);
        pumpSlotItemStack = pumpInputWrapper.getStackInSlot(pumpInputWrapper.getSlots()-1);
        robotArmSlotItemStack = robotArmInputWrapper.getStackInSlot(robotArmInputWrapper.getSlots()-1);
        sensorSlotItemStack = sensorInputWrapper.getStackInSlot(sensorInputWrapper.getSlots()-1);
    }

    @Override
    public void onRemove(){
        super.onRemove();
    }
}
