package mightydanp.industrialtech.common.tileentity;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;
import mightydanp.industrialtech.common.capabilty.machine.IndustrialTechMachineItemHandler;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.machine.event.ContentEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.tileentity.TileEntityType;
import tesseract.Tesseract;

import java.util.Optional;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class TileEntityMachineFrame extends TileEntityMachine {
    protected ContentEvent MOTOR_INPUT_CHANGER;
    public ItemStackWrapper motorInputWrapper;

    public TileEntityMachineFrame(Machine<?> type){
        super(type);
        motorInputWrapper = new ItemStackWrapper(this, this.getMachineType().getGui().getSlots(IndustrialTechSlotType.Motor_IN, this.getMachineTier()).size(), MOTOR_INPUT_CHANGER);
    }
    public void onServerUpdate(){
        super.onServerUpdate();
    }
}
