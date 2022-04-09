package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Created by MightyDanp on 7/31/2021.
 */
public class LeaveBlock extends Block implements net.minecraftforge.common.IForgeShearable {
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public String blockName;
    private static int leaveDisappearDistance;

    public LeaveBlock(String nameIn, BlockBehaviour.Properties propertiesIn, int leaveDisappearDistanceIn) {
        super(propertiesIn);
        blockName = nameIn;
        leaveDisappearDistance = leaveDisappearDistanceIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, Integer.valueOf(leaveDisappearDistanceIn)).setValue(PERSISTENT, Boolean.valueOf(false)));
    }

    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return p_149653_1_.getValue(DISTANCE) == leaveDisappearDistance && !p_149653_1_.getValue(PERSISTENT);
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        if (!blockState.getValue(PERSISTENT) && blockState.getValue(DISTANCE) == leaveDisappearDistance) {
            dropResources(blockState, serverLevel, blockPos);
            serverLevel.removeBlock(blockPos, false);
        }

    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        serverLevel.setBlock(blockPos, updateDistance(blockState, serverLevel, blockPos), 3);
    }

    public int getLightBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 1;
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        int i = getDistanceAt(blockState1) + 1;
        if (i != 1 || blockState.getValue(DISTANCE) != i) {
            levelAccessor.scheduleTick(blockPos, this, 1);
        }

        return blockState;
    }

    private static BlockState updateDistance(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        int i = leaveDisappearDistance;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(blockPos, direction);
            i = Math.min(i, getDistanceAt(levelAccessor.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return blockState.setValue(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState blockState) {
        if (blockState.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return blockState.getBlock() instanceof LeavesBlock ? blockState.getValue(DISTANCE) : 7;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        if (level.isRainingAt(blockPos.above())) {
            if (random.nextInt(15) == 1) {
                BlockPos blockpos = blockPos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double)blockPos.getX() + random.nextDouble();
                    double d1 = (double)blockPos.getY() - 0.05D;
                    double d2 = (double)blockPos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(DISTANCE, PERSISTENT);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, true), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }
}