package mightydanp.industrialtech.api.common.resources.data;

import mightydanp.industrialtech.api.common.resources.data.data.TagData;
import mightydanp.industrialtech.common.IndustrialTech;
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
    private static Map<String, TagData<Block>> blockTagDataMap = new HashMap<>();
    private static Map<String, TagData<Block>> blockMinableTagDataMap = new HashMap<>();
    private static Map<String, TagData<EntityType<?>>> entityTypeTagDataMap = new HashMap<>();
    private static Map<String, TagData<Fluid>> fluidTagDataMap = new HashMap<>();
    private static Map<String, TagData<GameEvent>> gameEventTagDataMap = new HashMap<>();
    private static Map<String, TagData<Item>> itemTagDataMap = new HashMap<>();
    private static Map<String, TagData<Biome>> hasStructureDataMap = new HashMap<>();
    private static Map<String, TagData<Biome>> biomeDataMap = new HashMap<>();
    private static Map<String, TagData<ConfiguredStructureFeature<?, ?>>> configuredStructureFeatureDataMap = new HashMap<>();

    public static void init() {
        blockTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/blocks" + (b.getParentFolder() == null ? "" : b.getParentFolder()) + "/" +  s + ".json"), b.createJson()));
        blockMinableTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/blocks/minable/" +  s + ".json"), b.createJson()));
        entityTypeTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/entity_type/" +  s + ".json"), b.createJson()));
        fluidTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/fluids/" +  s + ".json"), b.createJson()));
        gameEventTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/game_events/" +  s + ".json"), b.createJson()));
        itemTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/items" + (b.getParentFolder() == null ? "" : b.getParentFolder()) + "/" +  s + ".json"), b.createJson()));
        hasStructureDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/worldgen/biome/has_structure/" +  s + ".json"), b.createJson()));
        biomeDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/worldgen/biome/" +  s + ".json"), b.createJson()));
        configuredStructureFeatureDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(b.getVanilla() ? "" : "forge", "tags/worldgen/configured_structure_feature/" +  s + ".json"), b.createJson()));
    }

    public static TagData<Block> getBlockTagData(String name) {
        return blockTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<Block> getBlockMinableTagData(String name) {
        return blockMinableTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<EntityType<?>> getEntityTypeTagData(String name) {
        return entityTypeTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<Fluid> getFluidTagData(String name) {
        return fluidTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<GameEvent> getGameEventTagData(String name) {
        return gameEventTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<Item> getItemTagData(String name) {
        return itemTagDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<Biome> getHasStructureData(String name) {
        return hasStructureDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<Biome> getBiomeData(String name) {
        return biomeDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static TagData<ConfiguredStructureFeature<?, ?>> getConfiguredStructureFeatureData(String name) {
        return configuredStructureFeatureDataMap.getOrDefault(name, new TagData<>(name));
    }

    public static void saveBlockTagData(TagData<Block> data) {
        blockTagDataMap.put(data.getTagName(), data);
    }

    public static void saveBlockMinableTagData(TagData<Block> data) {
        blockMinableTagDataMap.put(data.getTagName(), data);
    }

    public static void saveEntityTypeTagData(TagData<EntityType<?>> data) {
        entityTypeTagDataMap.put(data.getTagName(), data);
    }

    public static void saveFluidTagData(TagData<Fluid> data) {
        fluidTagDataMap.put(data.getTagName(), data);
    }

    public static void saveGameEventTagData(TagData<GameEvent> data) {
        gameEventTagDataMap.put(data.getTagName(), data);
    }

    public static void saveItemTagData(TagData<Item> data) {
        itemTagDataMap.put(data.getTagName(), data);
    }

    public static void saveHasStructureData(TagData<Biome> data) {
        hasStructureDataMap.put(data.getTagName(), data);
    }

    public static void saveBiomeData(TagData<Biome> data) {
        biomeDataMap.put(data.getTagName(), data);
    }

    public static void saveConfiguredStructureFeatureData(TagData<ConfiguredStructureFeature<?, ?>> data) {
        configuredStructureFeatureDataMap.put(data.getTagName(), data);
    }
}