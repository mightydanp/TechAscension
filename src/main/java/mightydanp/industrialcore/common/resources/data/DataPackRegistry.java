package mightydanp.industrialcore.common.resources.data;

import mightydanp.industrialcore.common.resources.data.data.TagData;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class DataPackRegistry {
    private static Map<ResourceLocation, TagData<Block>> blockTagDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<EntityType<?>>> entityTypeTagDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<Fluid>> fluidTagDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<GameEvent>> gameEventTagDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<Item>> itemTagDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<Biome>> biomeDataMap = new HashMap<>();
    private static Map<ResourceLocation, TagData<ConfiguredStructureFeature<?, ?>>> configuredStructureFeatureDataMap = new HashMap<>();

    public static void init() {
        blockTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/blocks/" +  s.getPath() + ".json"), b.createJson()));
        entityTypeTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/entity_type/" +  s.getPath() + ".json"), b.createJson()));
        fluidTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/fluids/" +  s.getPath() + ".json"), b.createJson()));
        gameEventTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/game_events/" +  s.getPath() + ".json"), b.createJson()));
        itemTagDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/items/" + s.getPath() + ".json"), b.createJson()));
        biomeDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/worldgen/biome/" +  s.getPath() + ".json"), b.createJson()));
        configuredStructureFeatureDataMap.forEach((s, b) -> IndustrialTech.dataHolder.addToResources(new ResourceLocation(s.getNamespace(), "tags/worldgen/configured_structure_feature/" +  s.getPath() + ".json"), b.createJson()));
    }

    public static TagData<Block> getBlockTagData(ResourceLocation name) {
        return blockTagDataMap.getOrDefault(name, new TagData<>(name, Registry.BLOCK_REGISTRY));
    }

    public static TagData<EntityType<?>> getEntityTypeTagData(ResourceLocation name) {
        return entityTypeTagDataMap.getOrDefault(name, new TagData<>(name, Registry.ENTITY_TYPE_REGISTRY));
    }

    public static TagData<Fluid> getFluidTagData(ResourceLocation name) {
        return fluidTagDataMap.getOrDefault(name, new TagData<>(name, Registry.FLUID_REGISTRY));
    }

    public static TagData<GameEvent> getGameEventTagData(ResourceLocation name) {
        return gameEventTagDataMap.getOrDefault(name, new TagData<>(name, Registry.GAME_EVENT_REGISTRY));
    }

    public static TagData<Item> getItemTagData(ResourceLocation name) {
        return itemTagDataMap.getOrDefault(name, new TagData<>(name, Registry.ITEM_REGISTRY));
    }

    public static TagData<Biome> getBiomeData(ResourceLocation name) {
        return biomeDataMap.getOrDefault(name, new TagData<>(name, Registry.BIOME_REGISTRY));
    }

    public static TagData<ConfiguredStructureFeature<?, ?>> getConfiguredStructureFeatureData(ResourceLocation name) {
        return configuredStructureFeatureDataMap.getOrDefault(name, new TagData<>(name, Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY));
    }

    public static void saveBlockTagData(TagData<Block> data) {
        blockTagDataMap.put(data.getTagName(), data);
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

    public static void saveBiomeData(TagData<Biome> data) {
        biomeDataMap.put(data.getTagName(), data);
    }

    public static void saveConfiguredStructureFeatureData(TagData<ConfiguredStructureFeature<?, ?>> data) {
        configuredStructureFeatureDataMap.put(data.getTagName(), data);
    }
}