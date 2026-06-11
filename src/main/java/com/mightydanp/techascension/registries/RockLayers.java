package com.mightydanp.techascension.registries;

import com.mightydanp.techcore.world.level.levelgen.feature.RockLayerFeature;
import net.minecraft.world.level.Level;

public class RockLayers {
    public static void init() {
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.andesite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.basalt);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.deepslate);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.diorite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.granite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.shale);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.slate);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.marble);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.komatiite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.limestone);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.quartzite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.blueschist);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.kimberlite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.greenschist);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.red_granite);
        RockLayerFeature.addAllowedMaterial(Level.OVERWORLD, Materials.black_granite);
    }
}
