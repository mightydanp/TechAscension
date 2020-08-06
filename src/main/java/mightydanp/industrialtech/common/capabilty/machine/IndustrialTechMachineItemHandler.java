package mightydanp.industrialtech.common.capabilty.machine;

import mightydanp.industrialtech.client.gui.IndustrialTechSlotType;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.machine.event.ContentEvent;
import tesseract.Tesseract;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class IndustrialTechMachineItemHandler extends MachineItemHandler {

    public ItemStackWrapper motorInputWrapper;
    protected ContentEvent MOTOR_INPUT_CHANGER;
    public IndustrialTechMachineItemHandler(TileEntityMachineFrame tile) {
        super(tile);
        this.motorInputWrapper = new ItemStackWrapper(tile, tile.getMachineType().getGui().getSlots(IndustrialTechSlotType.Motor_IN, tile.getMachineTier()).size(), MOTOR_INPUT_CHANGER);
        Tesseract.ITEM.registerNode(tile.getDimension(), tile.getPos().toLong(), this);
    }
}
