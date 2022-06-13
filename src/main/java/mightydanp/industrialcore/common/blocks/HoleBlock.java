package mightydanp.industrialcore.common.blocks;

import mightydanp.industrialcore.common.blocks.state.ITBlockStateProperties;
import mightydanp.industrialcore.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialcore.common.crafting.recipe.Recipes;
import mightydanp.industrialcore.common.items.ITToolItem;
import mightydanp.industrialcore.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by MightyDanp on 7/24/2021.
 */
public class HoleBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPES = Block.box(0.0D, 0.0D, 0.0D, 15.99D, 16.0D, 15.99D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty RESIN = ITBlockStateProperties.RESIN;


    public HoleBlock() {
        super(Properties.of(Material.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(RESIN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES;
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player playerEntity, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        HoleTileEntity tileEntity = (HoleTileEntity) world.getBlockEntity(blockPos);
        BlockPos itemStackSpawnPos = blockPos.relative(blockRayTraceResult.getDirection());

        if (tileEntity != null && tileEntity.getDesiredBlockSlot() != null) {
            Optional<HoleRecipe> validRecipe = getValidRecipe(world, new ItemStack(tileEntity.getDesiredBlockSlot().getItem()));
            ItemStack mainHand = playerEntity.getMainHandItem();
            ItemStack offHand = playerEntity.getOffhandItem();

            if(validRecipe.isPresent()) {
                if (validRecipe.get().getIngredients().size() == 2 && validRecipe.get().getIngredients().contains(mainHand) && validRecipe.get().getIngredients().contains(offHand) && blockRayTraceResult.getDirection() == blockRayTraceResult.getDirection() && blockRayTraceResult.getDirection() != Direction.UP && blockRayTraceResult.getDirection() != Direction.DOWN) {
                    if (!validRecipe.get().getConsumeIngredients()) {
                        if (mainHand.getItem() instanceof ITToolItem && offHand.getItem() instanceof ITToolItem) {
                            ITToolItem mainHandTool = (ITToolItem) mainHand.getItem();
                            ITToolItem offHandTool = (ITToolItem) offHand.getItem();
                            mainHandTool.damageToolParts(playerEntity.getMainHandItem(), playerEntity, world, tileEntity.ingredientItemDamage);
                            offHandTool.damageToolParts(playerEntity.getOffhandItem(), playerEntity, world, tileEntity.ingredientItemDamage);
                        } else {
                            if (mainHand.isDamageableItem()) {
                                mainHand.setDamageValue(mainHand.getDamageValue() - 1);
                            }

                            if (offHand.isDamageableItem()) {
                                offHand.setDamageValue(offHand.getDamageValue() - 1);
                            }
                        }
                    } else {
                        mainHand.shrink(1);
                        offHand.shrink(1);
                    }

                    world.addFreshEntity(new ItemEntity(world, itemStackSpawnPos.getX(), itemStackSpawnPos.getY(), itemStackSpawnPos.getZ(), new ItemStack(tileEntity.getOutputSlot().getItem(), tileEntity.getOutputSlot().getCount())));
                    tileEntity.setOutputSlot(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }

                if (validRecipe.get().getIngredients().size() == 1 && blockRayTraceResult.getDirection() == blockRayTraceResult.getDirection() && blockRayTraceResult.getDirection() != Direction.UP && blockRayTraceResult.getDirection() != Direction.DOWN) {
                    if (!validRecipe.get().getConsumeIngredients()) {
                        if (!validRecipe.get().getIngredients().contains(mainHand)) {
                            if (mainHand.getItem() instanceof ITToolItem) {
                                ITToolItem mainHandTool = (ITToolItem) mainHand.getItem();
                                mainHandTool.damageToolParts(playerEntity.getMainHandItem(), playerEntity, world, tileEntity.ingredientItemDamage);
                            } else {
                                if (mainHand.isDamageableItem()) {
                                    mainHand.setDamageValue(mainHand.getDamageValue() - 1);
                                }
                            }
                        }
                    } else {
                        mainHand.shrink(1);
                    }

                    if (!validRecipe.get().getConsumeIngredients()) {
                        if (validRecipe.get().getIngredients().contains(offHand)) {
                            if (offHand.getItem() instanceof ITToolItem) {
                                ITToolItem offHandTool = (ITToolItem) offHand.getItem();
                                offHandTool.damageToolParts(playerEntity.getOffhandItem(), playerEntity, world, tileEntity.ingredientItemDamage);
                            } else {
                                if (offHand.isDamageableItem()) {
                                    offHand.setDamageValue(offHand.getDamageValue() - 1);
                                }
                            }
                        }
                    } else {
                        offHand.shrink(1);
                    }

                    world.addFreshEntity(new ItemEntity(world, itemStackSpawnPos.getX(), itemStackSpawnPos.getY(), itemStackSpawnPos.getZ(), new ItemStack(tileEntity.getOutputSlot().getItem(), tileEntity.getOutputSlot().getCount())));
                    tileEntity.setOutputSlot(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.FAIL;
    }

    public Optional<HoleRecipe> getValidRecipe(Level worldIn, ItemStack itemStackIn) {
        return worldIn.getRecipeManager().getRecipeFor(Recipes.holeType, new SimpleContainer(itemStackIn), worldIn);
    }

    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HoleTileEntity(blockPos, blockState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return type == ModBlockEntity.hole_block_entity.get() ? HoleTileEntity::tick : null;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, RESIN);
    }
}
