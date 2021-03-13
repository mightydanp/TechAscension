package mightydanp.industrialtech.api.common.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import mcp.MethodsReturnNonnullByDefault;
import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.datagen.ModBlockLootTables;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
                    for(RegistryObject<Block> blockRegistered : material.blockOre) {
                        standardDropTable(blockRegistered.get());
                    }
                }
                if (flag == EnumMaterialFlags.BLOCK_GEM) {
                    for(RegistryObject<Block> blockRegistered : material.blockOre) {
                        standardDropTable(blockRegistered.get());
                    }
                }
                if (flag == EnumMaterialFlags.SMALL_ORE) {
                    for(RegistryObject<Block> blockRegistered : material.blockSmallOre) {
                        standardDropTable(blockRegistered.get());
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