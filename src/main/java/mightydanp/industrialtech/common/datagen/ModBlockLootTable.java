package mightydanp.industrialtech.common.datagen;

import io.netty.util.Constant;
import mightydanp.industrialtech.api.common.datagen.GenLootTables;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.items.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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

        /*
        blockTable(ModBlocks.rock_block.get(), tableBuilder.withPool(
                poolBuilder.setRolls(ConstantValue.exactly(1.0F))
                        .add(AlternativesEntry.alternatives().otherwise(LootItem.lootTableItem(ModBlocks.rock_block.get())
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)))))
                        ))
                        .add(LootItem.lootTableItem(Items.FLINT)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F , 0.14285715F, 0.25F, 1.0F)
                        ))
                        .add(LootItem.lootTableItem(ModItems.rock_block.get()))
        ));

         */
    }

}
