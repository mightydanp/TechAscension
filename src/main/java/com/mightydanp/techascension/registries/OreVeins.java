package com.mightydanp.techascension.registries;

import com.mightydanp.techascension.client.ref.ModRef;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDimensionGenerationSettings;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinition;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinDefinitions;
import com.mightydanp.techcore.world.level.levelgen.vein.OreVeinOverlapSettings;
import com.mightydanp.techcore.world.level.levelgen.vein.VeinOreEntry;
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

        OreVeinDefinitions.registerGenerationSettings(new OreVeinDimensionGenerationSettings(
                Level.OVERWORLD,
                1000
        ));
        OreVeinDefinitions.registerOverlapSettings(
                Level.OVERWORLD,
                new OreVeinOverlapSettings(512, 1024)
        );

        OreVeinDefinitions.register(
                OreVeinDefinition.builder(ResourceLocation.fromNamespaceAndPath(ModRef.MOD_ID, "copper_vein"))
                .dimensions(Level.OVERWORLD)
                .generationWeight(80)
                .centerY(12, 27)
                .sizeX(17, 63)
                .sizeY(7, 7)
                .sizeZ(17, 63)
                .sparseReachBlocks(32)
                .ores(
                        new VeinOreEntry(Materials.chalcopyrite, 8),
                        new VeinOreEntry(Materials.hematite, 4),
                        new VeinOreEntry(Materials.pyrite, 2),
                        new VeinOreEntry(Materials.copper, 1)
                )
                .build()
        );
    }
}
