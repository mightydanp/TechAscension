package mightydanp.techascension.common.tool.tools;

import mightydanp.techcore.common.blocks.HoleBlock;
import mightydanp.techcore.common.blocks.ITBlocks;
import mightydanp.techcore.common.crafting.recipe.HoleRecipe;
import mightydanp.techcore.common.crafting.recipe.Recipes;
import mightydanp.techcore.common.items.ITToolItem;
import mightydanp.techcore.common.tileentities.HoleTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

/**
 * Created by MightyDanp on 10/9/2021.
 */
public class KnifeToolItem extends ITToolItem {
    public KnifeToolItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext itemUseContext) {
        Level world = itemUseContext.getLevel();
        Player playerEntity = itemUseContext.getPlayer();
        BlockPos blockPos = itemUseContext.getClickedPos();
        Direction clickedFace = itemUseContext.getClickedFace();
        BlockState clickedBlockState = world.getBlockState(blockPos);

        Container iinventory = new SimpleContainer(new ItemStack(clickedBlockState.getBlock()));
        List<HoleRecipe> recipe = world.getRecipeManager().getRecipesFor(Recipes.holeType, iinventory, world);
        Optional<HoleRecipe> validRecipe = world.getRecipeManager().getRecipeFor(Recipes.holeType, iinventory, world);

        int harvestToolDamage = validRecipe.map(HoleRecipe::getIngredientItemDamage).orElse(1);

        if(validRecipe.isPresent()) {
            BlockState newBlockState;
            newBlockState = ITBlocks.hole_block.get().defaultBlockState();
            world.setBlockAndUpdate(blockPos, newBlockState);

            HoleTileEntity tileEntity = (HoleTileEntity) world.getBlockEntity(blockPos);

            if (playerEntity != null && tileEntity != null) {
                tileEntity.desiredBlockState = clickedBlockState;
                tileEntity.setDesiredBlockSlot(validRecipe.get().getDesiredBlock());
                tileEntity.minTicksForHoleToFill = validRecipe.get().getMinTicks();
                tileEntity.maxTicksForHoleToFill = validRecipe.get().getMaxTicks();
                tileEntity.minResult = validRecipe.get().getMinResult();
                tileEntity.maxResult = validRecipe.get().getMaxResult();
                tileEntity.holeColor = validRecipe.get().getHoleColor();
                tileEntity.resinColor = validRecipe.get().getResinColor();
                tileEntity.ingredientItemDamage = validRecipe.get().getIngredientItemDamage();


                if (clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
                    if (itemUseContext.getItemInHand().getItem() instanceof ITToolItem mainHandTool) {
                        mainHandTool.damageToolParts(itemUseContext.getItemInHand(), playerEntity, world, harvestToolDamage);
                    }else{
                        if(itemUseContext.getItemInHand().isDamageableItem()){
                            itemUseContext.getItemInHand().hurtAndBreak(harvestToolDamage, Objects.requireNonNull(itemUseContext.getPlayer()), (item) -> {
                                item.broadcastBreakEvent(itemUseContext.getHand());
                            });
                        }else{
                            //itemUseContext.getItemInHand().shrink(1);
                        }
                    }

                    newBlockState = newBlockState.setValue(HoleBlock.FACING, clickedFace).setValue(HoleBlock.RESIN, false);
                    world.setBlockAndUpdate(blockPos, newBlockState);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        
        return super.useOn(itemUseContext);
    }

    public Optional<HoleRecipe> getValidRecipe(Level worldIn, ItemStack itemStackIn) {
        return worldIn.getRecipeManager().getRecipeFor(Recipes.holeType, new SimpleContainer(itemStackIn), worldIn);
    }
}