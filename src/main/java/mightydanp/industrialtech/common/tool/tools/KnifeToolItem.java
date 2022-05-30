package mightydanp.industrialtech.common.tool.tools;

import com.google.common.collect.ImmutableSet;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

/**
 * Created by MightyDanp on 10/9/2021.
 */
public class KnifeToolItem extends ITToolItem {
    private static final Set<String> EFFECTIVE_ON = ImmutableSet.of(String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));

    public KnifeToolItem() {
        super("knife", new ArrayList<>(EFFECTIVE_ON),  new Item.Properties().tab(ModItemGroups.tool_tab));
        toolsNeeded.put(String.valueOf(new ResourceLocation(Ref.mod_id, "hammer")), 1);

        parts.put("knife_head", 1);
        parts.put("wedge_handle", 2);
        parts.put("wedge", 3);

        disassembleTools.add(String.valueOf(new ResourceLocation(Ref.mod_id, "hammer")));
        disassembleTools.add(String.valueOf(new ResourceLocation(Ref.mod_id, "chisel")));
    }

    @Override
    public InteractionResult useOn(UseOnContext itemUseContext) {
        Level world = itemUseContext.getLevel();
        Player playerEntity = itemUseContext.getPlayer();
        BlockPos blockPos = itemUseContext.getClickedPos();
        Direction clickedFace = itemUseContext.getClickedFace();
        BlockState clickedBlockState = world.getBlockState(blockPos);

        TagKey<Item> LOGS = ItemTags.create(new ResourceLocation("forge", "gems"));

        boolean itemStack = new ItemStack(ModMaterials.almandine.gem.get()).is(LOGS);

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
                    if (itemUseContext.getItemInHand().getItem() instanceof ITToolItem) {
                        ITToolItem mainHandTool = (ITToolItem) itemUseContext.getItemInHand().getItem();
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