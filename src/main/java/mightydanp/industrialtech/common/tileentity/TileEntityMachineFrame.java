package mightydanp.industrialtech.common.tileentity;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;
import mightydanp.industrialtech.client.gui.slot.SlotCircuit;
import mightydanp.industrialtech.common.data.IndustrialTechData;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.event.ContentEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.item.ItemStack;

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

    public ItemStack circuitSlotItemStack;
    public ItemStack conveyorSlotItemStack;
    public ItemStack emitterSlotItemStack;
    public ItemStack fieldGeneratorInputSlotItemStack;
    public ItemStack motorSlotItemStack;
    public ItemStack pistonSlotItemStack;
    public ItemStack pumpSlotItemStack;
    public ItemStack robotArmSlotItemStack;
    public ItemStack sensorSlotItemStack;


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


    @Override
    public void onLoad(){
        super.onLoad();
        circuitInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.CIRCUIT_IN, CIRCUIT_INPUT_CHANGER);
        conveyorInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.CONVEYOR_IN, CONVEYOR_INPUT_CHANGER);
        emitterInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.EMITTER_IN, EMITTER_INPUT_CHANGER);
        fieldGeneratorInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.FIELD_GENERATOR_IN, FIELD_GENERATOR_INPUT_CHANGER);
        motorInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.Motor_IN, MOTOR_INPUT_CHANGER);
        pistonInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.PISTON_IN, PISTON_INPUT_CHANGER);
        pumpInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.PUMP_IN, PUMP_INPUT_CHANGER);
        robotArmInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.ROBOT_ARM_IN, ROBOT_INPUT_CHANGER);
        sensorInputWrapper = createItemStackWrapper(this, IndustrialTechSlotType.SENSOR_IN, SENSOR_INPUT_CHANGER);

        circuitSlotItemStack = getSlotItemstack(circuitInputWrapper, IndustrialTechData.CircuitBasic);
        conveyorSlotItemStack = getSlotItemstack(conveyorInputWrapper, IndustrialTechData.ConveyorLV);
        emitterSlotItemStack = getSlotItemstack(emitterInputWrapper, IndustrialTechData.EmitterLV);
        fieldGeneratorInputSlotItemStack = getSlotItemstack(fieldGeneratorInputWrapper, IndustrialTechData.FieldGenLV);
        motorSlotItemStack = getSlotItemstack(motorInputWrapper, IndustrialTechData.MotorLV);
        pistonSlotItemStack = getSlotItemstack(pistonInputWrapper, IndustrialTechData.PistonLV);
        pumpSlotItemStack = getSlotItemstack(pumpInputWrapper, IndustrialTechData.PumpLV);
        robotArmSlotItemStack = getSlotItemstack(robotArmInputWrapper, IndustrialTechData.RobotArmLV);
        sensorSlotItemStack = getSlotItemstack(sensorInputWrapper, IndustrialTechData.SensorLV);
    }

    @Override
    public void onRemove(){
        super.onRemove();
    }

    public static ItemStackWrapper createItemStackWrapper(TileEntityMachine tileEntityIn, SlotType slotTypeIn, ContentEvent contentEventIn){
       return new ItemStackWrapper(tileEntityIn, tileEntityIn.getMachineType().getGui().getSlots(slotTypeIn, tileEntityIn.getMachineTier()).size(), contentEventIn);
    }

    public static ItemStack getSlotItemstack(ItemStackWrapper itemStackWrapperIn, Object object){
        return itemStackWrapperIn.getStackInSlot(itemStackWrapperIn.getFirstValidSlot(object));
    }
}
