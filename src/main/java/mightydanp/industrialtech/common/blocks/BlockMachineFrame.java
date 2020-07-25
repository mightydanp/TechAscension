package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.Data;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class BlockMachineFrame extends Machine<BlockMachineFrame> {

    public BlockMachineFrame(String domain, String id, Object... data) {
        super(domain, id, data);
        setTile(() -> new TileEntityMachineFrame(this));
        setTiers(Tier.getAllElectric());
        setGUI(Data.BASIC_MENU_HANDLER);
        addFlags(MachineFlag.BASIC, MachineFlag.ENERGY, MachineFlag.COVERABLE, MachineFlag.CONFIGURABLE, MachineFlag.ITEM);
    }

}