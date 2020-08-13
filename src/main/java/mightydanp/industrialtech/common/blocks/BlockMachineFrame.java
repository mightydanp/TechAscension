package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.Data;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;
import static muramasa.antimatter.machine.MachineFlag.*;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class BlockMachineFrame extends Machine<BlockMachineFrame> {

    public BlockMachineFrame(String domain, String id, Object... data) {
        super(domain, id, data);
        setTile(() -> new TileEntityMachineFrame(this));
        setTiers(Tier.getAllElectric());
        setGUI(Data.BASIC_MENU_HANDLER);
        addFlags(BASIC, ENERGY, COVERABLE, CONFIGURABLE, ITEM);
    }
}