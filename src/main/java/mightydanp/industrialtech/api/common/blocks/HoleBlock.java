package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by MightyDanp on 7/24/2021.
 */
public class HoleBlock extends ContainerBlock {

    public static int holeColor = 0;
    public static int resinColor = 0;

    public HoleBlock() {
        super(Properties.of(Material.WOOD));
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        HoleTileEntity tileEntity = (HoleTileEntity) world.getBlockEntity(blockPos);
        BlockPos itemStackSpawnPos = blockPos.relative(blockRayTraceResult.getDirection());

        if (tileEntity != null && tileEntity.getDesiredBlockSlot() != null) {
            Optional<HoleRecipe> validRecipe = getValidRecipe(world, new ItemStack(tileEntity.getDesiredBlockSlot().getItem()));
            ItemStack mainHand = playerEntity.getMainHandItem();
            ItemStack offHand = playerEntity.getOffhandItem();

            if(validRecipe.isPresent()) {
                if (tileEntity.ingredientItems.size() == 2 && tileEntity.ingredientItems.contains(mainHand) && tileEntity.ingredientItems.contains(offHand) && blockRayTraceResult.getDirection() == blockRayTraceResult.getDirection() && blockRayTraceResult.getDirection() != Direction.UP && blockRayTraceResult.getDirection() != Direction.DOWN) {
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
                    return ActionResultType.SUCCESS;
                }

                if (tileEntity.ingredientItems.size() == 1 && blockRayTraceResult.getDirection() == blockRayTraceResult.getDirection() && blockRayTraceResult.getDirection() != Direction.UP && blockRayTraceResult.getDirection() != Direction.DOWN) {
                    if (!validRecipe.get().getConsumeIngredients()) {
                        if (!tileEntity.ingredientItems.contains(mainHand)) {
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
                        if (tileEntity.ingredientItems.contains(offHand)) {
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
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.FAIL;
    }

    public Optional<HoleRecipe> getValidRecipe(World worldIn, ItemStack itemStackIn) {
        return worldIn.getRecipeManager().getRecipeFor(Recipes.holeType, new Inventory(itemStackIn), worldIn);
    }

    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new HoleTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HoleTileEntity();
    }

}
