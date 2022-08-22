package mightydanp.techapi.common.resources.data;

import mightydanp.techapi.common.resources.data.data.LootTableData;
import mightydanp.techapi.common.resources.data.data.ShapelessRecipeData;
import mightydanp.techapi.common.resources.data.data.TagData;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public class DataPackRegistry {
    private static final Map<ResourceLocation, TagData<Block>> blockTagDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<EntityType<?>>> entityTypeTagDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<Fluid>> fluidTagDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<GameEvent>> gameEventTagDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<Item>> itemTagDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<Biome>> biomeDataMap = new HashMap<>();
    private static final Map<ResourceLocation, TagData<ConfiguredStructureFeature<?, ?>>> configuredStructureFeatureDataMap = new HashMap<>();
    private static final Map<ResourceLocation, LootTableData> blockLootTableDataMap = new HashMap<>();
    private static final Map<ResourceLocation, LootTableData> chestLootTableDataMap = new HashMap<>();
    private static final Map<ResourceLocation, LootTableData> entityLootTableDataMap = new HashMap<>();
    private static final Map<ResourceLocation, LootTableData> gameplayLootTableDataMap = new HashMap<>();
    private static final Map<ResourceLocation, ShapelessRecipeData> shapelessRecipeDataMap = new HashMap<>();

    public static void init() {
        int c;
        blockTagDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/blocks/" +  s.getPath() + ".json"), b.createJson()));
        entityTypeTagDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/entity_type/" +  s.getPath() + ".json"), b.createJson()));
        fluidTagDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/fluids/" +  s.getPath() + ".json"), b.createJson()));
        gameEventTagDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/game_events/" +  s.getPath() + ".json"), b.createJson()));
        itemTagDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/items/" + s.getPath() + ".json"), b.createJson()));
        biomeDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/worldgen/biome/" +  s.getPath() + ".json"), b.createJson()));
        configuredStructureFeatureDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/worldgen/configured_structure_feature/" +  s.getPath() + ".json"), b.createJson()));
        blockLootTableDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "loot_tables/blocks/" +  s.getPath() + ".json"), b.createJson()));
        chestLootTableDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "loot_tables/chests/" +  s.getPath() + ".json"), b.createJson()));
        entityLootTableDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "loot_tables/entities/" +  s.getPath() + ".json"), b.createJson()));
        gameplayLootTableDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "loot_tables/gameplay/" +  s.getPath() + ".json"), b.createJson()));
        shapelessRecipeDataMap.forEach((s, b) -> TechAscension.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "recipes" +  s.getPath() + ".json"), b.createJson()));
        int i;
    }

    public static TagData<Block> getBlockTagData(ResourceLocation name) {
        return blockTagDataMap.getOrDefault(name, new TagData<>(name, Registry.BLOCK_REGISTRY, Registry.BLOCK).replace(false));
    }

    public static TagData<EntityType<?>> getEntityTypeTagData(ResourceLocation name) {
        return entityTypeTagDataMap.getOrDefault(name, new TagData<>(name, Registry.ENTITY_TYPE_REGISTRY, Registry.ENTITY_TYPE).replace(false));
    }

    public static TagData<Fluid> getFluidTagData(ResourceLocation name) {
        return fluidTagDataMap.getOrDefault(name, new TagData<>(name, Registry.FLUID_REGISTRY, Registry.FLUID).replace(false));
    }

    public static TagData<GameEvent> getGameEventTagData(ResourceLocation name) {
        return gameEventTagDataMap.getOrDefault(name, new TagData<>(name, Registry.GAME_EVENT_REGISTRY, Registry.GAME_EVENT).replace(false));
    }

    public static TagData<Item> getItemTagData(ResourceLocation name) {
        return itemTagDataMap.getOrDefault(name, new TagData<>(name, Registry.ITEM_REGISTRY, Registry.ITEM).replace(false));
    }

    public static TagData<Biome> getBiomeData(ResourceLocation name) {
        return biomeDataMap.getOrDefault(name, new TagData<>(name, Registry.BIOME_REGISTRY, BuiltinRegistries.BIOME).replace(false));
    }

    public static TagData<ConfiguredStructureFeature<?, ?>> getConfiguredStructureFeatureData(ResourceLocation name) {
        return configuredStructureFeatureDataMap.getOrDefault(name, new TagData<>(name, Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE));
    }

    public static LootTableData getBlockLootTableData(ResourceLocation name) {
        return blockLootTableDataMap.getOrDefault(name, new LootTableData(name));
    }

    public static LootTableData getChestLootTableData(ResourceLocation name) {
        return chestLootTableDataMap.getOrDefault(name, new LootTableData(name));
    }

    public static LootTableData getEntityLootTableData(ResourceLocation name) {
        return entityLootTableDataMap.getOrDefault(name, new LootTableData(name));
    }

    public static LootTableData getGameplayLootTableData(ResourceLocation name) {
        return gameplayLootTableDataMap.getOrDefault(name, new LootTableData(name));
    }

    public static ShapelessRecipeData shapelessRecipeData(ResourceLocation name) {
        return shapelessRecipeDataMap.getOrDefault(name, new ShapelessRecipeData(name));
    }

    public static void saveBlockTagData(TagData<Block> data) {
        blockTagDataMap.put(data.getName(), data);
    }

    public static void saveEntityTypeTagData(TagData<EntityType<?>> data) {
        entityTypeTagDataMap.put(data.getName(), data);
    }

    public static void saveFluidTagData(TagData<Fluid> data) {
        fluidTagDataMap.put(data.getName(), data);
    }

    public static void saveGameEventTagData(TagData<GameEvent> data) {
        gameEventTagDataMap.put(data.getName(), data);
    }

    public static void saveItemTagData(TagData<Item> data) {
        itemTagDataMap.put(data.getName(), data);
    }

    public static void saveBiomeData(TagData<Biome> data) {
        biomeDataMap.put(data.getName(), data);
    }

    public static void saveConfiguredStructureFeatureData(TagData<ConfiguredStructureFeature<?, ?>> data) {
        configuredStructureFeatureDataMap.put(data.getName(), data);
    }

    public static void saveBlockLootTableDataMap(LootTableData data) {
        blockLootTableDataMap.put(data.getName(), data);
    }

    public static void saveChestLootTableDataMap(LootTableData data) {
        chestLootTableDataMap.put(data.getName(), data);
    }

    public static void saveEntityLootTableDataMap(LootTableData data) {
        entityLootTableDataMap.put(data.getName(), data);
    }

    public static void saveGameplayLootTableDataMap(LootTableData data) {
        gameplayLootTableDataMap.put(data.getName(), data);
    }

    public static void saveShapelessRecipeData(ShapelessRecipeData data) {
        shapelessRecipeDataMap.put(data.getName(), data);
    }
}