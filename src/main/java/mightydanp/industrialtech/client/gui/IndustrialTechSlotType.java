package mightydanp.industrialtech.client.gui;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.gui.SlotType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class IndustrialTechSlotType extends SlotType{
    private static final Item[] validCircuitItems = new Item[]{CircuitBasic, CircuitGood, CircuitAdv, CircuitDataStorage, CircuitDataControl, CircuitEnergyFlow, CircuitDataOrb};
    private static final Item[] validConveyorItems = new Item[]{ConveyorLV, ConveyorMV, ConveyorHV, ConveyorEV, ConveyorIV};
    private static final Item[] validEmitterItems = new Item[]{EmitterLV, EmitterMV, EmitterHV, EmitterEV, EmitterIV};
    private static final Item[] validFieldGeneratorItems = new Item[]{FieldGenLV, FieldGenMV, FieldGenHV, FieldGenEV, FieldGenIV};
    private static final Item[] validMotorItems = new Item[]{MotorLV, MotorMV, MotorHV, MotorEV, MotorIV};
    private static final Item[] validPistonItems = new Item[]{PistonLV, PistonMV, PistonHV, PistonEV, PistonIV};
    private static final Item[] validPumpItems = new Item[]{PumpLV, PumpMV, PumpHV, PumpEV, PumpIV};
    private static final Item[] validRobotArmItems = new Item[]{RobotArmLV, RobotArmMV, RobotArmHV, RobotArmEV, RobotArmIV};
    private static final Item[] validSensorItems = new Item[]{SensorLV, SensorMV, SensorHV, SensorEV, SensorIV};

    public static Set<Item> addValidCircuitItemToSlot = new HashSet<>(Arrays.asList(validCircuitItems));
    public static Set<Item> addValidConveyorItemToSlot = new HashSet<>(Arrays.asList(validConveyorItems));
    public static Set<Item> addValidEmitterItemToSlot = new HashSet<>(Arrays.asList(validEmitterItems));
    public static Set<Item> addValidFieldGeneratorItemToSlot = new HashSet<>(Arrays.asList(validFieldGeneratorItems));
    public static Set<Item> addValidMotorItemToSlot = new HashSet<>(Arrays.asList(validMotorItems));
    public static Set<Item> addValidPistonItemToSlot = new HashSet<>(Arrays.asList(validPistonItems));
    public static Set<Item> addValidPumpItemToSlot = new HashSet<>(Arrays.asList(validPumpItems));
    public static Set<Item> addValidRobotArmItemToSlot = new HashSet<>(Arrays.asList(validRobotArmItems));
    public static Set<Item> addValidSensorItemToSlot = new HashSet<>(Arrays.asList(validSensorItems));

    public IndustrialTechSlotType(String id, SlotType.ISlotSupplier slotSupplier){
        super(id, slotSupplier);
    }
    public static SlotType CIRCUIT_IN = new SlotType("circuit_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotCircuit(addValidCircuitItemToSlot, ((TileEntityMachineFrame) t).circuitInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });

    public static SlotType CONVEYOR_IN = new SlotType("conveyor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotCircuit(addValidConveyorItemToSlot, ((TileEntityMachineFrame) t).conveyorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType EMITTER_IN = new SlotType("emitter_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotEmitter(addValidEmitterItemToSlot, ((TileEntityMachineFrame) t).emitterInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType FIELD_GENERATOR_IN = new SlotType("field_generator_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotFieldGenerator(addValidFieldGeneratorItemToSlot, ((TileEntityMachineFrame) t).fieldGeneratorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType Motor_IN = new SlotType("motor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotMotor(addValidMotorItemToSlot, ((TileEntityMachineFrame) t).motorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PISTON_IN = new SlotType("piston_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPiston(addValidPistonItemToSlot, ((TileEntityMachineFrame) t).pistonInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PUMP_IN = new SlotType("pump_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPump(addValidPumpItemToSlot, ((TileEntityMachineFrame) t).pumpInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType ROBOT_ARM_IN = new SlotType("robot_arm_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotRobotArm(addValidRobotArmItemToSlot, ((TileEntityMachineFrame) t).robotArmInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType SENSOR_IN = new SlotType("sensor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotSensor(addValidSensorItemToSlot, ((TileEntityMachineFrame) t).sensorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
/*
    public static SlotType registerSlotType(String slotId, TileEntityMachine tileEntityIn, SlotType slotTypeIn, ItemStackWrapper itemStackWrapperIn){
        return new SlotType("slotId", (t, i, d) ->{
            if(t instanceof tileEntityIn){
                return Optional.of(t).map(handler-> new SlotData(slotTypeIn, d.x, d.y));
            }else{
                return Optional.empty();
            }
        });
    }
    public static SlotType addSlotType(TileEntity tileEntityIn){
        return new
    }
    */
}