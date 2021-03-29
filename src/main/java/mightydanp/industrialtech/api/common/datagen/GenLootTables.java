package mightydanp.industrialtech.api.common.datagen;

import com.mojang.datafixers.util.Pair;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.common.datagen.ModBlockLootTables;
import mightydanp.industrialtech.common.materials.ModMaterials;
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
import net.minecraftforge.fml.RegistryObject;

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

        for (MaterialHandler material : ModMaterials.materials) {
            for (EnumMaterialFlags flag : material.flags) {
                if (flag == EnumMaterialFlags.ORE) {
                    for(RegistryObject<Block> blockRegistered : material.oreBlock) {
                        standardDropTable(blockRegistered.get());
                    }
                }
                if (flag == EnumMaterialFlags.GEM) {
                    for(RegistryObject<Block> blockRegistered : material.oreBlock) {
                        standardDropTable(blockRegistered.get());
                    }
                }
                if (flag == EnumMaterialFlags.ORE || flag == EnumMaterialFlags.GEM) {
                    for(RegistryObject<Block> blockRegistered : material.smallOreBlock) {
                        standardDropTable(blockRegistered.get());
                    }

                    int i = 0;
                    for(RegistryObject<Block> blockRegistered : material.denseOreBlock) {
                        LootTable.Builder tableBuilder = LootTable.builder();
                        LootPool.Builder poolBuilder = LootPool.builder();

                        blockTable(blockRegistered.get(), tableBuilder.addLootPool(poolBuilder.rolls(ConstantRange.of(1))
                                        .addEntry(AlternativesLootEntry.builder().alternatively(ItemLootEntry.builder(blockRegistered.get())
                                                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate((Enchantments.SILK_TOUCH), MinMaxBounds.IntBound.atLeast(1)))))))
                                        .addEntry(ItemLootEntry.builder(material.oreBlock.get(i).get()))
                        ));
                        i++;
                    }
                }
            }
        }

        ModBlockLootTables.registerModBlockTables();
        return tables;
    }

    public static void standardDropTable(Block b) {
        blockTable(b, LootTable.builder().addLootPool(createStandardDrops(b)));
    }

    public static void blockTable(Block b, LootTable.Builder lootTable) {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    public static void addTable(ResourceLocation path, LootTable.Builder lootTable, LootParameterSet paramSet) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    public static LootPool.Builder createStandardDrops(IItemProvider itemProvider) {
        return LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SurvivesExplosion.builder()).addEntry(ItemLootEntry.builder(itemProvider));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }
}