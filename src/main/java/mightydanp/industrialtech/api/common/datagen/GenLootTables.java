package mightydanp.industrialtech.api.common.datagen;

import com.mojang.datafixers.util.Pair;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.flag.DefaultMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.datagen.ModBlockLootTable;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

/**
 * Created by MightyDanp on 3/5/2021.
 */
public class GenLootTables extends LootTableProvider {

    private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();

    public GenLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        tables.clear();

        IndustrialTech.configSync.material.getFirst().registryMap.values().forEach(material -> {

            for (IMaterialFlag flag : ((ITMaterial)material).materialFlags) {
                if (flag == DefaultMaterialFlag.ORE) {
                    for (Block blockRegistered : ((ITMaterial)material).oreList) {
                        standardDropTable(blockRegistered);
                    }
                }
                if (flag == DefaultMaterialFlag.GEM) {
                    for (Block blockRegistered : ((ITMaterial)material).oreList) {
                        standardDropTable(blockRegistered);
                    }
                }
                if (flag == DefaultMaterialFlag.ORE || flag == DefaultMaterialFlag.GEM) {
                    for (Block blockRegistered : ((ITMaterial)material).smallOreList) {
                        standardDropTable(blockRegistered);
                    }

                    int i = 0;
                    for (Block blockRegistered : ((ITMaterial)material).denseOreList) {
                        LootTable.Builder tableBuilder = LootTable.lootTable();
                        LootPool.Builder poolBuilder = LootPool.lootPool();

                        blockTable(blockRegistered, tableBuilder.withPool(poolBuilder.setRolls(ConstantValue.exactly(1))
                                .add(AlternativesEntry.alternatives().otherwise(LootItem.lootTableItem(blockRegistered)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)))))))
                                .add(LootItem.lootTableItem(((ITMaterial)material).oreList.get(i)))
                        ));
                        i++;
                    }
                }
            }
    });

        ModBlockLootTable.registerModBlockTables();
        return tables;
    }

    public static void standardDropTable(Block b) {
        blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
    }

    public static void blockTable(Block b, LootTable.Builder lootTable) {
        addTable(b.getLootTable(), lootTable, LootContextParamSets.BLOCK);
    }

    public static void addTable(ResourceLocation path, LootTable.Builder lootTable, LootContextParamSet paramSet) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    public static LootPool.Builder createStandardDrops(ItemLike itemProvider) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion()).add(LootItem.lootTableItem(itemProvider));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((loc, table) -> LootTables.validate(validationtracker, loc, table));
    }
}