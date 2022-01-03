package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by MightyDanp on 10/9/2021.
 */
public class KnifeToolItem extends ITToolItem{
    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);

    public static List<Pair<Item, Integer>> craftingToolsNeeded = new ArrayList<Pair<Item, Integer>>();

    public static List<Pair<Item, Integer>> parts = new ArrayList<Pair<Item, Integer>>();

    public static List<Item> disassembleTools = new ArrayList<Item>();

    public KnifeToolItem() {
        super(EFFECTIVE_ON,  new Item.Properties().tab(ModItemGroups.tool_tab), craftingToolsNeeded, parts, disassembleTools);
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        BlockPos blockPos = itemUseContext.getClickedPos();
        Direction clickedFace = itemUseContext.getClickedFace();
        BlockState clickedBlockState = world.getBlockState(blockPos);

        IInventory iinventory = new Inventory(new ItemStack(clickedBlockState.getBlock()));
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
                    if (itemUseContext.getItemInHand().getItem() instanceof ITToolItem) {
                        ITToolItem mainHandTool = (ITToolItem) itemUseContext.getItemInHand().getItem();
                        mainHandTool.damageToolParts(itemUseContext.getItemInHand(), playerEntity, world, harvestToolDamage);
                    }else{
                        if(itemUseContext.getItemInHand().isDamageableItem()){
                            itemUseContext.getItemInHand().hurtAndBreak(harvestToolDamage, Objects.requireNonNull(itemUseContext.getPlayer()), (item) -> {
                                item.broadcastBreakEvent(itemUseContext.getHand());
                            });
                        }else{
                            itemUseContext.getItemInHand().shrink(1);
                        }
                    }

                    newBlockState = newBlockState.setValue(HoleBlock.FACING, clickedFace).setValue(HoleBlock.RESIN, false);
                    world.setBlockAndUpdate(blockPos, newBlockState);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        
        return super.useOn(itemUseContext);
    }

    public Optional<HoleRecipe> getValidRecipe(World worldIn, ItemStack itemStackIn) {
        return worldIn.getRecipeManager().getRecipeFor(Recipes.holeType, new Inventory(itemStackIn), worldIn);
    }
}