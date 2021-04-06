package mightydanp.industrialtech.common.datagen;

import mightydanp.industrialtech.api.common.datagen.GenLootTables;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.items.ModItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.TableBonus;

/**
 * Created by MightyDanp on 3/6/2021.
 */
public class ModBlockLootTable extends GenLootTables {
    public ModBlockLootTable(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    public static void registerModBlockTables(){
        LootTable.Builder tableBuilder = LootTable.lootTable();
        LootPool.Builder poolBuilder = LootPool.lootPool();

        blockTable(ModBlocks.rock_block.get(), tableBuilder.withPool(
                poolBuilder.setRolls(ConstantRange.exactly(1))
                        .add(AlternativesLootEntry.alternatives().otherwise(ItemLootEntry.lootTableItem(ModBlocks.rock_block.get())
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.IntBound.atLeast(1)))))
                        ))
                        .add(ItemLootEntry.lootTableItem(Items.FLINT)
                        .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F , 0.14285715F, 0.25F, 1.0F)
                        ))
                        .add(ItemLootEntry.lootTableItem(ModItems.rock_block.get()))
        ));
    }

}
