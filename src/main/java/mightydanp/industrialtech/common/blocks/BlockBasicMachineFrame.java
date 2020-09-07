package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.inventory.container.ContainerBasicMachineFrame;
import mightydanp.industrialtech.common.tileentity.TileEntityBasicMachineFrame;
import muramasa.antimatter.Data;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 8/20/2020.
 */
public class BlockBasicMachineFrame extends ContainerBlock {
    private static final Vec3d FRAME_MIN_CORNER = new Vec3d(1.0, 0.0, 1.0);
    private static final Vec3d FRAME_MAX_CORNER = new Vec3d(15.0, 8.0, 15.0);
    private static final VoxelShape FRAME_SHAPE = Block.makeCuboidShape(
            FRAME_MIN_CORNER.getX(), FRAME_MIN_CORNER.getY(), FRAME_MIN_CORNER.getZ(),
            FRAME_MAX_CORNER.getX(), FRAME_MAX_CORNER.getY(), FRAME_MAX_CORNER.getZ());

    public BlockBasicMachineFrame(Properties builder) {
        super(builder);
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World world, BlockPos blockPos) {
        return getInventoryAtPosition(world, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D);
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
        IInventory iinventory = null;
        BlockPos blockpos = new BlockPos(x, y, z);
        BlockState blockstate = worldIn.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iinventory = ((ISidedInventoryProvider) block).createInventory(blockstate, worldIn, blockpos);
        } else if (blockstate.hasTileEntity()) {
            TileEntity tileentity = worldIn.getTileEntity(blockpos);
            if (tileentity instanceof IInventory) {
                iinventory = (IInventory) tileentity;
                if (iinventory instanceof ChestTileEntity && block instanceof ChestBlock) {
                    iinventory = ChestBlock.func_226916_a_((ChestBlock) block, blockstate, worldIn, blockpos, true);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntityPredicates.HAS_INVENTORY);
            if (!list.isEmpty()) {
                iinventory = (IInventory) list.get(worldIn.rand.nextInt(list.size()));
            }
        }

        return iinventory;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return Data.WRENCH.getToolType();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityBasicMachineFrame();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return createNewTileEntity(world);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityBasicMachineFrame) {
                ItemStackHandler itemStackHandler = ((TileEntityBasicMachineFrame) tileEntity).getItemHandler();
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.mytutorial.firstblock");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ContainerBasicMachineFrame(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());

                ItemStack playersHand = player.getHeldItemMainhand();
                if (!playersHand.isEmpty()) {
                    for (int i = 0; i < 9; i++) {
                        if (itemStackHandler.isItemValid(i, playersHand)) {
                            itemStackHandler.setStackInSlot(i, playersHand);
                            playersHand.shrink(1);
                        }
                    }
                }
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    public void onReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = world.getTileEntity(blockPos);
            if (tileentity instanceof TileEntityBasicMachineFrame) {
                TileEntityBasicMachineFrame tileEntityBasicMachineFrame = (TileEntityBasicMachineFrame) tileentity;
            }
            super.onReplaced(state, world, blockPos, newState, isMoving);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FRAME_SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
    }
}