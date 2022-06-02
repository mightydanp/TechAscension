package mightydanp.industrialtech.common.datagen;

import mightydanp.industrialcore.common.datagen.GenLootTables;
import net.minecraft.data.DataGenerator;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

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
