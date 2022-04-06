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

    public VoxelShape getBlockSupportShape(BlockState p_230335_1_, BlockGetter p_230335_2_, BlockPos p_230335_3_) {
        return Shapes.empty();
    }

    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return p_149653_1_.getValue(DISTANCE) == leaveDisappearDistance && !p_149653_1_.getValue(PERSISTENT);
    }

    public void randomTick(BlockState p_225542_1_, ServerLevel p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        if (!p_225542_1_.getValue(PERSISTENT) && p_225542_1_.getValue(DISTANCE) == leaveDisappearDistance) {
            dropResources(p_225542_1_, p_225542_2_, p_225542_3_);
            p_225542_2_.removeBlock(p_225542_3_, false);
        }

    }

    public void tick(BlockState p_225534_1_, ServerLevel p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        p_225534_2_.setBlock(p_225534_3_, updateDistance(p_225534_1_, p_225534_2_, p_225534_3_), 3);
    }

    public int getLightBlock(BlockState p_200011_1_, BlockGetter p_200011_2_, BlockPos p_200011_3_) {
        return 1;
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        int i = getDistanceAt(p_196271_3_) + 1;
        if (i != 1 || p_196271_1_.getValue(DISTANCE) != i) {
            p_196271_4_.getBlockTicks().scheduleTick(p_196271_5_, this, 1);
        }

        return p_196271_1_;
    }

    private static BlockState updateDistance(BlockState p_208493_0_, LevelAccessor p_208493_1_, BlockPos p_208493_2_) {
        int i = leaveDisappearDistance;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(p_208493_2_, direction);
            i = Math.min(i, getDistanceAt(p_208493_1_.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return p_208493_0_.setValue(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState p_208492_0_) {
        if (BlockTags.LOGS.contains(p_208492_0_.getBlock())) {
            return 0;
        } else {
            return p_208492_0_.getBlock() instanceof LeavesBlock ? p_208492_0_.getValue(DISTANCE) : 7;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_2_.isRainingAt(p_180655_3_.above())) {
            if (p_180655_4_.nextInt(15) == 1) {
                BlockPos blockpos = p_180655_3_.below();
                BlockState blockstate = p_180655_2_.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, Direction.UP)) {
                    double d0 = (double)p_180655_3_.getX() + p_180655_4_.nextDouble();
                    double d1 = (double)p_180655_3_.getY() - 0.05D;
                    double d2 = (double)p_180655_3_.getZ() + p_180655_4_.nextDouble();
                    p_180655_2_.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(DISTANCE, PERSISTENT);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)), p_196258_1_.getLevel(), p_196258_1_.getClickedPos());
    }
}