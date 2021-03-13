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
public class ModBlockLootTables extends GenLootTables {
    public ModBlockLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    public static void registerModBlockTables(){
        LootTable.Builder tableBuilder = LootTable.builder();
        LootPool.Builder poolBuilder = LootPool.builder();

        blockTable(ModBlocks.rock_block.get(), tableBuilder.addLootPool(
                poolBuilder.rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder().alternatively(ItemLootEntry.builder(ModBlocks.rock_block.get())
                        .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.IntBound.atLeast(1)))))
                        ))
                        .addEntry(ItemLootEntry.builder(Items.FLINT)
                        .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F , 0.14285715F, 0.25F, 1.0F)
                        ))
                        .addEntry(ItemLootEntry.builder(ModItems.rock_block.get()))
        ));
    }

}
