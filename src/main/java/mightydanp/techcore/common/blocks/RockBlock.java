package mightydanp.techcore.common.blocks;

import io.netty.handler.codec.mqtt.MqttProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.TagCommand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RockBlock extends Block {
    public String stoneLayerBlock;



    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)};

    public RockBlock(String stoneLayerBlockIn, Properties properties) {
        super(properties.noCollission());
        stoneLayerBlock = stoneLayerBlockIn;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockState, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluid) {
        ItemStack itemStack = new ItemStack(blockState.getBlock().asItem());

        if(player.isShiftKeyDown()) {
            CompoundTag tag = itemStack.getOrCreateTag();

            tag.putInt("x", blockPos.getX());
            tag.putInt("y", blockPos.getY());
            tag.putInt("z", blockPos.getZ());
        }

        level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack));

        return super.onDestroyedByPlayer(blockState, level, blockPos, player, willHarvest, fluid);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockState, LootContext.@NotNull Builder builder) {
        return super.getDrops(blockState, builder);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES[0];
    }
}
