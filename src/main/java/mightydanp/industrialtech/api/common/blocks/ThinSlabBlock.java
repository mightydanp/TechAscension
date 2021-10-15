package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class ThinSlabBlock extends Block implements ILiquidContainer {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPES = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 1.0D, 15.99D);

    public Block stoneLayerBlock;

    public ThinSlabBlock(AbstractBlock.Properties properties, Block stoneLayerBlockIn) {
        super(properties);
        stoneLayerBlock = stoneLayerBlockIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        final FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(true)) : this.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(final BlockState blockState, final Direction direction, final BlockState facingState, final IWorld world, final BlockPos currentPos, final BlockPos facingPos) {
        final BlockState newState = super.updateShape(blockState, direction, facingState, world, currentPos, facingPos);

        if (!newState.isAir(world, currentPos)) {
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(blockState, direction, facingState, world, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceLiquid(final IBlockReader world, final BlockPos pos, final BlockState state, final Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(final IWorld world, final BlockPos pos, final BlockState state, final FluidState fluidState) {
        return false;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockStateTop = worldIn.getBlockState(pos.above());
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (blockstate == Blocks.AIR.defaultBlockState()) {
            return false;
        }

        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}