package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.Data;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;

import static muramasa.antimatter.machine.MachineFlag.*;

/**
 * Created by MightyDanp on 8/20/2020.
 */
public class BlockBasicMachineFrame extends Machine<BlockBasicMachineFrame> {
    public BlockBasicMachineFrame(String domain, String id, Object... data) {
        super(domain, id, data);
        setTile(() -> new TileEntityMachineFrame(this));
        setTiers(Tier.getAllElectric());
        setGUI(Data.BASIC_MENU_HANDLER);
        addFlags(BASIC, ENERGY, COVERABLE, CONFIGURABLE, ITEM);
    }
}