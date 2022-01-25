package mightydanp.industrialtech.api.common.datagen;

import com.mojang.datafixers.util.Pair;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.flag.DefaultMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.common.datagen.ModBlockLootTable;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 3/5/2021.
 */
public class GenLootTables extends LootTableProvider {

    private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    public GenLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        tables.clear();

        for (ITMaterial material : RegistryHandler.MATERIAL.getValues()) {
            for (IMaterialFlag flag : material.materialFlags) {
                if (flag == DefaultMaterialFlag.ORE) {
                    for(Block blockRegistered : material.oreList) {
                        standardDropTable(blockRegistered);
                    }
                }
                if (flag == DefaultMaterialFlag.GEM) {
                    for(Block blockRegistered : material.oreList) {
                        standardDropTable(blockRegistered);
                    }
                }
                if (flag == DefaultMaterialFlag.ORE || flag == DefaultMaterialFlag.GEM) {
                    for(Block blockRegistered : material.smallOreList) {
                        standardDropTable(blockRegistered);
                    }

                    int i = 0;
                    for(Block blockRegistered : material.denseOreList) {
                        LootTable.Builder tableBuilder = LootTable.lootTable();
                        LootPool.Builder poolBuilder = LootPool.lootPool();

                        blockTable(blockRegistered, tableBuilder.withPool(poolBuilder.setRolls(ConstantRange.exactly(1))
                                        .add(AlternativesLootEntry.alternatives().otherwise(ItemLootEntry.lootTableItem(blockRegistered)
                                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.IntBound.atLeast(1)))))))
                                        .add(ItemLootEntry.lootTableItem(material.oreList.get(i)))
                        ));
                        i++;
                    }
                }
            }
        }

        ModBlockLootTable.registerModBlockTables();
        return tables;
    }

    public static void standardDropTable(Block b) {
        blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
    }

    public static void blockTable(Block b, LootTable.Builder lootTable) {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    public static void addTable(ResourceLocation path, LootTable.Builder lootTable, LootParameterSet paramSet) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    public static LootPool.Builder createStandardDrops(IItemProvider itemProvider) {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(SurvivesExplosion.survivesExplosion()).add(ItemLootEntry.lootTableItem(itemProvider));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validate(validationtracker, loc, table));
    }
}