package mightydanp.industrialtech.common.data;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class Guis {
    public static void init() {
        Machines.MACHINE_FRAME.getGui().add(IndustrialTechSlotType.MOTOR_IN, 53, 16);
    }
}
