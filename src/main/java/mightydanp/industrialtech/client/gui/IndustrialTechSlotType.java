package mightydanp.industrialtech.client.gui;

import muramasa.antimatter.gui.SlotType;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class IndustrialTechSlotType extends SlotType{

    public IndustrialTechSlotType(String id, SlotType.ISlotSupplier slotSupplier){
        super(id, slotSupplier);
    }
/*
    public static SlotType CIRCUIT_IN = new SlotType("circuit_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotCircuit(((TileEntityMachineFrame) t).machineFrameItemHandler.get().circuitWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });

    public static SlotType CONVEYOR_IN = new SlotType("conveyor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotCircuit(((TileEntityMachineFrame) t).machineFrameItemHandler.get().conveyorWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType EMITTER_IN = new SlotType("emitter_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotEmitter(((TileEntityMachineFrame) t).machineFrameItemHandler.get().emitterWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType FIELD_GENERATOR_IN = new SlotType("field_generator_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotFieldGenerator(((TileEntityMachineFrame) t).machineFrameItemHandler.get().fieldGeneratorWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType Motor_IN = new SlotType("motor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotMotor(((TileEntityMachineFrame) t).machineFrameItemHandler.get().motorWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PISTON_IN = new SlotType("piston_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPiston(((TileEntityMachineFrame) t).machineFrameItemHandler.get().pistonWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PUMP_IN = new SlotType("pump_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPump(((TileEntityMachineFrame) t).machineFrameItemHandler.get().pumpWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType ROBOT_ARM_IN = new SlotType("robot_arm_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotRobotArm(((TileEntityMachineFrame) t).machineFrameItemHandler.get().robotArmWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType SENSOR_IN = new SlotType("sensor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotSensor(((TileEntityMachineFrame) t).machineFrameItemHandler.get().sensorWrapper, i, d.x, d.y));
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