package mightydanp.industrialtech.common.data;

import mightydanp.industrialtech.common.blocks.BlockBasicMachineFrame;
import mightydanp.industrialtech.common.lib.References;

import static muramasa.antimatter.machine.MachineFlag.FLUID;
import static muramasa.antimatter.machine.MachineFlag.ITEM;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class Machines {
    // Machine Casing
    public static BlockBasicMachineFrame MACHINE_FRAME = new BlockBasicMachineFrame(References.ID, "machine_frame",ITEM, FLUID);

    public static void init() {
    }
}
