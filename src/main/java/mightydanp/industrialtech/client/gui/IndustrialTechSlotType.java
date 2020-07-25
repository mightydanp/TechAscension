package mightydanp.industrialtech.client.gui;

import mightydanp.industrialtech.client.gui.slot.*;
import mightydanp.industrialtech.common.capabilty.machine.IndustrialTechMachineItemHandler;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.gui.SlotType;

import java.util.Optional;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class IndustrialTechSlotType extends SlotType{
    public static SlotType CIRCUIT_IN = new SlotType("circuit_in", (t, i, d) -> Optional.of(new SlotCircuit(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType CONVEYOR_IN = new SlotType("conveyor_in", (t, i, d) -> Optional.of(new SlotConveyor(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType FIELDGENERATOR_IN = new SlotType("field_generator_in", (t, i, d) -> Optional.of(new SlotFieldGenerator(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));

    public static SlotType Mortor_IN = new SlotType("motor_in", (t, i, d) -> Optional.of(new SlotMotor(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType PISTON_IN = new IndustrialTechSlotType("piston_in", (t, i, d) -> Optional.of(new SlotPiston(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType PUMP_IN = new IndustrialTechSlotType("pump_in", (t, i, d) -> Optional.of(new SlotPump(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType ROBOTARM_IN = new IndustrialTechSlotType("robot_arm_in", (t, i, d) -> Optional.of(new SlotRobotArm(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));
    public static SlotType SENSOR_IN = new IndustrialTechSlotType("sensor_in", (t, i, d) -> Optional.of(new SlotSensor(t.itemHandler.get().getOutputWrapper(), i, d.x, d.y)));

    public IndustrialTechSlotType(String id, ISlotSupplier slotSupplier) {
        super(id, slotSupplier);
    }
}