package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class ThinSlabBlock extends Block implements LiquidBlockContainer {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPES = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 1.0D, 15.99D);

    public String stoneLayerBlock;

    public ThinSlabBlock(BlockBehaviour.Properties properties, String stoneLayerBlockIn) {
        super(properties.noCollission().strength(0.0f));
        stoneLayerBlock = stoneLayerBlockIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(true)) : this.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(final BlockState blockState, final Direction direction, final BlockState facingState, final LevelAccessor world, final BlockPos currentPos, final BlockPos facingPos) {
        final BlockState newState = super.updateShape(blockState, direction, facingState, world, currentPos, facingPos);

        if (!newState.isAir()) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(blockState, direction, facingState, world, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceLiquid(final BlockGetter world, final BlockPos pos, final BlockState state, final Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluidState) {
        return false;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState blockStateTop = worldIn.getBlockState(pos.above());
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (blockstate == Blocks.AIR.defaultBlockState()) {
            return false;
        }

        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}