package mightydanp.industrialtech.api.common.blocks;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.libs.ITBlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 7/24/2021.
 */
public class LogHoleBlock extends RotatedPillarBlock {
    public static final BooleanProperty hasHole = ITBlockStateProperties.hasHole;

    public List<ITToolItem> listOfToolsToMakeSide = new ArrayList<>();

    public LogHoleBlock(Properties blockPropertiesIn, List<ITToolItem> listOfToolsToMakeSideIn) {
        super(blockPropertiesIn);
        this.registerDefaultState(this.stateDefinition.any().setValue(hasHole, false));

        listOfToolsToMakeSide = listOfToolsToMakeSideIn;
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(playerEntity.getUseItem().getItem() instanceof ITToolItem) {
            ITToolItem handTool = (ITToolItem)playerEntity.getUseItem().getItem();
            if (listOfToolsToMakeSide.contains(handTool)) {
                //world.setBlock(blockPos, blockState)
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(hasHole);
    }


}
