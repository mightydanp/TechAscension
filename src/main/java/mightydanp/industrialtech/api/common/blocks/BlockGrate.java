package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.libs.EnumGrate;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BlockGrate  extends Block {
    public static final EnumProperty<EnumGrate> camp_fire = EnumProperty.create("camp_fire", EnumGrate.class);
    private final EnumProperty<EnumGrate> campFireState = camp_fire;
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public Boolean isLite;
    public Boolean hasClayPan;
    public Boolean hasClayPot;

    public BlockGrate() {
        super(AbstractBlock.Properties.create(Material.IRON).doesNotBlockMovement());
        BlockState defaultBlockState = this.stateContainer.getBaseState().with(campFireState, EnumGrate.NO_COOKWARE).with(FACING, Direction.NORTH);
        this.setDefaultState(defaultBlockState);
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();

        Direction direction = blockItemUseContext.getPlacementHorizontalFacing();
        float playerFacingDirectionAngle = blockItemUseContext.getPlacementYaw();

        BlockState blockState = getDefaultState().with(campFireState, EnumGrate.NO_COOKWARE).with(FACING, direction);
        return blockState;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, campFireState);
    }
}