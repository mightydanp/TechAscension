package com.mightydanp.techascension.registries;

import com.mightydanp.techascension.client.ref.ModRef;
import com.mightydanp.techcore.registries.VeinFeaturesRegistries;
import com.mightydanp.techcore.world.level.levelgen.vein.ConfiguredVeinFeature;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinition;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinitions;
import com.mightydanp.techcore.world.level.levelgen.vein.densenode.DenseNodeVeinFeature;
import com.mightydanp.techcore.world.level.levelgen.vein.sparsehalo.SparseHaloVeinFeature;
import com.mightydanp.techcore.world.level.levelgen.vein.sparsetransition.SparseTransitionVeinFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class OreVeins {
    private static boolean initialized;

    public static void init() {
        if (initialized) return;
        initialized = true;

        OreVeinDefinitions.registerOverlapSettings(
                Level.OVERWORLD,
                new OreVeinDefinitions.OverlapSettings(512, 1024)
        );

        OreVeinDefinitions.register(
                OreVeinDefinition.builder(ResourceLocation.fromNamespaceAndPath(ModRef.MOD_ID, "copper_vein"))
                        .dimensions(Level.OVERWORLD)
                        .generationWeight(80)
                        .centerY(12, 27)
                        .sizeY(8, 8)
                        .addVeinFeature(0, new ConfiguredVeinFeature(VeinFeaturesRegistries.DENSE_NODE.getId(), DenseNodeVeinFeature.defaultConfig()))
                        .addVeinFeature(1, new ConfiguredVeinFeature(VeinFeaturesRegistries.SPARSE_TRANSITION.getId(), SparseTransitionVeinFeature.defaultConfig()))
                        .addVeinFeature(2, new ConfiguredVeinFeature(VeinFeaturesRegistries.SPARSE_HALO.getId(), SparseHaloVeinFeature.defaultConfig()))
                    .ores(
                            new OreVeinDefinition.OreEntry(Materials.chalcopyrite, 8),
                            new OreVeinDefinition.OreEntry(Materials.hematite, 4),
                            new OreVeinDefinition.OreEntry(Materials.pyrite, 2),
                            new OreVeinDefinition.OreEntry(Materials.copper, 1))
                        .build()
        );
    }
}
