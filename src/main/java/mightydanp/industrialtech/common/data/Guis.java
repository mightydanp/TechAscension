package mightydanp.industrialtech.common.data;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class Guis {

    //TO-DO Cable
    public static void init() {
        Machines.MACHINE_FRAME.getGui()
                .add(IndustrialTechSlotType.CIRCUIT_IN, 118, 6)
                .add(IndustrialTechSlotType.CONVEYOR_IN, 136, 6)
                .add(IndustrialTechSlotType.EMITTER_IN, 154, 6)
                .add(IndustrialTechSlotType.FIELD_GENERATOR_IN, 118, 24)
                .add(IndustrialTechSlotType.Motor_IN, 136, 24)
                .add(IndustrialTechSlotType.PISTON_IN, 154, 24)
                .add(IndustrialTechSlotType.PUMP_IN, 118, 42)
                .add(IndustrialTechSlotType.ROBOT_ARM_IN, 136, 42)
                .add(IndustrialTechSlotType.SENSOR_IN, 154, 42);

    }
}