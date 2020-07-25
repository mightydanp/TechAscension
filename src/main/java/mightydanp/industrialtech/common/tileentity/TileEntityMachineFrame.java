package mightydanp.industrialtech.common.tileentity;

import mightydanp.industrialtech.common.capabilty.machine.IndustrialTechMachineItemHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.tileentity.TileEntityType;

import java.util.Optional;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class TileEntityMachineFrame extends TileEntityMachine {
    public Optional<IndustrialTechMachineItemHandler> itemITHandler;
    public TileEntityMachineFrame(Machine<?> type){
        super(type);
    }

    public void onServerUpdate(){
        super.onServerUpdate();
    }
}
