package mightydanp.industrialtech.client.gui;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.gui.SlotType;
import java.util.Optional;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class IndustrialTechSlotType extends SlotType{

    public static SlotType CIRCUIT_IN = new SlotType("circuit_in", (t, i, d) ->{
       if(t instanceof TileEntityMachineFrame){
           return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotCircuit(((TileEntityMachineFrame) t).circuitInputWrapper, i, d.x, d.y));
       }else{
           return Optional.empty();
       }
    });

    public static SlotType CONVEYOR_IN = new SlotType("conveyor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotConveyor(((TileEntityMachineFrame) t).conveyorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType EMITTER_IN = new SlotType("emitter_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotEmitter(((TileEntityMachineFrame) t).emitterInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType FIELD_GENERATOR_IN = new SlotType("field_generator_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotFieldGenerator(((TileEntityMachineFrame) t).fieldGeneratorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType Motor_IN = new SlotType("motor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
                return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotMotor(((TileEntityMachineFrame) t).motorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PISTON_IN = new SlotType("piston_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPiston(((TileEntityMachineFrame) t).pistonInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType PUMP_IN = new SlotType("pump_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotPump(((TileEntityMachineFrame) t).pumpInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType ROBOT_ARM_IN = new SlotType("robot_arm_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotRobotArm(((TileEntityMachineFrame) t).robotArmInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });
    public static SlotType SENSOR_IN = new SlotType("sensor_in", (t, i, d) ->{
        if(t instanceof TileEntityMachineFrame){
            return Optional.of((TileEntityMachineFrame) t).map(handler-> new SlotSensor(((TileEntityMachineFrame) t).sensorInputWrapper, i, d.x, d.y));
        }else{
            return Optional.empty();
        }
    });

    public IndustrialTechSlotType(String id, ISlotSupplier slotSupplier) {
        super(id, slotSupplier);
    }
}