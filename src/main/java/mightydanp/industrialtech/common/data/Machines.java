package mightydanp.industrialtech.common.data;

import mightydanp.industrialtech.common.blocks.BlockBasicMachineFrame;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class Machines {

    // Machine Casing

    public static BlockBasicMachineFrame MACHINE_FRAME = new BlockBasicMachineFrame(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 10.0f).sound(SoundType.METAL));

    //@ObjectHolder("industrialtech:machine_frame_lv")
    //public static final Block machineFrameLV = null;

    //@ObjectHolder("industrialtech:machine_frame_mv")
    //public static final Block machineFrameMV = null;

    public static void init() {
    }
}
