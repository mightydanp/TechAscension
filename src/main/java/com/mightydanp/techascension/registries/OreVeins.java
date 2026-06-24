package com.mightydanp.techascension.registries;

import com.mightydanp.techascension.client.ref.ModRef;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinition;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinitions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class OreVeins {
    private static boolean initialized;

    private OreVeins() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;

        OreVeinDefinitions.registerGenerationSettings(new OreVeinDefinitions.DimensionGenerationSettings(
                Level.OVERWORLD,
                1000
        ));
        OreVeinDefinitions.registerOverlapSettings(
                Level.OVERWORLD,
                new OreVeinDefinitions.OverlapSettings(512, 1024)
        );

        OreVeinDefinitions.register(
                OreVeinDefinition.builder(ResourceLocation.fromNamespaceAndPath(ModRef.MOD_ID, "copper_vein"))
                        .dimensions(Level.OVERWORLD)
                        .generationWeight(80)
                        .centerY(12, 27)
                        .maxSizeX(64)
                        .sizeY(8, 8)
                        .maxSizeZ(64)
                    .ores(
                            new OreVeinDefinition.OreEntry(Materials.chalcopyrite, 8),
                            new OreVeinDefinition.OreEntry(Materials.hematite, 4),
                            new OreVeinDefinition.OreEntry(Materials.pyrite, 2),
                            new OreVeinDefinition.OreEntry(Materials.copper, 1))
                        .build()
        );
    }
}
