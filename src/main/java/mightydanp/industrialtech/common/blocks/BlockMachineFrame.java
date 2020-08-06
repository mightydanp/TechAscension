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

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityMachineFrame){
            System.out.println("1");
            ItemStack itemstack = player.getHeldItem(handIn);
            if (itemstack.isEmpty()) {
                return ActionResultType.PASS;
            } else {
                Item item = itemstack.getItem();
                if(item == CircuitBasic.getItem() || item == CircuitAdv || item == CircuitGood || item == CircuitDataStorage || item == CircuitDataControl || item == CircuitEnergyFlow || item == CircuitDataOrb){
                    System.out.println("2");
                    if (((TileEntityMachineFrame) tileEntity).circuitSlotItemStack == null && !worldIn.isRemote) {
                        System.out.println("3");
                        if (!player.abilities.isCreativeMode) {
                            System.out.println("4");
                            ((TileEntityMachineFrame) tileEntity).circuitInputWrapper.setStackInSlot(((TileEntityMachineFrame) tileEntity).circuitInputWrapper.getSlots() ,player.getHeldItemMainhand());
                            player.setHeldItem(handIn, new ItemStack(null));
                        }
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }
}